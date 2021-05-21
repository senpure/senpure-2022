package com.senpure.io.server.support.autoconfigure;

import com.senpure.io.server.ServerProperties;
import com.senpure.io.server.gateway.provider.handler.*;
import org.springframework.context.annotation.Bean;

public class GatewayProviderHandlerAutoConfiguration {


    @Bean("gatewayProviderHeartMessageHandler")
    public CSHeartMessageHandler csHeartMessageHandler() {
        return new CSHeartMessageHandler();
    }

    @Bean
    public CSFrameworkVerifyProviderMessageHandler csFrameworkVerifyProviderMessageHandler() {
        return new CSFrameworkVerifyProviderMessageHandler();
    }

    @Bean("gatewayProviderFrameworkVerifyMessageHandler")
    public CSFrameworkVerifyMessageHandler csFrameworkVerifyMessageHandler(ServerProperties properties) {
        ServerProperties.GatewayProperties gatewayProperties = properties.getGateway();
        return new CSFrameworkVerifyMessageHandler(gatewayProperties.isSimpleVerify(), gatewayProperties.getSimpleToken(), gatewayProperties.getSimpleUserId());
    }

    @Bean
    public CSRegisterProviderMessageReqHandler scRegServerHandleMessageMessageHandler() {

        return new CSRegisterProviderMessageReqHandler();
    }

    @Bean
    public SCAskHandleMessageHandler scAskHandleMessageHandler() {
        return new SCAskHandleMessageHandler();
    }

    @Bean
    public SCFrameworkVerifyMessageHandler scConsumerVerifyMessageHandler() {
        return new SCFrameworkVerifyMessageHandler();
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
    public SCRelationUserGatewayMessageHandler scRelationUserGatewayMessageHandler() {
        return new SCRelationUserGatewayMessageHandler();
    }

    @Bean
    public SCStatisticMessageHandler scStatisticMessageHandler() {
        return new SCStatisticMessageHandler();
    }


}
