package com.senpure.javafx.autoconfigure;

import com.senpure.base.configure.BaseConfiguration;
import com.senpure.javafx.Javafx;
import com.senpure.javafx.JavafxProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JavafxAutoConfiguration
 *
 * @author senpure
 * @time 2020-06-19 15:30:23
 */
@Configuration(proxyBeanMethods = false)
public class JavafxAutoConfiguration extends BaseConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "javafx")
    public JavafxProperties javafxProperties() {
        JavafxProperties javafxProperties = new JavafxProperties();
        Javafx.setJavafxProperties(javafxProperties);
        return javafxProperties;
    }
}
