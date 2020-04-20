package com.senpure.base.configuration;

import com.senpure.base.AppEvn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;


public class CurrentSpringApplicationRunListener implements SpringApplicationRunListener, ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static boolean done = false;

    public CurrentSpringApplicationRunListener(SpringApplication springApplication, String[] args) {

    }

    @Override
    public void starting() {

    }


    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        //多个springContext 只需要执行一次
        //保证开发阶段的的有几个classpath 时rootPath正确性
        if (done) {
            return;
        }
        done = true;
        Class<?> clazz = AppEvn.getStartClass();
        if (clazz == null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            StackTraceElement stack = stacks[stacks.length - 1];
            try {
                clazz = Class.forName(stack.getClassName());
                AppEvn.markStartClass(clazz);

            } catch (Exception e) {
                logger.error("", e);
            }
        }
        AppEvn.markClassRootPath(clazz);
        AppEvn.installAnsiConsole(clazz);
        //logger.debug("{}={}", "spring.output.ansi.enabled", environment.getProperty("spring.output.ansi.enabled"));

    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext configurableApplicationContext) {
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext configurableApplicationContext) {
    }

    @Override
    public void started(ConfigurableApplicationContext configurableApplicationContext) {

    }

    @Override
    public void running(ConfigurableApplicationContext configurableApplicationContext) {

    }

    @Override
    public void failed(ConfigurableApplicationContext configurableApplicationContext, Throwable throwable) {

    }


    @Override
    public void run(ApplicationArguments args) throws Exception {


    }
}
