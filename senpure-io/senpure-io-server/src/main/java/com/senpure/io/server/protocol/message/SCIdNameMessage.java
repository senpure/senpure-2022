package com.senpure.io.server.protocol.message;

import com.senpure.io.server.protocol.bean.IdName;
import com.senpure.io.protocol.CompressMessage;
import io.netty.buffer.ByteBuf;

import java.util.List;
import java.util.ArrayList;

/**
 * 数字id与字符串的关联
 * 
 * @author senpure
 * @time 2020-11-20 17:37:42
 */
public class SCIdNameMessage extends CompressMessage {

    public static final int MESSAGE_ID = 106;
    private List<IdName> idNames = new ArrayList<>(16);

    public void copy(SCIdNameMessage source) {
        this.idNames.clear();
        for (IdName idName : source.getIdNames()) {
            IdName tempIdName = new IdName();
            tempIdName.copy(idName);
        }
    }

    /**
     * 写入字节缓存
     */
    @Override
    public void write(ByteBuf buf) {
        getSerializedSize();
        for (IdName value : idNames) {
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
                    IdName tempIdNamesBean = new IdName();
                    readBean(buf,tempIdNamesBean);
                    idNames.add(tempIdNamesBean);
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
        for (IdName value : idNames) {
            size += computeBeanSize(1, value);
        }
        serializedSize = size ;
        return size ;
    }

    public List<IdName> getIdNames() {
        return idNames;
    }

    public SCIdNameMessage setIdNames(List<IdName> idNames) {
        if (idNames == null) {
            this.idNames = new ArrayList<>(16);
            return this;
        }
        this.idNames = idNames;
        return this;
    }

    @Override
    public int getMessageId() {
        return 106;
    }

    @Override
    public String toString() {
        return "SCIdNameMessage[106]{"
                + "idNames=" + idNames
                + "}";
    }

    @Override
    public String toString(String indent) {
        //7 + 3 = 10 个空格
        String nextIndent = "          ";
        //最长字段长度 7
        indent = indent == null ? "" : indent;
        StringBuilder sb = new StringBuilder();
        sb.append("SCIdNameMessage").append("[106]").append("{");
        sb.append("\n");
        sb.append(indent).append("idNames = ");
        appendBeans(sb,idNames,indent,nextIndent);
        sb.append("\n");
        sb.append(indent).append("}");
        return sb.toString();
    }

}