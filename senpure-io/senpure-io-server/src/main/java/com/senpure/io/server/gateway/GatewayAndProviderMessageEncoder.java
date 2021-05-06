package com.senpure.io.server.gateway;

import com.senpure.io.protocol.CompressBean;
import com.senpure.io.protocol.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 将客户端发到网关的消息，重新编码，发送给具体的服务器
 */
public class GatewayAndProviderMessageEncoder extends MessageToByteEncoder<GatewaySendableMessage> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    protected void encode(ChannelHandlerContext ctx, GatewaySendableMessage frame, ByteBuf out) {
        int messageFrom = frame.getMessageFrom();
        if (messageFrom == GatewaySendableMessage.MESSAGE_FROM_CONSUMER) {
            encodeRemoteMessage(frame, out);
        } else if (messageFrom == GatewaySendableMessage.MESSAGE_FROM_GATEWAY) {
            encodeLocalMessage(frame, out);
        } else {
            encodeRemoteMessage(frame, out);
        }
    }

    private void encodeLocalMessage(GatewaySendableMessage frame, ByteBuf out) {
        int headLength = CompressBean.computeVar32Size(frame.messageType());
        headLength += CompressBean.computeVar32Size(frame.requestId());
        headLength += CompressBean.computeVar64Size(frame.getToken());
        headLength += CompressBean.computeVar64Size(frame.getUserId());
        Message message = frame.getMessage();
        int allSize = headLength + message.serializedSize();
        out.ensureWritable(CompressBean.computeVar32Size(allSize) + allSize);
        CompressBean.writeVar32(out, allSize);
        CompressBean.writeVar32(out, frame.messageType());
        CompressBean.writeVar32(out, frame.getRequestId());
        CompressBean.writeVar32(out, frame.getMessageId());
        CompressBean.writeVar64(out, frame.getToken());
        CompressBean.writeVar64(out, frame.getUserId());
        message.write(out);
    }

    private void encodeRemoteMessage(GatewaySendableMessage frame, ByteBuf out) {
        int headLength = CompressBean.computeVar32Size(frame.messageType());
        headLength += CompressBean.computeVar32Size(frame.requestId());
        headLength += CompressBean.computeVar32Size(frame.getMessageId());
        headLength += CompressBean.computeVar64Size(frame.getToken());
        headLength += CompressBean.computeVar64Size(frame.getUserId());
        int allSize = headLength + frame.getData().length;
        out.ensureWritable(CompressBean.computeVar32Size(allSize) + allSize);
        CompressBean.writeVar32(out, allSize);
        CompressBean.writeVar32(out, frame.messageType());
        CompressBean.writeVar32(out, frame.getRequestId());
        CompressBean.writeVar32(out, frame.getMessageId());
        CompressBean.writeVar64(out, frame.getToken());
        CompressBean.writeVar64(out, frame.getUserId());
        out.writeBytes(frame.getData());

    }
}
