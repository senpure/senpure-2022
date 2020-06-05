package com.senpure.io.server.protocol.message;

import com.senpure.io.protocol.CompressMessage;
import io.netty.buffer.ByteBuf;

/**
 * 网关注册处理消息返回
 * 
 * @author senpure
 * @time 2020-6-5 14:22:50
 */
public class CSRegServerHandleMessageMessage extends CompressMessage {

    public static final int MESSAGE_ID = 101;
    private boolean success;
    private String message;

    public void copy(CSRegServerHandleMessageMessage source) {
        this.success = source.isSuccess();
        this.message = source.getMessage();
    }

    /**
     * 写入字节缓存
     */
    @Override
    public void write(ByteBuf buf) {
        getSerializedSize();
        writeBoolean(buf, 8, success);
        if (message != null) {
            writeString(buf, 19, message);
        }
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
                case 19:// 2 << 3 | 3
                    message = readString(buf);
                    break;
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
        //tag size 8
        size += computeBooleanSize(1, success);
        if (message != null) {
             //tag size 19
             size += computeStringSize(1, message);
        }
        serializedSize = size ;
        return size ;
    }

    public boolean isSuccess() {
        return success;
    }

    public CSRegServerHandleMessageMessage setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public CSRegServerHandleMessageMessage setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public int getMessageId() {
        return 101;
    }

    @Override
    public String toString() {
        return "CSRegServerHandleMessageMessage[101]{"
                + "success=" + success
                + ",message=" + message
                + "}";
    }

    @Override
    public String toString(String indent) {
        //最长字段长度 7
        indent = indent == null ? "" : indent;
        StringBuilder sb = new StringBuilder();
        sb.append("CSRegServerHandleMessageMessage").append("[101]").append("{");
        sb.append("\n");
        sb.append(indent).append("success = ").append(success);
        sb.append("\n");
        sb.append(indent).append("message = ").append(message);
        sb.append("\n");
        sb.append(indent).append("}");
        return sb.toString();
    }

}