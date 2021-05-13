package com.senpure.io.server.gateway.consumer;

import com.senpure.io.protocol.CompressBean;
import com.senpure.io.protocol.Message;
import com.senpure.io.server.gateway.GatewaySendConsumerMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 将服务器发到网关的消息，转发给客户端
 */
public class ConsumerMessageEncoder extends MessageToByteEncoder<GatewaySendConsumerMessage> {
    protected Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    protected void encode(ChannelHandlerContext ctx, GatewaySendConsumerMessage frame, ByteBuf out) {
        int headLength = CompressBean.computeVar32Size(frame.requestId());
        headLength += CompressBean.computeVar32Size(frame.messageId());
        Message message = frame.message();
        int packageLength = headLength + message.serializedSize();
        out.ensureWritable(CompressBean.computeVar32Size(packageLength) + packageLength);
        CompressBean.writeVar32(out, packageLength);
        CompressBean.writeVar32(out, frame.requestId());
        CompressBean.writeVar32(out, frame.messageId());
        message.write(out);
    }


}
