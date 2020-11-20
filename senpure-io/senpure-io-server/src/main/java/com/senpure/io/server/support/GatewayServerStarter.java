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


    public void start(GatewayMessageExecutor  messageExecutor) {
        this.messageExecutor = messageExecutor;
        servers();
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
