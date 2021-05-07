package com.senpure.io.server.gateway;

import com.senpure.io.protocol.Message;
import com.senpure.io.server.AbstractMessageFrame;

public class GatewayLocalSendProviderMessage extends AbstractMessageFrame implements GatewaySendProviderMessage {

    private long token;
    private long userId;

    public GatewayLocalSendProviderMessage(Message message) {
        super(message);
    }

    public void setToken(long token) {
        this.token = token;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public long token() {
        return token;
    }

    @Override
    public long userId() {
        return userId;
    }
}
