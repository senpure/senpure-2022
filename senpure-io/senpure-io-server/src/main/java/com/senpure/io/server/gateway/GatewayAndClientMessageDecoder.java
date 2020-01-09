package com.senpure.io.server.gateway;

import com.senpure.base.util.Assert;
import com.senpure.io.protocol.CompressBean;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class GatewayAndClientMessageDecoder extends ByteToMessageDecoder {
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
            this.logger.info("数据不够一个数据包 packageLength ={} ,readableBytes={}", Integer.valueOf(packageLength), Integer.valueOf(in.readableBytes()));
            in.resetReaderIndex();
        } else {
            int requestId = CompressBean.readVar32(in);
            int messageId = CompressBean.readVar32(in);
            int messageLength = packageLength - (CompressBean.computeVar32Size(requestId) + CompressBean.computeVar32Size(messageId));
            byte[] data = new byte[messageLength];
            in.readBytes(data);
            Client2GatewayMessage transfer = new Client2GatewayMessage();
            transfer.setRequestId(requestId);
            transfer.setData(data);
            transfer.setMessageId(messageId);
            out.add(transfer);
        }
    }


}
