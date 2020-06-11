package com.senpure.io.server.support.annotation;

import com.senpure.io.server.support.condition.OnMissMessageHandlerCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * <b>该注解会与MessageHandler 实例交互,意味着会先实例化所有的
 * MessageHandler 请注意相关依赖顺序,建议单独使用一个类来配置，
 * 不要与其他配置混合使用</b>
 *
 * @author senpure
 * @time 2020-06-11 10:26:48
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnMissMessageHandlerCondition.class)
public @interface ConditionalOnMissingMessageHandler {
    @AliasFor("messageId")
    int value() default 0;

    @AliasFor("value")
    int messageId() default 0;
}


