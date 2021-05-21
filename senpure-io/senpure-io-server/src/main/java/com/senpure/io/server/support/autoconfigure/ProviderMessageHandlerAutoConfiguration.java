package com.senpure.io.server.support.autoconfigure;


import com.senpure.io.server.provider.handler.*;
import com.senpure.io.server.provider.handler.CSHeartMessageHandler;
import com.senpure.io.server.provider.handler.CSHeartMessageHandlerImpl;
import com.senpure.io.server.provider.handler.CSRelationUserGatewayMessageHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * ProviderMessageHandlerAutoConfiguration
 *
 * @author senpure
 * @time 2020-06-11 15:08:01
 */

public class ProviderMessageHandlerAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean(CSBreakUserGatewayMessageHandler.class)
    public CSBreakUserGatewayMessageHandler csBreakUserGatewayMessageHandler() {
        return new CSBreakUserGatewayMessageHandlerImpl();
    }

    @Bean
    public CSRelationUserGatewayMessageHandler csRelationUserGatewayMessageHandler() {
        return new CSRelationUserGatewayMessageHandler();
    }


    @Bean
    public CSAskHandleMessageHandler csAskHandleMessageHandler() {
        return new CSAskHandleMessageHandler();
    }

    @Bean("providerConsumerCSHeartMessageHandler")
    @ConditionalOnMissingBean(CSHeartMessageHandler.class)
    public CSHeartMessageHandler csHeartMessageHandler() {
        return new CSHeartMessageHandlerImpl();

    }

    @Bean("providerSCFrameworkErrorMessageHandler")
    @ConditionalOnMissingBean(SCFrameworkErrorMessageHandler.class)
    public SCFrameworkErrorMessageHandler scFrameworkErrorMessageHandler()
    {
        return new SCFrameworkErrorMessageHandlerImpl();
    }

}
