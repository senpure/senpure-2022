package com.senpure.io.server.producer;

import com.senpure.executor.TaskLoopGroup;
import com.senpure.io.server.producer.handler.ProducerMessageHandler;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledExecutorService;


public class ProducerMessageExecutor {
    private Logger logger = LoggerFactory.getLogger(ProducerMessageExecutor.class);
    private TaskLoopGroup service;
    private int serviceRefCount = 0;
    private GatewayManager gatewayManager;

    public ProducerMessageExecutor() {

    }

    public ScheduledExecutorService getService() {
        return service;
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
               //todo 错误返回
                return;
            }
            try {
                GatewayManager.setRequestId(frame.getRequestId());
                handler.execute(channel, frame.getToken(), userId, frame.getMessage());
            } catch (Exception e) {
                logger.error("执行handler[" + handler.getClass().getName() + "]逻辑出错 ", e);
                //todo 错误返回
            } finally {
                GatewayManager.clearRequestId();
            }

        });
    }

    /**
     * 引用计数+1
     */
    public void retainService() {
        serviceRefCount++;
    }

    public void releaseService() {
        serviceRefCount--;

    }

    public void releaseAndTryShutdownService() {
        serviceRefCount--;
        if (serviceRefCount <= 0) {
            service.shutdownGracefully();
        }
    }

    public void shutdownService() {
        if (serviceRefCount <= 0) {
            service.shutdownGracefully();
        } else {
            logger.warn("server 持有引用{}，请先释放后关闭", serviceRefCount);
        }
    }
}
