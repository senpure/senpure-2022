package com.senpure.io.server.protocol.message;

import com.senpure.io.server.protocol.bean.Consumer;
import com.senpure.io.protocol.CompressMessage;
import io.netty.buffer.ByteBuf;

import java.util.List;
import java.util.ArrayList;

/**
 * 加入匹配
 * 
 * @author senpure
 * @time 2021-5-20 17:05:32
 */
public class CSMatchingMessage extends CompressMessage {

    public static final int MESSAGE_ID = 121;
    private List<Consumer> consumers = new ArrayList<>(16);

    public void copy(CSMatchingMessage source) {
        this.consumers.clear();
        for (Consumer consumer : source.getConsumers()) {
            Consumer tempConsumer = new Consumer();
            tempConsumer.copy(consumer);
        }
    }

    /**
     * 写入字节缓存
     */
    @Override
    public void write(ByteBuf buf) {
        serializedSize();
        for (Consumer value : consumers) {
             writeBean(buf, 11, value);
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
                    Consumer tempConsumersBean = new Consumer();
                    readBean(buf,tempConsumersBean);
                    consumers.add(tempConsumersBean);
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
        for (Consumer value : consumers) {
            size += computeBeanSize(1, value);
        }
        serializedSize = size ;
        return size ;
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public CSMatchingMessage setConsumers(List<Consumer> consumers) {
        if (consumers == null) {
            this.consumers = new ArrayList<>(16);
            return this;
        }
        this.consumers = consumers;
        return this;
    }

    @Override
    public int messageType() {
        return MESSAGE_TYPE_CS;
    }

    @Override
    public int messageId() {
        return 121;
    }

    @Override
    public String toString() {
        return "CSMatchingMessage[121]{"
                + "consumers=" + consumers
                + "}";
    }

    @Override
    public String toString(String indent) {
        //9 + 3 = 12 个空格
        String nextIndent = "            ";
        //最长字段长度 9
        indent = indent == null ? "" : indent;
        StringBuilder sb = new StringBuilder();
        sb.append("CSMatchingMessage").append("[121]").append("{");
        sb.append("\n");
        sb.append(indent).append("consumers = ");
        appendBeans(sb,consumers,indent,nextIndent);
        sb.append("\n");
        sb.append(indent).append("}");
        return sb.toString();
    }

}