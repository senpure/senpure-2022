package com.senpure.reload.autoconfigure;

import com.senpure.base.AppEvn;
import com.senpure.reload.ReloadEnvironment;
import com.senpure.reload.ReloadProperties;
import net.bytebuddy.agent.ByteBuddyAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springsource.loaded.agent.SpringLoadedAgent;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.URL;
import java.util.*;


/**
 * AttachAgentAutoConfiguration
 *
 * @author senpure
 * @time 2020-08-28 15:47:45
 */
@EnableConfigurationProperties(ReloadProperties.class)
public class ReloadAutoConfiguration {


    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public ReloadEnvironment reloadEnvironment() {
        ReloadEnvironment reloadEnvironment = new ReloadEnvironment();
        boolean closeByteCodeVerify = false;
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        List<String> inputs = runtimeMXBean.getInputArguments();
        for (String input : inputs) {
            if ("-Xverify:none".equals(input)) {
                closeByteCodeVerify = true;
                break;
            }
        }
        reloadEnvironment.setCloseByteCodeVerify(closeByteCodeVerify);
        reloadEnvironment.setInstrumentation(InstallInstrumentation());
        return reloadEnvironment;
    }

    @Bean
    public String springloaded(ReloadEnvironment environment, ReloadProperties properties) {

        logger.info("classloader {}", getClass().getClassLoader());
        URL url = SpringLoadedAgent.class.getResource("SpringLoadedAgent.class");
        logger.info("{}", url.toString());

        if (!environment.isCloseByteCodeVerify()) {
            logger.error("没有关闭java字节码验证");
            logger.error("开启方式增加jvm启动参数 -noverify");
            throw new RuntimeException("没有关闭java字节码验证");
        }
        if (environment.getInstrumentation() == null) {
            logger.error("获取Instrumentation失败");
            throw new RuntimeException("获取Instrumentation失败");
        }

        // redefineClass();
        // RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();

        Set<String> jars = new HashSet<>();
        Class<?> clazz = AppEvn.getStartClass();
        if (properties.isDefaultJars()) {
            if (AppEvn.classInJar(clazz)) {
                File file = new File(Objects.requireNonNull(AppEvn.getJarPath(clazz)));
                String mainJar = file.getName();
                jars.add(mainJar);
                int index = mainJar.indexOf("-");
                if (index > 0) {
                    String prefix = mainJar.substring(0, index);
                    List<String> libs = findClassLib();
                    List<String> finds = new ArrayList<>();
                    for (String lib : libs) {
                        if (lib.startsWith(prefix)) {
                            finds.add(lib);
                        }
                    }
                    if (finds.size() > 0) {
                        jars.addAll(finds);
                    }
                }
            }
        }

        jars.addAll(properties.getWatchJars());
        Iterator<String> iterator = jars.iterator();
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()) {
            String name = iterator.next();
            sb.append(name);
            sb.append(":");
            logger.info("监听jar:{}", name);
        }

        String watchJar = "watchJars=";
        if (sb.length() > 0) {
            watchJar += sb.substring(0, sb.length() - 1);
        }
        StringBuilder args = new StringBuilder();
        if (properties.isVerbose()) {
            args.append("verbose=true;");
        }
        if (properties.isLogging()) {
            args.append("logging=true;");
        }
        args.append(watchJar);
        System.setProperty("springloaded", args.toString());
        SpringLoadedAgent.agentmain(null, environment.getInstrumentation());
        return "springloaded_true";
    }


    private Instrumentation InstallInstrumentation() {

        return ByteBuddyAgent.install();
    }

    private List<String> findClassLib() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        String[] jars = runtimeMXBean.getClassPath().split(";");
        List<String> libs = new ArrayList<>();
        for (String jar : jars) {
            if (jar.endsWith(".jar")) {
                int index = jar.lastIndexOf(File.separator);
                if (index > 0) {
                    jar = jar.substring(index + 1);
                    libs.add(jar);
                }
            }

        }
        return libs;
    }


}
