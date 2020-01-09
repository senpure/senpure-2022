package com.senpure.io.server.consumer.handler;


import com.senpure.io.protocol.Message;
import io.netty.channel.Channel;


public interface ConsumerMessageHandler<T extends Message> {


    T getEmptyMessage();

    void execute(Channel channel, T message) throws  Exception;

    int handlerId();


}
