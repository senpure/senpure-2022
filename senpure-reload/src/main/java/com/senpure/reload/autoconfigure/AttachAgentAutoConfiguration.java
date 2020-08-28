package com.senpure.reload.autoconfigure;

import com.senpure.base.AppEvn;
import com.senpure.reload.AttachAgentEnvironment;
import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springsource.loaded.agent.SpringLoadedAgent;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * AttachAgentAutoConfiguration
 *
 * @author senpure
 * @time 2020-08-28 15:47:45
 */
public class AttachAgentAutoConfiguration {


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public AttachAgentEnvironment attachAgentEnvironment() {

        AttachAgentEnvironment attachAgentEnvironment = new AttachAgentEnvironment();

        try {
            Class.forName("com.sun.tools.attach.VirtualMachine");
            attachAgentEnvironment.setHasVirtualMachine(true);
        } catch (ClassNotFoundException e) {
            attachAgentEnvironment.setHasVirtualMachine(false);
        }
        return attachAgentEnvironment;
    }

    @Bean
    public String attachSpringloadedAgent(AttachAgentEnvironment environment) {

        if (!environment.isHasVirtualMachine()) {
            logger.error("缺少tools.jar,请确认java环境保护tools.jar 或者打包tools.jar到应用");
            logger.error("tools.jar路径一般在 jdk安装路径/lib/tools.jar");
            throw new RuntimeException("运行环境没有tools.jar");
        }
        if (!environment.isCloseByteCodeVerify()) {
            logger.error("没有关闭java字节码验证");
            logger.error("开启方式增加jvm启动参数 -noverify");
            throw new RuntimeException("没有关闭java字节码验证");
        }
       // RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        String path = AppEvn.getJarPath(SpringLoadedAgent.class);
        logger.info("代理jar路径:" + path);
        String watchJar="";
        if (AppEvn.classInJar(AppEvn.getStartClass())) {
            File file = new File(Objects.requireNonNull(AppEvn.getJarPath(AppEvn.getStartClass())));
            watchJar += file.getName();
        }
        System.setProperty("springloaded", "logging;" + watchJar);
        VirtualMachine virtualMachine;
        String pid = AppEvn.getPid();
        try {

            virtualMachine = VirtualMachine.attach(pid);
            virtualMachine.loadAgent(path);
        } catch (AttachNotSupportedException | IOException | AgentLoadException | AgentInitializationException e) {
            logger.error("attach到进程[" + pid + "]出错", e);
        }


        return "springloadedAgent_true";
    }
}
