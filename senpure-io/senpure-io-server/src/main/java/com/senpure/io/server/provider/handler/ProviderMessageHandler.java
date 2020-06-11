package com.senpure.io.server.provider.handler;


import com.senpure.io.protocol.Message;
import com.senpure.io.server.handler.MessageHandler;
import io.netty.channel.Channel;


public interface ProviderMessageHandler<T extends Message> extends MessageHandler<T> {

    void execute(Channel channel, long token, long userId, T message) throws Exception;

    /**
     * 是否直接转发，false 网关会进行一次询问
     *
     * @return
     */
    boolean direct();


    /**
     * 内部请求不要注册到网关
     *
     * @return
     */
    boolean regToGateway();

}
