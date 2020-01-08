package com.senpure.io.server.direct;


import com.senpure.base.util.Assert;
import com.senpure.io.protocol.CompressBean;
import com.senpure.io.protocol.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class DirectMessageDecoder extends ByteToMessageDecoder {
    protected Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        in.markReaderIndex();
        int preIndex = in.readerIndex();
        int packageLength = CompressBean.tryReadVar32(in);
        int readerIndex = in.readerIndex();
        if (preIndex == readerIndex) {
            return;
        }
        if (packageLength == 0) {
            Assert.error("错误，数据包长度不能为0");
        }
        if (packageLength > in.readableBytes()) {
            this.logger.trace("数据不够一个数据包 packageLength ={} ,readableBytes={}", packageLength, in.readableBytes());
            in.resetReaderIndex();
        } else {
            int endIndex = readerIndex + packageLength;
            int requestId = CompressBean.readVar32(in);
            int messageId = CompressBean.readVar32(in);
            DirectMessage frame = new DirectMessage();
            frame.setRequestId(requestId);
            frame.setMessageId(messageId);
            Message message = DirectMessageHandlerUtil.getEmptyMessage(messageId);
            if (message == null) {
                int headSize = CompressBean.computeVar32Size(requestId) + CompressBean.computeVar32Size(messageId);
                int messageLength = packageLength - headSize;
                in.skipBytes(messageLength);
                logger.warn("没有找到消息处理程序 messageId {}", messageId);
            } else {
                try {
                    message.read(in, endIndex);
                    frame.setMessage(message);
                } catch (Exception e) {
                    ctx.close();
                    logger.debug("二进制转换为消息失败 messageId {}, message {}", messageId, message);
                    logger.error("error", e);
                }
            }
            out.add(frame);
        }
    }


}
