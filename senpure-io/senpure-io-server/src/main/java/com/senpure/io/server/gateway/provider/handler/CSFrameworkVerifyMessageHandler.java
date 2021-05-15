package com.senpure.io.server.gateway.provider.handler;

import com.senpure.io.server.ChannelAttributeUtil;
import com.senpure.io.server.Constant;
import com.senpure.io.server.gateway.GatewayReceiveConsumerMessage;
import com.senpure.io.server.gateway.GatewayReceiveProviderMessage;
import com.senpure.io.server.protocol.message.CSFrameworkVerifyMessage;
import com.senpure.io.server.protocol.message.SCFrameworkErrorMessage;
import io.netty.channel.Channel;

public class CSFrameworkVerifyMessageHandler extends AbstractGatewayProviderMessageHandler {

    private final boolean simpleVerify;
    private final String token;

    public CSFrameworkVerifyMessageHandler(boolean simpleVerify, String token) {
        this.simpleVerify = simpleVerify;
        this.token = token;
    }

    @Override
    public void execute(Channel channel, GatewayReceiveProviderMessage frame) {

        if (simpleVerify) {
            CSFrameworkVerifyMessage message = new CSFrameworkVerifyMessage();
            messageExecutor.readMessage(message, frame.getData());
            if (message.getToken().equals(token)) {
                ChannelAttributeUtil.setFramework(channel, true);
                logger.debug("{} provider 框架认证成功", channel);
            } else {
                logger.debug("{} provider 框架认证失败", channel);
                SCFrameworkErrorMessage errorMessage = new SCFrameworkErrorMessage();
                errorMessage.setCode(Constant.ERROR_VERIFY_FAILURE);
                errorMessage.setMessage("框架内部认证失败");
                messageExecutor.responseMessage2Producer(frame.requestId(), channel, errorMessage);
                channel.close();
            }
        }

        GatewayReceiveConsumerMessage message = new GatewayReceiveConsumerMessage(frame.getMessageLength(), frame.getData());





    }

    @Override
    public int messageId() {
        return CSFrameworkVerifyMessage.MESSAGE_ID;
    }
}
