package com.senpure.javafx;

import com.senpure.base.util.Spring;
import javafx.application.HostServices;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.BeansException;
import org.springframework.core.io.ResourceLoader;

import java.awt.*;
import java.util.ResourceBundle;

/**
 * Javafx
 *
 * @author senpure
 * @time 2020-06-18 16:32:07
 */
public class Javafx {

    private static Stage primaryStage;

    private static SystemTray systemTray;

    private static HostServices hostServices;

    private static JavafxProperties javafxProperties;


    private static ResourceLoader resourceLoader;

    // private static final Logger logger = LoggerFactory.getLogger(Javafx.class);

    public static void showView(Class<? extends JavafxView> view) {

        showView(getView(view));

    }

    public static void showView(JavafxView view) {

        if (primaryStage.getScene() == null) {
            String title = view.getOriginalTitle();
            if (title.length() == 0) {
                title = javafxProperties.getTitle();
            }
            if (title.startsWith("%")) {
                ResourceBundle resourceBundle = view.getResourceBundle();
                title = resourceBundle.getString(title.substring(1));
            }
            primaryStage.setTitle(title);
            Scene scene = new Scene(view.getView());

            primaryStage.setScene(scene);
        } else {
            primaryStage.getScene().setRoot(view.getView());
        }
        primaryStage.show();
    }

    public static void showView(Stage stage, JavafxView view) {

        if (stage.getScene() == null) {
            if (stage.getTitle() == null) {
                String title = view.getTitle();
                if (title.startsWith("%")) {
                    ResourceBundle resourceBundle = view.getResourceBundle();
                    title = resourceBundle.getString(title.substring(1));
                }
                stage.setTitle(title);
            }
            Scene scene = new Scene(view.getView());

            stage.setScene(scene);
        } else {
            stage.getScene().setRoot(view.getView());
        }
        stage.showAndWait();
    }

    public static JavafxView getView(Class<? extends JavafxView> view) {
        JavafxView javafxView;
        try {
            javafxView = Spring.getBean(view);
        } catch (BeansException e) {
            Spring.regBean(view);
            javafxView = Spring.getBean(view);
        }
        return javafxView;

    }


    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        Javafx.primaryStage = primaryStage;
    }

    public static SystemTray getSystemTray() {
        return systemTray;
    }

    public static void setSystemTray(SystemTray systemTray) {
        Javafx.systemTray = systemTray;
    }

    public static HostServices getHostServices() {
        return hostServices;
    }

    public static void setHostServices(HostServices hostServices) {
        Javafx.hostServices = hostServices;
    }


    public static JavafxProperties getJavafxProperties() {
        return javafxProperties;
    }

    public static void setJavafxProperties(JavafxProperties javafxProperties) {
        Javafx.javafxProperties = javafxProperties;
    }

    public static ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public static void setResourceLoader(ResourceLoader resourceLoader) {
        Javafx.resourceLoader = resourceLoader;
    }
}
