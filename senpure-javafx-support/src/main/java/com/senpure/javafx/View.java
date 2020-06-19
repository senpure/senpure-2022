package com.senpure.javafx;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * FXMLView
 *
 * @author senpure
 * @time 2020-06-18 16:11:43
 */
@Component
@Lazy
@Retention(RetentionPolicy.RUNTIME)
public @interface View {

    @AliasFor("fxml")
    String value() default "";

    @AliasFor("value")
    String fxml() default "";

    @AliasFor(value="value", annotation = Lazy.class)
    boolean lazy() default true;

    String[] css() default {};


    String bundle() default "";
}
