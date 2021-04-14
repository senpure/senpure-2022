package com.senpure.io.server.consumer;


import com.senpure.io.protocol.Message;
import com.senpure.io.server.ServerProperties;
import com.senpure.io.server.consumer.remoting.Response;
import com.senpure.io.server.consumer.remoting.ResponseCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * RemoteServerManager
 * 一个服务对应一个RemoteServerManager 对象 <br>
 * 一个RemoteServerManager   对象 可以有多个RemoteServerChannelManager <br>
 * 一个RemoteServerChannelManager 可以有多个channel
 *
 * @author senpure
 * @time 2019-06-29 15:15:57
 */
public class RemoteServerManager {


    private static Logger logger = LoggerFactory.getLogger(RemoteServerManager.class);

    private ConcurrentMap<String, RemoteServerChannelManager> remoteServerChannelManager = new ConcurrentHashMap<>();

    private AtomicInteger atomicRequestId = new AtomicInteger(1);

    private RemoteServerChannelManager defaultChannelManager;
    //  private ServerProperties.Consumer properties;

    private int defaultTimeout;

    public RemoteServerManager(ServerProperties.Consumer properties) {

        defaultTimeout = properties.getRequestTimeout();
    }

    public String getRemoteServerKey(String host, int port) {
        return host + ":" + port;
    }


    public synchronized RemoteServerChannelManager getRemoteServerChannelManager(String remoteServerKey) {

        RemoteServerChannelManager manager = remoteServerChannelManager.get(remoteServerKey);
        if (manager == null) {
            manager = new RemoteServerChannelManager(remoteServerKey);
            remoteServerChannelManager.put(remoteServerKey, manager);
            return remoteServerChannelManager.get(remoteServerKey);
        }
        return manager;
    }


    public void sendMessage(Message message) {
        ConsumerMessage frame = new ConsumerMessage();
        frame.setMessage(message);
        defaultChannelManager.sendMessage(frame);
    }

    /**
     * @param message  消息
     * @param callback 回调
     */
    public void sendMessage(Message message, ResponseCallback callback) {

        sendMessage(message, callback, defaultTimeout);
    }

    /**
     * @param message  消息
     * @param callback 回调
     * @param timeout  超时 毫秒
     */
    public void sendMessage(Message message, ResponseCallback callback, int timeout) {
        ConsumerMessage frame = new ConsumerMessage();
        frame.setRequestId(nextRequestId());
        frame.setMessage(message);

        defaultChannelManager.sendMessage(frame, callback, timeout);

    }

    /**
     * 发送一个同步消息
     *
     * @param message
     */
    public Response sendSyncMessage(Message message)  {
        return sendSyncMessage(message, defaultTimeout);
    }


    /**
     * 发送一个同步消息
     *
     * @param message
     * @param timeout 超时毫秒
     */
    public Response sendSyncMessage(Message message, int timeout)  {
        ConsumerMessage frame = new ConsumerMessage();
        frame.setRequestId(nextRequestId());
        frame.setMessage(message);
        return defaultChannelManager.sendSyncMessage(frame, timeout);
    }

    /**
     * 发送一个同步消息
     *
     * @param message
     * @param timeout               超时毫秒
     * @param messageRetryTimeLimit 如果连接不可用额外等待时间 毫秒
     * @return
     */
    public Response sendSyncMessage(Message message, int timeout, int messageRetryTimeLimit) {
        ConsumerMessage frame = new ConsumerMessage();
        frame.setRequestId(nextRequestId());
        frame.setMessage(message);
        return defaultChannelManager.sendSyncMessage(frame, timeout, messageRetryTimeLimit);
    }

    private int nextRequestId() {
        int requestId = atomicRequestId.getAndIncrement();
        if (requestId == 0) {
            return nextRequestId();
        }
        return requestId;
    }


    public RemoteServerChannelManager getDefaultChannelManager() {
        return defaultChannelManager;
    }

    public void setDefaultChannelManager(RemoteServerChannelManager defaultChannelManager) {
        this.defaultChannelManager = defaultChannelManager;
    }
}
