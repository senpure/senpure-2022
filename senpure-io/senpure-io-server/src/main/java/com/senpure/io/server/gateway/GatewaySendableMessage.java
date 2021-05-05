package com.senpure.io.server.gateway;

import com.senpure.io.protocol.Message;
import com.senpure.io.server.MessageFrame;

public class GatewaySendableMessage implements MessageFrame {

    public  static final int MESSAGE_FROM_CONSUMER=1;
    public  static final int MESSAGE_FROM_GATEWAY=2;
    protected  int messageFrom;
    protected int messageType;
    protected int requestId;
    protected int messageId;

    protected long userId;
    protected long token;

    protected byte[] data;
    protected Message message;

    public GatewaySendableMessage(int messageFrom,int messageType) {
        this.messageType = messageType;
    }

    @Override
    public int messageType() {
        return messageType;
    }

    @Override
    public int requestId() {
        return requestId;
    }

    @Override
    public int messageId() {
        return messageId;
    }

    @Override
    public Message message() {
        return message;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getToken() {
        return token;
    }

    public void setToken(long token) {
        this.token = token;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public int getMessageFrom() {
        return messageFrom;
    }
}
