package com.senpure.io.server.support.autoconfigure;

import com.senpure.io.server.consumer.handler.SCHeartMessageHandler;
import com.senpure.io.server.consumer.handler.SCHeartMessageHandlerImpl;
import com.senpure.io.server.consumer.handler.SCInnerErrorMessageHandler;
import com.senpure.io.server.consumer.handler.SCInnerErrorMessageHandlerImpl;
import com.senpure.io.server.protocol.message.SCHeartMessage;
import com.senpure.io.server.protocol.message.SCInnerErrorMessage;
import com.senpure.io.server.support.annotation.ConditionalOnMissingMessageHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * ConsumerMessageHandlerAutoConfiguration
 *
 * @author senpure
 * @time 2020-06-11 16:53:58
 */
public class ConsumerMessageHandlerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(SCInnerErrorMessageHandler.class)
    public SCInnerErrorMessageHandler scInnerErrorMessageHandler() {
        return new SCInnerErrorMessageHandlerImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SCHeartMessageHandler.class)
    public SCHeartMessageHandler scHeartMessageHandler() {
        return new SCHeartMessageHandlerImpl();
    }
}
