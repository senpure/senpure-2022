package com.senpure.reload.cases.aop;

import com.senpure.reload.cases.AbstractReloadTestSupport;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * AopTarget
 *
 * @author senpure
 * @time 2020-10-20 14:42:50
 */
@Service
public class AopTarget extends AbstractReloadTestSupport {
    @Override
    public void execute() {

        System.out.println("aop method88");
    }

    @PostConstruct
    public void init() {
        System.out.println("aop method init");
    }
}
