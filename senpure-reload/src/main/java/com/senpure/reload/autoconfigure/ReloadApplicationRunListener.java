package com.senpure.reload.autoconfigure;

import com.senpure.base.autoconfigure.AbstractRootApplicationRunListener;
import com.senpure.reload.ReloadEnvironment;
import com.senpure.reload.ReloadProperties;
import com.senpure.reload.util.SpringLoadedAgentStarter;
import org.springframework.boot.SpringApplication;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * ReloadLis
 *
 * @author senpure
 * @time 2020-10-16 11:36:49
 */
@Order(2)
public class ReloadApplicationRunListener extends AbstractRootApplicationRunListener {

    public ReloadApplicationRunListener(SpringApplication springApplication, String[] args) {
        super(springApplication, args);
    }

    @Override
    public void rootStarting() {

    }

    @Override
    public void rootEnvironmentPrepared(ConfigurableEnvironment environment) {
        String prefix = "senpure.reload.springloaded.";
        Boolean enable = environment.getProperty(prefix + "enable", Boolean.class, true);
        if (enable) {

            ReloadEnvironment reloadEnvironment = SpringLoadedAgentStarter.defaultReloadEnvironment();

            ReloadProperties properties = SpringLoadedAgentStarter.defaultReloadProperties();
            Boolean defaultJars = environment.getProperty(prefix + "defaultJars", Boolean.class);
            if (defaultJars != null) {
                properties.setDefaultJars(defaultJars);
            }

            Boolean value = environment.getProperty(prefix + "logging", Boolean.class);
            if (value != null) {
                properties.setLogging(value);

            }
            value = environment.getProperty(prefix + "verbose", Boolean.class);
            if (value != null) {
                properties.setVerbose(value);

            }
            List<?> list = environment.getProperty(prefix + "watch-jars", List.class);
            if (list != null) {
                List<String> watchJars = new ArrayList<>();
                for (Object o : list) {
                    watchJars.add(o.toString());
                }
                properties.setWatchJars(watchJars);

            }
            SpringLoadedAgentStarter.start(reloadEnvironment, properties);
        }
    }
}
