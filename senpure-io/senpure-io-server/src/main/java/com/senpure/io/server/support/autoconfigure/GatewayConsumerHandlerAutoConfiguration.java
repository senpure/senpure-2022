package com.senpure.io.server.support.autoconfigure;

import com.senpure.io.server.ServerProperties;
import com.senpure.io.server.gateway.consumer.handler.CSFrameworkVerifyMessageHandler;
import com.senpure.io.server.gateway.consumer.handler.CSHeartMessageHandler;
import com.senpure.io.server.gateway.consumer.handler.CSLoginMessageHandler;
import org.springframework.context.annotation.Bean;


public class GatewayConsumerHandlerAutoConfiguration {

    @Bean
    public CSFrameworkVerifyMessageHandler csConsumerVerifyMessageHandler(ServerProperties properties) {
        ServerProperties.GatewayProperties gatewayProperties = properties.getGateway();
        return new CSFrameworkVerifyMessageHandler(gatewayProperties.isSimpleVerify(), gatewayProperties.getVerifyToken());
    }

    @Bean
    public CSHeartMessageHandler csHeartMessageHandler() {
        return new CSHeartMessageHandler();
    }

    @Bean
    public CSLoginMessageHandler csLoginMessageHandler() {
        return new CSLoginMessageHandler();
    }
}
