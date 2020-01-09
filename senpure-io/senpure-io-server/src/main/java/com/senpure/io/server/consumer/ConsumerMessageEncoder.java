package com.senpure.io.server.consumer;


import com.senpure.io.protocol.CompressBean;
import com.senpure.io.protocol.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerMessageEncoder extends MessageToByteEncoder<ConsumerMessage> {
    protected Logger logger = LoggerFactory.getLogger(getClass());


    //包长int ,消息Id int, 二进制数据
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ConsumerMessage frame, ByteBuf out) throws Exception {
        Message message = frame.getMessage();
        int headLength = CompressBean.computeVar32Size(frame.getRequestId());
        headLength += CompressBean.computeVar32Size(message.getMessageId());
        int packageLength = headLength + message.getSerializedSize();
        out.ensureWritable(CompressBean.computeVar32Size(packageLength) + packageLength);
        CompressBean.writeVar32(out, packageLength);
        CompressBean.writeVar32(out, frame.getRequestId());
        CompressBean.writeVar32(out, message.getMessageId());
        message.write(out);
    }


}
