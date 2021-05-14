package com.senpure.io.server.gateway.provider.handler;

import com.senpure.io.server.ChannelAttributeUtil;
import com.senpure.io.server.gateway.GatewayReceiveProviderMessage;
import com.senpure.io.server.gateway.provider.ProviderManager;
import com.senpure.io.server.protocol.message.SCFrameworkVerifyMessage;
import io.netty.channel.Channel;

import java.util.Map;

public class SCFrameworkVerifyMessageHandler extends AbstractGatewayProviderMessageHandler {
    @Override
    public void execute(Channel channel, GatewayReceiveProviderMessage frame) {


    }


    @Override
    public int messageId() {
        return SCFrameworkVerifyMessage.MESSAGE_ID;
    }

    @Override
    public boolean stopConsumer() {
        return false;
    }
}
