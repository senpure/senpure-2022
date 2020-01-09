package com.senpure.io.server.support;

import com.senpure.executor.TaskLoopGroup;
import com.senpure.io.server.ChannelAttributeUtil;
import com.senpure.io.server.ServerProperties;
import com.senpure.io.server.event.EventHelper;
import com.senpure.io.server.producer.*;
import com.senpure.io.server.producer.handler.ProducerAskMessageHandler;
import com.senpure.io.server.producer.handler.ProducerMessageHandler;
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
    private ProducerMessageExecutor messageExecutor;
    @Resource
    private TaskLoopGroup service;
    @Value("${server.port:0}")
    private int httpPort;

    private ServerProperties.Producer producer;
    private ServerProperties.Gateway gateway = new ServerProperties.Gateway();


    private List<ProducerServer> servers = new ArrayList<>();


    private Map<String, Long> failGatewayMap = new HashMap<>();

    private long lastLogTime = 0;


    @PostConstruct
    public void init() {

        producer = serverProperties.getProducer();
        messageExecutor();
    }


    private void messageExecutor() {

        messageExecutor.setService(service);
        messageExecutor.setGatewayManager(gatewayManager);
        EventHelper.setService(service);
    }


    @PreDestroy
    public void destroy() {
        for (ProducerServer server : servers) {
            server.destroy();
        }

    }

    @Override
    public void run(ApplicationArguments args) {
        List<Integer> ids = ProducerMessageHandlerUtil.getHandlerMessageIds();
        List<HandleMessage> handleMessages = new ArrayList<>();
        for (Integer id : ids) {
            HandleMessage handleMessage = new HandleMessage();
            handleMessage.setHandleMessageId(id);
            ProducerMessageHandler handler = ProducerMessageHandlerUtil.getHandler(id);
            if (handler instanceof ProducerAskMessageHandler) {
                //避免编码出错
                handleMessage.setDirect(false);
                if (handler.direct()) {
                    logger.warn("{}实现了ProducerAskMessageHandler但是direct()返回true 修正为false", handler.getClass().getName());
                }
            } else {
                handleMessage.setDirect(true);
            }

            handleMessage.setMessageName(ProducerMessageHandlerUtil.getEmptyMessage(id).getClass().getName());
            handleMessages.add(handleMessage);
            MessageIdReader.relation(id, handler.getEmptyMessage().getClass().getSimpleName());
        }
        List<IdName> idNames = null;
        if (StringUtils.isNoneEmpty(producer.getIdNamesPackage())) {
            idNames = MessageScanner.scan(producer.getIdNamesPackage());

        }
        List<IdName> finalIdNames = idNames;
        service.scheduleWithFixedDelay(() -> {
            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(producer.getGatewayName());
            for (ServiceInstance instance : serviceInstances) {
                String portStr = instance.getMetadata().get("scPort");
                int port = 0;
                if (portStr == null) {
                    logger.info("网关 [{}] {} {} 没有 没有配置sc socket端口,使用默认端口 {}", producer.getGatewayName(), instance.getHost(), instance.getUri(), gateway.getScPort());
                    port = gateway.getScPort();
                } else {
                    port = Integer.parseInt(portStr);
                }
                String gatewayKey = gatewayManager.getGatewayKey(instance.getHost(), port);
                GatewayChannelManager gatewayChannelManager = gatewayManager.getGatewayChannelServer(gatewayKey);
                if (gatewayChannelManager.isConnecting()) {
                    continue;
                }
                long now = System.currentTimeMillis();
                if (gatewayChannelManager.getChannelSize() < producer.getGatewayChannel()) {
                    Long lastFailTime = failGatewayMap.get(gatewayKey);
                    boolean start = false;
                    if (lastFailTime == null) {
                        start = true;
                    } else {
                        if (now - lastFailTime >= producer.getConnectFailInterval()) {
                            start = true;
                        }
                    }
                    if (start) {
                        gatewayChannelManager.setConnecting(true);
                        ProducerServer producerServer = new ProducerServer();
                        producerServer.setGatewayManager(gatewayManager);
                        producerServer.setProperties(producer);
                        producerServer.setMessageExecutor(messageExecutor);
                        producerServer.setServerName(serverProperties.getName());
                        producerServer.setHttpPort(httpPort);
                        producerServer.setReadableServerName(producer.getReadableName());
                        if (producerServer.start(instance.getHost(), port)) {
                            servers.add(producerServer);
                            regServer(producerServer, handleMessages);
                            if (gatewayChannelManager.getChannelSize() == 0) {
                                gatewayChannelManager.setDefaultMessageRetryTimeLimit(producer.getMessageRetryTimeLimit());
                                if (finalIdNames != null && finalIdNames.size() > 0) {
                                    regIdNames(producerServer, finalIdNames);
                                }
                            }
                            //认证
                            gatewayChannelManager.addChannel(producerServer.getChannel());

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

    public void regServer(ProducerServer server, List<HandleMessage> handleMessages) {
        SCRegServerHandleMessageMessage message = new SCRegServerHandleMessageMessage();
        message.setServerName(serverProperties.getName());
        message.setReadableServerName(server.getReadableServerName());
        message.setServerKey(ChannelAttributeUtil.getLocalServerKey(server.getChannel()));
        message.setMessages(handleMessages);
        Producer2GatewayMessage gatewayMessage = new Producer2GatewayMessage();
        gatewayMessage.setMessageId(message.getMessageId());
        gatewayMessage.setMessage(message);
        gatewayMessage.setUserIds(new Long[]{0L});
        logger.debug("向{}注册服务", ChannelAttributeUtil.getRemoteServerKey(server.getChannel()));
        for (HandleMessage handleMessage : handleMessages) {
            logger.debug(handleMessage.toString());
        }
        server.getChannel().writeAndFlush(gatewayMessage);
    }

    public void regIdNames(ProducerServer server, List<IdName> idNames) {
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

    private void regIdNames(ProducerServer server, SCIdNameMessage message) {
        Producer2GatewayMessage gatewayMessage = new Producer2GatewayMessage();
        gatewayMessage.setMessageId(message.getMessageId());
        gatewayMessage.setMessage(message);
        gatewayMessage.setUserIds(new Long[]{0L});
        server.getChannel().writeAndFlush(gatewayMessage);
    }
}
