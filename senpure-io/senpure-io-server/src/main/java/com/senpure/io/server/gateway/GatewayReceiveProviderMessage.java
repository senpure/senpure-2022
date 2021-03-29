package com.senpure.io.server.gateway;


import com.senpure.io.server.support.MessageIdReader;

import java.util.Arrays;


public class GatewayReceiveProviderMessage {

    private Long[] userIds;

    private int  requestId;
    private long token;
    private int messageId;

    private byte[] data;


    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }


    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Long[] getUserIds() {
        return userIds;
    }

    public void setUserIds(Long[] userIds) {
        this.userIds = userIds;
    }

    public long getToken() {
        return token;
    }

    public void setToken(long token) {
        this.token = token;
    }


    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String toString() {

        return "SG{" +
                "messageId=" + MessageIdReader.read(messageId) +
                ",token=" + token +
                ", userIds=" + Arrays.toString(userIds) +
                ", dataLen=" + (data == null ? 0 : data.length) +
                '}';
    }


}
