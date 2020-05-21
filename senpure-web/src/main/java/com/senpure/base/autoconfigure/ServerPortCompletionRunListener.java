package com.senpure.base.autoconfigure;

import com.senpure.base.util.RandomUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PropertiesCompletion
 *
 * @author senpure
 * @time 2019-07-30 21:08:59
 */
public class ServerPortCompletionRunListener extends AbstractRootApplicationRunListener {

    public ServerPortCompletionRunListener(SpringApplication springApplication, String[] args) {
        super(springApplication, args);
    }

    @Override
    public void rootStarting() {

    }

    @Override
    public void rootEnvironmentPrepared(ConfigurableEnvironment environment) {
        Integer port = environment.getProperty("server.port", Integer.class);
        boolean add = false;
        if (port == null) {
            add = true;
        }
        if (add || port == 0) {
            String ports = environment.getProperty("server.ports");
            List<Integer> portList = new ArrayList<>();
            if (ports != null) {
                String[] portsStr = ports.split(",");
                for (String s : portsStr) {
                    try {
                        portList.add(Integer.valueOf(s));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (portList.size() > 0) {
                port = getPort(portList);
                logger.info("{} 没有配置端口使用随机端口{}", environment.getProperty("spring.application.name"), port);
                Map<String, Object> map = new HashMap<>();
                map.put("server.port", port);
                PropertySource<?> propertySource = new MapPropertySource("serverPort", map);
                if (add) {
                    environment.getPropertySources().addLast(propertySource);
                } else {
                    for (PropertySource<?> temp : environment.getPropertySources()) {
                        if (temp.containsProperty("server.port")) {
                            if (temp instanceof OriginTrackedMapPropertySource) {
                                logger.info("addBefore {} {} {}", temp.getName(), propertySource.getName(), port);
                                environment.getPropertySources().addBefore(temp.getName(), propertySource);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }


    public void environmentPreparedOld(ConfigurableEnvironment environment) {
        boolean current = false;
        for (PropertySource<?> propertySource : environment.getPropertySources()) {
            // logger.debug(propertySource.getClass().toString());
            if (propertySource.getName().startsWith("applicationConfig")) {
                current = true;
                break;
            }
        }
        if (current) {
            //统一用0表示随机值
            // String name = environment.getProperty("spring.application.name");
            // logger.debug("{}={}", "spring.application.name", name);

        }

    }

    private int getPort(List<Integer> ports) {
        for (Integer port : ports) {
            if (!isPortUsing("127.0.0.1", port)) {
                return port;
            }
        }
        return getPort();
    }


    private int getPort() {
        int port;
        do {
            //ServerSocket serverSocket =  new ServerSocket(0);
            //serverSocket.getLocalPort();
            port = RandomUtil.random(1, 65536);
        } while (isPortUsing("127.0.0.1", port));

        return port;

    }

    public static boolean isPortUsing(String host, int port) {
        boolean flag = false;

        try {
            InetAddress Address = InetAddress.getByName(host);
            new Socket(Address, port).close();  //建立一个Socket连接
            flag = true;
        } catch (Exception ignored) {
        }
        return flag;
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {

    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {

    }

    @Override
    public void started(ConfigurableApplicationContext context) {

    }

    @Override
    public void running(ConfigurableApplicationContext context) {

    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {

    }


}
