package com.senpure.base.autoconfigure;

import com.senpure.base.util.BannerShow;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;



public class DefaultBannerShowAutoConfiguration {

    @Value("${default.banner:true}")
    private boolean banner = true;

    @Bean
    @Order
    public DefaultBannerShow defaultBannerShow() {
        return new DefaultBannerShow();
    }

    class DefaultBannerShow implements ApplicationRunner {

        @Override
        public void run(ApplicationArguments applicationArguments) throws Exception {

            if (banner) {
                BannerShow.show();
                Thread.sleep(1500);
            }

        }
    }
}
