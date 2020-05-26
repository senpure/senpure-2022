package com.senpure.base.autoconfigure;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScanPackages;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * PermissionEntityAutoConfiguration
 *
 * @author senpure
 * @time 2020-05-26 11:34:54
 */
@ConditionalOnProperty(value = "senpure.permission.entity.enable", matchIfMissing = true)
@Import(PermissionEntityAutoConfiguration.PermissionEntityRegistrar.class)
public class PermissionEntityAutoConfiguration {
    public static class PermissionEntityRegistrar implements ImportBeanDefinitionRegistrar {
        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
            EntityScanPackages.register(registry, "com.senpure.base.entity");
        }
    }

}
