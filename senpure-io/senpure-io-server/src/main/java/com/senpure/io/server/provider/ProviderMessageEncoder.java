package com.senpure.io.server.provider;

import com.senpure.io.protocol.CompressBean;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProviderMessageEncoder extends MessageToByteEncoder<ProviderSendMessage> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    protected void encode(ChannelHandlerContext ctx, ProviderSendMessage frame, ByteBuf out) {
        int dataLength = frame.getMessage().serializedSize();
        int headLength = CompressBean.computeVar32Size(frame.getRequestId());
        headLength += CompressBean.computeVar32Size(frame.getMessageId());
        headLength += CompressBean.computeVar64Size(frame.getToken());
        headLength += CompressBean.computeVar32Size(frame.getUserIds().length);
        for (Long userId : frame.getUserIds()) {
            headLength += CompressBean.computeVar64Size(userId);
        }
        int packageLength = headLength + dataLength;
        out.ensureWritable(CompressBean.computeVar32Size(packageLength) + packageLength);

        CompressBean.writeVar32(out, packageLength);
        CompressBean.writeVar32(out, frame.getRequestId());
        CompressBean.writeVar32(out, frame.getMessageId());
        CompressBean.writeVar64(out, frame.getToken());
        CompressBean.writeVar32(out, frame.getUserIds().length);
        for (Long userId : frame.getUserIds()) {
            CompressBean.writeVar64(out, userId);
        }
        frame.getMessage().write(out);
    }
}
