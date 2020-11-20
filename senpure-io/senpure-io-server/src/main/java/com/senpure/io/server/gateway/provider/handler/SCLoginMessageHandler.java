package com.senpure.io.server.gateway.provider.handler;

import com.senpure.io.server.ChannelAttributeUtil;
import com.senpure.io.server.gateway.ProviderManager;
import com.senpure.io.server.gateway.Server2GatewayMessage;
import io.netty.channel.Channel;

import java.util.Map;

public class SCLoginMessageHandler extends  SCConsumerVerifyMessageHandler {


    @Override
    public int handleMessageId() {
        return messageExecutor.getScLoginMessageId();
    }

    @Override
    public boolean stopResponse() {
        return false;
    }
}
