package com.senpure.io.server.gateway.provider.handler;

import com.senpure.io.server.ChannelAttributeUtil;
import com.senpure.io.server.gateway.GatewayReceiveProviderMessage;
import com.senpure.io.server.gateway.provider.Provider;
import com.senpure.io.server.gateway.provider.ProviderManager;
import com.senpure.io.server.protocol.message.SCStatisticMessage;
import io.netty.channel.Channel;

public class SCStatisticMessageHandler  extends AbstractGatewayProviderMessageHandler {

    @Override
    public void execute(Channel channel, GatewayReceiveProviderMessage frame) {
        SCStatisticMessage message = new SCStatisticMessage();
        messageExecutor.readMessage(message, frame);
        String serverKey = ChannelAttributeUtil.getRemoteServerKey(channel);
        String serverName = ChannelAttributeUtil.getRemoteServerName(channel);
        ProviderManager providerManager =   messageExecutor.getProviderManager(serverName);
        if (providerManager != null) {
            Provider provider = providerManager.getProvider(serverKey);
            if (provider != null) {
                provider.updateScore(message.getStatistic().getScore());
            } else {
                logger.warn("{} producer is null", serverKey);
            }
        } else {

            logger.warn("{} producerManager is null", serverName);
        }

    }

    @Override
    public int messageId() {
        return SCStatisticMessage.MESSAGE_ID;
    }
}
