package com.senpure.io.server.gateway.provider.handler;

import com.senpure.io.server.ChannelAttributeUtil;
import com.senpure.io.server.gateway.GatewayReceiveProviderMessage;
import com.senpure.io.server.gateway.provider.ProviderManager;
import com.senpure.io.server.protocol.message.SCBreakUserGatewayMessage;
import io.netty.channel.Channel;

public class SCBreakUserGatewayMessageHandler extends AbstractGatewayProviderMessageHandler {
    @Override
    public void execute(Channel channel, GatewayReceiveProviderMessage frame) {

        SCBreakUserGatewayMessage message = new SCBreakUserGatewayMessage();
        messageExecutor.readMessage(message, frame);
        long tempUserId = frame.getMessageId();
        Channel userChannel = null;
        if (tempUserId > 0) {
            userChannel =  messageExecutor.userClientChannel.get(tempUserId);
        }
        if (userChannel == null) {
            userChannel =  messageExecutor.tokenChannel.get(frame.getToken());
        }
        if (userChannel != null) {
            Long userId = ChannelAttributeUtil.getUserId(userChannel);
            userId = userId == null ? 0 : userId;
            Long token = ChannelAttributeUtil.getToken(userChannel);
            String serverName = ChannelAttributeUtil.getRemoteServerName(channel);
            ProviderManager serverManager = messageExecutor.getProviderManager(serverName);
            if (serverManager != null) {
                serverManager.consumerLeaveProducer(userChannel, token, userId,0);
                // serverManager.breakUserGateway(userChannel, token, userId, Constant.BREAK_TYPE_ERROR);
            }
        }
    }

    @Override
    public int messageId() {
        return SCBreakUserGatewayMessage.MESSAGE_ID;
    }
}
