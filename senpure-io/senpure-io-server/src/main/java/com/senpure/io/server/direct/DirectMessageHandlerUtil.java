package com.senpure.io.server.direct;


import com.senpure.base.util.Assert;
import com.senpure.io.protocol.Message;

import java.util.HashMap;
import java.util.Map;


public class DirectMessageHandlerUtil {

    private static Map<Integer, DirectMessageHandler> handlerMap = new HashMap<>();

    public static  DirectMessageHandler getHandler(int messageId) {
        return handlerMap.get(messageId);
    }

    public static void regMessageHandler(DirectMessageHandler handler) {
        // Assert.isNull(handlerMap.get(handler.handlerId()), handler.handlerId() + " -> " + handler.getEmptyMessage().getClass().getName() + "  处理程序已经存在");
        DirectMessageHandler old = handlerMap.get(handler.handlerId());
        if (old != null) {
            Assert.error(handler.handlerId() + " -> " + handler.getEmptyMessage()
                    .getClass().getName() + "  处理程序已经存在"
                    + " 存在 " + old.getClass().getName() + " 注册 " + handler.getClass().getName());
        }
        handlerMap.put(handler.handlerId(), handler);
    }

    public static Message getEmptyMessage(int messageId) {
        DirectMessageHandler handler = handlerMap.get(messageId);
        if (handler == null) {
            return null;
        }
        return handler.getEmptyMessage();
    }
}
