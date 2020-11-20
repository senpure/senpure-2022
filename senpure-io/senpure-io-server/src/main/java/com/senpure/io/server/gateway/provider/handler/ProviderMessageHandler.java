package com.senpure.io.server.gateway.provider.handler;

import com.senpure.io.server.gateway.Server2GatewayMessage;
import io.netty.channel.Channel;

public interface ProviderMessageHandler {


    void execute(Channel channel, Server2GatewayMessage server2GatewayMessage);

    int handleMessageId();

    default boolean stopResponse() {
        return true;
    }

}
