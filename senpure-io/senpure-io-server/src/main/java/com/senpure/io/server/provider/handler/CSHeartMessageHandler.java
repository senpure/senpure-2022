package com.senpure.io.server.provider.handler;


import com.senpure.io.server.protocol.message.CSHeartMessage;
import io.netty.channel.Channel;

/**
 * 心跳
 *
 * @author senpure
 * @time 2018-10-17 14:59:15
 */
public class CSHeartMessageHandler extends AbstractInnerMessageHandler<CSHeartMessage> {

    @Override
    public void execute(Channel channel, long token, long userId, CSHeartMessage message) {

    }

    @Override
    public int handleMessageId() {
        return CSHeartMessage.MESSAGE_ID;
    }

    @Override
    public CSHeartMessage getEmptyMessage() {
        return new CSHeartMessage();
    }
}