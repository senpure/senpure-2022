package com.senpure.io.server.consumer;

import com.senpure.base.util.Spring;
import com.senpure.io.server.consumer.remoting.DefaultFuture;
import com.senpure.io.server.consumer.remoting.DefaultResponse;
import com.senpure.io.server.consumer.remoting.Response;
import com.senpure.io.server.consumer.remoting.ResponseCallback;
import com.senpure.io.server.protocol.message.SCInnerErrorMessage;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.*;

/**
 * RemoteServerChannelManager
 *
 * @author senpure
 * @time 2019-06-28 20:57:29
 */
public class RemoteServerChannelManager {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected List<Channel> channels = new ArrayList<>();

    protected AtomicInteger atomicIndex = new AtomicInteger(-1);

    protected String host;
    private int port;
    protected String serverKey;
    protected boolean connecting = false;
    private int defaultMessageRetryTimeLimit = 10000;
    protected ReadWriteLock connectLock = new ReentrantReadWriteLock();
    private final List<FailMessage> failMessages = new ArrayList<>();



    public RemoteServerChannelManager(String serverKey) {
        this.serverKey = serverKey;
    }

    public void sendMessage(ConsumerMessage frame) {
        Channel channel = nextChannel();
        if (channel != null) {
            channel.writeAndFlush(frame);
        } else {
            FailMessage failMessage = new FailMessage();
            failMessage.setFrame(frame);
            failMessage.setStartTime(System.currentTimeMillis());
            failMessage.setMessageRetryTimeLimit(defaultMessageRetryTimeLimit);
            addFailMessage(failMessage);
        }
    }

    public void sendMessage(ConsumerMessage frame, ResponseCallback callback, int timeout) {
        Channel channel = nextChannel();
        if (channel != null) {
            DefaultFuture future = new DefaultFuture(frame, channel, timeout);
            future.setCallback(callback);
            channel.writeAndFlush(frame);
        } else {
            FailMessage failMessage = new FailMessage();
            failMessage.setFrame(frame);
            failMessage.setStartTime(System.currentTimeMillis());
            failMessage.setCallback(callback);
            failMessage.setTimeout(timeout);
            failMessage.setMessageRetryTimeLimit(defaultMessageRetryTimeLimit);
            addFailMessage(failMessage);
        }

    }

    public Response sendSyncMessage(ConsumerMessage frame, int timeout) {

        return sendSyncMessage(frame, timeout, defaultMessageRetryTimeLimit);
    }

