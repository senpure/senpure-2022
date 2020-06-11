package com.senpure.io.server.consumer.handler;


import com.senpure.io.server.protocol.message.SCInnerErrorMessage;
import io.netty.channel.Channel;

/**
 * SCInnerErrorMessageHandler
 *
 * @author senpure
 * @time 2019-07-02 17:02:44
 */
public class SCInnerErrorMessageHandler extends AbstractConsumerMessageHandler<SCInnerErrorMessage> {
    @Override
    public void execute(Channel channel, SCInnerErrorMessage message) throws Exception {
        logger.debug(message.toString());
    }

    @Override
    public int handleMessageId() {
        return SCInnerErrorMessage.MESSAGE_ID;
    }

    @Override
    public SCInnerErrorMessage getEmptyMessage() {
        return new SCInnerErrorMessage();
    }
}
