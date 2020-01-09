package com.senpure.io.server.producer;


import com.senpure.io.protocol.Message;
import com.senpure.io.server.protocol.message.SCBreakUserGatewayMessage;
import com.senpure.io.server.protocol.message.SCKickOffMessage;
import io.netty.util.concurrent.FastThreadLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class GatewayManager {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private final static FastThreadLocal<Integer> requestIdLocal = new FastThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };
    private ConcurrentMap<String, GatewayChannelManager> gatewayChannelMap = new ConcurrentHashMap<>();

    private ConcurrentMap<Long, GatewayRelation> userGatewayMap = new ConcurrentHashMap<>();

    private ConcurrentMap<Long, GatewayRelation> tokenGatewayMap = new ConcurrentHashMap<>();


    public static void setRequestId(int requestId) {
        requestIdLocal.set(requestId);
    }

    public static void clearRequestId() {
        requestIdLocal.remove();
    }

    public static int getRequestId() {
        return requestIdLocal.get();
    }

    public String getGatewayKey(String host, int port) {
        return host + ":" + port;
    }

    public synchronized GatewayChannelManager getGatewayChannelServer(String gatewayKey) {

        GatewayChannelManager manager = gatewayChannelMap.get(gatewayKey);
        if (manager == null) {
            manager = new GatewayChannelManager(gatewayKey);
            gatewayChannelMap.put(gatewayKey, manager);
            return gatewayChannelMap.get(gatewayKey);
        }
        return manager;
    }

    public void report() {
        for (Map.Entry<String, GatewayChannelManager> entry : gatewayChannelMap.entrySet()) {
            logger.debug("{} {}", entry.getKey(), entry.getValue().toString());
        }
    }

    /**
     * 将userId与网关关联起来
     *
     * @param gatewayKey
     * @param userId
     * @param relationToken
     */
    public void relationUser(String gatewayKey, Long userId, long relationToken) {
        GatewayChannelManager channelManager = gatewayChannelMap.get(gatewayKey);
        if (channelManager != null) {
            GatewayRelation relation = new GatewayRelation();
            relation.relationToken = relationToken;
            relation.gatewayChannelManager = channelManager;
            userGatewayMap.put(userId, relation);
        }

    }

    /**
     * 将token与网关关联起来
     *
     * @param gatewayKey
     * @param token
     * @param relationToken
     */
    public void relationToken(String gatewayKey, Long token, long relationToken) {
        GatewayChannelManager channelManager = gatewayChannelMap.get(gatewayKey);
        if (channelManager != null) {
            GatewayRelation relation = new GatewayRelation();
            relation.relationToken = relationToken;
            relation.gatewayChannelManager = channelManager;
            tokenGatewayMap.put(token, relation);
        }
    }

    public boolean breakUser(Long userId, long relationToken) {
        GatewayRelation relation = userGatewayMap.get(userId);
        if (relation != null) {
            if (relation.relationToken == relationToken) {
                logger.debug("{} 取消关联user {}", relation.gatewayChannelManager.getGatewayKey(), userId);
                userGatewayMap.remove(userId);
                return true;
            }
        }
        return false;
    }

    public boolean breakToken(Long token, long relationToken) {
        GatewayRelation relation = tokenGatewayMap.get(token);
        if (relation != null) {
            if (relation.relationToken == relationToken) {
                logger.debug("{} 取消关联token {}", relation.gatewayChannelManager.getGatewayKey(), token);
                tokenGatewayMap.remove(token);
                return true;
            }
        }
        return false;
    }


    /**
     * 将消息发送给所有的网关
     *
     * @param message
     */
    public void dispatchMessage2Gateway(Producer2GatewayMessage message) {
        for (GatewayChannelManager value : gatewayChannelMap.values()) {
            value.sendMessage(message);
        }

    }

    /**
     * 用户登录成功后调用该方法
     *
     * @param token
     * @param userId
     * @param message
     */
    public void sendLoginSuccessMessage2Gateway(Long token, Long userId, Message message) {
        if (userId == 0) {
            return;
        }
        Producer2GatewayMessage toGateway = new Producer2GatewayMessage();
        toGateway.setToken(token);
        toGateway.setUserIds(new Long[]{userId});
        toGateway.setMessage(message);
        toGateway.setMessageId(message.getMessageId());
        toGateway.setRequestId(GatewayManager.getRequestId());
        GatewayRelation gatewayRelation = tokenGatewayMap.get(token);
        if (gatewayRelation != null) {
            gatewayRelation.gatewayChannelManager.sendMessage(toGateway);
            //关联userId
            userGatewayMap.put(userId, gatewayRelation);
        } else {
            logger.warn("token {} 不存在 GatewayRelation", token);
        }
    }


    /**
     * 用户主动离开该服务器后，调用该方法，断线不要调用
     * 与byToken 调用一个就可以了
     *
     * @param userId
     */
    public void sendBreakGatewayMessage2Gateway(Long userId) {

        sendMessage2Gateway(userId, new SCBreakUserGatewayMessage());
    }

    /**
     * 用户主动离开该服务器后，调用该方法，断线不要调用
     * 与userId 调用一个就可以了
     *
     * @param token
     */
    public void sendBreakGatewayMessage2GatewayByToken(Long token) {

        sendMessage2GatewayByToken(token, new SCBreakUserGatewayMessage());
    }

    /**
     * 踢下线
     *
     * @param userId
     */
    public void sendKickOffMessage2Gateway(Long userId) {
        SCKickOffMessage message = new SCKickOffMessage();
        message.setUserId(userId);
        sendMessage2Gateway(userId, message);
    }

    /**
     * 踢下线
     *
     * @param token
     */
    public void sendKickOffMessage2GatewayByToken(Long token) {
        SCKickOffMessage message = new SCKickOffMessage();
        message.setToken(token);
        sendMessage2GatewayByToken(token, message);

    }


    public GatewayChannelManager getGatewayChannelManager(Long userId) {
        GatewayRelation gatewayRelation = userGatewayMap.get(userId);
        if (gatewayRelation != null) {
            return gatewayRelation.gatewayChannelManager;
        } else {
            return null;
        }
    }

    public GatewayChannelManager getGatewayChannelManagerByToken(Long token) {
        GatewayRelation gatewayRelation = tokenGatewayMap.get(token);
        if (gatewayRelation != null) {
            return gatewayRelation.gatewayChannelManager;
        } else {
            return null;
        }
    }

    public void sendMessage2GatewayByToken(Long token, Message message) {
        Producer2GatewayMessage toGateway = new Producer2GatewayMessage();
        toGateway.setToken(token);
        toGateway.setUserIds(new Long[0]);
        toGateway.setMessage(message);
        toGateway.setMessageId(message.getMessageId());
        toGateway.setRequestId(GatewayManager.getRequestId());
        GatewayRelation gatewayRelation = tokenGatewayMap.get(token);
        if (gatewayRelation != null) {
            gatewayRelation.gatewayChannelManager.sendMessage(toGateway);
        } else {
            logger.warn("token {} 不存在 GatewayRelation", token);
        }
    }


    public void sendMessage2Gateway(Long userId, Message message) {
        Producer2GatewayMessage toGateway = new Producer2GatewayMessage();
        toGateway.setUserIds(new Long[]{userId});
        toGateway.setMessage(message);
        toGateway.setMessageId(message.getMessageId());
        toGateway.setRequestId(GatewayManager.getRequestId());
        GatewayRelation gatewayRelation = userGatewayMap.get(userId);
        if (gatewayRelation != null) {
            gatewayRelation.gatewayChannelManager.sendMessage(toGateway);
        } else {
            logger.warn("userId {} 不存在 GatewayRelation", userId);
        }

    }

    /**
     * 丢失requestId
     *
     * @param userId
     * @param messages
     */

    public void sendMessage2Gateway(Long userId, List<? extends Message> messages) {
        GatewayRelation gatewayRelation = userGatewayMap.get(userId);
        if (gatewayRelation != null) {
            Long[] userIds = new Long[]{userId};
            List<Producer2GatewayMessage> frames = new ArrayList<>(messages.size());
            for (Message message : messages) {
                Producer2GatewayMessage toGateway = new Producer2GatewayMessage();
                toGateway.setUserIds(userIds);
                toGateway.setMessage(message);
                toGateway.setMessageId(message.getMessageId());
                toGateway.setRequestId(0);
                frames.add(toGateway);
            }

            gatewayRelation.gatewayChannelManager.sendMessage(frames);
        } else {
            logger.warn("userId {} 不存在 GatewayRelation", userId);
        }
    }

    public void sendMessage2Gateway(List<Long> userIds, Message message) {
        Map<Integer, GatewayUsers> map = new HashMap<>();
        for (Long userId : userIds) {
            GatewayRelation gatewayRelation = userGatewayMap.get(userId);
            if (gatewayRelation != null) {
                Integer number = gatewayRelation.gatewayChannelManager.getGatewayChannelKey();
                GatewayUsers gatewayUsers = map.get(number);
                if (gatewayUsers == null) {
                    gatewayUsers = new GatewayUsers();
                    gatewayUsers.gatewayChannelManager = gatewayRelation.gatewayChannelManager;
                    map.put(number, gatewayUsers);
                }
                gatewayUsers.userIds.add(userId);
            } else {
                logger.warn("userIds -> userId {} 不存在 GatewayRelation", userId);
            }
        }
        map.values().forEach(gatewayUsers -> {
            Producer2GatewayMessage toGateway = new Producer2GatewayMessage();
            toGateway.setMessage(message);
            toGateway.setMessageId(message.getMessageId());
            Long[] users = new Long[gatewayUsers.userIds.size()];
            gatewayUsers.userIds.toArray(users);
            toGateway.setUserIds(users);
            gatewayUsers.gatewayChannelManager.sendMessage(toGateway);
        });
    }

    public void sendMessage2EveryOne(Message message) {
        Producer2GatewayMessage toGateway = new Producer2GatewayMessage();
        toGateway.setUserIds(new Long[]{0L});
        toGateway.setMessage(message);
        toGateway.setMessageId(message.getMessageId());
        dispatchMessage2Gateway(toGateway);
    }

    class GatewayUsers {
        List<Long> userIds = new ArrayList<>(16);
        GatewayChannelManager gatewayChannelManager;
    }

    class GatewayRelation {
        GatewayChannelManager gatewayChannelManager;
        Long relationToken;
    }
}
