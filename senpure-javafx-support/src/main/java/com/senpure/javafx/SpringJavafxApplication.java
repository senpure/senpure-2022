package com.senpure.javafx;

import com.senpure.base.AppEvn;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
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
    private CompletableFuture<Runnable> startFuture = new CompletableFuture<>();

    private SplashStage splashStage;
    @Value("javafx.icons")
    private List<String> iconNames;

    public static void launch(Class<? extends Application> primarySource, Class<? extends JavafxView> primaryView, String[] args) {
        AppEvn.markClassRootPath(primarySource);
        AppEvn.installAnsiConsole(primarySource);
        SpringJavafxApplication.args = args;
        SpringJavafxApplication.primaryView = primaryView;
        SpringJavafxApplication.primarySource = primarySource;
        launch(primarySource, args);
    }

    @Override
    public void init() {
        defaultIcons.addAll(loadDefaultIcons());
        Executor executor = Executors.newSingleThreadExecutor(r -> {
            Thread thread = new Thread(r, "single-temp-executor");
            thread.setDaemon(true);
            return thread;
        });

        CompletableFuture.supplyAsync(() ->
                SpringApplication.run(primarySource, args), executor)
                .whenComplete((configurableApplicationContext, throwable) -> {
                    if (throwable != null) {
                        logger.error("springboot 启动失败");
                        Platform.runLater(() -> showErrorAlert(throwable));
                    } else {
                        Platform.runLater(() -> {

                            // showPrimaryStage();
                        });
                    }
                }).thenAcceptBoth(startFuture, (configurableApplicationContext, runnable) -> {

            Platform.runLater(runnable);
        })
        ;


    }

    protected void showPrimaryStage() {
        loadIcons();
        Javafx.showView(primaryView);
        splashStage.close();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //  Class<?> primarySource
        Javafx.setPrimaryStage(primaryStage);
        Javafx.setHostServices(getHostServices());
        if (SystemTray.isSupported()) {
            Javafx.setSystemTray(SystemTray.getSystemTray());
        }
        splashStage = new SplashStage();
        splashStage.show();

        startFuture.complete(this::showPrimaryStage);
    }

    private Collection<Image> loadDefaultIcons() {
        return Arrays.asList(new Image(getClass().getResource("/icons/gear_16x16.png").toExternalForm()),
                new Image(getClass().getResource("/icons/gear_24x24.png").toExternalForm()),
                new Image(getClass().getResource("/icons/gear_36x36.png").toExternalForm()),
                new Image(getClass().getResource("/icons/gear_42x42.png").toExternalForm()),
                new Image(getClass().getResource("/icons/gear_64x64.png").toExternalForm()));
    }

    protected void loadIcons() {
        if (iconNames == null || iconNames.size() == 0) {
            icons.addAll(defaultIcons);
        } else {
            for (String iconName : iconNames) {
                Image img = new Image(getClass().getResource(iconName).toExternalForm());
                icons.add(img);
            }
        }
    }

    private static void showErrorAlert(Throwable throwable) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "出现错误，程序关闭\n" +
                "Error: " + throwable.getMessage());
        alert.showAndWait().ifPresent(response -> Platform.exit());
    }


    public static void main(String[] args) {

        CompletableFuture.supplyAsync(() -> {
            return "88";
        });
    }

}
