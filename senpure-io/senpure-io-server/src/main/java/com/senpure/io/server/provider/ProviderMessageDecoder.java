package com.senpure.io.server.provider;

import com.senpure.base.util.Assert;
import com.senpure.io.protocol.CompressBean;
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
public class ProviderMessageDecoder extends ByteToMessageDecoder {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        in.markReaderIndex();
        int preIndex = in.readerIndex();
        int packageLength = CompressBean.tryReadVar32(in);
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
            int requestId =CompressBean.readVar32(in);
            int messageId = CompressBean.readVar32(in);
            long channelToken = CompressBean.readVar64(in);
            long userId = CompressBean.readVar64(in);

            Message message = ProviderMessageHandlerUtil.getEmptyMessage(messageId);
            Gateway2ProviderMessage frame = new Gateway2ProviderMessage();
            frame.setRequestId(requestId);
            frame.setMessageId(messageId);
            frame.setToken(channelToken);
            frame.setUserId(userId);
            if (message == null) {
                int headSize = CompressBean.computeVar32Size(requestId);
                headSize += CompressBean.computeVar32Size(messageId);

                headSize += CompressBean.computeVar64Size(channelToken);
                headSize +=CompressBean.computeVar64Size(userId);
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



}