    public Response sendSyncMessage(ConsumerMessage frame, int timeout, int messageRetryTimeLimit) {
        Channel channel = nextChannel();
        try {
            if (channel != null) {
                DefaultFuture future = new DefaultFuture(frame, channel, timeout);
                channel.writeAndFlush(frame);
                return future.get();
            } else {
                if (messageRetryTimeLimit > 0) {
                    SyncFail syncFail = new SyncFail();
                    long start = System.currentTimeMillis();
                    FailMessage failMessage = new FailMessage();
                    failMessage.setFrame(frame);
                    failMessage.setStartTime(start);
                    failMessage.setTimeout(timeout);
                    failMessage.setSyncFail(syncFail);
                    failMessage.setMessageRetryTimeLimit(messageRetryTimeLimit);
                    addFailMessage(failMessage);
                    syncFail.lock.lock();
                    try {
                        while (!syncFail.isDone()) {
                            syncFail.done.await(messageRetryTimeLimit, TimeUnit.MILLISECONDS);
                            if (syncFail.isDone() || System.currentTimeMillis() - start > messageRetryTimeLimit) {
                                break;
                            }
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } finally {
                        syncFail.lock.unlock();
                    }
                    if (!syncFail.isDone()) {
                        throw new RuntimeException("同步请求重试等待  超时" + messageRetryTimeLimit);
                    }
                    return syncFail.future.get();
                }
            }
            throw new RuntimeException(serverKey + " " + host + ":" + port + " 没有可用的channel");
        } catch (Exception e) {
            logger.error("error",e);
            SCInnerErrorMessage errorMessage = new SCInnerErrorMessage();
            errorMessage.setCode("");
            errorMessage.setMessage("");

            return new DefaultResponse(channel, null, errorMessage);

        }
    }

    private void addFailMessage(FailMessage failMessage) {
        synchronized (failMessages) {
            logger.info("加入重新发送队列,等待可用channel {}", failMessage.frame);
            failMessages.add(failMessage);
        }
    }

    private void checkFailMessage() {
        synchronized (failMessages) {
            if (failMessages.size() > 0) {
                ConsumerMessageExecutor executor = Spring.getBean(ConsumerMessageExecutor.class);
                if (executor != null) {
                    executor.execute(this::sendFailMessage);
                } else {
                    logger.warn("没有从spring 容器中找到     ConsumerMessageExecutor");
                    new Thread(this::sendFailMessage).start();
                }
            }

        }

    }

    private void sendFailMessage() {
        List<FailMessage> list = new ArrayList<>(failMessages.size());
        long now = System.currentTimeMillis();
        synchronized (failMessages) {
            if (failMessages.size() > 0) {

                for (FailMessage message : failMessages) {
                    if (now - message.startTime <= message.messageRetryTimeLimit) {
                        list.add(message);
                    } else {
                        logger.warn("超过重试时间限制,不再重新发送 {}", message);
                    }
                }

                failMessages.clear();
            }
        }

        if (list.size() > 0) {
            Channel channel = nextChannel();
            if (channel != null) {
                int temp = 1;
                for (FailMessage failMessage : list) {
                    if (failMessage.timeout == 0) {        //handler 处理
                        logger.info("重新发送消息{}", failMessage.frame);
                        channel.write(failMessage.frame);
                    } else if (failMessage.callback != null) {//异步回调
                        DefaultFuture future = new DefaultFuture(failMessage.frame, channel, failMessage.timeout);
                        future.setCallback(failMessage.callback);
                        logger.info("重新发送异步回调消息{}", failMessage.frame);
                        channel.write(failMessage.frame);
                    } else if (failMessage.syncFail != null) {//同步回调
                        logger.info("重新发送同步回调消息{}", failMessage.frame);
                        DefaultFuture future = new DefaultFuture(failMessage.frame, channel, failMessage.timeout);
                        channel.write(failMessage.frame);
                        try {
                            failMessage.syncFail.lock.lock();
                            failMessage.syncFail.future = future;
                            failMessage.syncFail.done.signal();
                        } finally {
                            failMessage.syncFail.lock.unlock();
                        }

                    }
                    if (temp % 100 == 0) {
                        channel.flush();
                        temp = 1;
                    }
                    temp++;
                }
                if (temp > 1) {
                    channel.flush();
                }
            } else {
                synchronized (failMessages) {
                    failMessages.addAll(list);
                }
            }
        }

    }

    public boolean isConnecting() {
        boolean temp;
        connectLock.readLock().lock();
        temp = connecting;
        connectLock.readLock().unlock();
        return temp;
    }

    public void setConnecting(boolean connecting) {
        connectLock.writeLock().lock();
        this.connecting = connecting;
        connectLock.writeLock().unlock();

    }

    public int getChannelSize() {
        return channels.size();
    }

    public void addChannel(Channel channel) {
        if (channels.contains(channel)) {
            return;
        }
        channels.add(channel);
        checkFailMessage();
    }

    public void removeChannel(Channel channel) {
        channels.remove(channel);
    }

    public Channel nextChannel() {
        if (channels.size() == 0) {
            logger.warn("{}:{}没有可用得channel  ", host, port);
            return null;
        }
        for (int i = 0; i < channels.size(); i++) {
            Channel channel = channels.get(nextIndex());
            if (channel.isWritable()) {
                return channel;
            }
        }
        return null;
    }

    protected int nextIndex() {
        if (channels.size() == 1) {
            return 0;
        }
        int index = atomicIndex.incrementAndGet();
        return Math.abs(index % channels.size());
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getServerKey() {
        return serverKey;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getDefaultMessageRetryTimeLimit() {
        return defaultMessageRetryTimeLimit;
    }

    public void setDefaultMessageRetryTimeLimit(int defaultMessageRetryTimeLimit) {
        this.defaultMessageRetryTimeLimit = defaultMessageRetryTimeLimit;
    }

    private static class SyncFail {
        private final Lock lock = new ReentrantLock();
        private final Condition done = lock.newCondition();

        private volatile DefaultFuture future;

        private boolean isDone() {
            return future != null;
        }
    }

    private static class FailMessage {
        private ConsumerMessage frame;

        private ResponseCallback callback;

        private int timeout;
        private long startTime;
        private int messageRetryTimeLimit;
        private SyncFail syncFail;


        public void setFrame(ConsumerMessage frame) {
            this.frame = frame;
        }


        public void setCallback(ResponseCallback callback) {
            this.callback = callback;
        }

        public void setMessageRetryTimeLimit(int messageRetryTimeLimit) {
            this.messageRetryTimeLimit = messageRetryTimeLimit;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }



        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }


        public void setSyncFail(SyncFail syncFail) {
            this.syncFail = syncFail;
        }
    }
}
