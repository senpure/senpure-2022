package com.senpure.io.server.direct;

import com.senpure.base.util.Assert;
import com.senpure.io.protocol.Message;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClientManager
 *
 * @author senpure
 * @time 2019-09-17 17:26:46
 */
public class ClientManager {
    private final static ThreadLocal<Integer> requestIdLocal = ThreadLocal.withInitial(() -> 0);

    private static Map<String, OfflineListener> offlineListenerMap = new ConcurrentHashMap<>();

    private static Map<Long, Channel> userChannelMap = new ConcurrentHashMap<>();

    public static void setRequestId(int requestId) {
        requestIdLocal.set(requestId);
    }

    public static void clearRequestId() {
        requestIdLocal.remove();
    }

    public static int getRequestId() {
        return requestIdLocal.get();
    }


    public static void putUserChannel(long userId, Channel channel) {
        userChannelMap.put(userId, channel);
    }

    public static void sendMessage(Channel channel, Message message) {

        if (channel != null) {
            DirectMessage frame = new DirectMessage();
            frame.setMessage(message);
            frame.setRequestId(requestIdLocal.get());
            channel.writeAndFlush(frame);
        }

    }

    public static void sendMessageWithoutRequestId(List<Long> userIds, Message message) {
        DirectMessage frame = new DirectMessage();
        frame.setMessage(message);
        //frame.setRequestId(requestIdLocal.get());
        for (Long userId : userIds) {
            Channel channel = userChannelMap.get(userId);
            if (channel != null) {
                channel.writeAndFlush(frame);
            }
        }
    }

    private static void sendMessageWithoutRequestId(Channel channel, List<DirectMessage> frames) {
        if (channel != null) {
            for (DirectMessage frame : frames) {
                channel.write(frame);
            }
            channel.flush();
        }
    }

    /**
     * 丢失requestId
     *
     * @param userId
     * @param messages
     */
    public static void sendMessageWithoutRequestId(Long userId, List<? extends Message> messages) {

        List<DirectMessage> directMessages = new ArrayList<>(messages.size());
        for (Message message : messages) {
            DirectMessage frame = new DirectMessage();
            frame.setMessage(message);
            directMessages.add(frame);
        }
        Channel channel = userChannelMap.get(userId);
        sendMessageWithoutRequestId(channel, directMessages);
    }

    public static void sendMessage(Long userId, Message message) {
        Channel channel = userChannelMap.get(userId);
        sendMessage(channel, message);

    }


    public static void regOfflineListener(OfflineListener offlineListener) {
        Assert.isNull(offlineListenerMap.get(offlineListener.getName()));
        offlineListenerMap.put(offlineListener.getName(), offlineListener);
    }

    public static void channelOffline(Channel channel) {
        for (Map.Entry<String, OfflineListener> entry : offlineListenerMap.entrySet()) {
            entry.getValue().execute(channel);
        }

    }
}
