package com.senpure.io.server;

import com.senpure.io.protocol.Message;

public class AbstractMessageFrame implements MessageFrame {

    private int requestId;
    private final int messageId;
    private final int messageType;
    private final Message message;

    public AbstractMessageFrame(Message message) {

        this.message = message;
        this.messageId = message.messageId();
        this.messageType = messageType();
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

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }
}
