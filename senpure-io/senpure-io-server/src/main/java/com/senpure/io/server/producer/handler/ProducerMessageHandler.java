package com.senpure.io.server.producer.handler;


import com.senpure.io.protocol.Message;
import io.netty.channel.Channel;


public interface ProducerMessageHandler<T extends Message> {


    T getEmptyMessage();

    void execute(Channel channel, long token, long userId, T message) throws  Exception;

    int handlerId();

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
