package com.senpure.io.server.provider.handler;


import com.senpure.io.protocol.Message;

public interface ProviderAskMessageHandler<T extends Message> extends ProviderMessageHandler<T> {


    boolean ask(String value);

}
