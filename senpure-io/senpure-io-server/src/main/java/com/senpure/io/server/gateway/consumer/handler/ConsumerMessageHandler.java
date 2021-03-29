package com.senpure.io.server.gateway.consumer.handler;

import com.senpure.io.server.gateway.GatewayReceiveConsumerMessage;
import io.netty.channel.Channel;

public interface ConsumerMessageHandler {

    void execute(Channel channel, GatewayReceiveConsumerMessage message);

    int messageId();

    default boolean stopForward() {
        return true;
    }
}
