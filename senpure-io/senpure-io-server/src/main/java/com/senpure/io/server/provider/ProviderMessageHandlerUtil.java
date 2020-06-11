package com.senpure.io.server.provider;


import com.senpure.base.util.Assert;
import com.senpure.io.protocol.Message;
import com.senpure.io.server.provider.handler.ProviderMessageHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProviderMessageHandlerUtil {

    private final static Map<Integer, ProviderMessageHandler<?>> handlerMap = new HashMap<>();

    public  static ProviderMessageHandler<?> getHandler(int messageId) {
        return handlerMap.get(messageId);
    }

    private final static List<Integer> regMessageIds = new ArrayList<>(128);

    public static void regMessageHandler(ProviderMessageHandler<?> handler) {
        ProviderMessageHandler<?> old = handlerMap.get(handler.handleMessageId());
        if (old != null) {
            Assert.error(handler.handleMessageId() + " -> " + handler.getEmptyMessage()
                    .getClass().getName() + "  处理程序已经存在"
                    + " 存在 " + old.getClass().getName() + " 注册 " + handler.getClass().getName());
        }
        // Assert.isNull(handlerMap.get(handler.handlerId()), handler.handlerId() + " -> " + handler.getEmptyMessage().getClass().getName() + "  处理程序已经存在");

        handlerMap.put(handler.handleMessageId(), handler);
        if (handler.regToGateway()) {
            if (handler.handleMessageId() < 10000) {
                Assert.error("10000 以下为保留id不允许使用 " + handler.handleMessageId() + " " + handler.getEmptyMessage().getClass().getName());
            }
            regMessageIds.add(handler.handleMessageId());
        }
    }

    public static Message getEmptyMessage(int messageId) {
        ProviderMessageHandler<?> handler = handlerMap.get(messageId);
        if (handler == null) {
            return null;
        }
        return handler.getEmptyMessage();
    }

    public static List<Integer> getHandlerMessageIds() {
        return new ArrayList<>(regMessageIds);
    }
}
