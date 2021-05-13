package com.senpure.io.server.remoting;

import com.senpure.io.protocol.Message;
import com.senpure.io.server.MessageFrame;

import javax.annotation.Nonnull;

public abstract class AbstractSingleServerManager<T extends MessageFrame> extends AbstractServerManager<T> implements SingleServerManager {


    private final RemoteServer remoteServer;

    public AbstractSingleServerManager(RemoteServer remoteServer) {
        this.remoteServer = remoteServer;
    }

    @Override
    public void sendMessage(Message message) {

        remoteServer.sendMessage(createMessage(message));
    }

    @Override
    public void sendMessage(Message message, ResponseCallback callback) {
        remoteServer.sendMessage(createMessage(message, nextRequestId()), callback);
    }

    @Override
    public void sendMessage(Message message, ResponseCallback callback, int timeout) {
        remoteServer.sendMessage(createMessage(message, nextRequestId()), callback, timeout);
    }

    @Nonnull
    @Override
    public Response sendSyncMessage(Message message) {
        return remoteServer.sendSyncMessage(createMessage(message, nextRequestId()));
    }

    @Nonnull
    @Override
    public Response sendSyncMessage(Message message, int timeout) {
        return remoteServer.sendSyncMessage(createMessage(message, nextRequestId()), timeout);
    }
}
