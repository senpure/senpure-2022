package com.senpure.io.server.provider.handler;


import com.senpure.io.server.protocol.message.CSHeartMessage;
import com.senpure.io.server.protocol.message.SCHeartMessage;
import io.netty.channel.Channel;

import javax.annotation.Nonnull;

/**
 * 心跳
 *
 * @author senpure
 * @time 2018-10-17 14:59:15
 */
public class CSHeartMessageHandlerImpl extends AbstractFrameworkNecessaryMessageHandler<CSHeartMessage> implements CSHeartMessageHandler  {

    @Override
    public void execute(Channel channel, long token, long userId, CSHeartMessage message) {


        messageSender.sendMessageByToken(token, new SCHeartMessage());
    }


    @Override
    public int messageId() {
        return CSHeartMessage.MESSAGE_ID;
    }

    /**
     * new 一个空对象
     */
    @Nonnull
    @Override
    public CSHeartMessage newEmptyMessage() {
        return new CSHeartMessage();
    }

}