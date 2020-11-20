package com.senpure.io.server.gateway.provider.handler;

import com.senpure.io.server.gateway.ProviderManager;
import com.senpure.io.server.gateway.Server2GatewayMessage;
import com.senpure.io.server.protocol.message.SCMessageForwardMessage;
import com.senpure.io.server.support.autoconfigure.BreakUserSelector;
import io.netty.channel.Channel;

import java.util.Map;

public class SCMessageForwardMessageHandler extends AbstractProviderMessageHandler {
    @Override
    public void execute(Channel channel, Server2GatewayMessage server2GatewayMessage) {
        SCMessageForwardMessage message = new SCMessageForwardMessage();
        messageExecutor.readMessage(message, server2GatewayMessage);
        if (message.getServerName() != null) {
            ProviderManager providerManager = messageExecutor.producerManagerMap.get(message.getServerName());
            if (providerManager != null) {
                providerManager.sendMessage2Consumer(message.getServerKey(), message.getId(), message.getData());
            } else {
                for (Map.Entry<String, ProviderManager> entry : messageExecutor.producerManagerMap.entrySet()) {
                    entry.getValue().sendMessage2Consumer(message.getServerKey(), message.getId(), message.getData());
                }
            }
        } else {
            for (Map.Entry<String, ProviderManager> entry : messageExecutor.producerManagerMap.entrySet()) {
                entry.getValue().sendMessage2Consumer(message.getServerKey(), message.getId(), message.getData());
            }
        }
    }

    @Override
    public int handleMessageId() {
        return SCMessageForwardMessage.MESSAGE_ID;
    }
}
