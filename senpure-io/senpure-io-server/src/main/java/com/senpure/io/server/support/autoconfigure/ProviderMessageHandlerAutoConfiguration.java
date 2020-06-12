package com.senpure.io.server.support.autoconfigure;

import com.senpure.io.server.provider.handler.CSAskHandleMessageHandler;
import com.senpure.io.server.provider.handler.CSBreakUserGatewayMessageHandler;
import com.senpure.io.server.provider.handler.CSRegServerHandleMessageMessageHandler;
import com.senpure.io.server.provider.handler.CSRelationUserGatewayMessageHandler;
import org.springframework.context.annotation.Bean;

/**
 * ProviderMessageHandlerAutoConfiguration
 *
 * @author senpure
 * @time 2020-06-11 15:08:01
 */

public class ProviderMessageHandlerAutoConfiguration {

//    @Bean
//    @ConditionalOnProperty(
//            prefix = "server.io.provider.handler.break-user-gateway",
//            value = "enable",
//            matchIfMissing = true)
    public CSBreakUserGatewayMessageHandler csBreakUserGatewayMessageHandler() {
        return new CSBreakUserGatewayMessageHandler();
    }

    @Bean
    public CSRelationUserGatewayMessageHandler csRelationUserGatewayMessageHandler() {
        return new CSRelationUserGatewayMessageHandler();
    }

    @Bean
    public CSRegServerHandleMessageMessageHandler csRegServerHandleMessageMessageHandler() {
        return new CSRegServerHandleMessageMessageHandler();
    }


    @Bean
    public CSAskHandleMessageHandler csAskHandleMessageHandler() {
        return new CSAskHandleMessageHandler();
    }

}
