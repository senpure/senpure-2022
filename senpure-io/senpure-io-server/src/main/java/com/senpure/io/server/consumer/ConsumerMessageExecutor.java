package com.senpure.io.server.consumer;

import com.senpure.executor.TaskLoopGroup;
import com.senpure.io.server.Constant;
import com.senpure.io.server.MessageFrame;
import com.senpure.io.server.ServerProperties;
import com.senpure.io.server.consumer.handler.ConsumerMessageHandler;
import com.senpure.io.server.protocol.message.SCFrameworkErrorMessage;
import com.senpure.io.server.remoting.AbstractMessageExecutor;
import com.senpure.io.server.remoting.RemoteServerManager;
import io.netty.channel.Channel;
import org.springframework.util.StringUtils;

public class ConsumerMessageExecutor extends AbstractMessageExecutor {
    private final ConsumerMessageHandlerContext handlerContext;

    private final ProviderManager providerManager;

    public ConsumerMessageExecutor(TaskLoopGroup service,
                                   ServerProperties.ConsumerProperties properties,
                                   ConsumerMessageHandlerContext handlerContext, ProviderManager providerManager) {
        super(service);
        String[] ids = StringUtils.commaDelimitedListToStringArray(properties.getScErrorMessageId());
        for (String id : ids) {
            addErrorMessage(Integer.parseInt(id));
        }
        this.handlerContext = handlerContext;
        this.providerManager = providerManager;
    }

    public void execute(Channel channel, ConsumerMessage frame) {
        int messageType = frame.messageType();
        if (messageType == MessageFrame.MESSAGE_TYPE_SC) {
            executeSCMessage(channel, frame);
        } else if (messageType == MessageFrame.MESSAGE_TYPE_CS) {
            executeCSMessage(channel, frame);
        } else {
            executeSCMessage(channel, frame);
        }

    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void executeCSMessage(Channel channel, ConsumerMessage frame) {
        service.execute(() -> {
            ConsumerMessageHandler handler = handlerContext.handler(frame.messageId());
            if (handler == null) {
                logger.warn("没有找到消息处理程序 {} ", frame.messageId());

                SCFrameworkErrorMessage scFrameworkErrorMessage = new SCFrameworkErrorMessage();
                scFrameworkErrorMessage.setCode(Constant.ERROR_NOT_HANDLE_REQUEST);
                scFrameworkErrorMessage.setMessage("服务器没有处理程序:" + frame.messageId());
                scFrameworkErrorMessage.getArgs().add(String.valueOf(frame.messageId()));
                providerManager.sendMessage(scFrameworkErrorMessage);

            } else {
                try {
                    RemoteServerManager.REQUEST_ID.set(frame.requestId());
                    handler.execute(channel, frame.message());
                } catch (Exception e) {
                    logger.error("执行handler[" + handler.getClass().getName() + "]逻辑出错 ", e);
                    SCFrameworkErrorMessage scFrameworkErrorMessage = new SCFrameworkErrorMessage();
                    scFrameworkErrorMessage.setMessage("服务器执行错误:" + frame.message().getClass().getSimpleName()
                            + "[" + frame.messageId() + "]:" +
                            e.getMessage());
                    scFrameworkErrorMessage.setCode(Constant.ERROR_PROVIDER_ERROR);
                    scFrameworkErrorMessage.getArgs().add(String.valueOf(frame.messageId()));
                    providerManager.sendMessage(scFrameworkErrorMessage);
                } finally {
                    RemoteServerManager.REQUEST_ID.remove();
                }

            }
        });

    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    public void executeSCMessage(Channel channel, ConsumerMessage frame) {
        service.execute(() -> {
            int requestId = frame.requestId();
            if (requestId != 0) {
                receive(channel, requestId, frame.message());
            } else {
                ConsumerMessageHandler handler = handlerContext.handler(frame.messageId());
                if (handler == null) {
                    logger.warn("没有找到消息处理程序{} ", frame.messageId());
                } else {
                    try {
                        handler.execute(channel, frame.message());
                    } catch (Exception e) {
                        logger.error("执行handler[" + handler.getClass().getName() + "]逻辑出错 ", e);
                    }

                }
            }
        });
    }

    public static void main(String[] args) {

    }
}
