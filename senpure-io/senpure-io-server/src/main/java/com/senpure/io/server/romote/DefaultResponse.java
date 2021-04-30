package com.senpure.io.server.romote;

import com.senpure.io.protocol.Message;
import io.netty.channel.Channel;

import javax.annotation.Nonnull;

public class DefaultResponse implements Response {
    private final Message message;
    private final Message error;
    private final Channel channel;

    public DefaultResponse(Channel channel, Message message, Message error) {
        this.channel = channel;
        this.message = message;
        this.error = error;
    }

    @Override
    public boolean isSuccess() {
        return message != null;
    }


    @SuppressWarnings({"unchecked"})
    @Override
    public <T extends Message> T getMessage() {
        return (T) message;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public <T extends Message> T getError() {
        return (T) error;
    }

    @Nonnull
    @Override
    public Channel getChannel() {
        return channel;
    }
}
