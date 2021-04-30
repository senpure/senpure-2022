package com.senpure.io.server.gateway;


import com.senpure.io.server.support.MessageIdReader;

public class GatewayReceiveConsumerMessage extends GatewaySendableMessage {


    public GatewayReceiveConsumerMessage() {
        super(MESSAGE_TYPE_CS);
    }


    @Override
    public String toString() {
        return "{[" + userId + "]-->" + MessageIdReader.read(messageId) + "}";
    }


}
