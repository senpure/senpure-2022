package org.springframework.boot.autoconfigure.orm.jpa;

import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.spi.SessionImplementor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.domain.EntityScanPackages;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.SchemaManagementProvider;
import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadataProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Collection;

/**
 * PermissionEntityAutoConfiguration
 *
 * @author senpure
 * @time 2020-05-26 11:34:54
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({LocalContainerEntityManagerFactoryBean.class, EntityManager.class, SessionImplementor.class})
@EnableConfigurationProperties({HibernateProperties.class, JpaProperties.class})
@ConditionalOnSingleCandidate(DataSource.class)
@AutoConfigureBefore(HibernateJpaAutoConfiguration.class)
@ConditionalOnProperty(value = "senpure.permission.entity.enable", matchIfMissing = true)
public class PermissionEntityAutoConfiguration extends HibernateJpaConfiguration {
    PermissionEntityAutoConfiguration(DataSource dataSource, JpaProperties jpaProperties, ConfigurableListableBeanFactory beanFactory, ObjectProvider<JtaTransactionManager> jtaTransactionManager, HibernateProperties hibernateProperties, ObjectProvider<Collection<DataSourcePoolMetadataProvider>> metadataProviders, ObjectProvider<SchemaManagementProvider> providers, ObjectProvider<PhysicalNamingStrategy> physicalNamingStrategy, ObjectProvider<ImplicitNamingStrategy> implicitNamingStrategy, ObjectProvider<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers) {
        super(dataSource, jpaProperties, beanFactory, jtaTransactionManager, hibernateProperties, metadataProviders, providers, physicalNamingStrategy, implicitNamingStrategy, hibernatePropertiesCustomizers);
    }

    @Override
    protected String[] getPackagesToScan() {
        String senpureEntityPackage = "com.senpure.base.entity";
        String[] packages = super.getPackagesToScan();
        for (String aPackage : packages) {
            if (senpureEntityPackage.startsWith(aPackage)) {
                return packages;
            }
        }
        return StringUtils.addStringToArray(packages, senpureEntityPackage);
    }

    public static class PermissionEntityRegistrar implements ImportBeanDefinitionRegistrar {
        private BeanFactory beanFactory;

        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

            EntityScanPackages.register(registry, "com.senpure.base.entity");
        }

    }

}
