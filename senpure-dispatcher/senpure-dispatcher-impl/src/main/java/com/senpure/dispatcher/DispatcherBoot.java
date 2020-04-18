package com.senpure.dispatcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * SnowflakeBoot
 *
 * @author senpure
 * @time 2019-03-11 17:16:52
 */
@SpringBootApplication
@EnableDiscoveryClient
public class DispatcherBoot {
    public static void main(String[] args) {

        SpringApplication.run(DispatcherBoot.class, args);
    }
}
