package com.senpure.io.server.direct;

import com.senpure.io.protocol.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * ConsumerLoggingHandler
 *
 * @author senpure
 * @time 2019-08-06 14:59:59
 */
public class DirectLoggingHandler extends LoggingHandler {

    private boolean outFormat;

    private boolean inFormat;

    public DirectLoggingHandler(LogLevel level, boolean inFormat, boolean outFormat) {
        super(level);
        this.inFormat = inFormat;
        this.outFormat = outFormat;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {

        if (this.logger.isEnabled(this.internalLevel)) {
            if (msg instanceof DirectMessage) {
                if (outFormat) {
                    DirectMessage frame = (DirectMessage) msg;
                    Message message=frame.getMessage();
                    this.logger.log(this.internalLevel, "{} requestId:{} {}{}",
                            "WRITE", frame.getRequestId(), "\n", message==null?"null":frame.getMessage().toString(null));
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
            if (msg instanceof DirectMessage) {
                if (inFormat) {
                    DirectMessage message = (DirectMessage) msg;
                    this.logger.log(this.internalLevel, "{} requestId:{} {}{}",
                            "RECEIVED", message.getRequestId(), "\n", message.getMessage().toString(null));
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
