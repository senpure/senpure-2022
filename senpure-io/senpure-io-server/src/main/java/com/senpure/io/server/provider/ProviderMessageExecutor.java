package com.senpure.io.server.provider;

import com.senpure.executor.TaskLoopGroup;
import com.senpure.io.server.Constant;
import com.senpure.io.server.MessageFrame;
import com.senpure.io.server.protocol.message.SCInnerErrorMessage;
import com.senpure.io.server.provider.handler.ProviderMessageHandler;
import com.senpure.io.server.romote.ResponseFuture;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProviderMessageExecutor {
    private final Logger logger = LoggerFactory.getLogger(ProviderMessageExecutor.class);
    private final TaskLoopGroup service;

    private final MessageSender messageSender;

    private final ProviderMessageHandlerContext handlerContext;
    private Map<Integer, ResponseFuture> futureMap = new ConcurrentHashMap<>();

    public ProviderMessageExecutor(TaskLoopGroup service, MessageSender messageSender, ProviderMessageHandlerContext handlerContext) {
        this.service = service;
        this.messageSender = messageSender;
        this.handlerContext = handlerContext;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void executeCSMessage(Channel channel, ProviderReceivedMessage frame) {
        long userId = frame.getUserId();
        long id = userId > 0 ? userId : frame.getToken();
        service.get(id).execute(() -> {
            ProviderMessageHandler handler = handlerContext.handler(frame.getMessageId());
            if (handler == null) {
                logger.warn("没有找到消息处理程序 {} token {} userId:{}", frame.getMessageId(), frame.getToken(), userId);

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

    private void executeSCMessage(Channel channel, ProviderReceivedMessage frame) {

        int requestId = frame.requestId;
        if (requestId > 0) {
            ResponseFuture future = futureMap.remove(requestId);
            if (future == null) {
                logger.warn("远程服务器返回时间过长,服务器已经做了超时处理 {}", frame);
                return;
            }

        }
    }

    public void execute(Channel channel, ProviderReceivedMessage frame) {
        int messageType = frame.messageType();

        if (messageType == MessageFrame.MESSAGE_TYPE_CS) {
            executeCSMessage(channel, frame);
        } else if (messageType == MessageFrame.MESSAGE_TYPE_SC) {
            executeSCMessage(channel, frame);
        } else {
            executeCSMessage(channel, frame);
        }


    }
}
