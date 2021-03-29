package com.senpure.io.server.support.autoconfigure;


import com.senpure.io.server.direct.ClientManager;
import com.senpure.io.server.support.DirectServerStarter;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@ConditionalOnProperty(prefix = "server.io", value = "provider.model", havingValue = "DIRECT")
@AutoConfigureAfter(ProviderAutoConfiguration.class)
public class ProviderDirectAutoConfiguration {


    @Bean
    public ClientManager clientManager() {
        return new ClientManager();
    }


    @Bean
    public DirectServerStarter directServerStarter() {
        return new DirectServerStarter();
    }
}
