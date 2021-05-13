package com.senpure.io.server.gateway.provider.handler;

import com.senpure.io.server.gateway.GatewayReceiveProviderMessage;
import com.senpure.io.server.protocol.message.CSMatchingMessage;
import com.senpure.io.server.protocol.message.SCMatchingMessage;
import io.netty.channel.Channel;

public class CSMatchingMessageHandler  extends  AbstractProviderMessageHandler{
    @Override
    public void execute(Channel channel, GatewayReceiveProviderMessage frame) {

        SCMatchingMessage message = new SCMatchingMessage();
        messageExecutor.sendMessage2Producer(channel, message);
    }

    @Override
    public int messageId() {
        return CSMatchingMessage.MESSAGE_ID;
    }
}
