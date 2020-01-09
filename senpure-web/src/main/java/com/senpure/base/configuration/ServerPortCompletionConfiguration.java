package com.senpure.base.configuration;

import com.senpure.base.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
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
public class ServerPortCompletionConfiguration implements SpringApplicationRunListener {

    Logger logger = LoggerFactory.getLogger(getClass());

    public ServerPortCompletionConfiguration() {
    }

    public ServerPortCompletionConfiguration(SpringApplication springApplication, String[] args) {

        // this.springApplication = springApplication;
    }

    @Override
    public void starting() {

    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
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
            Integer port = environment.getProperty("server.port", Integer.class);
            // port == null || 统一用0配置随机
            if (port != null && port == 0) {
               // Integer tempPort = port;
                //  port = RandomUtil.random(1, 65536);
                String ports = environment.getProperty("server.ports");
                List<Integer> portList = new ArrayList<>();
                if (ports != null) {
                    String portsStr[] = ports.split(",");
                    for (String s : portsStr) {
                        try {
                            portList.add(Integer.valueOf(s));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
                port = getPort(portList);
                logger.info("{} 没有配置端口使用随机端口{}",  environment.getProperty("spring.application.name"), port);
                Map<String, Object> map = new HashMap<>();
                map.put("server.port", port);
                PropertySource propertySource = new MapPropertySource("serverPort", map);
                //其他框架可能会更改该值所以放在相对靠后的位置
                for (PropertySource<?> temp : environment.getPropertySources()) {
                    if (temp.containsProperty("server.port")) {
                        logger.info("addBefore {} {}{}", temp.getName(), propertySource.getName(), port);
                        environment.getPropertySources().addBefore(temp.getName(), propertySource);
                        break;
                    }
                }
            }

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
