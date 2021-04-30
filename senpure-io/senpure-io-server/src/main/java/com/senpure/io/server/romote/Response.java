package com.senpure.io.server.romote;

import com.senpure.io.protocol.Message;
import io.netty.channel.Channel;

import javax.annotation.Nonnull;

public interface Response {

    boolean isSuccess();

    /**
     * {@code isSuccess()} 为 true   {@code getValue()} 不为空
     */
    <T extends Message> T getMessage();

    /**
     * {@code isSuccess ()} 为 false  {@code getError ()} 不为空
     */
    <T extends Message> T getError();

    @Nonnull
    Channel getChannel();
}
