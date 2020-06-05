package com.senpure.io.server.protocol.message;

import com.senpure.io.protocol.CompressMessage;
import io.netty.buffer.ByteBuf;

/**
 * @author senpure
 * @time 2020-6-5 14:22:50
 */
public class CSAskHandleMessage extends CompressMessage {

    public static final int MESSAGE_ID = 109;
    //askToken
    private long askToken;
    private int fromMessageId;
    //值
    private String askValue;

    public void copy(CSAskHandleMessage source) {
        this.askToken = source.getAskToken();
        this.fromMessageId = source.getFromMessageId();
        this.askValue = source.getAskValue();
    }

    /**
     * 写入字节缓存
     */
    @Override
    public void write(ByteBuf buf) {
        getSerializedSize();
        //askToken
        writeVar64(buf, 8, askToken);
        writeVar32(buf, 16, fromMessageId);
        //值
        if (askValue != null) {
            writeString(buf, 27, askValue);
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
                //askToken
                case 8:// 1 << 3 | 0
                    askToken = readVar64(buf);
                    break;
                case 16:// 2 << 3 | 0
                    fromMessageId = readVar32(buf);
                    break;
                //值
                case 27:// 3 << 3 | 3
                    askValue = readString(buf);
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
        //askToken
        //tag size 8
        size += computeVar64Size(1, askToken);
        //tag size 16
        size += computeVar32Size(1, fromMessageId);
        //值
        if (askValue != null) {
             //tag size 27
             size += computeStringSize(1, askValue);
        }
        serializedSize = size ;
        return size ;
    }

    /**
     * get askToken
     *
     * @return
     */
    public long getAskToken() {
        return askToken;
    }

    /**
     * set askToken
     */
    public CSAskHandleMessage setAskToken(long askToken) {
        this.askToken = askToken;
        return this;
    }

    public int getFromMessageId() {
        return fromMessageId;
    }

    public CSAskHandleMessage setFromMessageId(int fromMessageId) {
        this.fromMessageId = fromMessageId;
        return this;
    }

    public String getAskValue() {
        return askValue;
    }

    public CSAskHandleMessage setAskValue(String askValue) {
        this.askValue = askValue;
        return this;
    }

    @Override
    public int getMessageId() {
        return 109;
    }

    @Override
    public String toString() {
        return "CSAskHandleMessage[109]{"
                + "askToken=" + askToken
                + ",fromMessageId=" + fromMessageId
                + ",askValue=" + askValue
                + "}";
    }

    @Override
    public String toString(String indent) {
        //最长字段长度 13
        indent = indent == null ? "" : indent;
        StringBuilder sb = new StringBuilder();
        sb.append("CSAskHandleMessage").append("[109]").append("{");
        //askToken
        sb.append("\n");
        sb.append(indent).append("askToken      = ").append(askToken);
        sb.append("\n");
        sb.append(indent).append("fromMessageId = ").append(fromMessageId);
        //值
        sb.append("\n");
        sb.append(indent).append("askValue      = ").append(askValue);
        sb.append("\n");
        sb.append(indent).append("}");
        return sb.toString();
    }

}