package com.senpure.io.server.producer;

import com.senpure.io.protocol.Message;

/**
 * Producer2GatewayMessage
 *
 * @author senpure
 * @time 2019-06-26 17:37:32
 */
public class Producer2GatewayMessage {

    private int requestId;
    private Long[] userIds;

    private long token;
    private int messageId;

    private Message message;

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public Long[] getUserIds() {
        return userIds;
    }

    public void setUserIds(Long[] userIds) {
        this.userIds = userIds;
    }

    public long getToken() {
        return token;
    }

    public void setToken(long token) {
        this.token = token;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
