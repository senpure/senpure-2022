package com.senpure.javafx.jfoenix;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JfoenixProperties
 *
 * @author senpure
 * @time 2020-07-09 18:23:24
 */
@ConfigurationProperties(prefix = "javafx.jfoenix")
public class JfoenixProperties {


    private Decorator decorator = new Decorator();

    public Decorator getDecorator() {
        return decorator;
    }

    public void setDecorator(Decorator decorator) {
        this.decorator = decorator;
    }

    public static class Decorator {

        /**
         * 启用JFXDecorator 装饰
         */
        private boolean enable = true;
        /**
         * 使用stage的图标
         */
        private boolean stageIcon = true;

        private String svgPathContent = "M512 682c-128.024-99.309-255.626-199.040-384-298l384-298 384 298c-128.375 98.958-255.974 198.693-384 298zM512 792l314-246 70 54-384 298-384-298 70-54z";


        public boolean isEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        public boolean isStageIcon() {
            return stageIcon;
        }

        public void setStageIcon(boolean stageIcon) {
            this.stageIcon = stageIcon;
        }

        public String getSvgPathContent() {
            return svgPathContent;
        }

        public void setSvgPathContent(String svgPathContent) {
            this.svgPathContent = svgPathContent;
        }

    }
}
