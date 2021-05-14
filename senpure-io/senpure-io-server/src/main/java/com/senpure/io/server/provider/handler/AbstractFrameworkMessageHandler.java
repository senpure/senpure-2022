package com.senpure.io.server.provider.handler;


import com.senpure.io.protocol.Message;
import com.senpure.io.server.provider.handler.AbstractProviderMessageHandler;


public abstract class AbstractFrameworkMessageHandler<T extends Message> extends AbstractProviderMessageHandler<T> {

   // private SCFrameworkErrorMessage
    /**
     * 是否向网关注册该handler
     * 内部请求不要注册到网关
     */
    @Override
    public boolean registerToGateway() {
        return false;
    }
}
