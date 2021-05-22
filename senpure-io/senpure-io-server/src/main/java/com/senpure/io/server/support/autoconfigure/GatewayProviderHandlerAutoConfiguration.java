package com.senpure.io.server.support.autoconfigure;

import com.senpure.io.server.ServerProperties;
import com.senpure.io.server.gateway.provider.handler.*;
import org.springframework.context.annotation.Bean;

public class GatewayProviderHandlerAutoConfiguration {


    @Bean("gatewayProviderCSHeartMessageHandler")
    public CSHeartMessageHandler csHeartMessageHandler() {
        return new CSHeartMessageHandler();
    }

    @Bean
    public CSFrameworkVerifyProviderMessageHandler csFrameworkVerifyProviderMessageHandler() {
        return new CSFrameworkVerifyProviderMessageHandler();
    }

    @Bean("gatewayProviderCSFrameworkVerifyMessageHandler")
    public CSFrameworkVerifyMessageHandler csFrameworkVerifyMessageHandler(ServerProperties properties) {
        ServerProperties.GatewayProperties gatewayProperties = properties.getGateway();
        return new CSFrameworkVerifyMessageHandler(gatewayProperties.isSimpleVerify(), gatewayProperties.getSimpleToken(), gatewayProperties.getSimpleUserId());
    }

    @Bean
    public CSRegisterProviderMessageHandler scRegServerHandleMessageMessageHandler() {

        return new CSRegisterProviderMessageHandler();
    }

    @Bean
    public SCAskHandleMessageHandler scAskHandleMessageHandler() {
        return new SCAskHandleMessageHandler();
    }

    @Bean
    public SCFrameworkVerifyMessageHandler scFrameworkVerifyMessageHandler() {
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
