package com.senpure.io.server.gateway.provider.handler;

import com.senpure.io.server.gateway.Server2GatewayMessage;
import com.senpure.io.server.protocol.message.SCIdNameMessage;
import com.senpure.io.server.support.MessageIdReader;
import io.netty.channel.Channel;

public class SCIdNameMessageHandler extends AbstractProviderMessageHandler {
    @Override
    public void execute(Channel channel, Server2GatewayMessage server2GatewayMessage) {
        SCIdNameMessage message = new SCIdNameMessage();
        messageExecutor.readMessage(message, server2GatewayMessage);
        MessageIdReader.relation(message.getIdNames());
    }

    @Override
    public int handleMessageId() {
        return SCIdNameMessage.MESSAGE_ID;
    }
}
