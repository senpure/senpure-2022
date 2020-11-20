package com.senpure.io.server.gateway.provider.handler;

import com.senpure.io.server.ChannelAttributeUtil;
import com.senpure.io.server.gateway.ProviderManager;
import com.senpure.io.server.gateway.Server2GatewayMessage;
import com.senpure.io.server.protocol.message.SCBreakUserGatewayMessage;
import io.netty.channel.Channel;

public class SCBreakUserGatewayMessageHandler extends AbstractProviderMessageHandler {
    @Override
    public void execute(Channel channel, Server2GatewayMessage server2GatewayMessage) {

        SCBreakUserGatewayMessage message = new SCBreakUserGatewayMessage();
        messageExecutor.readMessage(message, server2GatewayMessage);
        long tempUserId = server2GatewayMessage.getMessageId();
        Channel userChannel = null;
        if (tempUserId > 0) {
            userChannel =  messageExecutor.userClientChannel.get(tempUserId);
        }
        if (userChannel == null) {
            userChannel =  messageExecutor.tokenChannel.get(server2GatewayMessage.getToken());
        }
        if (userChannel != null) {
            Long userId = ChannelAttributeUtil.getUserId(userChannel);
            userId = userId == null ? 0 : userId;
            Long token = ChannelAttributeUtil.getToken(userChannel);
            String serverName = ChannelAttributeUtil.getRemoteServerName(channel);
            ProviderManager serverManager = messageExecutor.producerManagerMap.get(serverName);
            if (serverManager != null) {
                serverManager.consumerLeaveProducer(userChannel, token, userId);
                // serverManager.breakUserGateway(userChannel, token, userId, Constant.BREAK_TYPE_ERROR);
            }
        }
    }

    @Override
    public int handleMessageId() {
        return SCBreakUserGatewayMessage.MESSAGE_ID;
    }
}
