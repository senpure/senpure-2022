package com.senpure.io.server.producer.handler;


import com.senpure.io.server.ChannelAttributeUtil;
import com.senpure.io.server.producer.GatewayManager;
import com.senpure.io.server.protocol.message.CSRelationUserGatewayMessage;
import com.senpure.io.server.protocol.message.SCRelationUserGatewayMessage;
import io.netty.channel.Channel;

import javax.annotation.Resource;

/**
 * 关联用户与网关处理器
 *
 * @author senpure
 * @time 2018-10-17 14:59:15
 */
public class CSRelationUserGatewayMessageHandler extends AbstractInnerMessageHandler<CSRelationUserGatewayMessage> {

    @Resource
    private GatewayManager gatewayManager;

    @Override
    public void execute(Channel channel, long token, long userId, CSRelationUserGatewayMessage message) {
        String gatewayKey = ChannelAttributeUtil.getRemoteServerKey(channel);
        logger.debug("关联网关 与用户 gatewayKey :{}  token :{}  userId :{}", gatewayKey, message.getToken(), message.getUserId());
        if (message.getUserId() > 0) {
            gatewayManager.relationUser(gatewayKey, message.getUserId(), message.getRelationToken());
        }
        if (message.getToken()!= 0) {
            gatewayManager.relationToken(gatewayKey, message.getToken(), message.getRelationToken());
        }
        SCRelationUserGatewayMessage scMessage = new SCRelationUserGatewayMessage();
        scMessage.setRelationToken(message.getRelationToken());
        scMessage.setToken(message.getToken());
        scMessage.setUserId(message.getUserId());
        if (message.getUserId() > 0) {
            gatewayManager.sendMessage(message.getUserId(), scMessage);
        } else {
            gatewayManager.sendMessageByToken(message.getToken(), scMessage);
        }

    }

    @Override
    public int handlerId() {
        return CSRelationUserGatewayMessage.MESSAGE_ID;
    }

    @Override
    public CSRelationUserGatewayMessage getEmptyMessage() {
        return new CSRelationUserGatewayMessage();
    }
}