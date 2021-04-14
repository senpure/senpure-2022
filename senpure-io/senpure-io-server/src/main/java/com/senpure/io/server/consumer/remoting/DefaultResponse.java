package com.senpure.io.server.consumer.remoting;

import com.senpure.io.protocol.Message;
import io.netty.channel.Channel;

/**
 * DefaultResponse
 *
 * @author senpure
 * @time 2019-06-28 11:22:51
 */
public class DefaultResponse implements Response {


    private final Message message;
    private final Message error;
    private final Channel channel;

    public DefaultResponse(Channel channel,Message message, Message error) {
        this.channel = channel;
        this.message = message;
        this.error = error;
    }

    @Override
    public boolean isSuccess() {
        return message != null;
    }

    @Override
    public <T extends Message> T getValue() {
        return (T) message;
    }

    @Override
    public <T extends Message> T getError() {
        return (T) error;
    }

    @Override
    public Channel getChannel() {
        return channel;
    }
}
