package com.senpure.io.server.gateway.consumer.handler;

import com.senpure.io.server.gateway.GatewayReceiveConsumerMessage;
import com.senpure.io.server.protocol.message.CSConsumerVerifyMessage;
import io.netty.channel.Channel;

public class CSConsumerVerifyMessageHandler extends AbstractConsumerMessageHandler {
    @Override
    public void execute(Channel channel, GatewayReceiveConsumerMessage message) {
        messageExecutor.prepLoginChannels.put(message.getToken(), channel);
    }

    @Override
    public int messageId() {
        return CSConsumerVerifyMessage.MESSAGE_ID;
    }

    @Override
    public boolean stopForward() {
        return false;
    }
}
