package com.senpure.io.server.protocol.message;

import com.senpure.io.server.protocol.bean.Consumer;
import com.senpure.io.protocol.CompressMessage;
import io.netty.buffer.ByteBuf;

import java.util.List;
import java.util.ArrayList;

/**
 * @author senpure
 * @time 2021-5-31 20:56:51
 */
public class CSBindProviderMessage extends CompressMessage {

    public static final int MESSAGE_ID = 123;
    private String serverName;
    private String serverKey;
    private List<Consumer> consumers = new ArrayList<>(16);
    private int dataId;
    private byte [] data;

    public void copy(CSBindProviderMessage source) {
        this.serverName = source.getServerName();
        this.serverKey = source.getServerKey();
        this.consumers.clear();
        for (Consumer consumer : source.getConsumers()) {
            Consumer tempConsumer = new Consumer();
            tempConsumer.copy(consumer);
        }
        this.dataId = source.getDataId();
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
        for (Consumer value : consumers) {
             writeBean(buf, 27, value);
        }
        writeVar32(buf, 32, dataId);
        if (data != null) {
            writeBytes(buf, 43, data);
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
                case 27:// 3 << 3 | 3
                    Consumer tempConsumersBean = new Consumer();
                    readBean(buf,tempConsumersBean);
                    consumers.add(tempConsumersBean);
                    break;
                case 32:// 4 << 3 | 0
                    dataId = readVar32(buf);
                    break;
                case 43:// 5 << 3 | 3
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
        for (Consumer value : consumers) {
            size += computeBeanSize(1, value);
        }
        //tag size 32
        size += computeVar32Size(1, dataId);
        if (data != null) {
             //tag size 43
             size += computeBytesSize(1, data);
        }
        serializedSize = size ;
        return size ;
    }

    public String getServerName() {
        return serverName;
    }

    public CSBindProviderMessage setServerName(String serverName) {
        this.serverName = serverName;
        return this;
    }

    public String getServerKey() {
        return serverKey;
    }

    public CSBindProviderMessage setServerKey(String serverKey) {
        this.serverKey = serverKey;
        return this;
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public CSBindProviderMessage setConsumers(List<Consumer> consumers) {
        if (consumers == null) {
            this.consumers = new ArrayList<>(16);
            return this;
        }
        this.consumers = consumers;
        return this;
    }

    public int getDataId() {
        return dataId;
    }

    public CSBindProviderMessage setDataId(int dataId) {
        this.dataId = dataId;
        return this;
    }

    public byte [] getData() {
        return data;
    }

    public CSBindProviderMessage setData(byte [] data) {
        this.data = data;
        return this;
    }

    @Override
    public int messageType() {
        return MESSAGE_TYPE_CS;
    }

    @Override
    public int messageId() {
        return 123;
    }

    @Override
    public String toString() {
        return "CSBindProviderMessage[123]{"
                + "serverName=" + serverName
                + ",serverKey=" + serverKey
                + ",consumers=" + consumers
                + ",dataId=" + dataId
                + ",data=" + bytesToString(data)
                + "}";
    }

    @Override
    public String toString(String indent) {
        //10 + 3 = 13 个空格
        String nextIndent = "             ";
        //最长字段长度 10
        indent = indent == null ? "" : indent;
        StringBuilder sb = new StringBuilder();
        sb.append("CSBindProviderMessage").append("[123]").append("{");
        sb.append("\n");
        sb.append(indent).append("serverName = ").append(serverName);
        sb.append("\n");
        sb.append(indent).append("serverKey  = ").append(serverKey);
        sb.append("\n");
        sb.append(indent).append("consumers  = ");
        appendBeans(sb,consumers,indent,nextIndent);
        sb.append("\n");
        sb.append(indent).append("dataId     = ").append(dataId);
        sb.append("\n");
        sb.append(indent).append("data       = ").append(bytesToString(data));
        sb.append("\n");
        sb.append(indent).append("}");
        return sb.toString();
    }

}