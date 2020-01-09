package com.senpure.io.server.consumer;


import com.senpure.base.util.Assert;
import com.senpure.io.protocol.Message;
import com.senpure.io.server.consumer.handler.ConsumerMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


public class ConsumerMessageHandlerUtil {
    private static Logger logger = LoggerFactory.getLogger(ConsumerMessageHandlerUtil.class);
    private static Map<Integer, ConsumerMessageHandler> handlerMap = new HashMap<>();

    public static ConsumerMessageHandler getHandler(int messageId) {
        return handlerMap.get(messageId);
    }

    public static void regMessageHandler(ConsumerMessageHandler handler) {
       // Assert.isNull(handlerMap.get(handler.handlerId()), handler.handlerId() + " -> " + handler.getEmptyMessage().getClass().getName() + "  处理程序已经存在");
        ConsumerMessageHandler old = handlerMap.get(handler.handlerId());
        if (old != null) {
            Assert.error(handler.handlerId() + " -> " + handler.getEmptyMessage()
                    .getClass().getName() + "  处理程序已经存在"
                    + " 存在 " + old.getClass().getName() + " 注册 " + handler.getClass().getName());
        }
        handlerMap.put(handler.handlerId(), handler);
    }

    public static Message getEmptyMessage(int messageId) {
        ConsumerMessageHandler handler = handlerMap.get(messageId);
        if (handler == null) {
            return null;
        }
        return handler.getEmptyMessage();
    }
}
