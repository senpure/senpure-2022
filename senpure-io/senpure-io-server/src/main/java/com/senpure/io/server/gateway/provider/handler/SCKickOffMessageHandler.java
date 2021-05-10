package com.senpure.io.server.gateway.provider.handler;

import com.senpure.io.server.ChannelAttributeUtil;
import com.senpure.io.server.gateway.GatewayReceiveProviderMessage;
import com.senpure.io.server.protocol.message.SCKickOffMessage;
import io.netty.channel.Channel;

public class SCKickOffMessageHandler extends  AbstractProviderMessageHandler{
    @Override
    public void execute(Channel channel, GatewayReceiveProviderMessage gatewayReceiveProviderMessage) {
        SCKickOffMessage message = new SCKickOffMessage();
        messageExecutor.readMessage(message, gatewayReceiveProviderMessage);
        long tempUserId = message.getUserId();
        Channel userChannel = null;
        if (tempUserId > 0) {
            userChannel =   messageExecutor.userClientChannel.get(tempUserId);
        }
        if (userChannel == null) {
            userChannel =   messageExecutor.tokenChannel.get(message.getToken());
        }
        if (userChannel != null) {
            logger.info("{} token:{} uerId:{} 踢下线", userChannel, ChannelAttributeUtil.getToken(userChannel), ChannelAttributeUtil.getUserId(userChannel));
            userChannel.close();
        } else {
            logger.info("{} 踢下线失败，找不到channel", message.toString());
        }
    }

    @Override
    public int messageId() {
        return SCKickOffMessage.MESSAGE_ID;
    }
}
