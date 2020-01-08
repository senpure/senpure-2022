package com.senpure.io.server.producer;

import com.senpure.io.protocol.Bean;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProducerMessageEncoder extends MessageToByteEncoder<Producer2GatewayMessage> {
    protected Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    protected void encode(ChannelHandlerContext ctx, Producer2GatewayMessage frame, ByteBuf out) throws Exception {
        int dataLength = frame.getMessage().getSerializedSize();
        int headLength = Bean.computeVar32Size(frame.getRequestId());
        headLength += Bean.computeVar32Size(frame.getMessageId());
        headLength += Bean.computeVar64Size(frame.getToken());
        headLength += Bean.computeVar32Size(frame.getUserIds().length);
        for (Long userId : frame.getUserIds()) {
            headLength += Bean.computeVar64Size(userId);
        }
        int packageLength = headLength + dataLength;
        out.ensureWritable(Bean.computeVar32Size(packageLength) + packageLength);

        Bean.writeVar32(out, packageLength);
        Bean.writeVar32(out, frame.getRequestId());
        Bean.writeVar32(out, frame.getMessageId());
        Bean.writeVar64(out, frame.getToken());
        Bean.writeVar32(out, frame.getUserIds().length);
        for (Long userId : frame.getUserIds()) {
            Bean.writeVar64(out, userId);
        }
        frame.getMessage().write(out);
    }

    protected void encode2(ChannelHandlerContext ctx, Producer2GatewayMessage frame, ByteBuf out) throws Exception {
        //  ByteBuf buf = Unpooled.buffer(frame.getMessage().getSerializedSize());
        // frame.getMessage().write(buf);
        // int length = buf.writerIndex();

        int length = frame.getMessage().getSerializedSize();
        //head 4 +requestId 4 +messageId 4 channelId 8+ playerLen 2+userLen*8+ content length
        int userLen = frame.getUserIds().length;
        int packageLen = 22 + (userLen << 3) + length;
        out.ensureWritable(packageLen);
        out.writeInt(packageLen - 4);
        out.writeInt(frame.getRequestId());
        out.writeInt(frame.getMessageId());
        out.writeLong(frame.getToken());
        out.writeShort(userLen);
        for (long i : frame.getUserIds()) {
            out.writeLong(i);
        }
        frame.getMessage().write(out);
        // out.writeBytes(buf);
    }


}
