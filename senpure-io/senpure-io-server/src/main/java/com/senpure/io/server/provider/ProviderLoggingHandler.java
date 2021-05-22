package com.senpure.io.server.provider;

import com.senpure.io.server.MessageFrame;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.Arrays;

/**
 * ConsumerLoggingHandler
 *
 * @author senpure
 * @time 2019-08-06 14:59:59
 */
public class ProviderLoggingHandler extends LoggingHandler {

    private final boolean outFormat;

    private final boolean inFormat;

    public ProviderLoggingHandler(LogLevel level, boolean inFormat, boolean outFormat) {
        super(level);
        this.inFormat = inFormat;
        this.outFormat = outFormat;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {

        if (this.logger.isEnabled(this.internalLevel)) {
            if (msg instanceof ProviderSendMessage) {
                if (outFormat) {
                    ProviderSendMessage frame = (ProviderSendMessage) msg;
                    this.logger.log(this.internalLevel, "{} requestId:{} token:{} userIds:{}{}{}",
                            "WRITE",frame.requestId(), frame.getToken(), Arrays.toString(frame.getUserIds()), "\n", frame.message().toString(null));
                    //this.logger.log(this.internalLevel, this.format(ctx, ChannelAttributeUtil.getChannelPlayerStr(ctx.channel())+" WRITE", "\n"+((Message) msg).toString(null)));
                } else {
                    this.logger.log(this.internalLevel, "{} {}",
                            "WRITE: ", msg);
                    //  this.logger.log(this.internalLevel, this.format(ctx, ChannelAttributeUtil.getChannelPlayerStr(ctx.channel())+" WRITE", msg));

                }

            } else {
                this.logger.log(this.internalLevel, "{} {}",
                        "WRITE: ", msg);
            }
        }

        ctx.write(msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        if (this.logger.isEnabled(this.internalLevel)) {
            if (msg instanceof ProviderReceivedMessage) {

                if (inFormat) {
                    ProviderReceivedMessage frame = (ProviderReceivedMessage) msg;
                    this.logger.log(this.internalLevel, "{} {}[{}] requestId:{} token:{} userId:{}{}{}",
                            "RECEIVED",frame.messageType== MessageFrame.MESSAGE_TYPE_SC?"SC":"CS", frame.messageType, frame.requestId(), frame.getToken(), frame.getUserId(), "\n", frame.getMessage().toString(null));
                    // this.logger.log(this.internalLevel, this.format(ctx, ChannelAttributeUtil.getChannelPlayerStr(ctx.channel()) + " RECEIVED", "\n" + ((Message) msg).toString(null)));

                } else {
                    this.logger.log(this.internalLevel, "{} {}",
                            "RECEIVED: ", msg);
                }
            } else {
                this.logger.log(this.internalLevel, "{} {}",
                        "RECEIVED: ", msg);
            }
        }
        ctx.fireChannelRead(msg);

    }
}
