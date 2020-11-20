package com.senpure.io.server.gateway.provider.handler;

import com.senpure.io.server.gateway.Server2GatewayMessage;
import com.senpure.io.server.protocol.message.SCHeartMessage;
import io.netty.channel.Channel;

public class SCHeartMessageHandler extends AbstractProviderMessageHandler {
    @Override
    public void execute(Channel channel, Server2GatewayMessage server2GatewayMessage) {

    }

    @Override
    public int handleMessageId() {
        return SCHeartMessage.MESSAGE_ID;
    }
}
