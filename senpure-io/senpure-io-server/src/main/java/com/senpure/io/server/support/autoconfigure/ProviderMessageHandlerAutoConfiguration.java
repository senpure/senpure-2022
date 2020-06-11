package com.senpure.io.server.support.autoconfigure;

import com.senpure.io.server.protocol.message.CSAskHandleMessage;
import com.senpure.io.server.protocol.message.CSBreakUserGatewayMessage;
import com.senpure.io.server.protocol.message.CSRegServerHandleMessageMessage;
import com.senpure.io.server.protocol.message.CSRelationUserGatewayMessage;
import com.senpure.io.server.provider.handler.CSAskHandleMessageHandler;
import com.senpure.io.server.provider.handler.CSBreakUserGatewayMessageHandler;
import com.senpure.io.server.provider.handler.CSRegServerHandleMessageMessageHandler;
import com.senpure.io.server.provider.handler.CSRelationUserGatewayMessageHandler;
import com.senpure.io.server.support.annotation.ConditionalOnMissingMessageHandler;
import org.springframework.context.annotation.Bean;

/**
 * ProviderMessageHandlerAutoConfiguration
 *
 * @author senpure
 * @time 2020-06-11 15:08:01
 */

//@AutoConfigureAfter(ProviderAutoConfiguration.class)
public class ProviderMessageHandlerAutoConfiguration {
    @Bean
    @ConditionalOnMissingMessageHandler(CSBreakUserGatewayMessage.MESSAGE_ID)
    public CSBreakUserGatewayMessageHandler csBreakUserGatewayMessageHandler() {
        return new CSBreakUserGatewayMessageHandler();
    }

    @Bean
     @ConditionalOnMissingMessageHandler(CSRelationUserGatewayMessage.MESSAGE_ID)
    public CSRelationUserGatewayMessageHandler csRelationUserGatewayMessageHandler() {
        return new CSRelationUserGatewayMessageHandler();
    }

    @Bean
    @ConditionalOnMissingMessageHandler(CSRegServerHandleMessageMessage.MESSAGE_ID)
    public CSRegServerHandleMessageMessageHandler csRegServerHandleMessageMessageHandler() {
        return new CSRegServerHandleMessageMessageHandler();
    }


    @Bean
    @ConditionalOnMissingMessageHandler(CSAskHandleMessage.MESSAGE_ID)
    public CSAskHandleMessageHandler csAskHandleMessageHandler() {
        return new CSAskHandleMessageHandler();
    }


}
