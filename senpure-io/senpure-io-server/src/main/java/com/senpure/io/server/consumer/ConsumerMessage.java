package com.senpure.io.server.consumer;

import com.senpure.io.protocol.Message;

/**
 * ConsumerMessage
 *
 * @author senpure
 * @time 2019-06-27 17:43:29
 */
public class ConsumerMessage {
    private int requestId;
    private Message message;

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ConsumerMessage{" +
                "requestId=" + requestId +
                ", message=" + message +
                '}';
    }
}
