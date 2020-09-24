package com.senpure.reload.autoconfigure;

import com.senpure.reload.ReloadEnvironment;
import com.senpure.reload.ReloadProperties;
import com.senpure.reload.util.SpringLoadedAgentStarter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


/**
 * AttachAgentAutoConfiguration
 *
 * @author senpure
 * @time 2020-08-28 15:47:45
 */
@EnableConfigurationProperties(ReloadProperties.class)
public class ReloadAutoConfiguration {


    @Bean
    public ReloadEnvironment reloadEnvironment() {
        return SpringLoadedAgentStarter.defaultReloadEnvironment();
    }

    @Bean
    public String springloaded(ReloadEnvironment environment, ReloadProperties properties) {
        SpringLoadedAgentStarter.start(environment, properties);
        return "springloaded_true";
    }


}
