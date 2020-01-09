package com.senpure.io.server.producer.handler;


import com.senpure.io.server.producer.Producer2GatewayMessage;
import com.senpure.io.server.producer.ProducerMessageHandlerUtil;
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
        ProducerMessageHandler producerMessageHandler = ProducerMessageHandlerUtil.getHandler(message.getFromMessageId());
        ProducerAskMessageHandler askMessageHandler;

        boolean handle = false;
        if (producerMessageHandler instanceof ProducerAskMessageHandler) {
            askMessageHandler = (ProducerAskMessageHandler) producerMessageHandler;
            handle = askMessageHandler.ask(message.getAskValue());
        } else {
            logger.warn("{} 没有实现 ProducerAskMessageHandler", producerMessageHandler.getClass().getName());
        }
        SCAskHandleMessage scAskHandleMessage = new SCAskHandleMessage();
        scAskHandleMessage.setFromMessageId(message.getFromMessageId());
        scAskHandleMessage.setHandle(handle);
        scAskHandleMessage.setAskToken(message.getAskToken());
        scAskHandleMessage.setAskValue(message.getAskValue());
        Producer2GatewayMessage toGateway = new Producer2GatewayMessage();
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