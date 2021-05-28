package com.senpure.io.server.support;

import com.senpure.executor.TaskLoopGroup;
import com.senpure.io.server.Constant;
import com.senpure.io.server.ServerProperties;
import com.senpure.io.server.consumer.*;
import com.senpure.io.server.remoting.ChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * GatewayServerStarter
 *
 * @author senpure
 * @time 2019-03-01 15:17:33
 */
public class ConsumerServerStarter implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private DiscoveryClient discoveryClient;
    @Resource
    private ServerProperties properties;
    @Resource
    private ProviderManager remoteServerManager;

    @Resource
    private ConsumerMessageExecutor messageExecutor;
    @Resource
    private ConsumerMessageDecoderContext decoderContext;
    @Resource
    private TaskLoopGroup taskLoopGroup;

    private final ServerProperties.GatewayProperties gateway = new ServerProperties.GatewayProperties();
    private final List<ConsumerServer> servers = new ArrayList<>();
    private long lastFailTime = 0;
    private long lastLogTime = 0;
    private long failTimes = 0;
    private String lastFailServerKey;


    @PreDestroy
    public void destroy() {
        for (ConsumerServer server : servers) {
            server.destroy();
        }
    }

    public void setProperties(ServerProperties properties) {
        this.properties = properties;
    }


    private boolean canLog() {
        long now = System.currentTimeMillis();
        if (now - lastLogTime >= 60000) {
            lastLogTime = now;
            return true;
        }
        return false;
    }


    @Override
    public void run(ApplicationArguments args) {
        if (properties.getConsumer().isAutoConnect()) {
            messageExecutor.getService().scheduleWithFixedDelay(() -> {
                try {
                    boolean canLog = canLog();
                    if (remoteServerManager.getDefaultRemoteServer() == null) {
                        int port;
                        String host;
                        if (properties.getConsumer().getModel() == ServerProperties.ConsumerProperties.MODEL.DIRECT) {
                            host = properties.getConsumer().getRemoteHost();
                            port = properties.getConsumer().getRemotePort();
                        } else {

                            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(properties.getConsumer().getRemoteName());
                            if (serviceInstances.size() == 0) {
                                if (canLog) {
                                    logger.warn("没有服务可用{}", properties.getConsumer().getRemoteName());
                                }
                                return;
                            }
                            ServiceInstance instance;
                            if (lastFailServerKey == null) {
                                instance = serviceInstances.get(0);
                            } else {
                                Random random = new Random();
                                instance = serviceInstances.get(random.nextInt(serviceInstances.size()));
                            }

                            String portStr = instance.getMetadata().get(Constant.GATEWAY_METADATA_CONSUMER_PORT);

                            if (portStr == null) {
                                port = gateway.getConsumer().getPort();
                            } else {
                                port = Integer.parseInt(portStr);
                            }
                            host = instance.getHost();
                        }

                        String serverKey = remoteServerManager.getRemoteServerKey(host, port);
                        Provider provider = new Provider(taskLoopGroup);
                        provider.setRemoteServerKey(serverKey);
                        provider.setFutureService(messageExecutor);
                        if (properties.getConsumer().getRemoteChannel() <= 1) {
                            provider.setChannelService(new ChannelService.SingleChannelService(serverKey));
                        } else {
                            provider.setChannelService(new ChannelService.MultipleChannelService(serverKey));
                        }
                        provider.setDefaultTimeout(properties.getConsumer().getRequestTimeout());
                        provider.setDefaultWaitSendTimeout(properties.getConsumer().getMessageWaitSendTimeout());

                        provider.verifyWorkable();
                        remoteServerManager.setDefaultRemoteServer(provider);

                    } else {


                       Provider provider = remoteServerManager.getDefaultRemoteServer();

                        if (provider.isConnecting()) {
                            return;
                        }
                        long now = System.currentTimeMillis();
                        ServerProperties.ConsumerProperties consumerProperties = properties.getConsumer();
                        if (provider.getChannelSize() < properties.getConsumer().getRemoteChannel()) {
                            boolean start = false;
                            if (lastFailTime == 0) {
                                start = true;
                            } else {
                                if (now - lastFailTime >= properties.getConsumer().getConnectFailInterval()) {
                                    start = true;
                                }
                            }
                            if (start) {
                                provider.setConnecting(true);
                                ConsumerServer consumerServer = new ConsumerServer();
                                consumerServer.setMessageExecutor(messageExecutor);
                                consumerServer.setRemoteServerManager(remoteServerManager);
                                consumerServer.setProperties(properties.getConsumer());
                                consumerServer.setDecoderContext(decoderContext);
                                if (consumerServer.start(consumerProperties.getRemoteHost(), consumerProperties.getRemotePort())) {

                                    Iterator<ConsumerServer> iterator = servers.iterator();
                                    while (iterator.hasNext()) {
                                        ConsumerServer server = iterator.next();
                                        logger.info("server.isClosed() {}",server.isClosed());
                                        if (server.isClosed()) {
                                            iterator.remove();
                                            server.destroy();
                                        }
                                    }
                                    servers.add(consumerServer);
                                    //验证
                                    provider.addChannel(consumerServer.getChannel());
                                    failTimes = 0;
                                } else {
                                    lastFailTime = now;
                                    lastFailServerKey = provider.getRemoteServerKey();
                                    failTimes++;
                                    if (failTimes >= 10 && provider.getChannelSize() == 0) {
                                        remoteServerManager.setDefaultRemoteServer(null);
                                        failTimes = 0;
                                    }
                                }
                                provider.setConnecting(false);
                            }

                        }

                    }
                } catch (Exception e) {

                    logger.error("error", e);
                }

            }, 500, 50, TimeUnit.MILLISECONDS);
        }
    }

    public  void verify()
    {

    }
}
