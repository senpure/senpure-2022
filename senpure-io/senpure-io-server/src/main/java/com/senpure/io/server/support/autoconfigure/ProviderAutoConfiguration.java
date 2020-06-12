package com.senpure.io.server.support.autoconfigure;

import com.senpure.base.util.Assert;
import com.senpure.executor.DefaultTaskLoopGroup;
import com.senpure.executor.TaskLoopGroup;
import com.senpure.io.server.ServerProperties;
import com.senpure.io.server.protocol.message.CSBreakUserGatewayMessage;
import com.senpure.io.server.protocol.message.CSRelationUserGatewayMessage;
import com.senpure.io.server.provider.GatewayManager;
import com.senpure.io.server.provider.ProviderMessageExecutor;
import com.senpure.io.server.provider.ProviderMessageHandlerUtil;
import com.senpure.io.server.provider.handler.ProviderMessageHandler;
import com.senpure.io.server.support.ProducerServerStarter;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

import javax.annotation.PreDestroy;

/**
 * ProducerAutoConfiguration
 *
 * @author senpure
 * @time 2019-03-01 11:46:50
 */

public class ProviderAutoConfiguration {

    private TaskLoopGroup taskLoopGroup;

    private void check(ServerProperties serverProperties) {
        if (StringUtils.isEmpty(serverProperties.getName())) {
            serverProperties.setName("providerServer");
        }
        ServerProperties.Provider provider = serverProperties.getProvider();
        if (!provider.isSetReadableName()) {
            provider.setReadableName(serverProperties.getName());
        }
        //io *2 logic *1 综合1.5
        double size = Runtime.getRuntime().availableProcessors() * 1.5;
        int ioSize = (int) (size * 0.6);
        ioSize = Math.max(ioSize, 1);
        int logicSize = (int) (size * 0.4);
        logicSize = Math.max(logicSize, 1);
        if (provider.getIoWorkThreadPoolSize() < 1) {
            provider.setIoWorkThreadPoolSize(ioSize);
        }
        if (provider.getExecutorThreadPoolSize() < 1) {
            provider.setExecutorThreadPoolSize(logicSize);
        }
    }


    @Bean
    @ConditionalOnMissingBean(TaskLoopGroup.class)
    public TaskLoopGroup taskLoopGroup(ServerProperties serverProperties) {
        check(serverProperties);
        TaskLoopGroup service = new DefaultTaskLoopGroup(serverProperties.getProvider().getExecutorThreadPoolSize(),
                new DefaultThreadFactory(serverProperties.getName() + "-executor"));
        this.taskLoopGroup = service;
        return service;

    }


    @Bean
    public GatewayManager gatewayManager() {
        return new GatewayManager();
    }

    @Bean
    public ProviderMessageExecutor producerMessageExecutor() {

        return new ProviderMessageExecutor();
    }

    @Bean
    public ProducerServerStarter producerServerStarter() {
        return new ProducerServerStarter();
    }


    @PreDestroy
    public void destroy() {
        if (taskLoopGroup != null) {
            taskLoopGroup.shutdownGracefully();
        }
    }

    @Bean
    public ProviderHandlerChecker providerHandlerChecker() {
        return new ProviderHandlerChecker();
    }

    public static class ProviderHandlerChecker implements ApplicationRunner {
        @Override
        public void run(ApplicationArguments args) {
            ProviderMessageHandler<?> handler = ProviderMessageHandlerUtil.getHandler(CSBreakUserGatewayMessage.MESSAGE_ID);
            if (handler == null) {
                Assert.error("缺少[CSBreakUserGatewayMessage]处理器");
            }
            handler = ProviderMessageHandlerUtil.getHandler(CSRelationUserGatewayMessage.MESSAGE_ID);
            if (handler == null) {
                Assert.error("缺少[CSRelationUserGatewayMessage]处理器");
            }
        }
    }
}
