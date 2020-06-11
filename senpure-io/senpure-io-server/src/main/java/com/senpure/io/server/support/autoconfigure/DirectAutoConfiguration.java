package com.senpure.io.server.support.autoconfigure;


import com.senpure.io.server.direct.DirectMessageExecutor;
import com.senpure.io.server.support.DirectServerStarter;
import org.springframework.context.annotation.Bean;

/**
 * DirectAutoConfiguration
 *
 * @author senpure
 * @time 2019-09-18 10:02:40
 */
public class DirectAutoConfiguration {


    @Bean
    public DirectMessageExecutor directMessageExecutor() {
        return new DirectMessageExecutor();
    }

    @Bean
    public DirectServerStarter directServerStarter() {
        return new DirectServerStarter();
    }




}
