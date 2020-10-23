package com.senpure.reload.cases.aop;

import com.senpure.reload.cases.AbstractReloadStarter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * AopTargetReloadStarter
 *
 * @author senpure
 * @time 2020-10-20 14:44:04
 */
@SpringBootApplication
public class AopTargetReloadStarter  extends AbstractReloadStarter {
    public static void main(String[] args) {
        start(true,true,true);
       // SpringApplication.run(AopTargetReloadStarter.class, args);
//        Spring.getBean(ReloadCase.class).execute();
//
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
