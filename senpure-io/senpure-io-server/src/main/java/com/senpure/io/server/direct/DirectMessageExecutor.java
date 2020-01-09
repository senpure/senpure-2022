package com.senpure.io.server.direct;

import com.senpure.executor.TaskLoopGroup;
import com.senpure.io.server.ChannelAttributeUtil;
import com.senpure.io.server.Constant;
import com.senpure.io.server.protocol.message.SCInnerErrorMessage;
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
                SCInnerErrorMessage errorMessage = new SCInnerErrorMessage();
                errorMessage.setCode(Constant.ERROR_NOT_HANDLE_REQUEST);
                errorMessage.setMessage("服务器没有处理程序:" + frame.getMessageId());
                errorMessage.getArgs().add(String.valueOf(frame.getMessageId()));
                ClientManager.sendMessage(channel, errorMessage);
                return;
            }
            try {
                handler.execute(channel, frame.getMessage());
            } catch (Exception e) {
                logger.error("执行handler[" + handler.getClass().getName() + "]逻辑出错 ", e);
                SCInnerErrorMessage scInnerErrorMessage = new SCInnerErrorMessage();
                scInnerErrorMessage.setCode(Constant.ERROR_SERVER_ERROR);
                scInnerErrorMessage.setMessage("服务器执行错误:" + frame.getMessage().getClass().getSimpleName()
                        + "[" + frame.getMessageId() + "]:" +
                        e.getMessage());
                scInnerErrorMessage.getArgs().add(String.valueOf(frame.getMessageId()));
                ClientManager.sendMessage(channel, scInnerErrorMessage);
            }
        });


    }


}
