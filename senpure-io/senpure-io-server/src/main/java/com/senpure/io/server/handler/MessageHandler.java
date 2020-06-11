package com.senpure.io.server.handler;

import com.senpure.io.protocol.Message;

/**
 * MessageHandler
 *
 * @author senpure
 * @time 2020-06-11 16:43:03
 */
public interface MessageHandler<T extends Message> {
    T getEmptyMessage();

    int handleMessageId();
}
