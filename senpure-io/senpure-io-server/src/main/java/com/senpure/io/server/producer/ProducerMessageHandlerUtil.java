package com.senpure.io.server.producer;


import com.senpure.base.util.Assert;
import com.senpure.io.protocol.Message;
import com.senpure.io.server.producer.handler.ProducerMessageHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProducerMessageHandlerUtil {

    private static Map<Integer, ProducerMessageHandler> handlerMap = new HashMap<>();

    public static ProducerMessageHandler getHandler(int messageId) {
        return handlerMap.get(messageId);
    }

    private static List<Integer> regMessageIds = new ArrayList<>(128);

    public static void regMessageHandler(ProducerMessageHandler handler) {
        ProducerMessageHandler old = handlerMap.get(handler.handlerId());
        if (old != null) {
            Assert.error(handler.handlerId() + " -> " + handler.getEmptyMessage()
                    .getClass().getName() + "  处理程序已经存在"
                    + " 存在 " + old.getClass().getName() + " 注册 " + handler.getClass().getName());
        }
        // Assert.isNull(handlerMap.get(handler.handlerId()), handler.handlerId() + " -> " + handler.getEmptyMessage().getClass().getName() + "  处理程序已经存在");

        handlerMap.put(handler.handlerId(), handler);
        if (handler.regToGateway()) {
            if (handler.handlerId() < 10000) {
                Assert.error("10000 以下为保留id不允许使用 " + handler.handlerId() + " " + handler.getEmptyMessage().getClass().getName());
            }
            regMessageIds.add(handler.handlerId());
        }
    }

    public static Message getEmptyMessage(int messageId) {
        ProducerMessageHandler handler = handlerMap.get(messageId);
        if (handler == null) {
            return null;
        }
        return handler.getEmptyMessage();
    }

    public static List<Integer> getHandlerMessageIds() {
        return new ArrayList<>(regMessageIds);
    }
}
