package com.senpure.io.server.gateway.provider.handler;


import com.senpure.base.util.Assert;
import com.senpure.io.protocol.Message;
import com.senpure.io.server.gateway.GatewayMessageExecutor;
import com.senpure.io.server.gateway.GatewayReceiveProviderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;

public abstract class AbstractGatewayProviderMessageHandler implements GatewayProviderMessageHandler, InitializingBean {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    protected GatewayMessageExecutor messageExecutor;

    protected void readMessage(Message message, GatewayReceiveProviderMessage frame) {
        if (message.messageId() != messageId()) {

            Assert.error("消息id不匹配" + message.messageId() + "  " + messageId());
        }
        messageExecutor.readMessage(message, frame);

    }

    @Override
    public void afterPropertiesSet() {
        messageExecutor.registerProviderMessageHandler(this);

    }
}
