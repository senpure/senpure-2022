package com.senpure.io.server.protocol.message;

import com.senpure.io.protocol.CompressMessage;
import io.netty.buffer.ByteBuf;

/**
 * 消费者认证
 * 
 * @author senpure
 * @time 2021-5-6 19:44:19
 */
public class CSFrameworkVerifyMessage extends CompressMessage {

    public static final int MESSAGE_ID = 103;
    //服务名
    private String serverName;
    //服务实例唯一标识
    private String serverKey;
    //ip
    private String ip;
    private String token;

    public void copy(CSFrameworkVerifyMessage source) {
        this.serverName = source.getServerName();
        this.serverKey = source.getServerKey();
        this.ip = source.getIp();
        this.token = source.getToken();
    }

    /**
     * 写入字节缓存
     */
    @Override
    public void write(ByteBuf buf) {
        serializedSize();
        //服务名
        if (serverName != null) {
            writeString(buf, 11, serverName);
        }
        //服务实例唯一标识
        if (serverKey != null) {
            writeString(buf, 19, serverKey);
        }
        //ip
        if (ip != null) {
            writeString(buf, 27, ip);
        }
        if (token != null) {
            writeString(buf, 35, token);
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
                //服务名
                case 11:// 1 << 3 | 3
                    serverName = readString(buf);
                    break;
                //服务实例唯一标识
                case 19:// 2 << 3 | 3
                    serverKey = readString(buf);
                    break;
                //ip
                case 27:// 3 << 3 | 3
                    ip = readString(buf);
                    break;
                case 35:// 4 << 3 | 3
                    token = readString(buf);
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
        //服务名
        if (serverName != null) {
             //tag size 11
             size += computeStringSize(1, serverName);
        }
        //服务实例唯一标识
        if (serverKey != null) {
             //tag size 19
             size += computeStringSize(1, serverKey);
        }
        //ip
        if (ip != null) {
             //tag size 27
             size += computeStringSize(1, ip);
        }
        if (token != null) {
             //tag size 35
             size += computeStringSize(1, token);
        }
        serializedSize = size ;
        return size ;
    }

    /**
     * get 服务名
     *
     * @return
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * set 服务名
     */
    public CSFrameworkVerifyMessage setServerName(String serverName) {
        this.serverName = serverName;
        return this;
    }

    /**
     * get 服务实例唯一标识
     *
     * @return
     */
    public String getServerKey() {
        return serverKey;
    }

    /**
     * set 服务实例唯一标识
     */
    public CSFrameworkVerifyMessage setServerKey(String serverKey) {
        this.serverKey = serverKey;
        return this;
    }

    /**
     * get ip
     *
     * @return
     */
    public String getIp() {
        return ip;
    }

    /**
     * set ip
     */
    public CSFrameworkVerifyMessage setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getToken() {
        return token;
    }

    public CSFrameworkVerifyMessage setToken(String token) {
        this.token = token;
        return this;
    }

    @Override
    public int messageType() {
        return MESSAGE_TYPE_CS;
    }

    @Override
    public int messageId() {
        return 103;
    }

    @Override
    public String toString() {
        return "CSConsumerVerifyMessage[103]{"
                + "serverName=" + serverName
                + ",serverKey=" + serverKey
                + ",ip=" + ip
                + ",token=" + token
                + "}";
    }

    @Override
    public String toString(String indent) {
        //最长字段长度 10
        indent = indent == null ? "" : indent;
        StringBuilder sb = new StringBuilder();
        sb.append("CSConsumerVerifyMessage").append("[103]").append("{");
        //服务名
        sb.append("\n");
        sb.append(indent).append("serverName = ").append(serverName);
        //服务实例唯一标识
        sb.append("\n");
        sb.append(indent).append("serverKey  = ").append(serverKey);
        //ip
        sb.append("\n");
        sb.append(indent).append("ip         = ").append(ip);
        sb.append("\n");
        sb.append(indent).append("token      = ").append(token);
        sb.append("\n");
        sb.append(indent).append("}");
        return sb.toString();
    }

}