package com.senpure.javafx;

import com.senpure.base.util.Spring;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceResourceBundle;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URL;

/**
 * AbstractFxmlView
 *
 * @author senpure
 * @time 2020-06-18 16:09:54
 */

public class JavafxView {


    private static final CachingMetadataReaderFactory readerFactory = new CachingMetadataReaderFactory();

    private MergedAnnotation<View> annotation;
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private void checkAnnotation() {
        if (annotation != null) {
            return;
        }
        try {
            AnnotationMetadata annotationMetadata = readerFactory.getMetadataReader(getClass().getName())
                    .getAnnotationMetadata();
            annotation = annotationMetadata.getAnnotations().get(View.class);
            if (!annotation.isPresent()) {

                annotation = readerFactory.getMetadataReader(EmptyView.class.getName())
                        .getAnnotationMetadata()
                        .getAnnotations().get(View.class);
            }

        } catch (IOException e) {
            //不应该出现该错误
            logger.error("", e);
        }


    }


    public Parent getView() {
        checkAnnotation();
        String fxml = annotation.getString("fxml");
        String name;
        if (fxml.length() == 0) {
            Class<?> clazz = getClass();
            name = clazz.getSimpleName();
            if (name.endsWith("View")) {
                name = name.substring(0, name.length() - 4);
            }
            name += ".fxml";
        } else {
            name = fxml;
            if (!name.contains(".")) {
                name += ".fxml";
            }
        }
        String baseName = "";

        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        if (StringUtils.hasText(baseName)) {
            messageSource.setBasenames(StringUtils
                    .commaDelimitedListToStringArray(StringUtils.trimAllWhitespace(baseName)));
        }

        JavafxProperties javafxProperties = Javafx.getJavafxProperties();
        messageSource.setDefaultEncoding(javafxProperties.getEncoding().name());


        messageSource.setDefaultLocale(javafxProperties.getLocale());
        messageSource.setFallbackToSystemLocale(true);
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setAlwaysUseMessageFormat(false);


        MessageSourceResourceBundle resourceBundle = new MessageSourceResourceBundle(messageSource,
                javafxProperties.getLocale());

        logger.debug("{} fxml {}", getClass().getName(), name);
        URL url = getClass().getResource(name);
        logger.debug("url {}", url);
        FXMLLoader fxmlLoader = new FXMLLoader(url, resourceBundle);

        fxmlLoader.setControllerFactory(Spring::getBean);


        // Spring.getBean()

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            logger.error("");
        }

        return fxmlLoader.getRoot();
    }


    @View
    private static class EmptyView {
    }

    public static void main(String[] args) {

        JavafxView javafxView = new JavafxView();
        javafxView.getView();
    }
}
