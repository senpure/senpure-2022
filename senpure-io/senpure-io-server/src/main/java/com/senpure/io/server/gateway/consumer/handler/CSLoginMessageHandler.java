package com.senpure.io.server.gateway.consumer.handler;

public class CSLoginMessageHandler extends CSConsumerVerifyMessageHandler {


    @Override
    public int handleMessageId() {
        return messageExecutor.getCsLoginMessageId();
    }

    @Override
    public boolean stopForward() {
        return false;
    }
}
