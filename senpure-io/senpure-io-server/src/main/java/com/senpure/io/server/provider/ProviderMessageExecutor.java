package com.senpure.io.server.provider;

import com.senpure.executor.TaskLoopGroup;
import com.senpure.io.server.Constant;
import com.senpure.io.server.protocol.message.SCInnerErrorMessage;
import com.senpure.io.server.provider.handler.ProviderMessageHandler;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProviderMessageExecutor {
    private final Logger logger = LoggerFactory.getLogger(ProviderMessageExecutor.class);
    private final TaskLoopGroup service;

    private final MessageSender messageSender;

    private final ProviderMessageHandlerContext handlerContext;

    public ProviderMessageExecutor(TaskLoopGroup service, MessageSender messageSender, ProviderMessageHandlerContext handlerContext) {
        this.service = service;
        this.messageSender = messageSender;
        this.handlerContext = handlerContext;
    }
    @SuppressWarnings({"unchecked","rawtypes"})
    public void execute(Channel channel, ProviderReceiveMessage frame) {
        long userId = frame.getUserId();
        long id = userId > 0 ? userId : frame.getToken();
        service.get(id).execute(() -> {
            ProviderMessageHandler handler = handlerContext.handler(frame.getMessageId());
            if (handler == null) {
                logger.warn("没有找到消息处理程序 {} token {} userId:{}", frame.getMessageId(),frame.getToken(), userId);

                long token = frame.getToken();
                if (token == 0) {
                    logger.error("内部处理handler{} 没有注册到 handlerContext", frame.getMessageId());
                    //  channel.writeAndFlush( scInnerErrorMessage);
                } else {
                    SCInnerErrorMessage scInnerErrorMessage = new SCInnerErrorMessage();
                    scInnerErrorMessage.setCode(Constant.ERROR_NOT_HANDLE_REQUEST);
                    scInnerErrorMessage.setMessage("服务器没有处理程序:" + frame.getMessageId());
                    scInnerErrorMessage.getArgs().add(String.valueOf(frame.getMessageId()));
                    messageSender.sendMessageByToken(token, scInnerErrorMessage);
                }

            } else {
                try {
                    MessageSender.REQUEST_ID.set(frame.getRequestId());
                    handler.execute(channel, frame.getToken(), userId, frame.getMessage());
                } catch (Exception e) {
                    logger.error("执行handler[" + handler.getClass().getName() + "]逻辑出错 ", e);
                    SCInnerErrorMessage scInnerErrorMessage = new SCInnerErrorMessage();
                    scInnerErrorMessage.setMessage("服务器执行错误:" + frame.getMessage().getClass().getSimpleName()
                            + "[" + frame.getMessageId() + "]:" +
                            e.getMessage());
                    scInnerErrorMessage.setCode(Constant.ERROR_SERVER_ERROR);
                    scInnerErrorMessage.getArgs().add(String.valueOf(frame.getMessageId()));
                    messageSender.sendMessageByToken(frame.getToken(), scInnerErrorMessage);
                } finally {
                    MessageSender.REQUEST_ID.remove();
                }

            }
        });
    }
}
