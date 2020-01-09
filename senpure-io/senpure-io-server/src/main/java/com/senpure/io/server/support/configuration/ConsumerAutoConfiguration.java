package com.senpure.io.server.support.configuration;

import com.senpure.base.util.Assert;
import com.senpure.executor.DefaultTaskLoopGroup;
import com.senpure.executor.TaskLoopGroup;
import com.senpure.io.server.ServerProperties;
import com.senpure.io.server.consumer.ConsumerMessageExecutor;
import com.senpure.io.server.consumer.ConsumerMessageHandlerUtil;
import com.senpure.io.server.consumer.RemoteServerManager;
import com.senpure.io.server.consumer.handler.ConsumerMessageHandler;
import com.senpure.io.server.protocol.message.SCHeartMessage;
import com.senpure.io.server.protocol.message.SCInnerErrorMessage;
import com.senpure.io.server.support.ConsumerServerStarter;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * ConsumerAutoConfiguration
 *
 * @author senpure
 * @time 2019-03-01 11:46:50
 */


public class ConsumerAutoConfiguration {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Resource
    private ServerProperties serverProperties;

    private TaskLoopGroup taskLoopGroup;
    @PostConstruct
    public void init() {
        check();
    }


    private void check() {
        if (StringUtils.isEmpty(serverProperties.getName())) {
            serverProperties.setName("consumerServer");
        }
        ServerProperties.Consumer consumer = serverProperties.getConsumer();
        if (!consumer.isSetReadableName()) {
            consumer.setReadableName(serverProperties.getName());
        }
        //io *2 logic *1 综合1.5
        double size = Runtime.getRuntime().availableProcessors() * 1.5;
        int ioSize = (int) (size * 0.6);
        ioSize = Math.max(ioSize, 1);
        int logicSize = (int) (size * 0.4);
        logicSize = Math.max(logicSize, 1);
        if (consumer.getIoWorkThreadPoolSize() < 1) {
            consumer.setIoWorkThreadPoolSize(ioSize);
        }
        if (consumer.getExecutorThreadPoolSize() < 1) {
            consumer.setExecutorThreadPoolSize(logicSize);
        }
    }

    @Bean
    @ConditionalOnMissingBean(TaskLoopGroup.class)
    public TaskLoopGroup taskLoopGroup() {
        TaskLoopGroup service = new DefaultTaskLoopGroup(serverProperties.getConsumer().getExecutorThreadPoolSize(),
                new DefaultThreadFactory(serverProperties.getName() + "-executor"));
        this.taskLoopGroup = service;
        return service;

    }
    @PreDestroy
    public void destroy() {
        if (taskLoopGroup != null) {
            taskLoopGroup.shutdownGracefully();
        }
    }

    @Bean
    public RemoteServerManager remoteServerManager() {
        return new RemoteServerManager(serverProperties.getConsumer());
    }

    @Bean
    public ConsumerMessageExecutor messageExecutor() {

        return new ConsumerMessageExecutor(serverProperties.getConsumer());
    }

    @Bean
    public ConsumerServerStarter consumerServerStarter() {
        return new ConsumerServerStarter();
    }

    @Bean
    public ConsumerHandlerChecker consumerHandlerChecker() {
        return new ConsumerHandlerChecker();
    }


    public void setServerProperties(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    static class ConsumerHandlerChecker implements ApplicationRunner {
        @Override
        public void run(ApplicationArguments args) {
            ConsumerMessageHandler<?> handler = ConsumerMessageHandlerUtil.getHandler(SCInnerErrorMessage.MESSAGE_ID);
            if (handler == null) {
                Assert.error("缺少[SCInnerErrorMessage]处理器");
            }
            handler = ConsumerMessageHandlerUtil.getHandler(SCHeartMessage.MESSAGE_ID);
            if (handler == null) {
                Assert.error("缺少[SCHeartMessage]处理器");
            }
        }
    }
}
