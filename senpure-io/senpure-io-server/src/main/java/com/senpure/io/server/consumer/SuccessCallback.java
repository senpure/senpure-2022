package com.senpure.io.server.consumer;


import com.senpure.io.protocol.Message;
import com.senpure.io.server.consumer.handler.ConsumerMessageHandler;
import com.senpure.io.server.remoting.Response;
import com.senpure.io.server.remoting.ResponseCallback;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * SuccessCallback
 *
 * @author senpure
 * @time 2019-07-22 17:23:34
 */
public abstract class SuccessCallback<T extends Message> implements ResponseCallback {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static ConsumerMessageHandlerContext handlerContext;
    @Override
    public void execute(Response result) {
        if (result.isSuccess()) {
            success(result.getMessage());
        } else {
            error(result.getChannel(), result.getMessage());
        }
    }

    public abstract void success(T message);
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void error(Channel channel, Message message) {

        ConsumerMessageHandler handler = handlerContext.handler(message.messageId());
        try {
            handler.execute(channel, message);
        } catch (Exception e) {
            logger.error("执行handler[" + handler.getClass().getName() + "]逻辑出错 ", e);

        }
    }

    public static ConsumerMessageHandlerContext getHandlerContext() {
        return handlerContext;
    }

    public static void setHandlerContext(ConsumerMessageHandlerContext handlerContext) {
        SuccessCallback.handlerContext = handlerContext;
    }
}
