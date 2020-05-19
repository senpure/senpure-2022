package com.senpure.base.autoconfigure;

import com.senpure.base.util.DatabaseUtil;
import com.senpure.base.util.StringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * DatebaseAutoCreateLinister
 *
 * @author senpure
 * @time 2020-05-19 10:57:23
 */
public class DatabaseAutoCreateRunListener extends AbstractRootApplicationRunListener {


    public DatabaseAutoCreateRunListener(SpringApplication springApplication, String[] args) {
        super(springApplication, args);
    }

    @Override
    public void rootStarting() {

    }

    @Override
    public void rootEnvironmentPrepared(ConfigurableEnvironment environment) {

        String url=environment.getProperty("spring.datasource.url");
        String username = environment.getProperty("spring.datasource.username");
        String password = environment.getProperty("spring.datasource.password");
        if (StringUtil.isExist(url) && StringUtil.isExist(username) && StringUtil.isExist(password)) {

            DatabaseUtil.checkAndCreateDatabase(url,username,password);
        }
    }
}
