package com.senpure.io.server.provider;

import com.senpure.io.protocol.Message;
import com.senpure.io.server.AbstractMessageFrame;

import java.util.Arrays;


public class ProviderSendMessage extends AbstractMessageFrame {


    private Long[] userIds;

    private long token;

    public ProviderSendMessage(Message message) {
        super(message);
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

    @Override
    public String toString() {
        return "ProviderSendMessage{" +
                "requestId=" + requestId() +
                ", userIds=" + Arrays.toString(userIds) +
                ", token=" + token +
                ", message=" + message().getClass().getSimpleName() +
                '}';
    }
}
