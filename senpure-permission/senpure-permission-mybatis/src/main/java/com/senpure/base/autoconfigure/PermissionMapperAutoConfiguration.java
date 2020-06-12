package com.senpure.base.autoconfigure;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.stream.Stream;

/**
 * MybatisMapperAutoConfiguration
 *
 * @author senpure
 * @time 2020-05-25 16:01:04
 */
@Import(PermissionMapperAutoConfiguration.PermissionMapperRegistrar.class)
@AutoConfigureAfter(MybatisAutoConfiguration.class)
@ConditionalOnProperty(value = "senpure.permission.mapper.enable", matchIfMissing = true)
public class PermissionMapperAutoConfiguration {
    public static class PermissionMapperRegistrar implements ImportBeanDefinitionRegistrar {
        private static final String BEAN_NAME = MapperScannerConfigurer.class.getName();
        private static final String SENPURE_MAPPER_PACKAGE = "com.senpure.base.mapper";
        private Logger logger = LoggerFactory.getLogger(getClass());

        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
            if (registry.containsBeanDefinition(BEAN_NAME)) {
                updatePostProcessor(registry);
            } else {
                addPostProcessor(registry);
            }
        }

        private void updatePostProcessor(BeanDefinitionRegistry registry) {
            BeanDefinition definition = registry.getBeanDefinition(BEAN_NAME);
            String packages = (String) definition.getPropertyValues().get("basePackage");
            String[] temps = StringUtils.commaDelimitedListToStringArray(packages);
            for (String temp : temps) {
                if (SENPURE_MAPPER_PACKAGE.startsWith(temp)) {
                    logger.debug("package  {} 包含 {} 不用合并", packages, SENPURE_MAPPER_PACKAGE);
                    return;
                }
            }
            logger.debug("扫描mapper package 合并 {} -----{}", packages, SENPURE_MAPPER_PACKAGE);
            packages = packages + "," + SENPURE_MAPPER_PACKAGE;
            definition.getPropertyValues().add("basePackage", packages);
        }

        private void addPostProcessor(BeanDefinitionRegistry registry) {
            logger.debug("使用默认sessionFactory 扫描senpure-permission mapper");
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MapperScannerConfigurer.class);
            builder.addPropertyValue("processPropertyPlaceHolders", true);
            builder.addPropertyValue("annotationClass", Mapper.class);
            builder.addPropertyValue("basePackage", SENPURE_MAPPER_PACKAGE);
            BeanWrapper beanWrapper = new BeanWrapperImpl(MapperScannerConfigurer.class);
            Stream.of(beanWrapper.getPropertyDescriptors()).filter((x) -> x.getName().equals("lazyInitialization")).findAny().ifPresent((x) -> {
                builder.addPropertyValue("lazyInitialization", "${mybatis.lazy-initialization:false}");
            });
            registry.registerBeanDefinition(MapperScannerConfigurer.class.getName(), builder.getBeanDefinition());
        }
    }
}
