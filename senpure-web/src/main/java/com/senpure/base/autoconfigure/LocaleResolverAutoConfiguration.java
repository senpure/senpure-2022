package com.senpure.base.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;


@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class LocaleResolverAutoConfiguration {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    @ConditionalOnMissingBean
    public LocaleResolver localeResolver() {
        logger.info("springboot没有注册localeResolver 自己注册一个");
        SessionLocaleResolver slr = new SessionLocaleResolver();
        //设置默认区域,
        slr.setDefaultLocale(Locale.CHINA);
        logger.info("注册localeResolver [SessionLocaleResolver] defaultLocal {}", Locale.CHINA);
        return slr;
    }

}
