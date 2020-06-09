package com.senpure.io.server.support;

import com.senpure.executor.TaskLoopGroup;
import com.senpure.io.server.ChannelAttributeUtil;
import com.senpure.io.server.ServerProperties;
import com.senpure.io.server.event.EventHelper;
import com.senpure.io.server.provider.*;
import com.senpure.io.server.provider.handler.ProviderAskMessageHandler;
import com.senpure.io.server.provider.handler.ProviderMessageHandler;
import com.senpure.io.server.protocol.bean.HandleMessage;
import com.senpure.io.server.protocol.bean.IdName;
import com.senpure.io.server.protocol.message.SCIdNameMessage;
import com.senpure.io.server.protocol.message.SCRegServerHandleMessageMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * ProducerChecker
 *
 * @author senpure
 * @time 2019-03-04 17:51:39
 */
public class ProducerServerStarter implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(ProducerServerStarter.class);
    @Resource
    private DiscoveryClient discoveryClient;
    @Resource
    private ServerProperties serverProperties;
    @Resource
    private GatewayManager gatewayManager;
    @Resource
    private ProviderMessageExecutor messageExecutor;
    @Resource
    private TaskLoopGroup service;
    @Value("${server.port:8080}")
    private int httpPort;

    private ServerProperties.Provider provider;
    private ServerProperties.Gateway gateway = new ServerProperties.Gateway();


    private List<ProviderServer> servers = new ArrayList<>();


    private Map<String, Long> failGatewayMap = new HashMap<>();

    private long lastLogTime = 0;


    @PostConstruct
    public void init() {

        provider = serverProperties.getProvider();
        messageExecutor();
    }


    private void messageExecutor() {

        messageExecutor.setService(service);
        messageExecutor.setGatewayManager(gatewayManager);
        EventHelper.setService(service);
    }


    @PreDestroy
    public void destroy() {
        for (ProviderServer server : servers) {
            server.destroy();
        }

    }

    @Override
    public void run(ApplicationArguments args) {
        List<Integer> ids = ProviderMessageHandlerUtil.getHandlerMessageIds();
        List<HandleMessage> handleMessages = new ArrayList<>();
        for (Integer id : ids) {
            HandleMessage handleMessage = new HandleMessage();
            handleMessage.setHandleMessageId(id);
            ProviderMessageHandler handler = ProviderMessageHandlerUtil.getHandler(id);
            if (handler instanceof ProviderAskMessageHandler) {
                //避免编码出错
                handleMessage.setDirect(false);
                if (handler.direct()) {
                    logger.warn("{}实现了ProducerAskMessageHandler但是direct()返回true 修正为false", handler.getClass().getName());
                }
            } else {
                handleMessage.setDirect(true);
            }

            handleMessage.setMessageName(ProviderMessageHandlerUtil.getEmptyMessage(id).getClass().getName());
            handleMessages.add(handleMessage);
            MessageIdReader.relation(id, handler.getEmptyMessage().getClass().getSimpleName());
        }
        List<IdName> idNames = null;
        if (StringUtils.isNoneEmpty(provider.getIdNamesPackage())) {
            idNames = MessageScanner.scan(provider.getIdNamesPackage());

        }
        List<IdName> finalIdNames = idNames;
        service.scheduleWithFixedDelay(() -> {
            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(provider.getGatewayName());
            for (ServiceInstance instance : serviceInstances) {
                boolean useDefault = false;
                String portStr = instance.getMetadata().get("scPort");

                int port;
                if (portStr == null) {
                    useDefault = true;
                    port = gateway.getScPort();
                } else {
                    port = Integer.parseInt(portStr);
                }
                String gatewayKey = gatewayManager.getGatewayKey(instance.getHost(), port);
                GatewayChannelManager gatewayChannelManager = gatewayManager.getGatewayChannelServer(gatewayKey);

                if (gatewayChannelManager == null) {
                    gatewayChannelManager = new GatewayChannelManager(gatewayKey, provider.getGatewayChannel());
                    gatewayChannelManager = gatewayManager.addGatewayChannelServer(gatewayChannelManager);
                }
                if (gatewayChannelManager.isConnecting()) {
                    continue;
                }
                long now = System.currentTimeMillis();
                if (gatewayChannelManager.getChannelSize() < provider.getGatewayChannel()) {
                    Long lastFailTime = failGatewayMap.get(gatewayKey);
                    boolean start = false;
                    if (lastFailTime == null) {
                        start = true;
                    } else {
                        if (now - lastFailTime >= provider.getConnectFailInterval()) {
                            start = true;
                        }
                    }
                    if (start) {
                        if (useDefault) {
                            logger.info("网关 [{}] {} {} 没有 没有配置sc socket端口,使用默认端口 {}", provider.getGatewayName(), instance.getHost(), instance.getUri(), gateway.getScPort());
                        }
                        gatewayChannelManager.setConnecting(true);
                        ProviderServer providerServer = new ProviderServer();
                        providerServer.setGatewayManager(gatewayManager);
                        providerServer.setProperties(provider);
                        providerServer.setMessageExecutor(messageExecutor);
                        providerServer.setServerName(serverProperties.getName());
                        providerServer.setHttpPort(httpPort);
                        providerServer.setReadableServerName(provider.getReadableName());
                        if (providerServer.start(instance.getHost(), port)) {
                            servers.add(providerServer);
                            regServer(providerServer, handleMessages);
                            if (gatewayChannelManager.getChannelSize() == 0) {
                                gatewayChannelManager.setDefaultMessageRetryTimeLimit(provider.getMessageRetryTimeLimit());
                                if (finalIdNames != null && finalIdNames.size() > 0) {
                                    regIdNames(providerServer, finalIdNames);
                                }
                            }
                            //认证
                            gatewayChannelManager.addChannel(providerServer.getChannel());

                        } else {
                            failGatewayMap.put(gatewayKey, now);
                        }
                        gatewayChannelManager.setConnecting(false);
                    }
                }
            }
            long now = System.currentTimeMillis();
            if (now - lastLogTime >= 60000) {
                lastLogTime = now;
                gatewayManager.report();
            }
        }, 2000, 50, TimeUnit.MILLISECONDS);
    }

    public void regServer(ProviderServer server, List<HandleMessage> handleMessages) {
        SCRegServerHandleMessageMessage message = new SCRegServerHandleMessageMessage();
        message.setServerName(serverProperties.getName());
        message.setReadableServerName(server.getReadableServerName());
        message.setServerKey(ChannelAttributeUtil.getLocalServerKey(server.getChannel()));
        message.setMessages(handleMessages);
        Provider2GatewayMessage gatewayMessage = new Provider2GatewayMessage();
        gatewayMessage.setMessageId(message.getMessageId());
        gatewayMessage.setMessage(message);
        gatewayMessage.setUserIds(new Long[]{0L});
        logger.debug("向{}注册服务", ChannelAttributeUtil.getRemoteServerKey(server.getChannel()));
        for (HandleMessage handleMessage : handleMessages) {
            logger.debug(handleMessage.toString());
        }
        server.getChannel().writeAndFlush(gatewayMessage);
    }

    public void regIdNames(ProviderServer server, List<IdName> idNames) {
        SCIdNameMessage message = new SCIdNameMessage();
        for (int i = 0; i < idNames.size(); i++) {
            if (i > 0 && i % 100 == 0) {
                regIdNames(server, message);
                message = new SCIdNameMessage();
            }
            message.getIdNames().add(idNames.get(i));
        }
        if (message.getIdNames().size() > 0) {
            regIdNames(server, message);
        }
    }

    private void regIdNames(ProviderServer server, SCIdNameMessage message) {
        Provider2GatewayMessage gatewayMessage = new Provider2GatewayMessage();
        gatewayMessage.setMessageId(message.getMessageId());
        gatewayMessage.setMessage(message);
        gatewayMessage.setUserIds(new Long[]{0L});
        server.getChannel().writeAndFlush(gatewayMessage);
    }
}
