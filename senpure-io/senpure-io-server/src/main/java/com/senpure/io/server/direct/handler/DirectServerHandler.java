package com.senpure.io.server.direct.handler;


import com.senpure.io.server.ChannelAttributeUtil;
import com.senpure.io.server.direct.ClientManager;
import com.senpure.io.server.direct.DirectMessage;
import com.senpure.io.server.direct.DirectMessageExecutor;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;


public class DirectServerHandler extends SimpleChannelInboundHandler<DirectMessage> {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private DirectMessageExecutor messageExecutor;

    //  private SourceOffline sourceOffline;

    AtomicLong id = new AtomicLong(1);

    public DirectServerHandler(DirectMessageExecutor messageExecutor) {
        this.messageExecutor = messageExecutor;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DirectMessage msg) throws Exception {
        //  messageExecutor.execute(msg);
        messageExecutor.execute(ctx.channel(), msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //解决强断的错误 远程主机强迫关闭了一个现有的连接
        ctx.flush();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Long token = id.getAndIncrement();
        ChannelAttributeUtil.setToken(ctx.channel(), token);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        logger.debug("{}断开连接 userId:{} ", channel, ChannelAttributeUtil.getUserId(channel));
        ClientManager.channelOffline(channel);
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
}
