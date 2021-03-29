package com.senpure.io.server.provider;


import com.senpure.io.server.ChannelAttributeUtil;
import com.senpure.io.server.protocol.message.SCHeartMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class ProviderServerHandler extends SimpleChannelInboundHandler<ProviderReceiveMessage> {


    private final ProviderMessageExecutor messageExecutor;
    private final GatewayManager gatewayManager;


    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ProviderServerHandler(ProviderMessageExecutor messageExecutor, GatewayManager gatewayManager) {
        this.messageExecutor = messageExecutor;
        this.gatewayManager = gatewayManager;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProviderReceiveMessage msg) {
        messageExecutor.execute(ctx.channel(), msg);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        logger.info("{} :{} 网关连接断开 ", ChannelAttributeUtil.getRemoteServerKey(ctx.channel()), ctx.channel());
        GatewayChannelManager gatewayChannelManager = gatewayManager.getGatewayChannelManager(ChannelAttributeUtil.getRemoteServerKey(ctx.channel()));
        if (gatewayChannelManager != null) {
            gatewayChannelManager.removeChannel(ctx.channel());
        } else {
            logger.error("{} :{} 网关连接断开 error ", ChannelAttributeUtil.getRemoteServerKey(ctx.channel()), ctx.channel());
        }
        // Objects.requireNonNull(gatewayManager.getGatewayChannelServer(ChannelAttributeUtil.getRemoteServerKey(ctx.channel()))).removeChannel(ctx.channel());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        //解决强断的错误 远程主机强迫关闭了一个现有的连接
        ctx.flush();

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            Channel channel = ctx.channel();
            if (channel.isWritable()) {
                logger.info("维持网关心跳{} : {}", ChannelAttributeUtil.getRemoteServerKey(channel), channel);
                SCHeartMessage heartMessage = new SCHeartMessage();
                ProviderSendMessage frame=  gatewayManager.createMessageByToken(0L, heartMessage);
                channel.writeAndFlush(frame);
            } else {
                logger.warn("网关心跳失败并且channel不可用{}:{}", ChannelAttributeUtil.getRemoteServerKey(channel), channel);
                channel.close();
            }

            return;
        }
        super.userEventTriggered(ctx, evt);
    }
}
