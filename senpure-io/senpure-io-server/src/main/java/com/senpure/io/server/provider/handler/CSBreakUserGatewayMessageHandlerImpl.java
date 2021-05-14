package com.senpure.io.server.provider.handler;


import com.senpure.io.server.protocol.message.CSBreakUserGatewayMessage;
import io.netty.channel.Channel;


/**
 * 断开用户与网关处理器
 *
 * @author senpure
 * @time 2018-10-19 16:14:32
 */
public class CSBreakUserGatewayMessageHandlerImpl extends AbstractFrameworkMessageHandler<CSBreakUserGatewayMessage>
        implements CSBreakUserGatewayMessageHandler {



    @Override
    public void execute(Channel channel, long token, long userId, CSBreakUserGatewayMessage message) {
        if (message.getToken() != 0) {
            messageSender.breakToken(message.getToken(), message.getRelationToken());
        }
        messageSender.breakUser(message.getUserId(), message.getRelationToken());
    }


    /**
     * new 一个空对象
     */
    @Override
    public CSBreakUserGatewayMessage newEmptyMessage() {
        return new CSBreakUserGatewayMessage();
    }

    @Override
    public int messageId() {
        return CSBreakUserGatewayMessage.MESSAGE_ID;
    }
}