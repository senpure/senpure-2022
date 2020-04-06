package com.senpure.io.server.producer.handler;


import com.senpure.io.server.producer.GatewayManager;
import com.senpure.io.server.protocol.message.CSBreakUserGatewayMessage;
import io.netty.channel.Channel;

import javax.annotation.Resource;
import java.util.function.Consumer;

/**
 * 断开用户与网关处理器
 *
 * @author senpure
 * @time 2018-10-19 16:14:32
 */
public class CSBreakUserGatewayMessageHandler extends AbstractInnerMessageHandler<CSBreakUserGatewayMessage> {
    @Resource
    protected GatewayManager gatewayManager;


    @Override
    public void execute(Channel channel, long token, long userId, CSBreakUserGatewayMessage message) {
        if (message.getToken() != 0) {
            gatewayManager.breakToken(message.getToken(), message.getRelationToken());
        }
        gatewayManager.breakUser(message.getUserId(), message.getRelationToken());
    }


    @Override
    public int handlerId() {
        return CSBreakUserGatewayMessage.MESSAGE_ID;
    }

    @Override
    public CSBreakUserGatewayMessage getEmptyMessage() {
        return new CSBreakUserGatewayMessage();
    }


}