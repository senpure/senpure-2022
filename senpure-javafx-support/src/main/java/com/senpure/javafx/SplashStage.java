package com.senpure.javafx;

import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * SplashStage
 *
 * @author senpure
 * @time 2020-06-19 16:30:35
 */
public class SplashStage {
    private static final String DEFAULT_IMAGE = "/splash/javafx.png";
    private static final String SPLASH_IMAGE = "/splash";
    private Stage stage;
    private Stage parentStage;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void show() {
        final ImageView imageView = new ImageView(getClass().getResource(getImagePath()).toExternalForm());


        ProgressIndicator indicator = new ProgressIndicator();
        indicator.setPrefSize(22, 22);
        indicator.setLayoutX(imageView.getImage().getWidth() - 22);
        indicator.setLayoutY(imageView.getImage().getHeight() - 22);
        indicator.setStyle("-fx-progress-color:#ffffff");
        Pane pane = new Pane();
        pane.setPrefSize(imageView.getImage().getWidth(), imageView.getImage().getHeight());
        pane.getChildren().addAll(imageView, indicator);
        //final VBox vbox = new VBox();
        //  vbox.getChildren().addAll(imageView, indicator);
        parentStage = new Stage(StageStyle.UTILITY);
        parentStage.setOpacity(0);
        stage = new Stage(StageStyle.TRANSPARENT);
        stage.setScene(new Scene(pane));
        stage.initOwner(parentStage);
        parentStage.setIconified(true);
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
        List<String> suffixList = Arrays.asList(".png", ".jpg");
        for (String suffix : suffixList) {
            String imagePath = SPLASH_IMAGE + suffix;
            URL url = getClass().getResource(imagePath);
            if (url != null) {
                logger.debug("splash imagePath {}", imagePath);
                return imagePath;
            }
        }
        return DEFAULT_IMAGE;
    }


}
