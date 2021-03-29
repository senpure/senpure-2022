package com.senpure.io.server.gateway.provider.handler;

import com.senpure.io.server.gateway.GatewayReceiveProviderMessage;
import io.netty.channel.Channel;

public interface ProviderMessageHandler {


    void execute(Channel channel, GatewayReceiveProviderMessage gatewayReceiveProviderMessage);

    int handleMessageId();

    default boolean stopResponse() {
        return true;
    }

}
