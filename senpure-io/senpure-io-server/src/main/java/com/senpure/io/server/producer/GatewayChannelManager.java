package com.senpure.io.server.producer;


import com.senpure.base.util.Spring;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class GatewayChannelManager {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private static AtomicInteger atomicCount = new AtomicInteger(0);
    private List<Channel> channels = new ArrayList<>(16);

    private AtomicInteger atomicIndex = new AtomicInteger(-1);

    private final List<FailMessage> failMessages = new ArrayList<>(128);
    private int defaultMessageRetryTimeLimit = 10000;
    private boolean connecting = false;

    private ReadWriteLock connectLock = new ReentrantReadWriteLock();

    /**
     * GatewayChannelManager 的唯一标识
     */
    private int gatewayChannelKey;

    private String gatewayKey;


    public GatewayChannelManager(String gatewayKey) {
        this.gatewayKey = gatewayKey;
        gatewayChannelKey = atomicCount.incrementAndGet();
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

    private void checkFailMessage() {
        synchronized (failMessages) {
            if (failMessages.size() > 0) {
                ProducerMessageExecutor executor = Spring.getBean(ProducerMessageExecutor.class);
                if (executor != null) {
                    executor.execute(this::sendFailMessage);
                } else {
                    logger.warn("没有从spring 容器中找到ProducerMessageExecutor");
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
                        logger.warn("超过重试时间限制,不在重新发送 {}", message);
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
                    logger.info("重新发送消息{}", failMessage.frame);
                    channel.write(failMessage.frame);
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

    //心跳不会走这里
    private void addFailMessage(FailMessage failMessage) {
        synchronized (failMessages) {
            logger.info("加入重新发送队列,等待可用channel {}", failMessage.frame);
            failMessages.add(failMessage);
        }
    }

    public void sendMessage(List<Producer2GatewayMessage> frames) {

        sendMessage(frames, 100);
    }

    public void sendMessage(List<Producer2GatewayMessage> frames, int flushValue) {
        Channel channel = nextChannel();
        if (channel != null) {
            int temp = 1;
            for (Producer2GatewayMessage frame : frames) {
                channel.write(frame);
                if (temp % flushValue == 0) {
                    channel.flush();
                    temp = 1;
                }
                temp++;
            }
            if (temp > 1) {
                channel.flush();
            }
        }
    }

    public void sendMessage(Producer2GatewayMessage frame) {
        Channel channel = nextChannel();
        if (channel != null) {
            channel.writeAndFlush(frame);
            return;
        }
        FailMessage failMessage = new FailMessage();
        failMessage.startTime = System.currentTimeMillis();
        failMessage.frame = frame;
        failMessage.messageRetryTimeLimit = defaultMessageRetryTimeLimit;
        addFailMessage(failMessage);
        logger.error("全部channel 不可用 {}", toString());
    }

    /**
     * 返回一个可写的channel
     *
     * @return
     */
    private Channel nextChannel() {
        if (channels.size() == 0) {
            logger.warn("{}没有可用得channel ", gatewayKey);
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

    public int getGatewayChannelKey() {
        return gatewayChannelKey;
    }

    private int nextIndex() {
        if (channels.size() == 1) {
            return 0;
        }
        int index = atomicIndex.incrementAndGet();
        return Math.abs(index % channels.size());

    }

//    private int nextIndex2() {
//        if (channels.size() == 1) {
//            return 0;
//        }
//        int index = atomicIndex.incrementAndGet();
//        if (index >= channels.size()) {
//            boolean reset = atomicIndex.compareAndSet(index, 0);
//            if (!reset) {
//                return nextIndex2();
//            }
//            return 0;
//        }
//        return index;
//    }

    public String getGatewayKey() {
        return gatewayKey;
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


    public void setDefaultMessageRetryTimeLimit(int defaultMessageRetryTimeLimit) {
        this.defaultMessageRetryTimeLimit = defaultMessageRetryTimeLimit;
    }

    @Override
    public String toString() {
        return "GatewayChannelManager{" +
                "channels=" + channels +
                ", connecting=" + connecting +
                ", gatewayChannelKey=" + gatewayChannelKey +
                ", gatewayKey='" + gatewayKey + '\'' +
                '}';
    }

    private static class FailMessage {
        private long startTime;
        private Producer2GatewayMessage frame;

        private int messageRetryTimeLimit;

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public void setFrame(Producer2GatewayMessage frame) {
            this.frame = frame;
        }

        public void setMessageRetryTimeLimit(int messageRetryTimeLimit) {
            this.messageRetryTimeLimit = messageRetryTimeLimit;
        }
    }


}
