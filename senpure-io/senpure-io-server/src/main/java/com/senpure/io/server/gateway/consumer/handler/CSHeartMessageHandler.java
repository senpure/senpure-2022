package com.senpure.io.server.gateway.consumer.handler;

import com.senpure.io.server.gateway.GatewayReceiveConsumerMessage;
import com.senpure.io.server.protocol.message.CSHeartMessage;
import com.senpure.io.server.protocol.message.SCHeartMessage;
import io.netty.channel.Channel;

public class CSHeartMessageHandler extends AbstractConsumerMessageHandler {

    @Override
    public void execute(Channel channel, GatewayReceiveConsumerMessage message) {
        SCHeartMessage heartMessage = new SCHeartMessage();
        messageExecutor.sendMessage2Consumer(message.requestId(), message.token(), heartMessage);
    }

    @Override
    public int messageId() {
        return CSHeartMessage.MESSAGE_ID;
    }
}
