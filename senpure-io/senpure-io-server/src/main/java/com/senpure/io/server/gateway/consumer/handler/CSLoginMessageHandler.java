package com.senpure.io.server.gateway.consumer.handler;

public class CSLoginMessageHandler extends CSConsumerVerifyMessageHandler {


    @Override
    public int messageId() {
        return messageExecutor.getCsLoginMessageId();
    }

    @Override
    public boolean stopProvider() {
        return false;
    }
}
