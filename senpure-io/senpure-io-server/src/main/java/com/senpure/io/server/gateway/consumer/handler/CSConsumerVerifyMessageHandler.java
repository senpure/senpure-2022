package com.senpure.io.server.gateway.consumer.handler;

import com.senpure.io.server.gateway.GatewayReceiveConsumerMessage;
import com.senpure.io.server.protocol.message.CSConsumerVerifyMessage;
import io.netty.channel.Channel;

public class CSConsumerVerifyMessageHandler extends AbstractConsumerMessageHandler {
    @Override
    public void execute(Channel channel, GatewayReceiveConsumerMessage message) {
        logger.debug("{} 准备登录 ", message.token());
        messageExecutor.prepLoginChannels.put(message.token(), channel);
    }

    @Override
    public int messageId() {
        return CSConsumerVerifyMessage.MESSAGE_ID;
    }

    @Override
    public boolean stopProvider() {
        return false;
    }
}
