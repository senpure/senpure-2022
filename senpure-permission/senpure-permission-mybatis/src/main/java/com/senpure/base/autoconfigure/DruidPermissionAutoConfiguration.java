package com.senpure.base.autoconfigure;

import com.alibaba.druid.pool.DruidDataSource;
import com.senpure.base.annotation.ExtPermission;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMethod;

@ConditionalOnClass(DruidDataSource.class)
@ConditionalOnProperty(value = "senpure.permission.druid.enable", matchIfMissing = true)
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
