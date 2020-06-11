package com.senpure.io.server.consumer.handler;


import com.senpure.io.protocol.Message;
import com.senpure.io.server.handler.MessageHandler;
import io.netty.channel.Channel;


public interface ConsumerMessageHandler<T extends Message> extends MessageHandler<T> {

    void execute(Channel channel, T message) throws  Exception;


}
