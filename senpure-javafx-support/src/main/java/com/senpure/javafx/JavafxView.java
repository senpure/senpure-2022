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
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * AbstractFxmlView
 *
 * @author senpure
 * @time 2020-06-18 16:09:54
 */

public class JavafxView {


    private static final CachingMetadataReaderFactory readerFactory = new CachingMetadataReaderFactory();

    private static List<String> globalCss;
    private static List<String> globalBaseNames;

    private static ResourceBundle emptyResourceBundle;
    private MergedAnnotation<View> annotation;
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private FXMLLoader fxmlLoader;
    private Parent parent;
    private String title;
    private String originalTitle;

    private void checkGlobalCss() {
        if (globalCss != null) {
            return;
        }
        List<String> tempCss = new ArrayList<>();
        JavafxProperties javafxProperties = Javafx.getJavafxProperties();
        if (StringUtils.hasText(javafxProperties.getCss())) {

            String[] css = StringUtils.commaDelimitedListToStringArray(javafxProperties.getCss());
            for (String s : css) {
                try {
                    if (!s.endsWith(".css")) {
                        s += ".css";
                    }
                    tempCss.add(Javafx.getResourceLoader().getResource(s.trim()).getURL().toExternalForm());
                } catch (IOException e) {
                    logger.error(s + "css文件定义错误,忽略该css", e);
                }
            }
        }
        JavafxView.globalCss = tempCss;
    }

    private void checkGlobalBasenames() {
        if (globalBaseNames != null) {
            return;
        }
        List<String> tempBaseNames = new ArrayList<>();
        JavafxProperties javafxProperties = Javafx.getJavafxProperties();
        if (javafxProperties.getBasenames() != null) {
            tempBaseNames.addAll(javafxProperties.getBasenames());
        }

        globalBaseNames = tempBaseNames;
    }


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

    private String getConventionalName(String suffix) {
        Class<?> clazz = getClass();
        String modelName = clazz.getSimpleName();
        if (modelName.endsWith("View")) {
            modelName = modelName.substring(0, modelName.length() - 4);
        }
        return modelName + suffix;
    }

    private String getFxmlName(String name) {
        if (name.length() == 0) {

            return getConventionalName(".fxml");
        } else {
            if (!name.endsWith(".fxml")) {
                return name + ".fxml";
            }
            return name;
        }

    }

    private ResourceBundle createResourceBundle(List<String> basenames) {
        JavafxProperties javafxProperties = Javafx.getJavafxProperties();
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        if (!basenames.isEmpty()) {
            messageSource.setBasenames(basenames.toArray(new String[0]));
        }
        messageSource.setDefaultEncoding(javafxProperties.getEncoding().name());
        messageSource.setDefaultLocale(javafxProperties.getLocale());
        messageSource.setFallbackToSystemLocale(true);
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setAlwaysUseMessageFormat(false);


        return new MessageSourceResourceBundle(messageSource,
                javafxProperties.getLocale());

    }

    private void loadData() {
        if (fxmlLoader != null) {
            return;
        }
        checkAnnotation();
        checkGlobalBasenames();
        JavafxProperties javafxProperties = Javafx.getJavafxProperties();
        String fxml = getFxmlName(annotation.getString("fxml"));

        ResourceBundle resourceBundle = null;
        String[] basenames = annotation.getStringArray("basenames");

        List<String> viewBasenames = new ArrayList<>(4);
        if (basenames.length > 0) {
            viewBasenames.addAll(Arrays.asList(basenames));
        }
        viewBasenames.addAll(globalBaseNames);

        if (viewBasenames.isEmpty()) {
            if (emptyResourceBundle == null) {
                resourceBundle = createResourceBundle(viewBasenames);
                emptyResourceBundle = resourceBundle;
            } else {
                resourceBundle = emptyResourceBundle;
            }
        } else {
            resourceBundle = createResourceBundle(viewBasenames);
        }

        logger.debug("{} fxml = {}", getClass().getName(), fxml);
        URL url = getClass().getResource(fxml);
        logger.debug("url {}", url);


        fxmlLoader = new FXMLLoader(url, resourceBundle);
        fxmlLoader.setControllerFactory(Spring::getBean);
        fxmlLoader.setCharset(javafxProperties.getEncoding());
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @NonNull
    public FXMLLoader getFxmlLoader() {
        loadData();
        return fxmlLoader;
    }

    @NonNull
    public ResourceBundle getResourceBundle() {
        loadData();
        return fxmlLoader.getResources();
    }


    @NonNull
    public Parent getView() {
        if (parent != null) {
            return parent;
        }
        loadData();

        Parent parent = fxmlLoader.getRoot();
        checkGlobalCss();
        if (!globalCss.isEmpty()) {
            parent.getStylesheets().addAll(globalCss);
        }
        String[] css = annotation.getStringArray("css");

        if (css.length == 0) {
            String cssName = getConventionalName(".css");
            URL url = getClass().getResource(cssName);
            if (url != null) {
                parent.getStylesheets().add(url.toExternalForm());
            }

        } else {
            for (String s : css) {
                if (!s.endsWith(".css")) {
                    s += ".css";
                }
                URL url = getClass().getResource(s);
                if (url == null) {
                    logger.warn(s + "css文件定义错误,忽略该css");
                } else {
                    parent.getStylesheets().add(url.toExternalForm());
                }
            }
        }

        this.parent = parent;
        return parent;
    }

    @NonNull
    public String getTitle() {
        if (title != null) {
            return title;
        }
        loadData();
        String temp = annotation.getString("title");
        if (temp.length() == 0) {
            temp = getConventionalName("");
        }
        title = temp;
        return title;
    }

    @NonNull
    public String getOriginalTitle() {
        if (originalTitle != null) {
            return originalTitle;
        }
        loadData();
        originalTitle = annotation.getString("title");
        return originalTitle;
    }

    @View
    private static class EmptyView {
    }

    public static void main(String[] args) {

        JavafxView javafxView = new JavafxView();
        javafxView.getView();
    }
}
