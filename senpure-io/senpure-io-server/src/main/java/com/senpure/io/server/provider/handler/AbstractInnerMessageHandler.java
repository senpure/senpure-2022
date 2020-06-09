package com.senpure.io.server.provider.handler;


import com.senpure.io.protocol.Message;


public abstract class AbstractInnerMessageHandler<T extends Message> extends AbstractProviderMessageHandler<T> {

    @Override
    public boolean regToGateway() {
        return false;
    }
}
