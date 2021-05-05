package com.senpure.io.server.protocol.message;

import com.senpure.io.protocol.CompressMessage;
import io.netty.buffer.ByteBuf;

/**
 * @author senpure
 * @time 2021-5-5 15:28:47
 */
public class SCKickOffMessage extends CompressMessage {

    public static final int MESSAGE_ID = 114;
    //token
    private long token;
    //userId
    private long userId;

    public void copy(SCKickOffMessage source) {
        this.token = source.getToken();
        this.userId = source.getUserId();
    }

    /**
     * 写入字节缓存
     */
    @Override
    public void write(ByteBuf buf) {
        serializedSize();
        //token
        writeVar64(buf, 8, token);
        //userId
        writeVar64(buf, 16, userId);
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
                //token
                case 8:// 1 << 3 | 0
                    token = readVar64(buf);
                    break;
                //userId
                case 16:// 2 << 3 | 0
                    userId = readVar64(buf);
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
        //token
        //tag size 8
        size += computeVar64Size(1, token);
        //userId
        //tag size 16
        size += computeVar64Size(1, userId);
        serializedSize = size ;
        return size ;
    }

    /**
     * get token
     *
     * @return
     */
    public long getToken() {
        return token;
    }

    /**
     * set token
     */
    public SCKickOffMessage setToken(long token) {
        this.token = token;
        return this;
    }

    /**
     * get userId
     *
     * @return
     */
    public long getUserId() {
        return userId;
    }

    /**
     * set userId
     */
    public SCKickOffMessage setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public int messageType() {
        return MESSAGE_TYPE_SC;
    }

    @Override
    public int messageId() {
        return 114;
    }

    @Override
    public String toString() {
        return "SCKickOffMessage[114]{"
                + "token=" + token
                + ",userId=" + userId
                + "}";
    }

    @Override
    public String toString(String indent) {
        //最长字段长度 6
        indent = indent == null ? "" : indent;
        StringBuilder sb = new StringBuilder();
        sb.append("SCKickOffMessage").append("[114]").append("{");
        //token
        sb.append("\n");
        sb.append(indent).append("token  = ").append(token);
        //userId
        sb.append("\n");
        sb.append(indent).append("userId = ").append(userId);
        sb.append("\n");
        sb.append(indent).append("}");
        return sb.toString();
    }

}