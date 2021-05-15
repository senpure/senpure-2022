package com.senpure.io.server.protocol.message;

import com.senpure.io.protocol.CompressMessage;
import io.netty.buffer.ByteBuf;

/**
 * 加入匹配
 * 
 * @author senpure
 * @time 2021-5-15 15:39:34
 */
public class SCMatchingMessage extends CompressMessage {

    public static final int MESSAGE_ID = 122;
    private boolean success;

    public void copy(SCMatchingMessage source) {
        this.success = source.isSuccess();
    }

    /**
     * 写入字节缓存
     */
    @Override
    public void write(ByteBuf buf) {
        serializedSize();
        writeBoolean(buf, 8, success);
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
                case 8:// 1 << 3 | 0
                    success = readBoolean(buf);
                    break;
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
        //tag size 8
        size += computeBooleanSize(1, success);
        serializedSize = size ;
        return size ;
    }

    public boolean isSuccess() {
        return success;
    }

    public SCMatchingMessage setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    @Override
    public int messageType() {
        return MESSAGE_TYPE_SC;
    }

    @Override
    public int messageId() {
        return 122;
    }

    @Override
    public String toString() {
        return "SCMatchingMessage[122]{"
                + "success=" + success
                + "}";
    }

    @Override
    public String toString(String indent) {
        //最长字段长度 7
        indent = indent == null ? "" : indent;
        StringBuilder sb = new StringBuilder();
        sb.append("SCMatchingMessage").append("[122]").append("{");
        sb.append("\n");
        sb.append(indent).append("success = ").append(success);
        sb.append("\n");
        sb.append(indent).append("}");
        return sb.toString();
    }

}