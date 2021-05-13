package com.senpure.io.server.support;

import com.senpure.io.server.ServerProperties;
import com.senpure.io.server.gateway.consumer.ConsumerServer;
import com.senpure.io.server.gateway.provider.ProviderServer;
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
    private ConsumerServer consumerServer;
    private ProviderServer providerServer;


    public void start(GatewayMessageExecutor  messageExecutor) {
        this.messageExecutor = messageExecutor;
        servers();
    }






    private void servers() {
        ConsumerServer consumerServer = new ConsumerServer();
        consumerServer.setMessageExecutor(messageExecutor);
        consumerServer.setProperties(properties.getGateway());
        if (consumerServer.start()) {
            this.consumerServer = consumerServer;
        }
        ProviderServer providerServer = new ProviderServer();
        providerServer.setMessageExecutor(messageExecutor);
        providerServer.setProperties(properties.getGateway());
        if (providerServer.start()) {
            this.providerServer = providerServer;
        }
    }

    @PreDestroy
    public void destroy() {
        if (consumerServer != null) {
            consumerServer.destroy();
        }
        if (providerServer != null) {
            providerServer.destroy();
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
