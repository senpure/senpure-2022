package com.senpure.io.server.support.annotation;

import com.senpure.io.server.ServerProperties;
import com.senpure.io.server.support.configuration.BreakUserSelector;
import com.senpure.io.server.support.configuration.ProducerAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * EnableProducer
 *
 * @author senpure
 * @time 2020-01-09 15:14:07
 */
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = {java.lang.annotation.ElementType.TYPE})
@Documented
@EnableConfigurationProperties({ServerProperties.class})
@Import({ProducerAutoConfiguration.class, BreakUserSelector.class})
public @interface EnableProducer {
    /**
     * 注册断开处理器
     *
     * @return
     */
    boolean breakUser() default true;
}
