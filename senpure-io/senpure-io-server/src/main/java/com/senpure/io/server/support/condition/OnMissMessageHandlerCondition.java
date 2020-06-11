package com.senpure.io.server.support.condition;

import com.senpure.io.server.handler.MessageHandler;
import com.senpure.io.server.support.annotation.ConditionalOnMissingMessageHandler;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

/**
 * OnMissHandlerCondition
 *
 * @author senpure
 * @time 2020-06-11 14:14:34
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class OnMissMessageHandlerCondition extends SpringBootCondition {
    @Override
    @SuppressWarnings("rawtypes")
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConditionMessage matchMessage = ConditionMessage.empty();
        MergedAnnotations annotations = metadata.getAnnotations();
        //Map<String, Object> map = metadata.getAnnotationAttributes(ConditionalOnProviderMissingHandler.class.getName());
       // AnnotationAttributes attributes = AnnotationAttributes.fromMap(map);
        MergedAnnotation<ConditionalOnMissingMessageHandler> annotation = annotations.get(ConditionalOnMissingMessageHandler.class);
        if (annotation.isPresent()) {
           // Integer messageId = attributes.getNumber("messageId");
            Integer messageId = annotation.getInt("messageId");
            ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
            if (beanFactory != null) {
                Map<String, MessageHandler> handlerMap = beanFactory.
                        getBeansOfType(MessageHandler.class,true,true);
                for (Map.Entry<String, MessageHandler> entry : handlerMap.entrySet()) {
                    MessageHandler handler = entry.getValue();
                    if (handler.handleMessageId() == messageId) {
                        return ConditionOutcome.noMatch(ConditionMessage.forCondition(ConditionalOnMissingMessageHandler.class)
                                .found("handler").items(messageId)
                        );
                    }
                }
            }
            matchMessage.andCondition(ConditionalOnMissingMessageHandler.class).didNotFind("handler")
                    .items(messageId);
        }
        return ConditionOutcome.match(matchMessage);
    }
}
