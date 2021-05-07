package com.senpure.io.server.remoting;

import com.senpure.base.util.Assert;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public interface ChannelService {
    void addChannel(Channel channel);

    void removeChannel(Channel channel);

    Channel nextChannel();

    int channelSize();


    class SingleChannelService implements ChannelService {
        private Channel channel;
        private final String remoteServerKey;
        private final Logger logger = LoggerFactory.getLogger(getClass());

        public SingleChannelService(String remoteServerKey) {
            this.remoteServerKey = remoteServerKey;
        }

        @Override
        public void addChannel(Channel channel) {
            if (this.channel != null) {
                Assert.error(remoteServerKey + "该模式只允许同时存在一个channel");
            }
            this.channel = channel;
        }

        @Override
        public void removeChannel(Channel channel) {
            if (this.channel.equals(channel)) {
                this.channel = null;
            }
        }

        @Override
        public Channel nextChannel() {
            return channel;
        }

        @Override
        public int channelSize() {
            return channel == null ? 0 : 1;
        }
    }

    class MultipleChannelService implements ChannelService {

        private final List<Channel> channels = new ArrayList<>(16);
        private final AtomicInteger atomicIndex = new AtomicInteger(-1);
        private final String remoteServerKey;
        private final Logger logger = LoggerFactory.getLogger(getClass());

        public MultipleChannelService(String remoteServerKey) {
            this.remoteServerKey = remoteServerKey;
        }

        @Override
        public void addChannel(Channel channel) {
            if (channels.contains(channel)) {
                return;
            }
            channels.add(channel);
        }

        @Override
        public void removeChannel(Channel channel) {
            channels.remove(channel);
        }

        @Override
        public Channel nextChannel() {
            if (channels.size() == 0) {
                logger.warn("{}没有可用得channel ", remoteServerKey);
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

        @Override
        public int channelSize() {
            return channels.size();
        }

        private int nextIndex() {
            if (channels.size() == 1) {
                return 0;
            }
            int index = atomicIndex.incrementAndGet();
            return Math.abs(index % channels.size());

        }
    }
}