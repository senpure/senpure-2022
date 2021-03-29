package com.senpure.io.server.protocol.message;

import com.senpure.io.protocol.CompressMessage;
import com.senpure.io.server.protocol.bean.HandleMessage;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务器注册消息处理器到网关
 * 
 * @author senpure
 * @time 2020-11-20 17:37:42
 */
public class SCRegServerHandleMessageMessage extends CompressMessage {

    public static final int MESSAGE_ID = 102;
    //服务名
    private String serverName;
    //服务实例唯一标识
    private String serverKey;
    //服务名
    private String readableServerName;
    //可以处理的消息
    private List<HandleMessage> messages = new ArrayList<>(16);

    public void copy(SCRegServerHandleMessageMessage source) {
        this.serverName = source.getServerName();
        this.serverKey = source.getServerKey();
        this.readableServerName = source.getReadableServerName();
        this.messages.clear();
        for (HandleMessage handleMessage : source.getMessages()) {
            HandleMessage tempHandleMessage = new HandleMessage();
            tempHandleMessage.copy(handleMessage);
        }
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
        //服务名
        if (readableServerName != null) {
            writeString(buf, 27, readableServerName);
        }
        //可以处理的消息
        for (HandleMessage value : messages) {
             writeBean(buf, 35, value);
        }
    }

    /**
     * 读取字节缓存
     */
    @Override
    public void read(ByteBuf buf, int maxIndex) {
        while (true) {
            int tag = readTag(buf, maxIndex);
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
                //服务名
                case 27:// 3 << 3 | 3
                    readableServerName = readString(buf);
                    break;
                //可以处理的消息
                case 35:// 4 << 3 | 3
                    HandleMessage tempMessagesBean = new HandleMessage();
                    readBean(buf,tempMessagesBean);
                    messages.add(tempMessagesBean);
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
        //服务名
        if (readableServerName != null) {
             //tag size 27
             size += computeStringSize(1, readableServerName);
        }
        //可以处理的消息
        for (HandleMessage value : messages) {
            size += computeBeanSize(1, value);
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
    public SCRegServerHandleMessageMessage setServerName(String serverName) {
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
    public SCRegServerHandleMessageMessage setServerKey(String serverKey) {
        this.serverKey = serverKey;
        return this;
    }

    /**
     * get 服务名
     *
     * @return
     */
    public String getReadableServerName() {
        return readableServerName;
    }

    /**
     * set 服务名
     */
    public SCRegServerHandleMessageMessage setReadableServerName(String readableServerName) {
        this.readableServerName = readableServerName;
        return this;
    }

     /**
      * get 可以处理的消息
      *
      * @return
      */
    public List<HandleMessage> getMessages() {
        return messages;
    }

     /**
      * set 可以处理的消息
      */
    public SCRegServerHandleMessageMessage setMessages(List<HandleMessage> messages) {
        if (messages == null) {
            this.messages = new ArrayList<>(16);
            return this;
        }
        this.messages = messages;
        return this;
    }

    @Override
    public int messageId() {
        return 102;
    }

    @Override
    public String toString() {
        return "SCRegServerHandleMessageMessage[102]{"
                + "serverName=" + serverName
                + ",serverKey=" + serverKey
                + ",readableServerName=" + readableServerName
                + ",messages=" + messages
                + "}";
    }

    @Override
    public String toString(String indent) {
        //18 + 3 = 21 个空格
        String nextIndent = "                     ";
        //最长字段长度 18
        indent = indent == null ? "" : indent;
        StringBuilder sb = new StringBuilder();
        sb.append("SCRegServerHandleMessageMessage").append("[102]").append("{");
        //服务名
        sb.append("\n");
        sb.append(indent).append("serverName         = ").append(serverName);
        //服务实例唯一标识
        sb.append("\n");
        sb.append(indent).append("serverKey          = ").append(serverKey);
        //服务名
        sb.append("\n");
        sb.append(indent).append("readableServerName = ").append(readableServerName);
        //可以处理的消息
        sb.append("\n");
        sb.append(indent).append("messages           = ");
        appendBeans(sb,messages,indent,nextIndent);
        sb.append("\n");
        sb.append(indent).append("}");
        return sb.toString();
    }

}