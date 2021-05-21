package com.senpure.io.server.support.autoconfigure;

import com.senpure.io.server.consumer.handler.SCHeartMessageHandler;
import com.senpure.io.server.consumer.handler.SCHeartMessageHandlerImpl;
import com.senpure.io.server.consumer.handler.SCFrameworkMessageHandler;
import com.senpure.io.server.consumer.handler.SCFrameworkMessageHandlerImpl;
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
    @ConditionalOnMissingBean(SCFrameworkMessageHandler.class)
    public SCFrameworkMessageHandler scInnerErrorMessageHandler() {
        return new SCFrameworkMessageHandlerImpl();
    }

    @Bean
    @ConditionalOnMissingBean(SCHeartMessageHandler.class)
    public SCHeartMessageHandler scHeartMessageHandler() {
        return new SCHeartMessageHandlerImpl();
    }
}
