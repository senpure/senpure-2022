package com.senpure.io.server.gateway.provider.handler;

import com.senpure.io.server.ChannelAttributeUtil;
import com.senpure.io.server.Constant;
import com.senpure.io.server.gateway.GatewayReceiveProviderMessage;
import com.senpure.io.server.gateway.provider.Provider;
import com.senpure.io.server.gateway.provider.ProviderManager;
import com.senpure.io.server.protocol.message.SCFrameworkErrorMessage;
import com.senpure.io.server.protocol.message.SCMatchingSuccessMessage;
import io.netty.channel.Channel;

public class SCMatchingSuccessMessageHandler extends AbstractGatewayProviderMessageHandler {
    @Override
    public void execute(Channel channel, GatewayReceiveProviderMessage frame) {
        SCMatchingSuccessMessage message = new SCMatchingSuccessMessage();
        readMessage(message, frame);
        ProviderManager providerManager = messageExecutor.getProviderManager(message.getServerName());
        if (providerManager != null) {
            Provider provider = providerManager.getProvider(message.getServerKey());
            if (provider != null) {

                if (frame.getUserIds().length == 0) {
                    long token = frame.getToken();
                    Channel userChannel = messageExecutor.tokenChannel.get(token);
                    if (userChannel == null) {
                        logger.warn("token {} channel is null", token);
                        return;
                    }
                    Long userId = ChannelAttributeUtil.getUserId(userChannel);

                    if (userId == null) {
                        userId = 0L;
                    }
                    Provider old = providerManager.provider(token);
                    if (old != null && old != provider) {
                        logger.warn("匹配错误，已经有了关联");
                        SCFrameworkErrorMessage errorMessage = new SCFrameworkErrorMessage();
                        errorMessage.setCode(Constant.ERROR_GATEWAY_ERROR);
                        errorMessage.setMessage("系统错误，匹配失败");
                        messageExecutor.responseMessage2Consumer(frame.requestId(), token, errorMessage);
                        return;
                    }

                    providerManager.relation(provider, token, userId, (cs, sc) -> messageExecutor.responseMessage2Consumer(frame.requestId(), token, frame));

                } else {
                    for (Long userId : frame.getUserIds()) {
                        Channel userChannel = messageExecutor.userClientChannel.get(userId);
                        if (userChannel == null) {
                            logger.warn("userId {} channel is null", userId);
                            return;
                        }
                        Long token = ChannelAttributeUtil.getToken(userChannel);
                        Provider old = providerManager.provider(token);
                        if (old != null && old != provider) {
                            SCFrameworkErrorMessage errorMessage = new SCFrameworkErrorMessage();
                            errorMessage.setCode(Constant.ERROR_GATEWAY_ERROR);
                            errorMessage.setMessage("系统错误，匹配失败");
                            logger.warn("匹配错误，已经有了关联");
                            messageExecutor.responseMessage2Consumer(frame.requestId(), token, errorMessage);
                            return;
                        }

                        providerManager.relation(provider, token, userId, (cs, sc) -> messageExecutor.responseMessage2Consumer(frame.requestId(), token, frame));

                    }
                }


            }
        }
    }

    @Override
    public int messageId() {
        return SCMatchingSuccessMessage.MESSAGE_ID;
    }
}
