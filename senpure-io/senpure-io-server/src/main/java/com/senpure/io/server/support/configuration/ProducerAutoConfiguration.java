package com.senpure.io.server.support.configuration;

import com.senpure.base.util.Assert;
import com.senpure.executor.DefaultTaskLoopGroup;
import com.senpure.executor.TaskLoopGroup;
import com.senpure.io.server.ServerProperties;
import com.senpure.io.server.producer.GatewayManager;
import com.senpure.io.server.producer.ProducerMessageExecutor;
import com.senpure.io.server.producer.ProducerMessageHandlerUtil;
import com.senpure.io.server.producer.handler.CSAskHandleMessageHandler;
import com.senpure.io.server.producer.handler.CSRegServerHandleMessageMessageHandler;
import com.senpure.io.server.producer.handler.CSRelationUserGatewayMessageHandler;
import com.senpure.io.server.producer.handler.ProducerMessageHandler;
import com.senpure.io.server.protocol.message.CSBreakUserGatewayMessage;
import com.senpure.io.server.support.ProducerServerStarter;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * ProducerAutoConfiguration
 *
 * @author senpure
 * @time 2019-03-01 11:46:50
 */

public class ProducerAutoConfiguration {

    @Resource
    private ServerProperties serverProperties;

    private TaskLoopGroup taskLoopGroup;

    @PostConstruct
    public void init() {
        check();
    }

    private void check() {
        if (StringUtils.isEmpty(serverProperties.getName())) {
            serverProperties.setName("producerServer");
        }
        ServerProperties.Producer producer = serverProperties.getProducer();
        if (!producer.isSetReadableName()) {
            producer.setReadableName(serverProperties.getName());
        }
        //io *2 logic *1 综合1.5
        double size = Runtime.getRuntime().availableProcessors() * 1.5;
        int ioSize = (int) (size * 0.6);
        ioSize = Math.max(ioSize, 1);
        int logicSize = (int) (size * 0.4);
        logicSize = Math.max(logicSize, 1);
        if (producer.getIoWorkThreadPoolSize() < 1) {
            producer.setIoWorkThreadPoolSize(ioSize);
        }
        if (producer.getExecutorThreadPoolSize() < 1) {
            producer.setExecutorThreadPoolSize(logicSize);
        }
    }

    @Bean
    @ConditionalOnMissingBean(TaskLoopGroup.class)
    public TaskLoopGroup taskLoopGroup() {
        TaskLoopGroup service = new DefaultTaskLoopGroup(serverProperties.getProducer().getExecutorThreadPoolSize(),
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
    public CSRelationUserGatewayMessageHandler csRelationUserGatewayMessageHandler() {
        return new CSRelationUserGatewayMessageHandler();
    }

    @Bean
    public CSRegServerHandleMessageMessageHandler csRegServerHandleMessageMessageHandler() {
        return new CSRegServerHandleMessageMessageHandler();
    }


    @Bean
    public CSAskHandleMessageHandler csAskHandleMessageHandler() {
        return new CSAskHandleMessageHandler();
    }


    @Bean
    public GatewayManager gatewayManager() {
        return new GatewayManager();
    }

    @Bean
    public ProducerMessageExecutor producerMessageExecutor() {

        return new ProducerMessageExecutor();
    }

    @Bean
    public ProducerServerStarter producerServerStarter() {
        return new ProducerServerStarter();
    }


    @Bean
    public ProducerHandlerChecker producerHandlerChecker() {
        return new ProducerHandlerChecker();
    }

    static class ProducerHandlerChecker implements ApplicationRunner {
        @Override
        public void run(ApplicationArguments args) {
            ProducerMessageHandler<?> handler = ProducerMessageHandlerUtil.getHandler(CSBreakUserGatewayMessage.MESSAGE_ID);
            if (handler == null) {
                Assert.error("缺少[CSBreakUserGatewayMessage]处理器");
            }
        }
    }

}