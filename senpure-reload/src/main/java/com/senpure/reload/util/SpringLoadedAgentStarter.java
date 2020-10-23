package com.senpure.reload.util;

import com.senpure.base.AppEvn;
import com.senpure.reload.ReloadEnvironment;
import com.senpure.reload.ReloadProperties;
import net.bytebuddy.agent.ByteBuddyAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springsource.loaded.agent.SpringLoadedAgent;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.*;

/**
 * SpringLoadedAgentStarer
 *
 * @author senpure
 * @time 2020-09-24 17:27:48
 */
public class SpringLoadedAgentStarter {

    private static final Logger logger = LoggerFactory.getLogger(SpringLoadedAgentStarter.class);


    public static ReloadEnvironment defaultReloadEnvironment() {
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

    public static ReloadProperties defaultReloadProperties() {
        return new ReloadProperties();
    }

    public static Instrumentation InstallInstrumentation() {
        return ByteBuddyAgent.install();
    }

    public static void start() {
        start(defaultReloadEnvironment(), defaultReloadProperties());
    }

    public static void start(ReloadEnvironment environment, ReloadProperties properties) {
        if (!environment.isCloseByteCodeVerify()) {
            logger.error("没有关闭java字节码验证");
            logger.error("开启方式增加jvm启动参数 -noverify");
            throw new RuntimeException("没有关闭java字节码验证");
        }
        if (environment.getInstrumentation() == null) {
            logger.error("获取Instrumentation失败");
            throw new RuntimeException("获取Instrumentation失败");
        }

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
                        //最多找两次
                        index = mainJar.indexOf("-", index + 1);
                        if (index > 0) {
                            prefix = mainJar.substring(0, index);
                            List<String> finds2 = new ArrayList<>();
                            for (String find : finds) {
                                if (find.startsWith(prefix)) {
                                    finds2.add(find);
                                }
                            }
                            if (finds2.size() > 0) {
                                jars.addAll(finds2);
                            } else {
                                jars.addAll(finds);
                            }
                        } else {
                            jars.addAll(finds);
                        }

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
    }

    private static List<String> findClassLib() {
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
