package com.senpure.io.server.producer.handler;


import com.senpure.io.protocol.Message;


public abstract class AbstractInnerMessageHandler<T extends Message> extends AbstractProducerMessageHandler<T> {

    @Override
    public boolean regToGateway() {
        return false;
    }
}
