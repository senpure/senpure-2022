package com.senpure.base.autoconfigure;


import com.senpure.base.interceptor.MultipleInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@ConditionalOnProperty(value = "senpure.permission.multipleInterceptor.enable", matchIfMissing = true)
public class MultipleInterceptorAutoConfiguration implements WebMvcConfigurer {


    @Resource
    private MultipleInterceptor multipleInterceptor;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public MultipleInterceptorAutoConfiguration() {

        logger.info("=====================");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {


        registry.addInterceptor(multipleInterceptor);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
