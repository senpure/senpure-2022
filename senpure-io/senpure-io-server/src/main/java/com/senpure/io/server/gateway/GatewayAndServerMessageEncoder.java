package com.senpure.io.server.gateway;

import com.senpure.io.protocol.CompressBean;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 将客户端发到网关的消息，重新编码，发送给具体的服务器
 */
public class GatewayAndServerMessageEncoder extends MessageToByteEncoder<Client2GatewayMessage> {
    protected Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    protected void encode(ChannelHandlerContext ctx, Client2GatewayMessage msg, ByteBuf out) throws Exception {
        int headLength = CompressBean.computeVar32Size(msg.getRequestId());
        headLength += CompressBean.computeVar32Size(msg.getMessageId());

        headLength += CompressBean.computeVar64Size(msg.getToken());
        headLength += CompressBean.computeVar64Size(msg.getUserId());

        int allSize = headLength + msg.getData().length;
        out.ensureWritable(CompressBean.computeVar32Size(allSize) + allSize);

        CompressBean.writeVar32(out, allSize);
        CompressBean.writeVar32(out, msg.getRequestId());
        CompressBean.writeVar32(out, msg.getMessageId());
        CompressBean.writeVar64(out, msg.getToken());
        CompressBean.writeVar64(out, msg.getUserId());
        out.writeBytes(msg.getData());
    }

}
