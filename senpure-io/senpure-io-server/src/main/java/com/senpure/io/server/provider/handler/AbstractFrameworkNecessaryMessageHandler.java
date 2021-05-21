package com.senpure.io.server.provider.handler;


import com.senpure.io.protocol.Message;


public abstract class AbstractFrameworkNecessaryMessageHandler<T extends Message> extends AbstractProviderMessageHandler<T> {


    @Override
    public boolean registerToGateway() {
        return false;
    }
}
