package com.senpure.io.server.gateway.provider.handler;

import com.senpure.io.server.ChannelAttributeUtil;
import com.senpure.io.server.gateway.ProviderManager;
import com.senpure.io.server.gateway.Server2GatewayMessage;
import com.senpure.io.server.gateway.WaitAskTask;
import com.senpure.io.server.gateway.provider.Provider;
import com.senpure.io.server.protocol.message.SCAskHandleMessage;
import com.senpure.io.server.support.MessageIdReader;
import io.netty.channel.Channel;

public class SCAskHandleMessageHandler extends AbstractProviderMessageHandler {
    @Override
    public void execute(Channel channel, Server2GatewayMessage server2GatewayMessage) {
        SCAskHandleMessage message = new SCAskHandleMessage();
        messageExecutor.readMessage(message, server2GatewayMessage);
        WaitAskTask waitAskTask = messageExecutor.waitAskMap.get(message.getAskToken());
        if (waitAskTask != null) {
            if (message.isHandle()) {
                String serverName = ChannelAttributeUtil.getRemoteServerName(channel);
                String serverKey = ChannelAttributeUtil.getRemoteServerKey(channel);
                logger.debug("{} {} 可以处理 {} 值位 {} 的请求", serverName, serverKey,
                        MessageIdReader.read(waitAskTask.getFromMessageId()), message.getAskValue());
                ProviderManager providerManager = messageExecutor.producerManagerMap.get(serverName);
                for (Provider useProvider : providerManager.getUseProviders()) {
                    if (useProvider.getServerKey().equalsIgnoreCase(serverKey)) {
                        waitAskTask.answer(providerManager, useProvider, true);
                        return;
                    }
                }
            }
            if (logger.isDebugEnabled()) {
                String serverName = ChannelAttributeUtil.getRemoteServerName(channel);
                String serverKey = ChannelAttributeUtil.getRemoteServerKey(channel);
                logger.debug("{} {} 无法处理 {} 值位 {} 的请求", serverName, serverKey,
                        MessageIdReader.read(waitAskTask.getFromMessageId()), message.getAskValue());
            }
            waitAskTask.answer(null, null, false);
        }
    }

    @Override
    public int handleMessageId() {
        return SCAskHandleMessage.MESSAGE_ID;
    }
}
