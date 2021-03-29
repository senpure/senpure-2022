package com.senpure.io.server.provider;

import com.senpure.io.protocol.Message;
import com.senpure.io.server.MessageFrame;

/**
 * Gateway2ProducerMessage
 *
 * @author senpure
 * @time 2019-06-26 16:52:09
 */
public class ProviderReceiveMessage implements MessageFrame {

    //请求编号

    private int  requestId;
    private long token;
    private long userId;
    private int messageId;
    private Message message;


    public int  getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public long getToken() {
        return token;
    }

    public void setToken(long token) {
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }



}
