package com.senpure.io.server.gateway;


import com.senpure.io.server.Constant;
import com.senpure.io.server.gateway.provider.Producer;
import com.senpure.io.server.gateway.provider.ProviderDefaultNextStrategy;
import com.senpure.io.server.gateway.provider.ProviderNextStrategy;
import com.senpure.io.server.protocol.message.CSBreakUserGatewayMessage;
import com.senpure.io.server.protocol.message.CSRelationUserGatewayMessage;
import com.senpure.io.server.protocol.message.SCInnerErrorMessage;
import com.senpure.io.server.support.MessageIdReader;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 网关管理一个服务的多个实例 每个实例可能含有多个管道channel
 * 一个服务对应一个 producerManager
 */
public class ProviderManager {


    private Logger logger = LoggerFactory.getLogger(getClass());
    private GatewayMessageExecutor messageExecutor;

    private ProviderNextStrategy nextStrategy = new ProviderDefaultNextStrategy();

    public ProviderManager(GatewayMessageExecutor messageExecutor) {
        this.messageExecutor = messageExecutor;
    }


    private final ConcurrentMap<Long, ProducerRelation> tokenProducerMap = new ConcurrentHashMap<>();

    private final List<Producer> useProducers = new ArrayList<>();

    private final List<Producer> prepStopOldInstance = new ArrayList<>();
    private final Map<Integer, Boolean> handleIdsMap = new HashMap<>();
    private final AtomicInteger atomicIndex = new AtomicInteger(-1);
    private String serverName;


    public void bind(Long token, Long relationToken, Producer producer) {
        ProducerRelation producerRelation = new ProducerRelation();
        producerRelation.producer = producer;
        producerRelation.relationToken = relationToken;
        tokenProducerMap.put(token, producerRelation);

    }


    public void sendMessage(Client2GatewayMessage client2GatewayMessage) {
        ProducerRelation producerRelation = tokenProducerMap.get(client2GatewayMessage.getToken());
        Producer producer;
        if (producerRelation == null) {
            producer = nextProducer();
            if (producer == null) {
                logger.warn("{}没有服务实例可以使用", serverName);
                SCInnerErrorMessage errorMessage = new SCInnerErrorMessage();
                errorMessage.setCode(Constant.ERROR_NOT_FOUND_SERVER);
                errorMessage.getArgs().add(String.valueOf(client2GatewayMessage.getMessageId()));
                errorMessage.setMessage("没有服务器处理" + MessageIdReader.read(client2GatewayMessage.getMessageId()));
                messageExecutor.sendMessage2Client(client2GatewayMessage.getRequestId(), errorMessage, client2GatewayMessage.getToken());
            } else {
                relationAndWaitSendMessage(producer, client2GatewayMessage);
            }

        } else {
            producerRelation.producer.sendMessage(client2GatewayMessage);

        }
    }

    /**
     * @param producer
     * @param relationToken
     * @param client2GatewayMessage 可以为空
     */
    private void waitRelationTask(Producer producer,
                                  Long relationToken,
                                  Client2GatewayMessage client2GatewayMessage) {
        WaitRelationTask waitRelationTask = new WaitRelationTask();
        waitRelationTask.setRelationToken(relationToken);
        waitRelationTask.setMessage(client2GatewayMessage);
        waitRelationTask.setServerChannelManager(producer);
        waitRelationTask.setServerManager(this);
        messageExecutor.waitRelationMap.put(relationToken, waitRelationTask);
    }

    public void relationAndWaitSendMessage(Producer serverChannelManager, Client2GatewayMessage client2GatewayMessage) {
        long relationToken = messageExecutor.idGenerator.nextId();
        CSRelationUserGatewayMessage message = new CSRelationUserGatewayMessage();
        message.setToken(client2GatewayMessage.getToken());
        message.setUserId(client2GatewayMessage.getUserId());
        message.setRelationToken(relationToken);
        Client2GatewayMessage toMessage = messageExecutor.createMessage(message);
        waitRelationTask(serverChannelManager, relationToken, client2GatewayMessage);
        serverChannelManager.sendMessage(toMessage);
    }

    protected Producer nextProducer() {
        int size = useProducers.size();
        switch (size) {
            case 0:
                return null;
            case 1:
                return useProducers.get(0);
            default:
                return nextStrategy.next(useProducers);
        }

    }


    public synchronized void prepStopOldInstance() {
        prepStopOldInstance.addAll(useProducers);
        useProducers.clear();
    }

    /**
     * 服务器掉线
     *
     * @param channel
     */
    public synchronized void serverOffLine(Channel channel) {
        serverOffLine(channel, prepStopOldInstance);
        serverOffLine(channel, useProducers);
    }

    private void serverOffLine(Channel channel, List<Producer> channelManagers) {
        Iterator<Producer> iterator = channelManagers.iterator();
        while (iterator.hasNext()) {
            Producer producer = iterator.next();
            if (producer.offline(channel)) {
                if (!producer.isActive()) {
                    iterator.remove();
                    clearRelation(producer);
                }
            }
        }


    }

