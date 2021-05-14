package com.senpure.io.server.remoting;

import com.senpure.executor.TaskLoopGroup;
import com.senpure.io.protocol.Message;
import com.senpure.io.server.Constant;
import com.senpure.io.server.protocol.message.SCFrameworkErrorMessage;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractMessageExecutor implements FutureService {
    private final Map<Integer, DefaultFuture> futureMap = new ConcurrentHashMap<>();
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final Set<Integer> errorMessageIds = new HashSet<>();

    protected final TaskLoopGroup service;

    public AbstractMessageExecutor(TaskLoopGroup service) {
        this.service = service;
        errorMessageIds.add(SCFrameworkErrorMessage.MESSAGE_ID);
    }

    @Override
    public ResponseFuture future(Channel channel, int requestId, Message message, int timeout) {
        DefaultFuture future = new DefaultFuture(channel, requestId, message, timeout);
        futureMap.put(requestId, future);
        return future;
    }

    public void receive(Channel channel, int requestId, Message message) {
        DefaultFuture future = futureMap.remove(requestId);
        if (future != null) {
            boolean success = !isErrorMessage(message);
            DefaultResponse response = new DefaultResponse(success, channel, message);
            future.doReceived(response);

        } else {
            logger.warn("远程服务器返回时间过长,服务器已经做了超时处理 {} {}", requestId, message);
        }
    }


    public boolean isErrorMessage(Message message) {
        return errorMessageIds.contains(message.messageId());
    }

    public void checkTimeoutFuture() {

        long now = System.currentTimeMillis();
        for (DefaultFuture future : futureMap.values()) {
            if (future.isDone()) {
                continue;
            }
            if (now - future.getSendTime() > future.getTimeout()) {
                SCFrameworkErrorMessage errorMessage = new SCFrameworkErrorMessage();
                errorMessage.setCode(Constant.ERROR_TIMEOUT);
                errorMessage.setMessage("同步请求超时[" + future.getMessage().messageId() + "][" + future.getRequestId() + "]:" + future.getTimeout());
                errorMessage.getArgs().add(String.valueOf(future.getMessage().messageId()));
                errorMessage.getArgs().add(String.valueOf(future.getRequestId()));

                service.execute(() -> receive(future.getChannel(), future.getRequestId(), errorMessage));
            }
        }
    }


    public void addErrorMessage(int messageId) {

        errorMessageIds.add(messageId);
    }

    public void addErrorMessages(List<Integer> messageIds) {

        errorMessageIds.addAll(messageIds);
    }

    public TaskLoopGroup getService() {
        return service;
    }

    public void shutdownService() {
        service.shutdownGracefully();
    }

}