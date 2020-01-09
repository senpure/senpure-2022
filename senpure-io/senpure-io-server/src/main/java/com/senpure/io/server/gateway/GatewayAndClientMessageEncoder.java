package com.senpure.io.server.gateway;

import com.senpure.io.protocol.CompressBean;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 将服务器发到网关的消息，转发给客户端
 */
public class GatewayAndClientMessageEncoder extends MessageToByteEncoder<Server2GatewayMessage> {
    protected Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    protected void encode(ChannelHandlerContext ctx, Server2GatewayMessage msg, ByteBuf out) {
        int headlength = CompressBean.computeVar32Size(msg.getRequestId());
        headlength += CompressBean.computeVar32Size(msg.getMessageId());
        int packageLength = headlength + msg.getData().length;
        out.ensureWritable(CompressBean.computeVar32Size(packageLength) + packageLength);

        CompressBean.writeVar32(out, packageLength);
        CompressBean.writeVar32(out, msg.getRequestId());
        CompressBean.writeVar32(out, msg.getMessageId());
        out.writeBytes(msg.getData());
    }


}
