package com.senpure.io.server.gateway.consumer.handler;

import com.senpure.io.server.ChannelAttributeUtil;
import com.senpure.io.server.gateway.Client2GatewayMessage;
import com.senpure.io.server.protocol.message.CSHeartMessage;
import com.senpure.io.server.protocol.message.SCHeartMessage;
import io.netty.channel.Channel;

public class CSHeartMessageHandler extends AbstractConsumerMessageHandler {

    @Override
    public void execute(Channel channel, Client2GatewayMessage message) {
        SCHeartMessage heartMessage = new SCHeartMessage();
        messageExecutor.sendMessage2Consumer(message.getRequestId(), message.getToken(), heartMessage);
    }

    @Override
    public int handleMessageId() {
        return CSHeartMessage.MESSAGE_ID;
    }
}
