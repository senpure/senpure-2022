package com.senpure.io.server.support;

import com.senpure.io.server.ServerProperties;
import com.senpure.io.server.gateway.GatewayAndConsumerServer;
import com.senpure.io.server.gateway.GatewayAndProviderServer;
import com.senpure.io.server.gateway.GatewayMessageExecutor;
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

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ServerProperties properties;
    private GatewayMessageExecutor messageExecutor;
    private GatewayAndConsumerServer gatewayAndConsumerServer;
    private GatewayAndProviderServer gatewayAndProviderServer;


    public void start(GatewayMessageExecutor  messageExecutor) {
        this.messageExecutor = messageExecutor;
        servers();
    }






    private void servers() {
        GatewayAndConsumerServer gatewayAndConsumerServer = new GatewayAndConsumerServer();
        gatewayAndConsumerServer.setMessageExecutor(messageExecutor);
        gatewayAndConsumerServer.setProperties(properties.getGateway());
        if (gatewayAndConsumerServer.start()) {
            this.gatewayAndConsumerServer = gatewayAndConsumerServer;
        }
        GatewayAndProviderServer gatewayAndProviderServer = new GatewayAndProviderServer();
        gatewayAndProviderServer.setMessageExecutor(messageExecutor);
        gatewayAndProviderServer.setProperties(properties.getGateway());
        if (gatewayAndProviderServer.start()) {
            this.gatewayAndProviderServer = gatewayAndProviderServer;
        }
    }

    @PreDestroy
    public void destroy() {
        if (gatewayAndConsumerServer != null) {
            gatewayAndConsumerServer.destroy();
        }
        if (gatewayAndProviderServer != null) {
            gatewayAndProviderServer.destroy();
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
