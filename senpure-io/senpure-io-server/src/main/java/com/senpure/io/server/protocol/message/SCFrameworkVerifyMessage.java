package com.senpure.io.server.protocol.message;

import com.senpure.io.protocol.CompressMessage;
import io.netty.buffer.ByteBuf;

/**
 * 框架内部验证结果
 * 
 * @author senpure
 * @time 2021-5-17 10:55:33
 */
public class SCFrameworkVerifyMessage extends CompressMessage {

    public static final int MESSAGE_ID = 102;
    private boolean success;
    //message
    private String message;
    //认证分配的id
    private String userId;

    public void copy(SCFrameworkVerifyMessage source) {
        this.success = source.isSuccess();
        this.message = source.getMessage();
        this.userId = source.getUserId();
    }

    /**
     * 写入字节缓存
     */
    @Override
    public void write(ByteBuf buf) {
        serializedSize();
        writeBoolean(buf, 8, success);
        //message
        if (message != null) {
            writeString(buf, 19, message);
        }
        //认证分配的id
        if (userId != null) {
            writeString(buf, 27, userId);
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
                //message
                case 19:// 2 << 3 | 3
                    message = readString(buf);
                    break;
                //认证分配的id
                case 27:// 3 << 3 | 3
                    userId = readString(buf);
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
        //message
        if (message != null) {
             //tag size 19
             size += computeStringSize(1, message);
        }
        //认证分配的id
        if (userId != null) {
             //tag size 27
             size += computeStringSize(1, userId);
        }
        serializedSize = size ;
        return size ;
    }

    public boolean isSuccess() {
        return success;
    }

    public SCFrameworkVerifyMessage setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    /**
     * get message
     *
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * set message
     */
    public SCFrameworkVerifyMessage setMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * get 认证分配的id
     *
     * @return
     */
    public String getUserId() {
        return userId;
    }

    /**
     * set 认证分配的id
     */
    public SCFrameworkVerifyMessage setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public int messageType() {
        return MESSAGE_TYPE_SC;
    }

    @Override
    public int messageId() {
        return 102;
    }

    @Override
    public String toString() {
        return "SCFrameworkVerifyMessage[102]{"
                + "success=" + success
                + ",message=" + message
                + ",userId=" + userId
                + "}";
    }

    @Override
    public String toString(String indent) {
        //最长字段长度 7
        indent = indent == null ? "" : indent;
        StringBuilder sb = new StringBuilder();
        sb.append("SCFrameworkVerifyMessage").append("[102]").append("{");
        sb.append("\n");
        sb.append(indent).append("success = ").append(success);
        //message
        sb.append("\n");
        sb.append(indent).append("message = ").append(message);
        //认证分配的id
        sb.append("\n");
        sb.append(indent).append("userId  = ").append(userId);
        sb.append("\n");
        sb.append(indent).append("}");
        return sb.toString();
    }

}