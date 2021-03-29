package com.senpure.io.server.provider.handler;


import com.senpure.io.protocol.Message;
import com.senpure.io.server.protocol.message.CSAskHandleMessage;
import com.senpure.io.server.protocol.message.SCAskHandleMessage;
import com.senpure.io.server.provider.ProviderSendMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.util.Arrays;

/**
 * 询问服务器是否可以处理该值得请求处理器
 *
 * @author senpure
 * @time 2018-10-17 14:59:15
 */
public class CSAskHandleMessageHandler extends AbstractInnerMessageHandler<CSAskHandleMessage> {


    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void execute(Channel channel, long token, long userId, CSAskHandleMessage message) {

        ProviderMessageHandler handler = handlerContext.handler(message.getFromMessageId());
        ProviderAskMessageHandler askHandler;
        ProviderAskMessageHandler.Answer answer = null;
        if (handler instanceof ProviderAskMessageHandler) {
            askHandler = (ProviderAskMessageHandler) handler;
            ByteBuf buf = Unpooled.copiedBuffer(message.getData());
            Message askMessage = handler.newEmptyMessage();
            askMessage.read(buf, buf.writerIndex());
            answer = askHandler.ask(askMessage);

        } else {
            logger.warn("{} 没有实现 ProducerAskMessageHandler messageId {}", handler.getClass().getName(), message.getFromMessageId());
        }

        SCAskHandleMessage scAskHandleMessage = new SCAskHandleMessage();
        scAskHandleMessage.setFromMessageId(message.getFromMessageId());
        scAskHandleMessage.setAskToken(message.getAskToken());
        if (answer == null) {
            scAskHandleMessage.setAskValue(Arrays.toString(message.getData()));
        } else {
            scAskHandleMessage.setHandle(answer.isHandle());
            scAskHandleMessage.setAskValue(answer.getValue());
        }

        ProviderSendMessage frame = messageSender.createMessageByToken(token, scAskHandleMessage);
        //这里的token是无效的所以直接使用 channel
        channel.writeAndFlush(frame);

    }


    @Override
    public int messageId() {
        return CSAskHandleMessage.MESSAGE_ID;
    }

    /**
     * new 一个空对象
     */
    @Override
    public CSAskHandleMessage newEmptyMessage() {
        return new CSAskHandleMessage();
    }
}