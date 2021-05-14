package com.senpure.io.server.gateway.provider.handler;

import com.senpure.io.server.gateway.GatewayReceiveProviderMessage;
import com.senpure.io.server.protocol.message.SCHeartMessage;
import io.netty.channel.Channel;

public class SCHeartMessageHandler extends AbstractGatewayProviderMessageHandler {
    @Override
    public void execute(Channel channel, GatewayReceiveProviderMessage frame) {

    }

    @Override
    public int messageId() {
        return SCHeartMessage.MESSAGE_ID;
    }
}
