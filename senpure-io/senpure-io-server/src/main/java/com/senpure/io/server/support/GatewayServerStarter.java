package com.senpure.io.server.support;

import com.senpure.base.util.IDGenerator;
import com.senpure.executor.DefaultTaskLoopGroup;
import com.senpure.executor.TaskLoopGroup;
import com.senpure.io.server.ServerProperties;
import com.senpure.io.server.gateway.GatewayAndClientServer;
import com.senpure.io.server.gateway.GatewayAndServerServer;
import com.senpure.io.server.gateway.GatewayMessageExecutor;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;

/**
 * GatewayServerStarter
 *
 * @author senpure
 * @time 2019-03-01 15:17:33
 */
public class GatewayServerStarter{

    private Logger logger = LoggerFactory.getLogger(getClass());

    private ServerProperties properties;
    private GatewayMessageExecutor messageExecutor;
    private GatewayAndClientServer gatewayAndClientServer;
    private GatewayAndServerServer gatewayAndServerServer;


    public void start() {
        check();
        messageExecutor();
        servers();
    }

    private void check() {
        if (StringUtils.isEmpty(properties.getName())) {
            properties.setName("gateway");
        }

        ServerProperties.Gateway gateway = properties.getGateway();
        if (!gateway.isSetReadableName()) {
            gateway.setReadableName(properties.getName());
        }
        //io *2 logic *1 综合1.5
        double size = Runtime.getRuntime().availableProcessors() * 1.5;
        int ioSize = (int) (size * 0.6);
        ioSize = Math.max(ioSize, 1);
        int logicSize = (int) (size * 0.4);
        logicSize = Math.max(logicSize, 1);
        if (gateway.getExecutorThreadPoolSize() < 1) {
            gateway.setExecutorThreadPoolSize(logicSize);
        }
        if (gateway.getIoCsWorkThreadPoolSize() < 1) {
            int workSize = ioSize << 1;
            workSize = Math.max(workSize, 1);
            gateway.setIoCsWorkThreadPoolSize(workSize);
        }
        if (gateway.getIoScWorkThreadPoolSize() < 1) {
            int workSize = ioSize << 1;
            workSize = Math.max(workSize, 1);
            gateway.setIoScWorkThreadPoolSize(workSize);
        }
        logger.info(gateway.toString());
    }

    private void messageExecutor() {
        ServerProperties.Gateway gateway = properties.getGateway();
        TaskLoopGroup service =new DefaultTaskLoopGroup(gateway.getExecutorThreadPoolSize(),
                new DefaultThreadFactory(properties.getName() + "-executor"));
        messageExecutor = new GatewayMessageExecutor(service, new IDGenerator(gateway.getSnowflakeDataCenterId(), gateway.getSnowflakeWorkId()));
        messageExecutor.setCsLoginMessageId(gateway.getCsLoginMessageId());
        messageExecutor.setScLoginMessageId(gateway.getScLoginMessageId());
        messageExecutor.setGateway(properties.getGateway());
        messageExecutor.init();
    }



    private void servers() {
        GatewayAndClientServer gatewayAndClientServer = new GatewayAndClientServer();
        gatewayAndClientServer.setMessageExecutor(messageExecutor);
        gatewayAndClientServer.setProperties(properties.getGateway());
        if (gatewayAndClientServer.start()) {
            this.gatewayAndClientServer = gatewayAndClientServer;
        }
        GatewayAndServerServer gatewayAndServerServer = new GatewayAndServerServer();
        gatewayAndServerServer.setMessageExecutor(messageExecutor);
        gatewayAndServerServer.setProperties(properties.getGateway());
        if (gatewayAndServerServer.start()) {
            this.gatewayAndServerServer = gatewayAndServerServer;
        }
    }

    @PreDestroy
    public void destroy() {
        if (gatewayAndClientServer != null) {
            gatewayAndClientServer.destroy();
        }
        if (gatewayAndServerServer != null) {
            gatewayAndServerServer.destroy();
        }
        if (messageExecutor != null) {
            messageExecutor.shutdownService();
        }
    }

    public void setProperties(ServerProperties properties) {
        this.properties = properties;
    }

    public static void main(String[] args) {

    }


}
