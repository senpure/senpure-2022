package com.senpure.io.server.support.autoconfigure;

import com.senpure.base.util.Assert;
import com.senpure.io.server.DefaultMessageDecoderContext;
import com.senpure.io.server.MessageDecoderContext;
import com.senpure.io.server.ServerProperties;
import com.senpure.io.server.protocol.message.CSRelationUserGatewayMessage;
import com.senpure.io.server.provider.DefaultProviderMessageHandlerContext;
import com.senpure.io.server.provider.ProviderMessageHandlerContext;
import com.senpure.io.server.provider.handler.CSBreakUserGatewayMessageHandler;
import com.senpure.io.server.provider.handler.ProviderMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * ProducerAutoConfiguration
 *
 * @author senpure
 * @time 2019-03-01 11:46:50
 */


public class ProviderAutoConfiguration {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    public ProviderAutoConfiguration(Environment env, ServerProperties serverProperties) {
        check(env, serverProperties);
    }

    private void check(Environment env, ServerProperties properties) {
        if (!StringUtils.hasText(properties.getName())) {
            String name = env.getProperty("spring.application.name");
            if (StringUtils.hasText(name)) {
                logger.debug("使用 name {}", name);
                properties.setName(name);
            } else {
                logger.warn("spring.application.name 值为空");
            }
        }
        if (!StringUtils.hasText(properties.getName())) {
            properties.setName("provider");
        }
        ServerProperties.ProviderProperties provider = properties.getProvider();

        if (!StringUtils.hasText(provider.getReadableName()) || provider.getReadableName().equals(new ServerProperties.ProviderProperties().getReadableName())) {
            provider.setReadableName(properties.getName());
        }

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
    public ProviderHandlerChecker providerHandlerChecker() {
        return new ProviderHandlerChecker();
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
