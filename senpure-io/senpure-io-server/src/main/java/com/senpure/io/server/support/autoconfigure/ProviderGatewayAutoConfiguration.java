package com.senpure.io.server.support.autoconfigure;


import com.senpure.io.server.ServerProperties;
import com.senpure.io.server.provider.GatewayManager;
import com.senpure.io.server.support.ProviderServerStarter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@ConditionalOnProperty(prefix = "server.io", value = "provider.model", havingValue = "GATEWAY", matchIfMissing = true)
@AutoConfigureAfter(ProviderAutoConfiguration.class)
public class ProviderGatewayAutoConfiguration {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public GatewayManager gatewayManager() {
        logger.info("bean gatewayManager");
        return new GatewayManager();
    }


    @Bean
    public ProviderServerStarter providerServerStarter(ServerProperties properties) {
        return new ProviderServerStarter(properties);
    }


}
