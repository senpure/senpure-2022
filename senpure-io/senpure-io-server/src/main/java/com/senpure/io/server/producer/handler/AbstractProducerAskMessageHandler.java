package com.senpure.io.server.producer.handler;


import com.senpure.io.protocol.Message;


public abstract class AbstractProducerAskMessageHandler<T extends Message> extends AbstractProducerMessageHandler<T> implements ProducerAskMessageHandler<T> {


    @Override
    public boolean direct() {
        return false;
    }



}
