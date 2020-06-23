package com.senpure.javafx;

import com.senpure.base.AppEvn;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.core.io.Resource;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Application
 *
 * @author senpure
 * @time 2020-06-18 15:52:31
 */
public class SpringJavafxApplication extends Application {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static String[] args;
    private static Class<? extends JavafxView> primaryView;
    private static Class<?> primarySource;
    private static final List<Image> icons = new ArrayList<>();
    private final List<Image> defaultIcons = new ArrayList<>();
    private final CompletableFuture<Runnable> startFuture = new CompletableFuture<>();

    private static SplashStage splashStage;


    public static void launch(Class<? extends Application> primarySource, Class<? extends JavafxView> primaryView, String[] args) {
        launch(primarySource, primaryView, new SplashStage(), args);
    }


    public static void launch(Class<? extends Application> primarySource, Class<? extends JavafxView> primaryView, SplashStage splashStage, String[] args) {
        AppEvn.markClassRootPath(primarySource);
        AppEvn.installAnsiConsole(primarySource);
        SpringJavafxApplication.args = args;
        SpringJavafxApplication.primaryView = primaryView;
        SpringJavafxApplication.primarySource = primarySource;
        SpringJavafxApplication.splashStage = splashStage;
        launch(primarySource, args);
    }

    @Override
    public void init() {
        defaultIcons.addAll(loadDefaultIcons());
        ExecutorService executor = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread thread = new Thread(r, "single-temp-executor");
            thread.setDaemon(true);
            return thread;
        });

        CompletableFuture.supplyAsync(() ->
                SpringApplication.run(primarySource, args), executor)
                .whenComplete((configurableApplicationContext, throwable) -> {
                    if (throwable != null) {
                        logger.error("springboot 启动失败", throwable);
                        Platform.runLater(() -> showErrorAlert(throwable));
                    } else {
                        Platform.runLater(() -> Thread.currentThread().setUncaughtExceptionHandler(javaFxUncaughtExceptionHandler()));
                    }
                }).thenAcceptBoth(startFuture, (configurableApplicationContext, runnable) -> {

            Platform.runLater(runnable);
            executor.shutdown();
        })
        ;


    }

    protected void showPrimaryStage() {
        try {
            loadIcons();
            Javafx.getPrimaryStage().getIcons().addAll(icons);
            Javafx.showView(primaryView);
        } finally {
            if (splashStage != null && splashStage.visible()) {
                splashStage.close();
            }
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        //  Class<?> primarySource
        Javafx.setPrimaryStage(primaryStage);
        Javafx.setHostServices(getHostServices());
        if (SystemTray.isSupported()) {
            Javafx.setSystemTray(SystemTray.getSystemTray());
        }

        if (splashStage != null && splashStage.visible()) {
            splashStage.show();
        }

        startFuture.complete(this::showPrimaryStage);
    }

    protected Thread.UncaughtExceptionHandler javaFxUncaughtExceptionHandler() {

        return (t, e) -> {
            e.printStackTrace();
            logger.error("程序出现错误", e);
            Alert alert = new Alert(Alert.AlertType.ERROR, "程序出现错误\n" +
                    "Error: " + e);
            alert.showAndWait();
        };
    }

    private Collection<Image> loadDefaultIcons() {
        return Arrays.asList(new Image(getClass().getResource("/icons/gear_16x16.png").toExternalForm()),
                new Image(getClass().getResource("/icons/gear_24x24.png").toExternalForm()),
                new Image(getClass().getResource("/icons/gear_36x36.png").toExternalForm()),
                new Image(getClass().getResource("/icons/gear_42x42.png").toExternalForm()),
                new Image(getClass().getResource("/icons/gear_64x64.png").toExternalForm()));
    }

    protected void loadIcons() {
        List<String> iconNames = Javafx.getJavafxProperties().getIcons();
        if (Javafx.getJavafxProperties().getIcons() == null || iconNames.size() == 0) {
            icons.addAll(defaultIcons);
        } else {
            for (String iconName : iconNames) {
                Resource resource = Javafx.getResourceLoader().getResource(iconName);
                if (resource.exists()) {
                    try {
                        Image img = new Image(resource.getURL().toExternalForm());
                        //  Image img = new Image(getClass().getResource(iconName).toExternalForm());
                        icons.add(img);
                    } catch (IOException e) {
                        logger.error("", e);
                    }
                }

            }
        }
    }

    private static void showErrorAlert(Throwable throwable) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "出现错误，程序关闭\n" +
                "Error: " + throwable);
        alert.showAndWait().ifPresent(response -> Platform.exit());
    }

    public static void main(String[] args) {

        CompletableFuture.supplyAsync(() -> {
            return "88";
        });
    }

}
