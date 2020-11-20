package com.senpure.io.server.gateway.consumer.handler;

import com.senpure.io.server.ChannelAttributeUtil;
import com.senpure.io.server.gateway.Client2GatewayMessage;
import com.senpure.io.server.protocol.message.CSConsumerVerifyMessage;
import io.netty.channel.Channel;

public class CSConsumerVerifyMessageHandler extends AbstractConsumerMessageHandler {
    @Override
    public void execute(Channel channel, Client2GatewayMessage message) {
        messageExecutor.prepLoginChannels.put(message.getToken(), channel);
    }

    @Override
    public int handleMessageId() {
        return CSConsumerVerifyMessage.MESSAGE_ID;
    }

    @Override
    public boolean stopForward() {
        return false;
    }
}
