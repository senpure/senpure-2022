package com.senpure.io.server.protocol.message;

import com.senpure.io.protocol.CompressMessage;
import io.netty.buffer.ByteBuf;

/**
 * @author senpure
 * @time 2021-5-6 19:44:19
 */
public class SCConsumerVerifyMessage extends CompressMessage {

    public static final int MESSAGE_ID = 104;

    public void copy(SCConsumerVerifyMessage source) {
    }

    /**
     * 写入字节缓存
     */
    @Override
    public void write(ByteBuf buf) {
        serializedSize();
    }

    /**
     * 读取字节缓存
     */
    @Override
    public void read(ByteBuf buf, int endIndex) {
        while (true) {
            int tag = readTag(buf, endIndex);
            switch (tag) {
                case 0://end
                    return;
                default://skip
                    skip(buf, tag);
                    break;
            }
        }
    }

    private int serializedSize = -1;

    @Override
    public int serializedSize() {
        int size = serializedSize;
        if (size != -1) {
            return size;
        }
        size = 0;
        serializedSize = size ;
        return size ;
    }


    @Override
    public int messageType() {
        return MESSAGE_TYPE_SC;
    }

    @Override
    public int messageId() {
        return 104;
    }

    @Override
    public String toString() {
        return "SCConsumerVerifyMessage[104]{"
                + "}";
    }

    @Override
    public String toString(String indent) {
        indent = indent == null ? "" : indent;
        StringBuilder sb = new StringBuilder();
        sb.append("SCConsumerVerifyMessage").append("[104]").append("{");
        sb.append("\n");
        sb.append(indent).append("}");
        return sb.toString();
    }

}