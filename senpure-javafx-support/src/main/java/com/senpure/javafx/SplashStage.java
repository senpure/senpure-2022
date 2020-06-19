package com.senpure.javafx;

import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * SplashStage
 *
 * @author senpure
 * @time 2020-06-19 16:30:35
 */
public class SplashStage {
    private static String DEFAULT_IMAGE = "/splash/javafx.png";
    private Stage stage ;
    private Stage parentStage;

    public void show() {
        final ImageView imageView = new ImageView(getClass().getResource(getImagePath()).toExternalForm());
        final ProgressBar splashProgressBar = new ProgressBar();
        splashProgressBar.setPrefWidth(imageView.getImage().getWidth());

        final VBox vbox = new VBox();
        vbox.getChildren().addAll(imageView, splashProgressBar);
        parentStage = new Stage(StageStyle.UTILITY);
        parentStage.setOpacity(0);
        stage = new Stage(StageStyle.TRANSPARENT);

        stage.setScene(new Scene(vbox));
        stage.initOwner(parentStage);
        parentStage.show();
        stage.show();
    }

    public void close() {
        parentStage.hide();
        stage.hide();
        parentStage.close();
        stage.close();
    }


    public boolean visible() {
        return true;
    }


    public String getImagePath() {
        return DEFAULT_IMAGE;
    }
}
