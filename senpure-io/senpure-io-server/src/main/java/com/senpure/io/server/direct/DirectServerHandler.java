package com.senpure.io.server.direct;


import com.senpure.io.server.ChannelAttributeUtil;
import com.senpure.io.server.protocol.message.CSBreakUserGatewayMessage;
import com.senpure.io.server.protocol.message.CSRelationUserGatewayMessage;
import com.senpure.io.server.provider.ProviderMessageExecutor;
import com.senpure.io.server.provider.ProviderReceiveMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;


public class DirectServerHandler extends SimpleChannelInboundHandler<ProviderReceiveMessage> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private  final ProviderMessageExecutor messageExecutor;

    private  final ClientManager clientManager;
    //  private SourceOffline sourceOffline;

  private  final static   AtomicLong ID = new AtomicLong(1);

    public DirectServerHandler(ProviderMessageExecutor messageExecutor, ClientManager clientManager) {
        this.messageExecutor = messageExecutor;
        this.clientManager = clientManager;
    }



    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        //解决强断的错误 远程主机强迫关闭了一个现有的连接
        ctx.flush();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        Long token = ID.getAndIncrement();
        ChannelAttributeUtil.setToken(channel, token);
        String key = String.valueOf(token);
        ChannelAttributeUtil.setRemoteServerKey(channel, key);
        clientManager.addChannel(key,channel);

        CSRelationUserGatewayMessage message = new CSRelationUserGatewayMessage();
        message.setToken(token);
        message.setRelationToken(token);
        ProviderReceiveMessage frame = new ProviderReceiveMessage();
        frame.setToken(token);
        frame.setMessageId(message.messageId());
        frame.setMessage(message);

        messageExecutor.execute(channel,frame);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        logger.debug("{}断开连接 userId:{} ", channel, ChannelAttributeUtil.getUserId(channel));
        ProviderReceiveMessage frame = new ProviderReceiveMessage();
        CSBreakUserGatewayMessage message = new CSBreakUserGatewayMessage();
        Long token=ChannelAttributeUtil.getToken(channel);
        message.setToken(token);
        message.setRelationToken(token);
        Long userId = ChannelAttributeUtil.getUserId(channel);
        if (userId != null) {
            message.setUserId(userId);
            frame.setUserId(userId);
        }
        frame.setMessageId(message.messageId());
        frame.setToken(token);
        frame.setMessage(message);
        messageExecutor.execute(channel,frame);
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            Channel channel = ctx.channel();
            logger.info("{}心跳失败  userId:{}", channel, ChannelAttributeUtil.getUserId(channel));
            ctx.close();
            return;
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ProviderReceiveMessage providerReceiveMessage) throws Exception {
        messageExecutor.execute(channelHandlerContext.channel(),providerReceiveMessage);
    }
}
