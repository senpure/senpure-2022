package com.senpure.io.server.provider.handler;


import com.senpure.io.server.provider.Provider2GatewayMessage;
import com.senpure.io.server.provider.ProviderMessageHandlerUtil;
import com.senpure.io.server.protocol.message.CSAskHandleMessage;
import com.senpure.io.server.protocol.message.SCAskHandleMessage;
import io.netty.channel.Channel;

/**
 * 询问服务器是否可以处理该值得请求处理器
 *
 * @author senpure
 * @time 2018-10-17 14:59:15
 */
public class CSAskHandleMessageHandler extends AbstractInnerMessageHandler<CSAskHandleMessage> {

    @Override
    public void execute(Channel channel, long token, long userId, CSAskHandleMessage message) {
        ProviderMessageHandler producerMessageHandler = ProviderMessageHandlerUtil.getHandler(message.getFromMessageId());
        ProviderAskMessageHandler askMessageHandler;

        boolean handle = false;
        if (producerMessageHandler instanceof ProviderAskMessageHandler) {
            askMessageHandler = (ProviderAskMessageHandler) producerMessageHandler;
            handle = askMessageHandler.ask(message.getAskValue());
        } else {
            logger.warn("{} 没有实现 ProducerAskMessageHandler", producerMessageHandler.getClass().getName());
        }
        SCAskHandleMessage scAskHandleMessage = new SCAskHandleMessage();
        scAskHandleMessage.setFromMessageId(message.getFromMessageId());
        scAskHandleMessage.setHandle(handle);
        scAskHandleMessage.setAskToken(message.getAskToken());
        scAskHandleMessage.setAskValue(message.getAskValue());
        Provider2GatewayMessage toGateway = new Provider2GatewayMessage();
        toGateway.setToken(token);
        toGateway.setUserIds(new Long[0]);
        toGateway.setMessage(scAskHandleMessage);
        toGateway.setMessageId(scAskHandleMessage.getMessageId());
        channel.writeAndFlush(toGateway);

    }

    @Override
    public int handlerId() {
        return CSAskHandleMessage.MESSAGE_ID;
    }

    @Override
    public CSAskHandleMessage getEmptyMessage() {
        return new CSAskHandleMessage();
    }
}