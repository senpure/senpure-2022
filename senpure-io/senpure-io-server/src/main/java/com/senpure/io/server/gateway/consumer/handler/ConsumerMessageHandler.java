package com.senpure.io.server.gateway.consumer.handler;

import com.senpure.io.server.gateway.Client2GatewayMessage;
import com.senpure.io.server.gateway.Server2GatewayMessage;
import io.netty.channel.Channel;

public interface ConsumerMessageHandler {

    void execute(Channel channel, Client2GatewayMessage message);

    int handleMessageId();

    default boolean stopForward() {
        return true;
    }
}
