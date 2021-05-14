package com.senpure.io.server.gateway.consumer.handler;

import com.senpure.io.server.gateway.GatewayReceiveConsumerMessage;
import io.netty.channel.Channel;

public class CSLoginMessageHandler extends CSFrameworkVerifyMessageHandler {


    @Override
    public void execute(Channel channel, GatewayReceiveConsumerMessage message) {
        logger.debug("{} 准备登录 ", message.token());
        messageExecutor.prepLoginChannels.put(message.token(), channel);
    }

    @Override
    public int messageId() {
        return messageExecutor.getCsLoginMessageId();
    }

    @Override
    public boolean stopProvider() {
        return false;
    }
}
