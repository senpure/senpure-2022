package com.senpure.io.server.gateway.consumer.handler;

import com.senpure.io.server.gateway.GatewayReceiveConsumerMessage;
import com.senpure.io.server.protocol.message.CSFrameworkVerifyMessage;
import io.netty.channel.Channel;

public class CSFrameworkVerifyMessageHandler extends AbstractGatewayConsumerMessageHandler {
    @Override
    public void execute(Channel channel, GatewayReceiveConsumerMessage message) {

    }

    @Override
    public int messageId() {
        return CSFrameworkVerifyMessage.MESSAGE_ID;
    }

    @Override
    public boolean stopProvider() {
        return false;
    }
}
