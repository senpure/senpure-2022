package com.senpure.base.autoconfigure;

import com.senpure.base.annotation.ExtPermission;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMethod;


public class DruidPermissionAutoConfiguration {

    @Bean
    public DruidPermission druidPermission() {
        return new DruidPermission();
    }

    @ExtPermission
    static class DruidPermission {

        @ExtPermission(value = {"/druid", "/druid/**.*", "/druid/**/*.*"}, name = "/druid_read")
        public void readDruid() {
        }

        @ExtPermission(value = {"/druid/**.*"}, name = "/druid_read", method = RequestMethod.POST)
        public void readDruidJson() {
        }


    }
}
