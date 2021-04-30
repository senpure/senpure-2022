package com.senpure.base.autoconfigure;

import com.senpure.base.filter.VerifyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * PermissionFilterAutoConfiguration
 *
 * @author senpure
 * @time 2020-07-29 15:28:27
 */
public class PermissionFilterAutoConfiguration {
    @Resource
    private VerifyFilter verifyFilter;
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean  bean = new FilterRegistrationBean(verifyFilter);

        bean.addUrlPatterns("/*");
        return bean;
    }

}
