package com.senpure.io.server.gateway.provider.handler;

import com.senpure.io.server.gateway.GatewayReceiveProviderMessage;
import com.senpure.io.server.gateway.provider.ProviderManager;
import com.senpure.io.server.protocol.message.SCMessageForwardMessage;
import io.netty.channel.Channel;

import java.util.Map;

public class SCMessageForwardMessageHandler extends AbstractProviderMessageHandler {
    @Override
    public void execute(Channel channel, GatewayReceiveProviderMessage gatewayReceiveProviderMessage) {
        SCMessageForwardMessage message = new SCMessageForwardMessage();
        messageExecutor.readMessage(message, gatewayReceiveProviderMessage);
        if (message.getServerName() != null) {
            ProviderManager providerManager = messageExecutor.providerManagerMap.get(message.getServerName());
            if (providerManager != null) {
                providerManager.sendMessage2Consumer(message.getServerKey(), message.getId(), message.getData());
            } else {
                for (Map.Entry<String, ProviderManager> entry : messageExecutor.providerManagerMap.entrySet()) {
                    entry.getValue().sendMessage2Consumer(message.getServerKey(), message.getId(), message.getData());
                }
            }
        } else {
            for (Map.Entry<String, ProviderManager> entry : messageExecutor.providerManagerMap.entrySet()) {
                entry.getValue().sendMessage2Consumer(message.getServerKey(), message.getId(), message.getData());
            }
        }
    }

    @Override
    public int messageId() {
        return SCMessageForwardMessage.MESSAGE_ID;
    }
}
