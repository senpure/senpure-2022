package com.senpure.io.server.protocol.message;

import com.senpure.io.protocol.CompressMessage;
import io.netty.buffer.ByteBuf;

/**
 * @author senpure
 * @time 2021-5-6 19:44:19
 */
public class SCMessageForwardMessage extends CompressMessage {

    public static final int MESSAGE_ID = 118;
    private String serverName;
    private String serverKey;
    private int id;
    private byte [] data;

    public void copy(SCMessageForwardMessage source) {
        this.serverName = source.getServerName();
        this.serverKey = source.getServerKey();
        this.id = source.getId();
        if (source.getData() != null) {
            this.data = copyBytes(source.getData());
        } else {
            this.data = null;
        }
    }

    /**
     * 写入字节缓存
     */
    @Override
    public void write(ByteBuf buf) {
        serializedSize();
        if (serverName != null) {
            writeString(buf, 11, serverName);
        }
        if (serverKey != null) {
            writeString(buf, 19, serverKey);
        }
        writeVar32(buf, 24, id);
        if (data != null) {
            writeBytes(buf, 35, data);
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
                case 11:// 1 << 3 | 3
                    serverName = readString(buf);
                    break;
                case 19:// 2 << 3 | 3
                    serverKey = readString(buf);
                    break;
                case 24:// 3 << 3 | 0
                    id = readVar32(buf);
                    break;
                case 35:// 4 << 3 | 3
                    data = readBytes(buf);
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
        if (serverName != null) {
             //tag size 11
             size += computeStringSize(1, serverName);
        }
        if (serverKey != null) {
             //tag size 19
             size += computeStringSize(1, serverKey);
        }
        //tag size 24
        size += computeVar32Size(1, id);
        if (data != null) {
             //tag size 35
             size += computeBytesSize(1, data);
        }
        serializedSize = size ;
        return size ;
    }

    public String getServerName() {
        return serverName;
    }

    public SCMessageForwardMessage setServerName(String serverName) {
        this.serverName = serverName;
        return this;
    }

    public String getServerKey() {
        return serverKey;
    }

    public SCMessageForwardMessage setServerKey(String serverKey) {
        this.serverKey = serverKey;
        return this;
    }

    public int getId() {
        return id;
    }

    public SCMessageForwardMessage setId(int id) {
        this.id = id;
        return this;
    }

    public byte [] getData() {
        return data;
    }

    public SCMessageForwardMessage setData(byte [] data) {
        this.data = data;
        return this;
    }

    @Override
    public int messageType() {
        return MESSAGE_TYPE_SC;
    }

    @Override
    public int messageId() {
        return 118;
    }

    @Override
    public String toString() {
        return "SCMessageForwardMessage[118]{"
                + "serverName=" + serverName
                + ",serverKey=" + serverKey
                + ",id=" + id
                + ",data=" + bytesToString(data)
                + "}";
    }

    @Override
    public String toString(String indent) {
        //最长字段长度 10
        indent = indent == null ? "" : indent;
        StringBuilder sb = new StringBuilder();
        sb.append("SCMessageForwardMessage").append("[118]").append("{");
        sb.append("\n");
        sb.append(indent).append("serverName = ").append(serverName);
        sb.append("\n");
        sb.append(indent).append("serverKey  = ").append(serverKey);
        sb.append("\n");
        sb.append(indent).append("id         = ").append(id);
        sb.append("\n");
        sb.append(indent).append("data       = ").append(bytesToString(data));
        sb.append("\n");
        sb.append(indent).append("}");
        return sb.toString();
    }

}