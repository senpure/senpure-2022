package com.senpure.io.server.gateway.provider;


import com.senpure.io.server.ChannelAttributeUtil;
import com.senpure.io.server.gateway.Client2GatewayMessage;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;


public class Producer {

    private Logger logger = LoggerFactory.getLogger(getClass());
    protected List<Channel> channels = new ArrayList<>(16);
    private final AtomicInteger atomicIndex = new AtomicInteger(-1);
    private final Set<Integer> handleIds = new HashSet<>();
    private final Statistic statistic = new Statistic();
    private String serverKey;
    private final AtomicInteger consumerCount = new AtomicInteger();


    public void sendMessage(Client2GatewayMessage message) {
        Channel channel = nextChannel();
        if (channel == null) {
            logger.warn("{} 没有可用得channel", serverKey);
            return;
        }
        channel.writeAndFlush(message);

    }

    public boolean isActive() {
        return channels.size() > 0;
    }


    public synchronized void addChannel(Channel channel) {
        if (channels.contains(channel)) {
            return;
        }
        channels.add(channel);
    }

    public synchronized boolean offline(Channel channel) {
        for (int i = 0; i < channels.size(); i++) {
            if (channel == channels.get(i)) {
                logger.info("{} {}与网关断开连接", ChannelAttributeUtil.getRemoteServerName(channel), channel);
                channels.remove(i);
                return true;
            }
        }
        return false;
    }

    public synchronized void updateScore(int score) {
        this.statistic.setScore(score);

    }

    public String getServerKey() {
        return serverKey;
    }

    public void setServerKey(String serverKey) {
        this.serverKey = serverKey;
    }

    public boolean handleMessageId(int messageId) {
        return handleIds.contains(messageId);

    }

    public void markMessageId(int messageId) {
        handleIds.add(messageId);
    }

    /**
     * 返回一个可用的channel
     *
     * @return
     */
    public Channel nextChannel() {
        if (channels.size() == 0) {
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

    private int nextIndex() {
        if (channels.size() == 1) {
            return 0;
        }
        int index = atomicIndex.incrementAndGet();
        return Math.abs(index % channels.size());
//        int index = atomicIndex.incrementAndGet();
//        if (index >= channels.size()) {
//            boolean reset = atomicIndex.compareAndSet(index, 0);
//            if (!reset) {
//                return nextIndex();
//            }
//            return 0;
//        }
        //  return index;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    static class Statistic {
        //producer 汇报的分数
        int score;
        //生产者链接数量
        int consumerCount;

        public void consumerIncr() {
            consumerCount++;
        }
        public void consumerDecr() {
            consumerCount--;
        }
        public void setScore(int score) {
            this.score = score;
        }

        public int getScore() {
            return score;
        }

        public int getConsumerCount() {
            return consumerCount;
        }
    }

    public static void main(String[] args) {
        ConcurrentMap<Integer, Integer> ids = new ConcurrentHashMap<>();


        System.out.println(ids.putIfAbsent(1, 1));
        System.out.println(ids.putIfAbsent(1, 2));
        System.out.println(ids.putIfAbsent(1, 3));
    }
}
