package com.senpure.io.server.support;

import com.senpure.executor.TaskLoopGroup;
import com.senpure.io.protocol.Message;
import com.senpure.io.server.*;
import com.senpure.io.server.protocol.bean.HandleMessage;
import com.senpure.io.server.protocol.bean.IdName;
import com.senpure.io.server.protocol.message.CSFrameworkVerifyMessage;
import com.senpure.io.server.protocol.message.CSFrameworkVerifyProviderMessage;
import com.senpure.io.server.protocol.message.SCIdNameMessage;
import com.senpure.io.server.protocol.message.SCRegServerHandleMessageMessage;
import com.senpure.io.server.provider.ProviderMessageExecutor;
import com.senpure.io.server.provider.ProviderMessageHandlerContext;
import com.senpure.io.server.provider.ProviderSendMessage;
import com.senpure.io.server.provider.gateway.Gateway;
import com.senpure.io.server.provider.gateway.GatewayManager;
import com.senpure.io.server.provider.gateway.ProviderGatewayServer;
import com.senpure.io.server.provider.handler.ProviderAskMessageHandler;
import com.senpure.io.server.provider.handler.ProviderMessageHandler;
import com.senpure.io.server.remoting.ChannelService;
import io.netty.channel.Channel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ProviderGatewayServerStarter implements ApplicationRunner {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ServerProperties properties;
    private final List<ProviderGatewayServer> servers = new ArrayList<>();
    private final Map<String, Long> failGatewayMap = new HashMap<>();
    private long lastLogTime = 0;

    @Resource
    private DiscoveryClient discoveryClient;
    @Resource
    private GatewayManager gatewayManager;
    @Resource
    private ProviderMessageHandlerContext handlerContext;
    @Resource
    private MessageDecoderContext decoderContext;
    @Resource
    private ProviderMessageExecutor messageExecutor;
    @Resource
    private TaskLoopGroup service;
    @Value("${server.port:8080}")
    private int httpPort;

    public ProviderGatewayServerStarter(ServerProperties properties) {
        this.properties = properties;
    }

    @PreDestroy
    public void destroy() {
        for (ProviderGatewayServer server : servers) {
            server.destroy();
        }

    }

    private final List<HandleMessage> handleMessages = new ArrayList<>();
    private List<IdName> idNames = new ArrayList<>();

    @Override
    public void run(ApplicationArguments args) {
        List<Integer> ids = handlerContext.registerMessageIds();

        logger.debug("ids {}", ids.size());


        for (Integer id : ids) {
            HandleMessage handleMessage = new HandleMessage();
            handleMessage.setHandleMessageId(id);

            ProviderMessageHandler<?> handler = handlerContext.handler(id);
            if (handler != null) {
                if (handler instanceof ProviderAskMessageHandler) {
                    //避免编码出错
                    handleMessage.setDirect(false);
                    if (handler.direct()) {
                        logger.warn("{}实现了ProducerAskMessageHandler但是direct()返回true 修正为false", handler.getClass().getName());
                    }
                } else {
                    handleMessage.setDirect(true);
                }

                Message message = handler.newEmptyMessage();
                handleMessage.setMessageName(message.getClass().getName());
                handleMessages.add(handleMessage);
                MessageIdReader.relation(id, message.getClass().getSimpleName());
            }
        }
        ServerProperties.ProviderProperties providerProperties = properties.getProvider();
        ServerProperties.ProviderProperties.GatewayProperties providerGateway = properties.getProvider().getGateway();

        if (StringUtils.isNoneEmpty(providerProperties.getIdNamesPackage())) {
            idNames = MessageScanner.scan(providerProperties.getIdNamesPackage());

        }
        ServerProperties.GatewayProperties gatewayProperties = new ServerProperties.GatewayProperties();
        List<IdName> finalIdNames = idNames;
        service.scheduleWithFixedDelay(() -> {
            try {
                boolean log = false;
                long now = System.currentTimeMillis();
                if (now - lastLogTime >= 60000) {
                    lastLogTime = now;
                    log = true;

                }
                List<ServiceInstance> serviceInstances = discoveryClient.getInstances(providerGateway.getName());
                if (log) {
                    logger.debug("{} 实例数量 {}", providerGateway.getName(), serviceInstances.size());
                    gatewayManager.report();
                }
                for (ServiceInstance instance : serviceInstances) {
                    boolean useDefault = false;
                    String portStr = instance.getMetadata().get(Constant.GATEWAY_METADATA_PROVIDER_PORT);

                    int port;
                    if (portStr == null) {
                        useDefault = true;
                        port = gatewayProperties.getProvider().getPort();
                    } else {
                        port = Integer.parseInt(portStr);
                    }
                    String gatewayKey = gatewayManager.getRemoteServerKey(instance.getHost(), port);
                    Gateway gateway = gatewayManager.getGateway(gatewayKey);
                    if (gateway == null) {
                        gateway = new Gateway(service);
                        if (providerGateway.getChannel() <= 1) {
                            gateway.setChannelService(new ChannelService.SingleChannelService(gatewayKey));
                        } else {
                            gateway.setChannelService(new ChannelService.MultipleChannelService(gatewayKey));
                        }
                        gateway.setFutureService(messageExecutor);
                        gateway.setRemoteServerKey(gatewayKey);

                        gateway.verifyWorkable();
                        gateway = gatewayManager.addGateway(gateway);
                    }

                    if (gateway.isConnecting()) {
                        continue;
                    }
                    if (gateway.getChannelSize() < providerGateway.getChannel()) {
                        logger.debug("{} channel 数量 {}/{} {}", gatewayKey, gateway.getChannelSize(), providerGateway.getChannel(), gateway);
                        Long lastFailTime = failGatewayMap.get(gatewayKey);
                        boolean start = false;
                        if (lastFailTime == null) {
                            start = true;
                        } else {
                            if (now - lastFailTime >= providerGateway.getConnectFailInterval()) {
                                start = true;
                            }
                        }
                        if (start) {
                            if (useDefault) {
                                logger.info("网关 [{}] {} {} 没有 没有配置provider socket端口,使用默认端口 {}", providerGateway.getName(), instance.getHost(), instance.getUri(), gatewayProperties.getProvider().getPort());
                            }
                            gateway.setConnecting(true);
                            ProviderGatewayServer providerGatewayServer = new ProviderGatewayServer();
                            providerGatewayServer.setGatewayManager(gatewayManager);
                            providerGatewayServer.setProperties(properties);
                            providerGatewayServer.setMessageExecutor(messageExecutor);
                            providerGatewayServer.setDecoderContext(decoderContext);

                            providerGatewayServer.setServerName(properties.getServerName());
                            providerGatewayServer.setHttpPort(httpPort);
                            providerGatewayServer.setReadableServerName(providerProperties.getReadableName());
                            if (providerGatewayServer.start(instance.getHost(), port)) {

                                Iterator<ProviderGatewayServer> iterator = servers.iterator();
                                while (iterator.hasNext()) {
                                    ProviderGatewayServer server = iterator.next();
                                    if (server.isClosed()) {
                                        iterator.remove();
                                        server.destroy();
                                    }
                                }
                                servers.add(providerGatewayServer);
                                if (gateway.getChannelSize() == 0) {
                                    gateway.setDefaultWaitSendTimeout(providerGateway.getMessageWaitSendTimeout());

                                }

                                Context context = new Context();
                                context.gateway = gateway;
                                context.server = providerGatewayServer;
                                frameworkVerifyProvider(context);

                                //认证
                                gateway.addChannel(providerGatewayServer.getChannel());
                                logger.debug("新增channel {} {}", providerGatewayServer.getChannel(), gateway.getChannelSize());

                            } else {
                                logger.warn("{}  socket {}:{} 连接失败", providerGateway.getName(), instance.getHost(), port);
                                failGatewayMap.put(gatewayKey, now);
                                gateway.setConnecting(false);
                            }

                        }
                    }
                }
            } catch (Exception e) {
                logger.error("", e);
            }

        }, 2000, 50, TimeUnit.MILLISECONDS);
    }

    private void frameworkVerifyProvider(Context context) {
        Gateway gateway = context.gateway;
        Channel channel = context.server.getChannel();

        if (properties.getProvider().isFrameworkVerifyProvider()) {
            if (gateway.isFrameworkVerifyProviderPassed()) {
                frameworkVerify(context);
            } else {
                CSFrameworkVerifyProviderMessage message = new CSFrameworkVerifyProviderMessage();
                message.setServerName(properties.getServerName());
                MessageFrame frame = gatewayManager.createMessageByToken(0L, message);

                gateway.sendMessage(channel, frame, response -> {
                    if (response.isSuccess()) {
                        gateway.setFrameworkVerifyProviderPassed(true);
                        logger.info("向网关表明自己可以提供认证功能成功");
                        frameworkVerify(context);
                    } else {
                        logger.error("向网关表明自己可以提供认证功能失败");
                        logger.error(response.getMessage().toString());
                        gateway.setConnecting(false);
                        channel.close();
                    }
                });
            }
        } else {

            frameworkVerify(context);
        }


    }


    private void frameworkVerify(Context context) {
        Gateway gateway = context.gateway;
        Channel channel = context.server.getChannel();
        if (gateway.isFrameworkVerifyPassed()) {
            registerProvider(context);
            return;
        }
        CSFrameworkVerifyMessage message = new CSFrameworkVerifyMessage();
        ServerProperties.Verify verify = properties.getVerify();
        message.setUserName(verify.getUserName());
        message.setUserType(verify.getUserType());
        message.setPassword(verify.getPassword());
        message.setToken(verify.getToken());

        MessageFrame frame = gatewayManager.createMessageByToken(0L, message);

        gateway.sendMessage(channel, frame, response -> {
            if (response.isSuccess()) {
                logger.info("框架内部认证成功");
                gateway.setFrameworkVerifyPassed(true);
                gateway.addChannel(channel);
                gateway.setConnecting(false);
                registerProvider(context);
            } else {
                logger.error("框架认证失败");
                logger.error(response.getMessage().toString());

                channel.close();
                gateway.setConnecting(false);
            }
        });


    }

    public void registerProvider(Context context) {
        Gateway gateway = context.gateway;

        ProviderGatewayServer server = context.server;
        SCRegServerHandleMessageMessage message = new SCRegServerHandleMessageMessage();
        message.setServerName(properties.getServerName());
        message.setReadableServerName(server.getReadableServerName());
        message.setServerKey(ChannelAttributeUtil.getLocalServerKey(server.getChannel()));
        message.setServerType(properties.getServerType());
        message.setServerOption(properties.getServerOption());
        message.setMessages(handleMessages);
        ProviderSendMessage frame = gatewayManager.createMessageByToken(0L, message);


        logger.debug("向{}注册服务", ChannelAttributeUtil.getRemoteServerKey(server.getChannel()));
        for (HandleMessage handleMessage : handleMessages) {
            logger.debug(handleMessage.toString());
        }

        gateway.sendMessage(server.getChannel(), frame);

        registerIdNames(gateway, server.getChannel());

    }

    public void registerIdNames(Gateway gateway, Channel channel) {
        SCIdNameMessage message = new SCIdNameMessage();
        for (int i = 0; i < idNames.size(); i++) {
            if (i > 0 && i % 100 == 0) {
                registerIdNames(gateway, channel, message);
                message = new SCIdNameMessage();
            }
            message.getIdNames().add(idNames.get(i));
        }
        if (message.getIdNames().size() > 0) {
            registerIdNames(gateway, channel, message);
        }
    }

    private void registerIdNames(Gateway gateway, Channel channel, SCIdNameMessage message) {
        ProviderSendMessage frame = gatewayManager.createMessageByToken(0L, message);
        gateway.sendMessage(channel, frame);

    }

    private static class Context {
        private ProviderGatewayServer server;
        private Gateway gateway;
    }
}
