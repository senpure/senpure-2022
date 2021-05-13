package com.senpure.io.server.consumer;

import com.senpure.executor.TaskLoopGroup;
import com.senpure.io.protocol.Message;
import com.senpure.io.server.Constant;
import com.senpure.io.server.ServerProperties;
import com.senpure.io.server.consumer.handler.ConsumerMessageHandler;
import com.senpure.io.server.consumer.remoting.DefaultFuture;
import com.senpure.io.server.consumer.remoting.DefaultResponse;
import com.senpure.io.server.consumer.remoting.Response;
import com.senpure.io.server.protocol.message.SCFrameworkErrorMessage;
import com.senpure.io.server.remoting.AbstractMessageExecutor;
import com.senpure.io.server.remoting.RemoteServerManager;
import io.netty.channel.Channel;
import org.springframework.util.StringUtils;

public class ConsumerMessageExecutor2 extends AbstractMessageExecutor {
    private final ConsumerMessageHandlerContext handlerContext;

    private final   ProviderManager providerManager;
    public ConsumerMessageExecutor2(TaskLoopGroup service,
                                    ServerProperties.ConsumerProperties properties,
                                    ConsumerMessageHandlerContext handlerContext,ProviderManager providerManager) {
        super(service);
        String[] ids = StringUtils.commaDelimitedListToStringArray(properties.getScErrorMessageId());
        for (String id : ids) {
            addErrorMessage(Integer.parseInt(id));
        }
        this.handlerContext = handlerContext;
        this.providerManager = providerManager;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void execute(Channel channel, ConsumerMessage frame) {
        service.execute(() -> {
            int requestId = frame.requestId();
            Message message = frame.message();
            if (requestId == 0) {
                ConsumerMessageHandler handler = handlerContext.handler(message.messageId());
                if (handler == null) {
                    logger.warn("没有找到消息处理程序{} ", message.messageId());
                } else {
                    try {
                        handler.execute(channel, message);
                    } catch (Exception e) {
                        logger.error("执行handler[" + handler.getClass().getName() + "]逻辑出错 ", e);
                    }

                }
            } else {
                DefaultFuture future = DefaultFuture.received(requestId);
                if (future != null) {
                    if (isErrorMessage(message)) {
                        Response response = new DefaultResponse(channel, null, message);
                        future.doReceived(response);
                    } else {
                        Response response = new DefaultResponse(channel, message, null);
                        future.doReceived(response);
                    }
                } else {
                    logger.warn("远程服务器返回时间过长,服务器已经做了超时处理 {}", frame);
                }

            }
        });

    }
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void executeCSMessage(Channel channel, ConsumerMessage frame) {
        service.execute(new Runnable() {
            @Override
            public void run() {
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
}
