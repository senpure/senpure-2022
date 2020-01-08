package com.senpure.io.server.direct;


import com.senpure.executor.TaskLoopGroup;
import com.senpure.io.server.ChannelAttributeUtil;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DirectMessageExecutor
 *
 * @author senpure
 * @time 2019-09-17 16:19:00
 */
public class DirectMessageExecutor {

    protected static Logger logger = LoggerFactory.getLogger(DirectMessageExecutor.class);
    private TaskLoopGroup service;


    public DirectMessageExecutor() {

    }

    public DirectMessageExecutor(TaskLoopGroup service) {
        this.service = service;
    }

    public void setService(TaskLoopGroup service) {
        this.service = service;
    }

    public void execute(Channel channel, DirectMessage frame) {
        service.get(ChannelAttributeUtil.getToken(channel)).execute(() -> {
            int requestId = frame.getRequestId();
            ClientManager.setRequestId(requestId);
            DirectMessageHandler handler = DirectMessageHandlerUtil.getHandler(frame.getMessageId());
            if (handler == null) {
                logger.warn("没有找到消息处理程序 {} ", frame.getMessageId());
                //todo 错误返回
                return;
            }
            try {
                handler.execute(channel, frame.getMessage());
            } catch (Exception e) {
                logger.error("执行handler[" + handler.getClass().getName() + "]逻辑出错 ", e);

                //todo 错误返回
            }
        });


    }


}
