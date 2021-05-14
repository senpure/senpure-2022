package com.senpure.io.server.gateway.provider.handler;

import com.senpure.io.server.ChannelAttributeUtil;
import com.senpure.io.server.gateway.GatewayReceiveProviderMessage;
import com.senpure.io.server.gateway.provider.ProviderManager;
import com.senpure.io.server.protocol.message.SCConsumerVerifyMessage;
import io.netty.channel.Channel;

import java.util.Map;

public class SCConsumerVerifyMessageHandler extends AbstractGatewayProviderMessageHandler {
    @Override
    public void execute(Channel channel, GatewayReceiveProviderMessage frame) {
        long userId = frame.getUserIds()[0];
        Channel consumerChannel = messageExecutor.prepLoginChannels.remove(frame.getToken());
        if (consumerChannel != null) {
            Long token = ChannelAttributeUtil.getToken(consumerChannel);
            Long oldUserId = ChannelAttributeUtil.getUserId(consumerChannel);
            if (oldUserId == null) {
                for (Map.Entry<String, ProviderManager> entry : messageExecutor.providerManagerMap.entrySet()) {
                    ProviderManager providerManager = entry.getValue();
                    providerManager.afterUserAuthorize(token, userId);
                }
            }
            else {
                if (oldUserId == userId) {
                    logger.info("{}重复登陆 {} 不做额外的处理", consumerChannel, userId);
                } else {
                    logger.info("{}切换账号{}  -》  {} ", consumerChannel, oldUserId, userId);
                    messageExecutor.consumerUserChange(consumerChannel, token, oldUserId);
                }
            }
            ChannelAttributeUtil.setUserId(consumerChannel, userId);
            messageExecutor.userClientChannel.put(userId, consumerChannel);

        } else {
            logger.warn("登录成功 userId:{} channel缺失 token{}", userId, frame.getToken());
        }

    }


    @Override
    public int messageId() {
        return SCConsumerVerifyMessage.MESSAGE_ID;
    }

    @Override
    public boolean stopConsumer() {
        return false;
    }
}
