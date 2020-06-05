package com.senpure.io.server.protocol.message;

import com.senpure.io.server.protocol.bean.Statistic;
import com.senpure.io.protocol.CompressMessage;
import io.netty.buffer.ByteBuf;

/**
 * @author senpure
 * @time 2020-6-5 14:22:50
 */
public class SCStatisticMessage extends CompressMessage {

    public static final int MESSAGE_ID = 114;
    private Statistic statistic;

    public void copy(SCStatisticMessage source) {
        if (source.getStatistic() != null) {
            Statistic tempStatistic = new Statistic();
            tempStatistic.copy(source.getStatistic());
            this.statistic = tempStatistic;
        } else {
            this.statistic = null;
            }
    }

    /**
     * 写入字节缓存
     */
    @Override
    public void write(ByteBuf buf) {
        getSerializedSize();
        if (statistic != null) {
            writeBean(buf, 11, statistic);
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
                    statistic = new Statistic();
                    readBean(buf,statistic);
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
        if (statistic != null) {
             //tag size 11
            size += computeBeanSize(1, statistic);
        }
        serializedSize = size ;
        return size ;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public SCStatisticMessage setStatistic(Statistic statistic) {
        this.statistic = statistic;
        return this;
    }

    @Override
    public int getMessageId() {
        return 114;
    }

    @Override
    public String toString() {
        return "SCStatisticMessage[114]{"
                + "statistic=" + statistic
                + "}";
    }

    @Override
    public String toString(String indent) {
        //9 + 3 = 12 个空格
        String nextIndent = "            ";
        //最长字段长度 9
        indent = indent == null ? "" : indent;
        StringBuilder sb = new StringBuilder();
        sb.append("SCStatisticMessage").append("[114]").append("{");
        sb.append("\n");
        sb.append(indent).append("statistic = ");
        if (statistic != null){
            sb.append(statistic.toString(indent+nextIndent));
        } else {
            sb.append("null");
        }
        sb.append("\n");
        sb.append(indent).append("}");
        return sb.toString();
    }

}