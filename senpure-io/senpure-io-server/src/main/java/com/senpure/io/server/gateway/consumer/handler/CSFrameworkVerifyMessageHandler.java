package com.senpure.io.server.gateway.consumer.handler;

import com.senpure.io.server.ChannelAttributeUtil;
import com.senpure.io.server.Constant;
import com.senpure.io.server.gateway.GatewayReceiveConsumerMessage;
import com.senpure.io.server.protocol.message.CSFrameworkVerifyMessage;
import com.senpure.io.server.protocol.message.SCFrameworkErrorMessage;
import io.netty.channel.Channel;

public class CSFrameworkVerifyMessageHandler extends AbstractGatewayConsumerMessageHandler {


    private final boolean simpleVerify;
    private final String token;

    public CSFrameworkVerifyMessageHandler(boolean simpleVerify, String token) {
        this.simpleVerify = simpleVerify;
        this.token = token;
    }

    @Override
    public void execute(Channel channel, GatewayReceiveConsumerMessage frame) {

        if (simpleVerify) {
            CSFrameworkVerifyMessage message = new CSFrameworkVerifyMessage();
            messageExecutor.readMessage(message, frame.getData());
            if (message.getToken().equals(token)) {
                ChannelAttributeUtil.setFramework(channel, true);
                logger.debug("{} 框架认证成功", channel);
                return;
            }
        }
        logger.debug("{} 框架认证失败", channel);
        SCFrameworkErrorMessage errorMessage = new SCFrameworkErrorMessage();
        errorMessage.setCode(Constant.ERROR_VERIFY_FAILURE);
        errorMessage.setMessage("框架内部认证失败");
        messageExecutor.responseMessage2Consumer(frame.requestId(), frame.token(), errorMessage);
        channel.close();

    }

    @Override
    public int messageId() {
        return CSFrameworkVerifyMessage.MESSAGE_ID;
    }

    @Override
    public boolean stopProvider() {
        return true;
    }
}
