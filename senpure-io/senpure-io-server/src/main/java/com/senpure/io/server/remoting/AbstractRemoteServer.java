package com.senpure.io.server.remoting;

import com.senpure.base.util.Assert;
import com.senpure.io.server.Constant;
import com.senpure.io.server.MessageFrame;
import com.senpure.io.server.protocol.message.SCFrameworkErrorMessage;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractRemoteServer implements RemoteServer {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected int timeout = 500;
    protected int waitSendTimeout = 10000;

    protected String remoteHost;
    protected String remotePort;
    protected String remoteServerKey;

    protected final List<WaitSendMessage> waitSendMessages = new ArrayList<>();

    protected FutureService futureService;

    protected ChannelService channelService;


    public void verifyWorkable() {
        Assert.notNull(remoteServerKey);
        Assert.notNull(remoteHost);
        Assert.notNull(remotePort);
        Assert.notNull(futureService);
        Assert.notNull(channelService);
    }

    protected void addWaitMessage(WaitSendMessage waitSendMessage) {
        synchronized (waitSendMessages) {
            logger.info("加入重新发送队列,等待可用channel {}", waitSendMessage.getFrame());
            waitSendMessages.add(waitSendMessage);
        }
    }

    protected void sendWaitMessage() {
        List<WaitSendMessage> list = new ArrayList<>(waitSendMessages.size());
        long now = System.currentTimeMillis();
        synchronized (waitSendMessages) {
            if (waitSendMessages.size() > 0) {
                for (WaitSendMessage message : waitSendMessages) {
                    if (now - message.firstSendTime <= message.waitSendTimeout) {
                        list.add(message);
                    } else {
                        logger.warn("超过重试时间限制,不再重新发送 {}", message);
                    }
                }
                waitSendMessages.clear();
            }
        }

        if (list.size() > 0) {
            Channel channel = channelService.nextChannel();
            if (channel != null) {
                int count = 1;
                for (WaitSendMessage failMessage : list) {
                    if (failMessage.timeout == 0) {        //handler 处理
                        logger.info("重新发送消息{}", failMessage.frame);
                        channel.write(failMessage.frame);
                    } else if (failMessage.callback != null) {//异步回调
                        ResponseFuture future = futureService.future(failMessage.timeout, channel, failMessage.frame.requestId(), failMessage.frame.message());
                        future.setCallback(failMessage.callback);
                        logger.info("重新发送异步回调消息{}", failMessage.frame);
                        channel.write(failMessage.frame);

                    } else if (failMessage.synchronizer != null) {//同步回调
                        logger.info("重新发送同步回调消息{}", failMessage.frame);
                        ResponseFuture future = futureService.future(failMessage.timeout, channel, failMessage.frame.requestId(), failMessage.frame.message());
                        channel.write(failMessage.frame);
                        Synchronizer synchronizer = failMessage.synchronizer;
                        try {
                            synchronizer.lock.lock();
                            synchronizer.future = future;
                            synchronizer.done.signal();
                        } finally {
                            synchronizer.lock.unlock();
                        }

                    }
                    if (count % 100 == 0) {
                        channel.flush();
                        count = 1;
                    }
                    count++;
                }
                if (count > 1) {
                    channel.flush();
                }
            } else {
                synchronized (waitSendMessages) {
                    waitSendMessages.addAll(list);
                }
            }
        }

    }

    public abstract void checkWaitSendMessage();

    public void addChannel(Channel channel) {
        channelService.addChannel(channel);
        checkWaitSendMessage();

    }

    public void removeChannel(Channel channel) {
        channelService.removeChannel(channel);
    }


    @Override
    public void sendMessage(List<MessageFrame> frames) {
        Channel channel = channelService.nextChannel();
        if (channel != null) {
            sendMessage(channel, frames, 100);
        } else {
            for (MessageFrame frame : frames) {
                WaitSendMessage waitSendMessage = new WaitSendMessage();
                waitSendMessage.setFirstSendTime(System.currentTimeMillis());
                waitSendMessage.setFrame(frame);
                waitSendMessage.setWaitSendTimeout(waitSendTimeout);
                addWaitMessage(waitSendMessage);
            }
        }
    }

    @Override
    public void sendMessage(Channel channel, List<MessageFrame> frames) {

        sendMessage(channel, frames, 100);
    }

    protected void sendMessage(Channel channel, List<MessageFrame> frames, int flushValue) {

        int temp = 1;
        for (MessageFrame frame : frames) {
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

    @Override
    public void sendMessage(MessageFrame frame) {
        Channel channel = channelService.nextChannel();
        if (channel != null) {
            sendMessage(channel, frame);
        } else {
            WaitSendMessage waitSendMessage = new WaitSendMessage();
            waitSendMessage.setFirstSendTime(System.currentTimeMillis());
            waitSendMessage.setFrame(frame);
            waitSendMessage.setWaitSendTimeout(waitSendTimeout);
            addWaitMessage(waitSendMessage);
        }
    }

    @Override
    public void sendMessage(Channel channel, MessageFrame frame) {

        channel.writeAndFlush(frame);

    }

    @Override
    public void sendMessage(MessageFrame frame, ResponseCallback callback) {

        sendMessage(frame, callback, timeout);
    }

    @Override
    public void sendMessage(Channel channel, MessageFrame frame, ResponseCallback callback) {

        sendMessage(channel, frame, callback, timeout);
    }

    @Override
    public void sendMessage(MessageFrame frame, ResponseCallback callback, int timeout) {

        Channel channel = channelService.nextChannel();
        if (channel != null) {

            sendMessage(channel, frame, callback, timeout);
        } else {
            WaitSendMessage waitSendMessage = new WaitSendMessage();
            waitSendMessage.setFirstSendTime(System.currentTimeMillis());
            waitSendMessage.setFrame(frame);
            waitSendMessage.setTimeout(timeout);
            waitSendMessage.setWaitSendTimeout(waitSendTimeout);
            waitSendMessage.setCallback(callback);
            addWaitMessage(waitSendMessage);
        }
    }

    @Override
    public void sendMessage(Channel channel, MessageFrame frame, ResponseCallback callback, int timeout) {

        ResponseFuture future = futureService.future(timeout, channel, frame.requestId(), frame.message());
        future.setCallback(callback);
        channel.writeAndFlush(frame);

    }

    @Nonnull
    @Override
    public Response sendSyncMessage(MessageFrame frame) {
        return sendSyncMessage(frame, timeout);
    }

    @Nonnull
    @Override
    public Response sendSyncMessage(Channel channel, MessageFrame frame) {
        return sendSyncMessage(channel, frame, timeout);
    }

    @Nonnull
    @Override
    public Response sendSyncMessage(MessageFrame frame, int timeout) {
        return sendSyncMessage(frame, timeout, waitSendTimeout);
    }

    @Nonnull
    protected Response sendSyncMessage(MessageFrame frame, int timeout, int waitSendTimeout) {
        Channel channel = channelService.nextChannel();
        if (channel != null) {
            return sendSyncMessage(channel, frame, timeout);
        } else if (waitSendTimeout > 0) {
            Synchronizer synchronizer = new Synchronizer();
            WaitSendMessage waitSendMessage = new WaitSendMessage();
            waitSendMessage.setFirstSendTime(System.currentTimeMillis());
            waitSendMessage.setFrame(frame);
            waitSendMessage.setTimeout(timeout);
            waitSendMessage.setWaitSendTimeout(waitSendTimeout);
            waitSendMessage.setSynchronizer(synchronizer);
            addWaitMessage(waitSendMessage);
            try {
                synchronizer.lock.lock();
                if (synchronizer.done.await(waitSendTimeout, TimeUnit.MILLISECONDS)) {
                    return synchronizer.future.get(timeout);
                }
                return FrameworkErrorResponse.timeout(EmptyChannel.EMPTY_CHANNEL, frame.message().messageId(), waitSendTimeout);

            } catch (InterruptedException e) {
                return FrameworkErrorResponse.interrupted(EmptyChannel.EMPTY_CHANNEL, frame.message().messageId(), timeout);

            } finally {
                synchronizer.lock.unlock();
            }
        } else {

            SCFrameworkErrorMessage errorMessage = new SCFrameworkErrorMessage();
            errorMessage.setCode(Constant.ERROR_CHANNEL_NOT_AVAILABLE);
            errorMessage.setMessage(remoteServerKey + "没有可用的channel");
            errorMessage.getArgs().add(remoteServerKey);
            errorMessage.getArgs().add(remoteHost);
            errorMessage.getArgs().add(remotePort);
            return new FrameworkErrorResponse(EmptyChannel.EMPTY_CHANNEL, errorMessage);
        }

    }

    @Nonnull
    @Override
    public Response sendSyncMessage(Channel channel, MessageFrame frame, int timeout) {
        ResponseFuture future = futureService.future(timeout, channel, frame.requestId(), frame.message());
        if (channel.isWritable()) {
            channel.writeAndFlush(frame);
        }
        return future.get(timeout);
    }


    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getWaitSendTimeout() {
        return waitSendTimeout;
    }

    public void setWaitSendTimeout(int waitSendTimeout) {
        this.waitSendTimeout = waitSendTimeout;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public String getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(String remotePort) {
        this.remotePort = remotePort;
    }

    public String getRemoteServerKey() {
        return remoteServerKey;
    }

    public void setRemoteServerKey(String remoteServerKey) {
        this.remoteServerKey = remoteServerKey;
    }

    public List<WaitSendMessage> getWaitSendMessages() {
        return waitSendMessages;
    }

    public FutureService getFutureService() {
        return futureService;
    }

    public void setFutureService(FutureService futureService) {
        this.futureService = futureService;
    }

    public ChannelService getChannelService() {
        return channelService;
    }

    public void setChannelService(ChannelService channelService) {
        this.channelService = channelService;
    }

    protected static class WaitSendMessage {
        private MessageFrame frame;
        private long firstSendTime;
        private ResponseCallback callback;
        //等于0表示不需要服务器返回
        private int timeout;
        private int waitSendTimeout;
        private Synchronizer synchronizer;

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public MessageFrame getFrame() {
            return frame;
        }

        public void setFrame(MessageFrame frame) {
            this.frame = frame;
        }

        public long getFirstSendTime() {
            return firstSendTime;
        }

        public void setFirstSendTime(long firstSendTime) {
            this.firstSendTime = firstSendTime;
        }

        public Synchronizer getSynchronizer() {
            return synchronizer;
        }

        public void setSynchronizer(Synchronizer synchronizer) {
            this.synchronizer = synchronizer;
        }

        public ResponseCallback getCallback() {
            return callback;
        }

        public void setCallback(ResponseCallback callback) {
            this.callback = callback;
        }

        public int getWaitSendTimeout() {
            return waitSendTimeout;
        }

        public void setWaitSendTimeout(int waitSendTimeout) {
            this.waitSendTimeout = waitSendTimeout;
        }
    }

    protected static class Synchronizer {
        private final Lock lock = new ReentrantLock();
        private final Condition done = lock.newCondition();
        private volatile ResponseFuture future;

    }
}
