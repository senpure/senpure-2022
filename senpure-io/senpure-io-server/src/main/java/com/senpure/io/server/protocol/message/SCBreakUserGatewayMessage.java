package com.senpure.io.server.protocol.message;

import com.senpure.io.protocol.CompressMessage;
import io.netty.buffer.ByteBuf;

/**
 * 断开用户与网关
 * 
 * @author senpure
 * @time 2020-6-5 14:22:50
 */
public class SCBreakUserGatewayMessage extends CompressMessage {

    public static final int MESSAGE_ID = 108;

    public void copy(SCBreakUserGatewayMessage source) {
    }

    /**
     * 写入字节缓存
     */
    @Override
    public void write(ByteBuf buf) {
        getSerializedSize();
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
    public int getSerializedSize() {
        int size = serializedSize;
        if (size != -1) {
            return size;
        }
        size = 0;
        serializedSize = size ;
        return size ;
    }


    @Override
    public int getMessageId() {
        return 108;
    }

    @Override
    public String toString() {
        return "SCBreakUserGatewayMessage[108]{"
                + "}";
    }

    @Override
    public String toString(String indent) {
        indent = indent == null ? "" : indent;
        StringBuilder sb = new StringBuilder();
        sb.append("SCBreakUserGatewayMessage").append("[108]").append("{");
        sb.append("\n");
        sb.append(indent).append("}");
        return sb.toString();
    }

}