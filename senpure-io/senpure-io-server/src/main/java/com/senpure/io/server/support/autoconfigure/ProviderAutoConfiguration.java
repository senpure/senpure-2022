package com.senpure.io.server.support.autoconfigure;

import com.senpure.base.util.Assert;
import com.senpure.executor.DefaultTaskLoopGroup;
import com.senpure.executor.TaskLoopGroup;
import com.senpure.io.server.DefaultMessageDecoderContext;
import com.senpure.io.server.MessageDecoderContext;
import com.senpure.io.server.ServerProperties;
import com.senpure.io.server.protocol.message.CSRelationUserGatewayMessage;
import com.senpure.io.server.provider.DefaultProviderMessageHandlerContext;
import com.senpure.io.server.provider.MessageSender;
import com.senpure.io.server.provider.ProviderMessageExecutor;
import com.senpure.io.server.provider.ProviderMessageHandlerContext;
import com.senpure.io.server.provider.handler.CSBreakUserGatewayMessageHandler;
import com.senpure.io.server.provider.handler.ProviderMessageHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * ProducerAutoConfiguration
 *
 * @author senpure
 * @time 2019-03-01 11:46:50
 */

public class ProviderAutoConfiguration {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private TaskLoopGroup taskLoopGroup;
    private ServerProperties properties;

    private void check(ServerProperties properties) {
        if (StringUtils.isEmpty(properties.getName())) {
            properties.setName("providerServer");
        }
        ServerProperties.Provider provider = properties.getProvider();
        if (!provider.isSetReadableName()) {
            provider.setReadableName(properties.getName());
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
        this.properties = properties;
    }

    public ProviderAutoConfiguration(ServerProperties serverProperties) {
        check(serverProperties);
    }

    @Bean
    @ConditionalOnMissingBean(TaskLoopGroup.class)
    public TaskLoopGroup taskLoopGroup() {

        TaskLoopGroup service = new DefaultTaskLoopGroup(properties.getProvider().getExecutorThreadPoolSize(),
                new DefaultThreadFactory(properties.getName() + "-executor"));
        this.taskLoopGroup = service;
        return service;

    }

    @Bean
    @ConditionalOnMissingBean(MessageDecoderContext.class)
    public MessageDecoderContext messageDecoderContext() {

        return new DefaultMessageDecoderContext();
    }

    @Bean
    public ProviderMessageHandlerContext providerMessageHandlerContext() {
        return new DefaultProviderMessageHandlerContext();
    }

    @Bean
    public ProviderMessageExecutor providerMessageExecutor(TaskLoopGroup service, MessageSender messageSender, ProviderMessageHandlerContext handlerContext) {

        return new ProviderMessageExecutor(service, messageSender, handlerContext);
    }

    @Bean
    public ProviderHandlerChecker providerHandlerChecker() {
        return new ProviderHandlerChecker();
    }


    @PreDestroy
    public void destroy() {
        if (taskLoopGroup != null) {
            taskLoopGroup.shutdownGracefully();
        }
    }


    public static class ProviderHandlerChecker implements ApplicationRunner {
        @Resource
        private CSBreakUserGatewayMessageHandler csBreakUserGatewayMessageHandler;
        @Resource
        private ProviderMessageHandlerContext handlerContext;

        @Override
        public void run(ApplicationArguments args) {
            ProviderMessageHandler<?> handler;

            if (csBreakUserGatewayMessageHandler == null) {
                Assert.error("缺少[CSBreakUserGatewayMessage]处理器");
            }
            handler = handlerContext.handler(CSRelationUserGatewayMessage.MESSAGE_ID);
            if (handler == null) {
                Assert.error("缺少[CSRelationUserGatewayMessage]处理器");
            }
        }
    }
}
