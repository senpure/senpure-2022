package com.senpure.io.server;

import com.senpure.io.protocol.Message;
import io.netty.buffer.ByteBuf;

public class NettyMessage implements Message {
    private final int messageId;
    private final int messageLength;
    private final byte[] data;

    public NettyMessage(int messageId, int messageLength, byte[] data) {
        this.messageId = messageId;
        this.messageLength = messageLength;
        this.data = data;
    }

    @Override
    public void write(ByteBuf buf) {
        buf.writeBytes(data);
    }

    @Override
    public void read(ByteBuf buf, int maxIndex) {

    }

    @Override
    public String toString(String indent) {
        return "NettyMessage[" + messageId + "]";
    }

    @Override
    public int serializedSize() {
        return messageLength;
    }

    @Override
    public int messageId() {
        return messageId;
    }
}
