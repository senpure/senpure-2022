package com.senpure.io.server.gateway.provider.handler;

import com.senpure.io.server.gateway.GatewayReceiveProviderMessage;
import com.senpure.io.server.protocol.message.CSHeartMessage;
import io.netty.channel.Channel;

public class CSHeartMessageHandler extends AbstractGatewayProviderMessageHandler {
    @Override
    public void execute(Channel channel, GatewayReceiveProviderMessage frame) {

    }

    @Override
    public int messageId() {
        return CSHeartMessage.MESSAGE_ID;
    }
}
