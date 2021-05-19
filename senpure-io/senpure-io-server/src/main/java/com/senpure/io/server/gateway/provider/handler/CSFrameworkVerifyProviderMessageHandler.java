package com.senpure.io.server.gateway.provider.handler;

import com.senpure.io.server.Constant;
import com.senpure.io.server.gateway.GatewayReceiveProviderMessage;
import com.senpure.io.server.gateway.provider.Provider;
import com.senpure.io.server.gateway.provider.ProviderManager;
import com.senpure.io.server.protocol.message.CSFrameworkVerifyProviderMessage;
import com.senpure.io.server.protocol.message.SCFrameworkErrorMessage;
import com.senpure.io.server.protocol.message.SCSuccessMessage;
import io.netty.channel.Channel;

public class CSFrameworkVerifyProviderMessageHandler extends AbstractGatewayProviderMessageHandler {


    @Override
    public void execute(Channel channel, GatewayReceiveProviderMessage frame) {


        verify(channel, frame);
    }

    private synchronized void verify(Channel channel, GatewayReceiveProviderMessage frame) {
        CSFrameworkVerifyProviderMessage message = new CSFrameworkVerifyProviderMessage();
        messageExecutor.readMessage(message, frame);


        ProviderManager providerManager = messageExecutor.getFrameworkVerifyProviderManager();

        if (providerManager == null) {
            providerManager = new ProviderManager(messageExecutor);
            providerManager.setServerName(message.getServerName());
            providerManager = messageExecutor.addFrameworkVerifyProviderManager(providerManager);

        }
        String verifyName = providerManager.getServerName();
        if (!verifyName.equals(message.getServerName())) {
            logger.warn("已经存在认证服务，且服务名不相同 {} -> {}", verifyName, message.getServerName());
            SCFrameworkErrorMessage errorMessage = new SCFrameworkErrorMessage();
            errorMessage.setCode(Constant.ERROR_GATEWAY_ERROR);
            errorMessage.setMessage("已经存在认证服务，且服务名不相同 " + verifyName + " -> " + message.getServerName());
            messageExecutor.responseMessage2Producer(frame.requestId(), channel, errorMessage);
            return;
        }
        Provider provider = providerManager.getProvider(message.getServerKey());
        if (provider == null) {
            String serverKey = message.getServerKey();
            provider = messageExecutor.createProvider(serverKey);
            provider = providerManager.addProvider(provider);
        }
        provider.addChannel(channel);

        SCSuccessMessage successMessage = new SCSuccessMessage();
        messageExecutor.responseMessage2Producer(frame.requestId(), channel, successMessage);

    }

    @Override
    public int messageId() {
        return CSFrameworkVerifyProviderMessage.MESSAGE_ID;
    }
}
