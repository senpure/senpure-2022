package com.senpure.io.server.gateway;


import com.senpure.io.server.protocol.message.CSBreakUserGatewayMessage;

/**
 * WaitRelationTask
 *
 * @author senpure
 * @time 2018-11-01 14:04:52
 */
public class WaitRelationTask {

    private boolean relation = false;
    protected long startTime;
    private long relationTime;
    private long maxDelay = 5000;

    private Client2GatewayMessage message;

    private ProducerChannelManager serverChannelManager;

    private ProducerManager serverManager;
    private Long relationToken;


    public WaitRelationTask() {
        startTime = System.currentTimeMillis();
    }

    public boolean check() {

        return relation;
    }

    public boolean cancel() {
        if (relation) {
            return false;
        }
        return System.currentTimeMillis() - startTime > maxDelay;
    }


    public void sendMessage() {
        if (message != null) {
            serverManager.bind(message.getToken(), relationToken, serverChannelManager);
            serverManager.sendMessage(message);
        }

    }

    public void sendCancelMessage(GatewayMessageExecutor messageExecutor) {
        CSBreakUserGatewayMessage breakMessage = new CSBreakUserGatewayMessage();
        breakMessage.setRelationToken(relationToken);
        breakMessage.setToken(message.getToken());
        breakMessage.setUserId(message.getUserId());
        messageExecutor.sendMessage(serverChannelManager, breakMessage);

    }


    public Client2GatewayMessage getMessage() {
        return message;
    }

    public void setMessage(Client2GatewayMessage message) {
        this.message = message;
    }

    public ProducerChannelManager getServerChannelManager() {
        return serverChannelManager;
    }

    public ProducerManager getServerManager() {
        return serverManager;
    }

    public void setServerManager(ProducerManager serverManager) {
        this.serverManager = serverManager;
    }

    public void setServerChannelManager(ProducerChannelManager serverChannelManager) {
        this.serverChannelManager = serverChannelManager;
    }


    public Long getRelationToken() {
        return relationToken;
    }

    public void setRelationToken(Long relationToken) {
        this.relationToken = relationToken;
    }

    public boolean isRelation() {
        return relation;
    }

    public void setRelation(boolean relation) {
        this.relation = relation;
    }


    public long getRelationTime() {
        return relationTime;
    }

    public void setRelationTime(long relationTime) {
        this.relationTime = relationTime;
    }
}
