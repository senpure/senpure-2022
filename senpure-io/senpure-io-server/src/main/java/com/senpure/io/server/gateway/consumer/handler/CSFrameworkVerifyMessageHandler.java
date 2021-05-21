package com.senpure.io.server.gateway.consumer.handler;

import com.senpure.io.server.ChannelAttributeUtil;
import com.senpure.io.server.Constant;
import com.senpure.io.server.gateway.GatewayReceiveConsumerMessage;
import com.senpure.io.server.gateway.provider.Provider;
import com.senpure.io.server.gateway.provider.ProviderManager;
import com.senpure.io.server.protocol.message.CSFrameworkVerifyMessage;
import com.senpure.io.server.protocol.message.SCFrameworkErrorMessage;
import com.senpure.io.server.protocol.message.SCFrameworkVerifyMessage;
import io.netty.channel.Channel;

public class CSFrameworkVerifyMessageHandler extends AbstractGatewayConsumerMessageHandler {


    private final boolean simpleVerify;
    private final String token;
    private final long userId;

    public CSFrameworkVerifyMessageHandler(boolean simpleVerify, String token, long userId) {
        this.simpleVerify = simpleVerify;
        this.token = token;
        this.userId = userId;
    }

    @Override
    public void execute(Channel channel, GatewayReceiveConsumerMessage frame) {

        if (simpleVerify) {
            CSFrameworkVerifyMessage message = new CSFrameworkVerifyMessage();
            messageExecutor.readMessage(message, frame.getData());
            if (message.getToken().equals(token)) {
                ChannelAttributeUtil.setFramework(channel, true);
                ChannelAttributeUtil.setUserId(channel, userId);
                SCFrameworkVerifyMessage verifyMessage = new SCFrameworkVerifyMessage();
                verifyMessage.setUserId(userId);
                messageExecutor.responseMessage2Consumer(frame.requestId(), frame.token(), verifyMessage);
                logger.debug("{} 框架认证成功", channel);
            } else {
                logger.debug("{} 框架认证失败", channel);
                SCFrameworkErrorMessage errorMessage = new SCFrameworkErrorMessage();
                errorMessage.setCode(Constant.ERROR_VERIFY_FAILURE);
                errorMessage.setMessage("框架内部认证失败");
                messageExecutor.responseMessage2Consumer(frame.requestId(), frame.token(), errorMessage);
                channel.close();
            }
        } else {
            ProviderManager providerManager = messageExecutor.getFrameworkVerifyProviderManager();
            if (providerManager == null) {
                logger.debug("{} provider 框架认证失败", channel);
                SCFrameworkErrorMessage errorMessage = new SCFrameworkErrorMessage();
                errorMessage.setCode(Constant.ERROR_VERIFY_FAILURE);
                errorMessage.setMessage("框架内部认证失败,没有认证服务提供者");
                messageExecutor.responseMessage2Consumer(frame.requestId(), frame.token(), errorMessage);
                return;
            }
            Provider provider = providerManager.nextProvider();
            if (provider == null) {
                logger.warn("{} 没有服务实例可以使用", providerManager.getServerName());
                SCFrameworkErrorMessage errorMessage = new SCFrameworkErrorMessage();
                errorMessage.setCode(Constant.ERROR_VERIFY_FAILURE);
                errorMessage.setMessage("框架内部认证失败,没有认证服务实例");
                messageExecutor.responseMessage2Consumer(frame.requestId(), frame.token(), errorMessage);
                return;
            }
            int requestId = frame.requestId();
            providerManager.sendMessage(provider, frame, response -> {
                if (response.isSuccess()) {
                    ChannelAttributeUtil.setFramework(channel, true);
                    GatewayReceiveConsumerMessage returnMessage = new GatewayReceiveConsumerMessage(frame.getMessageLength(), frame.getData());
                    messageExecutor.responseMessage2Consumer(requestId, frame.token(), returnMessage);
                } else {
                    logger.debug("{} provider 框架认证失败", channel);
                    SCFrameworkErrorMessage errorMessage = new SCFrameworkErrorMessage();
                    errorMessage.setCode(Constant.ERROR_VERIFY_FAILURE);
                    errorMessage.setMessage("框架内部认证失败");
                    messageExecutor.responseMessage2Consumer(requestId, frame.token(), errorMessage);
                    channel.close();
                }
            });
        }


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
