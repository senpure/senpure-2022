package com.senpure.io.server.gateway.provider.handler;

public class SCLoginMessageHandler extends  SCConsumerVerifyMessageHandler {


    @Override
    public int handleMessageId() {
        return messageExecutor.getScLoginMessageId();
    }

    @Override
    public boolean stopResponse() {
        return false;
    }
}
