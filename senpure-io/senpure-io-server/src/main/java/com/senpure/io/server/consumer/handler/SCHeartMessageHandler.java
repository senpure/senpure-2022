package com.senpure.io.server.consumer.handler;


import com.senpure.io.server.protocol.message.SCHeartMessage;
import io.netty.channel.Channel;

/**
 * SCHeartMessageHandler
 *
 * @author senpure
 * @time 2019-03-19 14:59:03
 */
public class SCHeartMessageHandler extends AbstractConsumerMessageHandler<SCHeartMessage> {
    @Override
    public void execute(Channel channel, SCHeartMessage message) throws Exception {
      //  logger.debug(message.toString());
    }

    @Override
    public SCHeartMessage getEmptyMessage() {
        return new SCHeartMessage();
    }

    @Override
    public int handlerId() {
        return SCHeartMessage.MESSAGE_ID;
    }
}