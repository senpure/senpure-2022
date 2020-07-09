package com.senpure.javafx.jfoenix;

import com.jfoenix.assets.JFoenixResources;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.svg.SVGGlyph;
import com.senpure.javafx.Javafx;
import com.senpure.javafx.SceneFactory;
import com.senpure.javafx.autoconfigure.JavafxAutoConfiguration;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * JfoenixAutoConfiguration
 *
 * @author senpure
 * @time 2020-07-09 18:33:37
 */
@ConditionalOnClass(JFXDecorator.class)
@AutoConfigureAfter(JavafxAutoConfiguration.class)
@EnableConfigurationProperties(JfoenixProperties.class)
public class JfoenixAutoConfiguration {

    private  JfoenixProperties jfoenixProperties;

    public JfoenixAutoConfiguration(JfoenixProperties jfoenixProperties) {
        this.jfoenixProperties = jfoenixProperties;
        Jfoenix.setJfoenixProperties(jfoenixProperties);
        JfoenixProperties.Decorator decorator=jfoenixProperties.getDecorator();
        if (decorator.isEnable()) {
            Javafx.setSceneFactory(new SceneFactory() {
                @Override
                public Scene get(Parent parent) {
                    JFXDecorator jfxDecorator = new JFXDecorator(Javafx.getPrimaryStage(), parent);
                    Scene scene = new Scene(jfxDecorator);
                    final ObservableList<String> stylesheets = scene.getStylesheets();
                    stylesheets.addAll(JFoenixResources.load("css/jfoenix-fonts.css").toExternalForm(),
                            JFoenixResources.load("css/jfoenix-design.css").toExternalForm());
                    if (decorator.isStageIcon()) {

                    }
                    else {
                       SVGGlyph  svgGlyph= new  SVGGlyph (decorator.getSvgPathContent());
                        jfxDecorator.setGraphic(svgGlyph);
                    }

                    return scene;

                }
            });

        }
    }



}
