package com.senpure.io.server.consumer.remoting;

import com.senpure.io.protocol.Message;
import io.netty.channel.Channel;

import javax.annotation.Nullable;

/**
 * Response
 *
 * @author senpure
 * @time 2019-06-28 11:13:28
 */
public interface Response {

    boolean isSuccess();

    /**
     * {@code isSuccess()} 为 true   {@code getValue()} 不为空
     *
     */
    <T extends Message> T getValue();

    /**
     * {@code isSuccess ()} 为 false  {@code getError ()} 不为空
     *
     */
    <T extends Message> T getError();

    /**
     * {@code isSuccess ()} 为 false  {@code getChannel ()} 可能为空
     *
     */
    Channel getChannel();
}
