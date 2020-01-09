package com.senpure.io.server.support;

import com.senpure.executor.TaskLoopGroup;
import com.senpure.io.server.ServerProperties;
import com.senpure.io.server.consumer.ConsumerMessageExecutor;
import com.senpure.io.server.consumer.ConsumerServer;
import com.senpure.io.server.consumer.RemoteServerChannelManager;
import com.senpure.io.server.consumer.RemoteServerManager;
import com.senpure.io.server.event.EventHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.ArrayList;
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

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private DiscoveryClient discoveryClient;
    @Resource
    private ServerProperties properties;
    @Resource
    private RemoteServerManager remoteServerManager;

    @Resource
    private ConsumerMessageExecutor messageExecutor;

    @Resource
    private  TaskLoopGroup taskLoopGroup;

    private ServerProperties.Gateway gateway = new ServerProperties.Gateway();
    private List<ConsumerServer> servers = new ArrayList<>();
    private long lastFailTime = 0;
    private long lastLogTime = 0;
    private long failTimes = 0;
    private String lastFailServerKey;

    @PostConstruct
    public void init() {
        messageExecutor();
    }

    private void messageExecutor() {
        messageExecutor.setService(taskLoopGroup);
        EventHelper.setService(taskLoopGroup);
    }

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

    public void run(ApplicationArguments args) throws Exception {
        if (properties.getConsumer().isAutoConnect()) {
            messageExecutor.getService().scheduleWithFixedDelay(() -> {
                try {
                    boolean canLog = canLog();
                    if (remoteServerManager.getDefaultChannelManager() == null) {

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

                        String portStr = instance.getMetadata().get("csPort");
                        int port;
                        if (portStr == null) {
                            port = gateway.getCsPort();
                        } else {
                            port = Integer.parseInt(portStr);
                        }
                        String serverKey = remoteServerManager.getRemoteServerKey(instance.getHost(), port);
                        RemoteServerChannelManager remoteServerChannelManager = remoteServerManager.
                                getRemoteServerChannelManager(serverKey);
                        remoteServerChannelManager.setHost(instance.getHost());
                        remoteServerChannelManager.setPort(port);
                        remoteServerChannelManager.setDefaultMessageRetryTimeLimit(properties.getConsumer().getMessageRetryTimeLimit());
                        remoteServerManager.setDefaultChannelManager(remoteServerChannelManager);

                    } else {


                        RemoteServerChannelManager remoteServerChannelManager = remoteServerManager.getDefaultChannelManager();

                        if (remoteServerChannelManager.isConnecting()) {
                            return;
                        }
                        long now = System.currentTimeMillis();
                        if (remoteServerChannelManager.getChannelSize() < properties.getConsumer().getRemoteChannel()) {
                            boolean start = false;
                            if (lastFailTime == 0) {
                                start = true;
                            } else {
                                if (now - lastFailTime >= properties.getConsumer().getConnectFailInterval()) {
                                    start = true;
                                }
                            }
                            if (start) {
                                remoteServerChannelManager.setConnecting(true);
                                ConsumerServer consumerServer = new ConsumerServer();
                                consumerServer.setMessageExecutor(messageExecutor);
                                consumerServer.setRemoteServerManager(remoteServerManager);
                                consumerServer.setProperties(properties.getConsumer());
                                if (consumerServer.start(remoteServerChannelManager.getHost(), remoteServerChannelManager.getPort())) {
                                    servers.add(consumerServer);
                                    //验证
                                    remoteServerChannelManager.addChannel(consumerServer.getChannel());
                                    failTimes = 0;
                                } else {
                                    lastFailTime = now;
                                    lastFailServerKey = remoteServerChannelManager.getServerKey();
                                    failTimes++;
                                    if (failTimes >= 10 && remoteServerChannelManager.getChannelSize() == 0) {
                                        remoteServerManager.setDefaultChannelManager(null);
                                        failTimes = 0;
                                    }
                                }
                                remoteServerChannelManager.setConnecting(false);
                            }

                        }

                    }
                } catch (Exception e) {

                    logger.error("error", e);
                }

            }, 500, 50, TimeUnit.MILLISECONDS);
        }
    }
}
