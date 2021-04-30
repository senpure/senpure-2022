package com.senpure.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class PermissionBoot {

    public static void main(String[] args) {

        SpringApplication.run(PermissionBoot.class, args);
    }
}
