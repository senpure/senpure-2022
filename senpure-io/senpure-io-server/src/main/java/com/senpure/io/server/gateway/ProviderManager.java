package com.senpure.io.server.gateway;


import com.senpure.io.server.Constant;
import com.senpure.io.server.gateway.provider.Provider;
import com.senpure.io.server.gateway.provider.ProviderDefaultNextStrategy;
import com.senpure.io.server.gateway.provider.ProviderNextStrategy;
import com.senpure.io.server.protocol.message.CSBreakUserGatewayMessage;
import com.senpure.io.server.protocol.message.CSRelationUserGatewayMessage;
import com.senpure.io.server.protocol.message.SCFrameworkErrorMessage;
import com.senpure.io.server.support.MessageIdReader;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 网关管理一个服务的多个实例 每个实例可能含有多个管道channel
 * 一个服务对应一个 producerManager
 */
public class ProviderManager {


    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final GatewayMessageExecutor messageExecutor;

    private ProviderNextStrategy nextStrategy = new ProviderDefaultNextStrategy();

    public ProviderManager(GatewayMessageExecutor messageExecutor) {
        this.messageExecutor = messageExecutor;
    }


    private final ConcurrentMap<Long, ProviderRelation> tokenProviderMap = new ConcurrentHashMap<>();

    private final List<Provider> useProviders = new ArrayList<>();

    private final List<Provider> prepStopOldInstance = new ArrayList<>();
    private final Map<Integer, Boolean> handleIdsMap = new HashMap<>();

    private String serverName;


    public void bind(Long token, Long relationToken, Provider provider) {
        ProviderRelation providerRelation = new ProviderRelation();
        providerRelation.provider = provider;
        providerRelation.relationToken = relationToken;
        ProviderRelation old = tokenProviderMap.put(token, providerRelation);
        if (old == null) {
            provider.getStatistic().consumerIncr();
        }
    }


    public void sendMessage(GatewayReceiveConsumerMessage gatewayReceiveConsumerMessage) {
        ProviderRelation providerRelation = tokenProviderMap.get(gatewayReceiveConsumerMessage.getToken());
        Provider provider;
        if (providerRelation == null) {
            provider = nextProvider();
            if (provider == null) {
                logger.warn("{}没有服务实例可以使用", serverName);
                SCFrameworkErrorMessage errorMessage = new SCFrameworkErrorMessage();
                errorMessage.setCode(Constant.ERROR_NOT_FOUND_SERVER);
                errorMessage.getArgs().add(String.valueOf(gatewayReceiveConsumerMessage.getMessageId()));
                errorMessage.setMessage("没有服务器处理" + MessageIdReader.read(gatewayReceiveConsumerMessage.getMessageId()));
                messageExecutor.sendMessage2Consumer(gatewayReceiveConsumerMessage.getRequestId(), gatewayReceiveConsumerMessage.getToken(), errorMessage);
            } else {
                relationAndWaitSendMessage(provider, gatewayReceiveConsumerMessage);
            }

        } else {
            providerRelation.provider.sendMessage(gatewayReceiveConsumerMessage);

        }
    }


    public void sendMessage2Consumer(@Nullable String serverKey, int messageId, byte[] data) {
        if (serverKey != null) {
            for (Map.Entry<Long, ProviderRelation> entry : tokenProviderMap.entrySet()) {
                Long userToken = entry.getKey();
                ProviderRelation providerRelation = entry.getValue();
                Provider provider = providerRelation.provider;
                if (provider.getServerKey().equals(serverKey)) {
                    messageExecutor.sendMessage2Consumer(userToken, messageId, data);
                    break;
                }
            }
        } else {
            for (Map.Entry<Long, ProviderRelation> entry : tokenProviderMap.entrySet()) {
                Long userToken = entry.getKey();
                messageExecutor.sendMessage2Consumer(userToken, messageId, data);


            }
        }

    }

    /**
     * @param provider provider
     * @param relationToken relationToken
     * @param message 可以为空
     */
    private void waitRelationTask(Provider provider,
                                  Long relationToken,
                                  @Nullable GatewayReceiveConsumerMessage message) {
        WaitRelationTask waitRelationTask = new WaitRelationTask();
        waitRelationTask.setRelationToken(relationToken);
        waitRelationTask.setMessage(message);
        waitRelationTask.setProvider(provider);
        waitRelationTask.setProviderManager(this);
        messageExecutor.waitRelationMap.put(relationToken, waitRelationTask);
    }

    public void relationAndWaitSendMessage(Provider provider, GatewayReceiveConsumerMessage frame) {
        long relationToken = messageExecutor.idGenerator.nextId();
        CSRelationUserGatewayMessage relationUserGatewayMessage = new CSRelationUserGatewayMessage();
        relationUserGatewayMessage.setToken(frame.getToken());
        relationUserGatewayMessage.setUserId(frame.getUserId());
        relationUserGatewayMessage.setRelationToken(relationToken);
        GatewayReceiveConsumerMessage toMessage = messageExecutor.createMessage(relationUserGatewayMessage);
        waitRelationTask(provider, relationToken, frame);
        provider.sendMessage(toMessage);
    }

