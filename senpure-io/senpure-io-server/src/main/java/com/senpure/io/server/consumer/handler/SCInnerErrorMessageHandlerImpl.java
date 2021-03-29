package com.senpure.io.server.consumer.handler;


import com.senpure.io.server.protocol.message.SCInnerErrorMessage;
import io.netty.channel.Channel;

/**
 * SCInnerErrorMessageHandler
 *
 * @author senpure
 * @time 2019-07-02 17:02:44
 */
public class SCInnerErrorMessageHandlerImpl extends AbstractConsumerMessageHandler<SCInnerErrorMessage> implements SCInnerErrorMessageHandler {
    @Override
    public void execute(Channel channel, SCInnerErrorMessage message) throws Exception {
        logger.error(message.toString());
    }

    @Override
    public int messageId() {
        return SCInnerErrorMessage.MESSAGE_ID;
    }

    @Override
    public SCInnerErrorMessage newEmptyMessage() {
        return new SCInnerErrorMessage();
    }
}
