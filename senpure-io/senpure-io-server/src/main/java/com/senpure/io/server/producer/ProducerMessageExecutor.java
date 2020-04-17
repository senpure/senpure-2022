package com.senpure.io.server.producer;

import com.senpure.executor.TaskLoopGroup;
import com.senpure.io.server.Constant;
import com.senpure.io.server.producer.handler.ProducerMessageHandler;
import com.senpure.io.server.protocol.message.SCInnerErrorMessage;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProducerMessageExecutor {
    private Logger logger = LoggerFactory.getLogger(ProducerMessageExecutor.class);
    private TaskLoopGroup service;

    private GatewayManager gatewayManager;

    public ProducerMessageExecutor() {

    }


    public TaskLoopGroup getTaskLoopGroup() {
        return service;
    }

    public void setGatewayManager(GatewayManager gatewayManager) {
        this.gatewayManager = gatewayManager;
    }

    public void setService(TaskLoopGroup service) {
        this.service = service;
    }

    public void execute(Runnable runnable) {
        service.execute(runnable);
    }

    public void execute(Channel channel, Gateway2ProducerMessage frame) {
        long userId = frame.getUserId();
        long id = userId > 0 ? userId : frame.getToken();
        service.get(id).execute(() -> {
            ProducerMessageHandler handler = ProducerMessageHandlerUtil.getHandler(frame.getMessageId());
            if (handler == null) {
                logger.warn("没有找到消息处理程序{} userId:{}", frame.getMessageId(), userId);
                SCInnerErrorMessage scInnerErrorMessage = new SCInnerErrorMessage();
                scInnerErrorMessage.setCode(Constant.ERROR_NOT_HANDLE_REQUEST);
                scInnerErrorMessage.setMessage("服务器没有处理程序:" + frame.getMessageId());
                scInnerErrorMessage.getArgs().add(String.valueOf(frame.getMessageId()));
                gatewayManager.sendMessageByToken(frame.getToken(), scInnerErrorMessage);
                return;
            }
            try {
                GatewayManager.setRequestId(frame.getRequestId());
                handler.execute(channel, frame.getToken(), userId, frame.getMessage());
            } catch (Exception e) {
                logger.error("执行handler[" + handler.getClass().getName() + "]逻辑出错 ", e);
                SCInnerErrorMessage scInnerErrorMessage = new SCInnerErrorMessage();
                scInnerErrorMessage.setMessage("服务器执行错误:" + frame.getMessage().getClass().getSimpleName()
                        + "[" + frame.getMessageId() + "]:" +
                        e.getMessage());
                scInnerErrorMessage.setCode(Constant.ERROR_SERVER_ERROR);
                scInnerErrorMessage.getArgs().add(String.valueOf(frame.getMessageId()));
                gatewayManager.sendMessageByToken(frame.getToken(), scInnerErrorMessage);
            } finally {
                GatewayManager.clearRequestId();
            }

        });
    }


}
