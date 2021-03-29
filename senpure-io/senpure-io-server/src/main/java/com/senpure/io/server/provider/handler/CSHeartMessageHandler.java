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
    public int messageId() {
        return CSHeartMessage.MESSAGE_ID;
    }

    /**
     * new 一个空对象
     */
    @Override
    public CSHeartMessage newEmptyMessage() {
        return new CSHeartMessage();
    }

}