    public void afterUserAuthorize(long channelToken, long userId) {
        ProducerRelation producerRelation = tokenProducerMap.get(channelToken);
        if (producerRelation != null) {
            logger.info("对 {} --> {} 进行userId关联", serverName, producerRelation.producer.getServerKey());
            CSRelationUserGatewayMessage message = new CSRelationUserGatewayMessage();
            message.setUserId(userId);
            message.setRelationToken(producerRelation.relationToken);
            Client2GatewayMessage toMessage = messageExecutor.createMessage(message);
            waitRelationTask(producerRelation.producer, producerRelation.relationToken, null);
            producerRelation.producer.sendMessage(toMessage);
        }
    }

    //消费方离开了生产方
    public void consumerLeaveProducer(Channel consumerChannel, Long token, Long userId) {
        breakUserGateway(consumerChannel, token, userId, Constant.BREAK_TYPE_USER_LEAVE);
    }

    //消费方离线
    public void consumerOffline(Channel consumerChannel, Long token, Long userId) {
        breakUserGateway(consumerChannel, token, userId, Constant.BREAK_TYPE_USER_OFFlINE);
    }

    //消费方切换用户
    public void consumerUserChange(Channel consumerChannel, Long token, Long userId, int csLoginMessageId) {
        if (getHandleIds().contains(csLoginMessageId)) {
            breakUserGateway(consumerChannel, token, userId, Constant.BREAK_TYPE_USER_CHANGE, false);
        } else {
            breakUserGateway(consumerChannel, token, userId, Constant.BREAK_TYPE_USER_CHANGE);
        }
    }

    private void breakUserGateway(Channel consumerChannel, Long token, Long userId, String type) {
        breakUserGateway(consumerChannel, token, userId, type, true);
    }

    private void breakUserGateway(Channel consumerChannel, Long token, Long userId, String type, boolean localRemove) {
        ProducerRelation producerRelation = localRemove ? tokenProducerMap.remove(token) : tokenProducerMap.get(token);
        if (producerRelation != null) {
            if (localRemove) {

            }
            logger.info("{} {} 取消 对{} :token{} userId:{}的 关联  {}",
                    serverName, producerRelation.producer.getServerKey(), consumerChannel, token, userId, localRemove ? "移除" : "不移除");
            CSBreakUserGatewayMessage breakUserGatewayMessage = new CSBreakUserGatewayMessage();
            breakUserGatewayMessage.setRelationToken(producerRelation.relationToken);
            breakUserGatewayMessage.setUserId(userId);
            breakUserGatewayMessage.setToken(localRemove ? token : 0);
            breakUserGatewayMessage.setType(type);
            Client2GatewayMessage client2GatewayMessage = messageExecutor.createMessage(breakUserGatewayMessage);
            client2GatewayMessage.setMessageId(breakUserGatewayMessage.getMessageId());
            client2GatewayMessage.setUserId(breakUserGatewayMessage.getUserId());
            client2GatewayMessage.setToken(breakUserGatewayMessage.getToken());
            producerRelation.producer.sendMessage(client2GatewayMessage);
        } else {
            logger.info("{} 没有对{} 有关联 :token{} userId:{} ",
                    serverName, consumerChannel, token, userId);

        }
    }


    private void clearRelation(Producer serverChannelManager) {
        logger.warn("{} {} 全部channel已经下线 清空关联列表", serverName, serverChannelManager.getServerKey());
        List<Long> tokens = new ArrayList<>();
        for (Map.Entry<Long, ProducerRelation> entry : tokenProducerMap.entrySet()) {
            if (serverChannelManager == entry.getValue().producer) {
                tokens.add(entry.getKey());
            }
        }
        for (Long token : tokens) {
            logger.info("{} 取消关联 {} {} ", token, serverName, serverChannelManager.getServerKey());
            tokenProducerMap.remove(token);
        }
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }


    public Producer getProducer(String producerKey) {
        for (Producer producer : useProducers) {
            if (producer.getServerKey().equals(producerKey)) {
                return producer;
            }
        }
        Producer manager = new Producer();
        manager.setServerKey(producerKey);
        return manager;

    }

    public List<Producer> getUseProducers() {
        return useProducers;
    }

    public synchronized void checkChannelServer(String serverKey, Producer channelManager) {
        for (Producer manager : useProducers) {
            if (manager.getServerKey().equals(serverKey)) {
                return;
            }
        }
        useProducers.add(channelManager);
    }

    public void markHandleId(int messageId) {
        handleIdsMap.put(messageId, true);
    }

    public List<Integer> getHandleIds() {
        return new ArrayList<>(handleIdsMap.keySet());
    }


    public boolean handleId(int messageId) {
        return handleIdsMap.get(messageId) != null;
    }

    static class ProducerRelation {
        Producer producer;
        Long relationToken;
    }

    public static void main(String[] args) {

        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();

        Integer key = 1;
        String s = map.putIfAbsent(key, "2");
        System.out.println(map.get(key));
        System.out.println(s);
        s = map.putIfAbsent(key, "3");
        System.out.println(map.get(key));
        System.out.println(s);
    }
}
