package com.senpure.io.server.provider.handler;


import com.senpure.io.protocol.Message;
import io.netty.channel.Channel;


public abstract class AbstractFrameworkNecessaryMessageHandler<T extends Message> extends AbstractProviderMessageHandler<T> {


    @Override
    public void execute(Channel channel, long token, long userId, T message) throws Exception {

        execute(channel, message);
    }

    public abstract void execute(Channel channel, T message);

    public  void responseMessage()
    {

    }

    @Override
    public boolean registerToGateway() {
        return false;
    }
}
