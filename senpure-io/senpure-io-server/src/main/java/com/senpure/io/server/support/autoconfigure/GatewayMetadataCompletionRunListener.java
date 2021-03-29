package com.senpure.io.server.support.autoconfigure;

import com.senpure.base.AppEvn;
import com.senpure.base.autoconfigure.AbstractRootApplicationRunListener;
import com.senpure.io.server.ServerProperties;
import com.senpure.io.server.support.annotation.EnableGateway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * GatewayMetadataCompletionConfiguration
 *
 * @author senpure
 * @time 2020-05-07 18:31:27
 */
public class GatewayMetadataCompletionRunListener extends AbstractRootApplicationRunListener {

    public GatewayMetadataCompletionRunListener(SpringApplication springApplication, String[] args) {
       super(springApplication,args);
    }

    @Override
    public void rootStarting() {

    }

    @Override
    public void rootEnvironmentPrepared(ConfigurableEnvironment environment) {
//            ConfigurationPropertySourcesPropertySource temp = null;
//            for (PropertySource<?> propertySource : environment.getPropertySources()) {
//                if (propertySource instanceof ConfigurationPropertySourcesPropertySource) {
//                    temp = (ConfigurationPropertySourcesPropertySource) propertySource;
//                    break;
//                }
//            }
//            if (temp == null) {
//                logger.warn("没有找到ConfigurationPropertySourcesPropertySource，可能是springboot版本问题，无法自动补全元数据，请您自己注意配置元数据");
//                return;
//            }
//            SpringConfigurationPropertySource source=  SpringConfigurationPropertySource.from(temp);
//            ConfigurationPropertyName configurationPropertyName = ConfigurationPropertyName.of("eureka.instance.metadata-map.cs-port");
//            source.getConfigurationProperty(configurationPropertyName).getValue();

        Class<?> bootClass = AppEvn.getStartClass();
        EnableGateway enableGateway = bootClass.getAnnotation(EnableGateway.class);
        if (enableGateway != null) {
            Integer cs = environment.getProperty("server.io.gateway.csPort", Integer.class);
            Integer sc = environment.getProperty("server.io.gateway.scPort", Integer.class);
            ServerProperties serverProperties = new ServerProperties();
            ServerProperties.Gateway gateway = serverProperties.getGateway();
            if (cs == null) {
                cs = gateway.getCsPort();
            }
            if (sc == null) {
                sc = gateway.getScPort();
            }
            try {
                Class.forName("org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean");
            } catch (ClassNotFoundException e) {
                logger.warn("自动补全元数据目前只支持eureka注册中心，请您自己注意配置元数据");
                return;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("eureka.instance.metadata-map.csPort", cs);
            map.put("eureka.instance.metadata-map.scPort", sc);
            PropertySource<?> propertySource = new MapPropertySource("gatewayMetadataMap", map);
            boolean add = true;
            for (PropertySource<?> temp : environment.getPropertySources()) {
                if (temp.containsProperty("eureka.instance.metadata-map.csPort")
                        || temp.containsProperty("eureka.instance.metadata-map.scPort")) {
                    if (temp instanceof OriginTrackedMapPropertySource) {
                        add = false;
                        environment.getPropertySources().addBefore(temp.getName(), propertySource);
                        break;
                    }
                }
            }
            if (add) {
                environment.getPropertySources().addLast(propertySource);
            }
        }
    }

}
