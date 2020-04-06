package com.senpure.io.server.support.annotation;


import com.senpure.io.server.ServerProperties;
import com.senpure.io.server.support.configuration.DirectAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * EnableConsumer
 *
 * @author senpure
 * @time 2019-07-02 16:58:29
 */
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = {java.lang.annotation.ElementType.TYPE})
@Documented
@EnableConfigurationProperties({ServerProperties.class})
@Import({DirectAutoConfiguration.class})
public @interface EnableDirect {

}
