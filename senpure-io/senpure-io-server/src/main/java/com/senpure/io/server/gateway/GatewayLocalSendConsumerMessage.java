package com.senpure.io.server.gateway;

import com.senpure.io.protocol.Message;
import com.senpure.io.server.AbstractMessageFrame;

public class GatewayLocalSendConsumerMessage  extends AbstractMessageFrame implements GatewaySendConsumerMessage{

   private  long token;
    public GatewayLocalSendConsumerMessage(Message message) {
        super(message);
    }

    public void setToken(long token) {
        this.token = token;
    }
}
