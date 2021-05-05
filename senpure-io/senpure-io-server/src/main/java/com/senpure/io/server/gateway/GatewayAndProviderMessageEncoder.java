package com.senpure.io.server.gateway;

import com.senpure.io.protocol.CompressBean;
import com.senpure.io.protocol.Message;
import com.senpure.io.server.MessageFrame;
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
    protected void encode(ChannelHandlerContext ctx, GatewaySendableMessage msg, ByteBuf out) {

        int messageType = msg.messageType();
        if (messageType == MessageFrame.MESSAGE_TYPE_CS) {
            encodeCSMessage(msg, out);
        } else if (messageType == MessageFrame.MESSAGE_TYPE_SC) {
            int headLength = 1 + CompressBean.computeVar32Size(msg.getRequestId());
            headLength += CompressBean.computeVar32Size(msg.getMessageId());
            headLength += CompressBean.computeVar64Size(msg.getToken());
            headLength += CompressBean.computeVar64Size(msg.getUserId());
            Message message = msg.getMessage();
            int allSize = headLength + message.serializedSize();
            out.ensureWritable(CompressBean.computeVar32Size(allSize) + allSize);
            CompressBean.writeVar32(out, allSize);
            CompressBean.writeVar32(out, msg.messageType());
            CompressBean.writeVar32(out, msg.getRequestId());
            CompressBean.writeVar32(out, msg.getMessageId());
            CompressBean.writeVar64(out, msg.getToken());
            CompressBean.writeVar64(out, msg.getUserId());
            message.write(out);
        } else {
            encodeCSMessage(msg, out);
        }

    }

    private void encodeCSMessage(GatewaySendableMessage msg, ByteBuf out) {
        int headLength = 1 + CompressBean.computeVar32Size(msg.getRequestId());
        headLength += CompressBean.computeVar32Size(msg.getMessageId());
        headLength += CompressBean.computeVar64Size(msg.getToken());
        headLength += CompressBean.computeVar64Size(msg.getUserId());
        int allSize = headLength + msg.getData().length;
        out.ensureWritable(CompressBean.computeVar32Size(allSize) + allSize);
        CompressBean.writeVar32(out, allSize);
        CompressBean.writeVar32(out, msg.messageType());
        CompressBean.writeVar32(out, msg.getRequestId());
        CompressBean.writeVar32(out, msg.getMessageId());
        CompressBean.writeVar64(out, msg.getToken());
        CompressBean.writeVar64(out, msg.getUserId());
        out.writeBytes(msg.getData());

    }
}
