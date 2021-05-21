package com.senpure.io.server.provider.handler;

import com.senpure.io.server.protocol.message.SCFrameworkErrorMessage;
import io.netty.channel.Channel;

import javax.annotation.Nonnull;

public class SCFrameworkErrorMessageHandlerImpl extends AbstractFrameworkNecessaryMessageHandler<SCFrameworkErrorMessage>
        implements SCFrameworkErrorMessageHandler {
    @Override
    public int messageId() {
        return SCFrameworkErrorMessage.MESSAGE_ID;
    }

    @Override
    public void execute(Channel channel, long token, long userId, SCFrameworkErrorMessage message) throws Exception {

        logger.error("{}", message);
    }

    @Nonnull
    @Override
    public SCFrameworkErrorMessage newEmptyMessage() {
        return new SCFrameworkErrorMessage();
    }
}
