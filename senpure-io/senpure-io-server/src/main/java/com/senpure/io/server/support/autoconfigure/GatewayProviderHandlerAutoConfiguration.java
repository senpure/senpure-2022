package com.senpure.io.server.support.autoconfigure;

import com.senpure.io.server.gateway.provider.handler.*;
import org.springframework.context.annotation.Bean;

public class GatewayProviderHandlerAutoConfiguration {

    @Bean
    public SCAskHandleMessageHandler scAskHandleMessageHandler() {
        return new SCAskHandleMessageHandler();
    }

    @Bean
    public SCFrameworkVerifyMessageHandler scConsumerVerifyMessageHandler() {
        return new SCFrameworkVerifyMessageHandler();
    }

    @Bean
    public CSHeartMessageHandler csHeartMessageHandler() {
        return new CSHeartMessageHandler();
    }

    @Bean
    public SCIdNameMessageHandler scIdNameMessageHandler() {
        return new SCIdNameMessageHandler();
    }

    @Bean
    public SCKickOffMessageHandler scKickOffMessageHandler() {
        return new SCKickOffMessageHandler();
    }

    @Bean
    public SCLoginMessageHandler scLoginMessageHandler() {
        return new SCLoginMessageHandler();

    }

    @Bean
    public SCMessageForwardMessageHandler scMessageForwardMessageHandler() {
        return new SCMessageForwardMessageHandler();
    }

    @Bean
    public SCRegServerHandleMessageMessageHandler scRegServerHandleMessageMessageHandler() {

        return new SCRegServerHandleMessageMessageHandler();
    }

    @Bean
    public SCRelationUserGatewayMessageHandler scRelationUserGatewayMessageHandler() {
        return new SCRelationUserGatewayMessageHandler();
    }

    @Bean
    public SCStatisticMessageHandler scStatisticMessageHandler() {
        return new SCStatisticMessageHandler();
    }


    @Bean
    public CSFrameworkVerifyProviderMessageHandler csFrameworkVerifyProviderMessageHandler() {
        return new CSFrameworkVerifyProviderMessageHandler();
    }
}
