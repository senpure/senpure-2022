package com.senpure.io.server.direct;


import com.senpure.io.protocol.Message;
import io.netty.channel.Channel;


public interface DirectMessageHandler<T extends Message> {

    T getEmptyMessage();

    void execute(Channel channel, T message) throws Exception;

    int handlerId();

}
