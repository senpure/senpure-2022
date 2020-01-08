package com.senpure.io.server.producer;

import com.senpure.base.util.Assert;
import com.senpure.io.protocol.Bean;
import com.senpure.io.protocol.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 解析网关发过来的消息
 */
public class ProducerMessageDecoder extends ByteToMessageDecoder {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        in.markReaderIndex();
        int preIndex = in.readerIndex();
        int packageLength = Bean.tryReadVar32(in);
        if (preIndex == in.readerIndex()) {
            return;
        }

        if (packageLength == 0) {
            Assert.error("错误，数据包长度不能为0");
        }
        if (packageLength > in.readableBytes()) {
            this.logger.info("数据不够一个数据包 packageLength ={} ,readableBytes={}", packageLength, in.readableBytes());
            in.resetReaderIndex();
        } else {
            int endIndex = in.readerIndex() + packageLength;
            int requestId = Bean.readVar32(in);
            int messageId = Bean.readVar32(in);
            long channelToken = Bean.readVar64(in);
            long userId = Bean.readVar64(in);

            Message message = ProducerMessageHandlerUtil.getEmptyMessage(messageId);
            Gateway2ProducerMessage frame = new Gateway2ProducerMessage();
            frame.setRequestId(requestId);
            frame.setMessageId(messageId);
            frame.setToken(channelToken);
            frame.setUserId(userId);
            if (message == null) {
                int headSize = Bean.computeVar32Size(requestId);
                headSize += Bean.computeVar32Size(messageId);

                headSize += Bean.computeVar64Size(channelToken);
                headSize += Bean.computeVar64Size(userId);
                int messageLen = packageLength - headSize;
                in.skipBytes(messageLen);
                logger.warn("没有找到消息处理程序{} token:{} userId:{}", channelToken, messageId, userId);
            }
            else {
                message.read(in, endIndex);
                frame.setMessage(message);
            }
            out.add(frame);
        }

    }

    protected void decode2(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        int rl = in.readableBytes();

        if (rl < 4) {
            this.logger.debug("数据过短 {}", rl);
        } else {
            in.markReaderIndex();
            int packageLength = in.readInt();
            if (packageLength == 0) {
                Assert.error("错误，数据包长度不能为0");
            }
            if (packageLength > in.readableBytes()) {
                if (packageLength > 2000000) {
                    ctx.close().sync();
                    return;
                }
                this.logger.info("数据不够一个数据包 packageLength ={} ,readableBytes={}", packageLength, in.readableBytes());
                in.resetReaderIndex();
            } else {
                int requestId = in.readInt();
                int messageId = in.readInt();
                long channelToken = in.readLong();
                long userId = in.readLong();
                int messageLen = packageLength - 24;
                Message message = ProducerMessageHandlerUtil.getEmptyMessage(messageId);
                if (message == null) {
                    logger.warn("没有找到消息处理程序{} token:{} userId:{}", channelToken, messageId, userId);
                    in.skipBytes(messageLen);
                    return;
                }
                message.read(in, in.readerIndex() + messageLen);
                Gateway2ProducerMessage frame = new Gateway2ProducerMessage();
                frame.setRequestId(requestId);
                frame.setMessageId(messageId);
                frame.setToken(channelToken);
                frame.setUserId(userId);
                frame.setMessage(message);
                out.add(frame);

            }

        }
    }


}
