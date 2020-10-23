package com.senpure.reload.cases.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Aop
 *
 * @author senpure
 * @time 2020-10-20 14:40:58
 */
@Component
@Aspect
public class Aop {
      @Pointcut("execution(public * com.senpure.reload.cases.aop.AopTarget.*(..)))")
    public void BrokerAspect(){

    }
      @Before("BrokerAspect()")
    public  void before()
    {
        System.out.println("before aop77");
    }
}