    protected Provider nextProvider() {
        int size = useProviders.size();
        switch (size) {
            case 0:
                return null;
            case 1:
                return useProviders.get(0);
            default:
                return nextStrategy.next(useProviders);
        }

    }


    public synchronized void prepStopOldInstance() {
        prepStopOldInstance.addAll(useProviders);
        useProviders.clear();
    }

    /**
     * 服务器掉线
     *
     * @param channel channel
     */
    public synchronized void providerOffLine(Channel channel) {
        providerOffLine(channel, prepStopOldInstance);
        providerOffLine(channel, useProviders);
    }

    private void providerOffLine(Channel channel, List<Provider> providers) {
        Iterator<Provider> iterator = providers.iterator();
        while (iterator.hasNext()) {
            Provider provider = iterator.next();
            if (provider.offline(channel)) {
                if (!provider.isActive()) {
                    iterator.remove();
                    clearRelation(provider);
                }
            }
        }


    }

    public void afterUserAuthorize(long channelToken, long userId) {
        ProviderRelation providerRelation = tokenProviderMap.get(channelToken);
        if (providerRelation != null) {
            logger.info("对 {} --> {} 进行userId关联", serverName, providerRelation.provider.getServerKey());
            CSRelationUserGatewayMessage message = new CSRelationUserGatewayMessage();
            message.setUserId(userId);
            message.setRelationToken(providerRelation.relationToken);
            GatewayReceiveConsumerMessage toMessage = messageExecutor.createMessage(message);
            waitRelationTask(providerRelation.provider, providerRelation.relationToken, null);
            providerRelation.provider.sendMessage(toMessage);
        }
    }

    //消费方离开了服务提供方
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
        ProviderRelation providerRelation = localRemove ? tokenProviderMap.remove(token) : tokenProviderMap.get(token);
        if (providerRelation != null) {
            if (localRemove) {
                providerRelation.provider.getStatistic().consumerDecr();
            }
            logger.info("{} {} 取消 对{} :token{} userId:{}的 关联  {}",
                    serverName, providerRelation.provider.getServerKey(), consumerChannel, token, userId, localRemove ? "移除" : "不移除");
            CSBreakUserGatewayMessage breakUserGatewayMessage = new CSBreakUserGatewayMessage();
            breakUserGatewayMessage.setRelationToken(providerRelation.relationToken);
            breakUserGatewayMessage.setUserId(userId);
            breakUserGatewayMessage.setToken(localRemove ? token : 0);
            breakUserGatewayMessage.setType(type);
            GatewayReceiveConsumerMessage gatewayReceiveConsumerMessage = messageExecutor.createMessage(breakUserGatewayMessage);
            gatewayReceiveConsumerMessage.setMessageId(breakUserGatewayMessage.messageId());
            gatewayReceiveConsumerMessage.setUserId(breakUserGatewayMessage.getUserId());
            gatewayReceiveConsumerMessage.setToken(breakUserGatewayMessage.getToken());
            providerRelation.provider.sendMessage(gatewayReceiveConsumerMessage);
        } else {
            logger.info("{} 没有对{} 有关联 :token{} userId:{} ",
                    serverName, consumerChannel, token, userId);

        }
    }


    private void clearRelation(Provider serverChannelManager) {
        logger.warn("{} {} 全部channel已经下线 清空关联列表", serverName, serverChannelManager.getServerKey());
        List<Long> tokens = new ArrayList<>();
        for (Map.Entry<Long, ProviderRelation> entry : tokenProviderMap.entrySet()) {
            if (serverChannelManager == entry.getValue().provider) {
                tokens.add(entry.getKey());
            }
        }
        for (Long token : tokens) {
            logger.info("{} 取消关联 {} {} ", token, serverName, serverChannelManager.getServerKey());
            tokenProviderMap.remove(token);
        }
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }


    public Provider getProducer(String producerKey) {
        for (Provider provider : useProviders) {
            if (provider.getServerKey().equals(producerKey)) {
                return provider;
            }
        }
        Provider manager = new Provider();
        manager.setServerKey(producerKey);
        return manager;

    }

    public List<Provider> getUseProviders() {
        return useProviders;
    }

    public synchronized void checkChannelServer(String serverKey, Provider channelManager) {
        for (Provider manager : useProviders) {
            if (manager.getServerKey().equals(serverKey)) {
                return;
            }
        }
        useProviders.add(channelManager);
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

    public void setNextStrategy(ProviderNextStrategy nextStrategy) {
        this.nextStrategy = nextStrategy;
    }

    static class ProviderRelation {
        Provider provider;
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
