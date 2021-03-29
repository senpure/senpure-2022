package com.senpure.io.server.provider;

import com.senpure.io.protocol.Message;
import com.senpure.io.server.protocol.message.SCBreakUserGatewayMessage;
import com.senpure.io.server.protocol.message.SCKickOffMessage;
import com.senpure.io.server.protocol.message.SCMessageForwardMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class GatewayManager implements MessageSender {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ConcurrentMap<String, GatewayChannelManager> gatewayChannelMap = new ConcurrentHashMap<>();

    private final ConcurrentMap<Long, GatewayRelation> userGatewayMap = new ConcurrentHashMap<>();

    private final ConcurrentMap<Long, GatewayRelation> tokenGatewayMap = new ConcurrentHashMap<>();



    public static int getRequestId() {
        return REQUEST_ID.get();
    }

    public String getGatewayKey(String host, int port) {
        return host + ":" + port;
    }

    @Nullable
    public GatewayChannelManager getGatewayChannelManager(String gatewayKey) {
        return gatewayChannelMap.get(gatewayKey);
    }

    @Nonnull
    public GatewayChannelManager addGatewayChannelManager(@Nonnull GatewayChannelManager gatewayChannelManager) {
        gatewayChannelMap.putIfAbsent(gatewayChannelManager.getGatewayKey(), gatewayChannelManager);
        return gatewayChannelMap.get(gatewayChannelManager.getGatewayKey());
    }

    public void report() {
        for (Map.Entry<String, GatewayChannelManager> entry : gatewayChannelMap.entrySet()) {
            logger.debug("{} {}", entry.getKey(), entry.getValue().toString());
        }
    }

    /**
     * 将userId与网关关联起来
     *
     * @param gatewayKey  gatewayKey
     * @param userId userId
     * @param relationToken relationToken
     */
    @Override
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
     * @param gatewayKey gatewayKey
     * @param token token
     * @param relationToken relationToken
     */
    @Override
    public void relationToken(String gatewayKey, Long token, long relationToken) {
        GatewayChannelManager channelManager = gatewayChannelMap.get(gatewayKey);
        if (channelManager != null) {
            GatewayRelation relation = new GatewayRelation();
            relation.relationToken = relationToken;
            relation.gatewayChannelManager = channelManager;
            tokenGatewayMap.put(token, relation);
        }
    }

    @Override
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
    @Override
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

    public void sendMessage(Long userId, Message message, boolean tryAllGateway) {
        ProviderSendMessage toGateway = createMessage(userId, message);
        GatewayRelation gatewayRelation = userGatewayMap.get(userId);
        if (gatewayRelation != null) {
            gatewayRelation.gatewayChannelManager.sendMessage(toGateway);
        } else {
            if (tryAllGateway) {
                dispatchMessage(toGateway);
            } else {
                logger.warn("userId {} 不存在 GatewayRelation", userId);
            }

        }
    }
    public void sendMessageByToken(Long token, Message message, boolean tryAllGateway) {
        ProviderSendMessage toGateway = createMessageByToken(token, message);
        GatewayRelation gatewayRelation = tokenGatewayMap.get(token);
        if (gatewayRelation != null) {
            gatewayRelation.gatewayChannelManager.sendMessage(toGateway);
        } else {
            if (tryAllGateway) {
                dispatchMessage(toGateway);
            } else {
                logger.warn("token {} 不存在 GatewayRelation", token);
            }

        }
    }
    /**
     * 向用户发送消息
     *
     * @param userId  用户唯一标识
     * @param message 消息
     */
    @Override
    public void sendMessage(Long userId, Message message) {
        sendMessage(userId, message, false);
    }

    /**
     * 向用户发送消息
     *
     * @param token   用户本次临时唯一标识
     * @param message 消息
     */
    @Override
    public void sendMessageByToken(Long token, Message message) {
        sendMessageByToken(token, message, false);
    }

    /**
     * 响应用户消息
     *
     * @param userId  用户唯一标识
     * @param message 消息
     */
    @Override
    public void respondMessage(Long userId, Message message) {
        respondMessage(userId, message, getRequestId());
    }

    /**
     * 响应用户消息
     *
     * @param userId    用户唯一标识
     * @param message   消息
     * @param requestId 用户请求唯一标识
     */
    @Override
    public void respondMessage(Long userId, Message message, int requestId) {
        ProviderSendMessage toGateway = createMessage(userId, message);
        toGateway.setRequestId(requestId);
        GatewayRelation gatewayRelation = userGatewayMap.get(userId);
        if (gatewayRelation != null) {
            gatewayRelation.gatewayChannelManager.sendMessage(toGateway);
        } else {
            logger.warn("userId {} 不存在 GatewayRelation", userId);
        }
    }

    /**
     * 响应用户消息
     *
     * @param token   用户本次临时唯一标识
     * @param message 消息
     */
    @Override
    public void respondMessageByToken(Long token, Message message) {

        respondMessageByToken(token,message,getRequestId());
    }

    /**
     * 响应用户消息
     *
     * @param token     用户本次临时唯一标识
     * @param message   消息
     * @param requestId 用户请求唯一标识
     */
    @Override
    public void respondMessageByToken(Long token, Message message, int requestId) {
        ProviderSendMessage toGateway = createMessageByToken(token, message);
        toGateway.setRequestId(requestId);
        GatewayRelation gatewayRelation = tokenGatewayMap.get(token);
        if (gatewayRelation != null) {
            gatewayRelation.gatewayChannelManager.sendMessage(toGateway);
        } else {
            logger.warn("token {} 不存在 GatewayRelation", token);
        }
    }

    @Override
    public void sendMessage(Long userId, List<Message> messages) {
        GatewayRelation gatewayRelation = userGatewayMap.get(userId);
        if (gatewayRelation != null) {
            List<ProviderSendMessage> frames = new ArrayList<>(messages.size());
            Long[] userIds = new Long[]{userId};
            for (Message message : messages) {
                ProviderSendMessage toGateway =createMessage(userIds,message);
                frames.add(toGateway);
            }

            gatewayRelation.gatewayChannelManager.sendMessage(frames);
        } else {
            logger.warn("userId {} 不存在 GatewayRelation", userId);
        }
    }

    @Override
    public void sendMessageByToken(Long token, List<Message> messages) {
        GatewayRelation gatewayRelation = tokenGatewayMap.get(token);
        if (gatewayRelation != null) {
            List<ProviderSendMessage> frames = new ArrayList<>(messages.size());
            for (Message message : messages) {
                ProviderSendMessage toGateway =createMessageByToken(token,message);
                frames.add(toGateway);
            }
            gatewayRelation.gatewayChannelManager.sendMessage(frames);
        } else {
            logger.warn("token {} 不存在 GatewayRelation", token);
        }
    }

    @Override
    public void sendMessage(List<Long> userIds, Message message) {
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
            Long[] users = new Long[gatewayUsers.userIds.size()];
            gatewayUsers.userIds.toArray(users);
            ProviderSendMessage toGateway = createMessage(users,message);
            gatewayUsers.gatewayChannelManager.sendMessage(toGateway);
        });
    }

    @Override
    public void sendMessageByToken(List<Long> tokens, Message message) {
        Map<Integer, GatewayUsers> map = new HashMap<>();
        for (Long token : tokens) {
            GatewayRelation gatewayRelation = tokenGatewayMap.get(token);
            if (gatewayRelation != null) {
                Integer number = gatewayRelation.gatewayChannelManager.getGatewayChannelKey();
                GatewayUsers gatewayUsers = map.get(number);
                if (gatewayUsers == null) {
                    gatewayUsers = new GatewayUsers();
                    gatewayUsers.gatewayChannelManager = gatewayRelation.gatewayChannelManager;
                    map.put(number, gatewayUsers);
                }
                gatewayUsers.userIds.add(token);
            } else {
                logger.warn("tokens -> token {} 不存在 GatewayRelation", token);
            }
        }
        map.values().forEach(gatewayUsers -> {
            Long[] users = new Long[gatewayUsers.userIds.size()];
            gatewayUsers.userIds.toArray(users);
            ProviderSendMessage toGateway = createMessage(users,message);
            gatewayUsers.gatewayChannelManager.sendMessage(toGateway);
        });
    }

    /**
     * 将消息发送给所有的consumer
     *
     * @param message 消息
     */
    @Override
    public void dispatchMessage(Message message) {
        ProviderSendMessage toGateway = createMessage(new Long[]{0L},message);
        dispatchMessage(toGateway);
    }

    /**
     * 将消息发送给所有的consumer
     *
     * @param message 消息
     */
    @Override
    public void dispatchMessage(ProviderSendMessage message) {
        for (GatewayChannelManager value : gatewayChannelMap.values()) {
            value.sendMessage(message);
        }

    }

    /**
     * 将消息发送给其他Provider
     *
     * @param serverName Provider服务名
     * @param serverKey  Provider服务唯一表示
     * @param message    消息
     */
    @Override
    public void dispatchMessage(String serverName, String serverKey, Message message) {
        SCMessageForwardMessage forwardMessage = new SCMessageForwardMessage();
        forwardMessage.setServerName(serverName);
        forwardMessage.setServerKey(serverKey);

        ByteBuf buf = Unpooled.buffer(forwardMessage.serializedSize());
        message.write(buf);
        forwardMessage.setData(buf.array());

        dispatchMessage(forwardMessage);
    }

    @Override
    public void sendKickOffMessage(Long userId) {
        SCKickOffMessage message = new SCKickOffMessage();
        message.setUserId(userId);
        sendMessage(userId, message, true);
    }

    @Override
    public void sendKickOffMessageByToken(Long token) {
        SCKickOffMessage message = new SCKickOffMessage();
        message.setToken(token);
        sendMessageByToken(token, message, true);
    }

    /**
     * 用户主动离开该服务器后，调用该方法，断线不要调用
     * 与byToken 调用一个就可以了
     *
     * @param userId userId
     */
    @Override
    public void sendUserLeaveMessage(Long userId) {
        sendMessage(userId, new SCBreakUserGatewayMessage());
    }

    /**
     * 用户主动离开该服务器后，调用该方法，断线不要调用
     * 与userId 调用一个就可以了
     *
     * @param token token
     */
    @Override
    public void sendUserLeaveMessageByToken(Long token) {
        sendMessageByToken(token, new SCBreakUserGatewayMessage());
    }

    @Override
    public void respondMessageByTokenAndRelationUser(Long token, Long userId, Message message) {

        respondMessageByTokenAndRelationUser(token,userId,message,getRequestId());
    }

    @Override
    public void respondMessageByTokenAndRelationUser(Long token, Long userId, Message message, int requestId) {
        if (userId == 0) {
            logger.warn("userId 不能为0");
            return;
        }
        ProviderSendMessage toGateway = new ProviderSendMessage();
        toGateway.setToken(token);
        toGateway.setUserIds(new Long[]{userId});
        toGateway.setMessage(message);
        toGateway.setMessageId(message.messageId());
        toGateway.setRequestId(requestId);
        GatewayRelation gatewayRelation = tokenGatewayMap.get(token);
        if (gatewayRelation != null) {
            gatewayRelation.gatewayChannelManager.sendMessage(toGateway);
            //关联userId
            userGatewayMap.put(userId, gatewayRelation);
        } else {
            logger.warn("token {} 不存在 GatewayRelation", token);
        }
    }

    @Override
    public void sendMessageByTokenAndRelationUser(Long token, Long userId, Message message) {
        respondMessageByTokenAndRelationUser(token,userId,message,getRequestId());
    }

    private static class GatewayUsers {
        List<Long> userIds = new ArrayList<>(16);
        GatewayChannelManager gatewayChannelManager;
    }

    private   static class GatewayRelation {
        GatewayChannelManager gatewayChannelManager;
        Long relationToken;
    }
}
