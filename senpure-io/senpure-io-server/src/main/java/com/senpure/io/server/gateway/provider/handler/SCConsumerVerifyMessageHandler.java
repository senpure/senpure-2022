package com.senpure.io.server.gateway.provider.handler;

import com.senpure.io.server.ChannelAttributeUtil;
import com.senpure.io.server.gateway.ProviderManager;
import com.senpure.io.server.gateway.Server2GatewayMessage;
import com.senpure.io.server.protocol.message.SCConsumerVerifyMessage;
import io.netty.channel.Channel;

import java.util.Map;

public class SCConsumerVerifyMessageHandler extends AbstractProviderMessageHandler {
    @Override
    public void execute(Channel channel, Server2GatewayMessage message) {
        long userId = message.getUserIds()[0];
        Channel clientChannel = messageExecutor.prepLoginChannels.remove(message.getToken());
        if (clientChannel != null) {
            Long token = ChannelAttributeUtil.getToken(clientChannel);
            Long oldUserId = ChannelAttributeUtil.getUserId(clientChannel);
            if (oldUserId != null) {
                if (oldUserId == userId) {
                    logger.info("{}重复登陆 {} 不做额外的处理", clientChannel, userId);
                } else {
                    logger.info("{}切换账号{}  -》  {} ", clientChannel, oldUserId, userId);
                    messageExecutor.consumerUserChange(clientChannel, token, oldUserId);
                }
            }
            ChannelAttributeUtil.setUserId(clientChannel, userId);
            messageExecutor.userClientChannel.put(userId, clientChannel);
            for (Map.Entry<String, ProviderManager> entry : messageExecutor.producerManagerMap.entrySet()) {
                ProviderManager providerManager = entry.getValue();
                providerManager.afterUserAuthorize(token, userId);

            }
        } else {
            logger.warn("登录成功 userId:{} channel缺失 token{}", userId, message.getToken());
        }

    }


    @Override
    public int handleMessageId() {
        return SCConsumerVerifyMessage.MESSAGE_ID;
    }

    @Override
    public boolean stopResponse() {
        return false;
    }
}
