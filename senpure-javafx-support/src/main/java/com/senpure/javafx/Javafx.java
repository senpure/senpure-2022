package com.senpure.javafx;

import com.senpure.base.util.Spring;
import javafx.application.HostServices;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

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

    public static void showView(Class<? extends JavafxView> view) {
        JavafxView javafxView = Spring.getBean(view);
        showView(javafxView);

    }

    public static void showView(JavafxView view) {

        if (primaryStage.getScene() == null) {
            Scene scene = new Scene(view.getView());
            primaryStage.setScene(scene);
        } else {
            primaryStage.getScene().setRoot(view.getView());
        }
        primaryStage.show();
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
}
