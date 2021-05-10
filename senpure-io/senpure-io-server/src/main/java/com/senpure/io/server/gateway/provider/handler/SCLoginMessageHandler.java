package com.senpure.io.server.gateway.provider.handler;

public class SCLoginMessageHandler extends  SCConsumerVerifyMessageHandler {


    @Override
    public int messageId() {
        return messageExecutor.getScLoginMessageId();
    }

    @Override
    public boolean stopConsumer() {
        return false;
    }
}
