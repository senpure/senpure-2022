package com.senpure.io.server.remoting;

import com.senpure.io.protocol.Message;
import com.senpure.io.server.MessageFrame;
import io.netty.channel.Channel;

import javax.annotation.Nonnull;

public abstract class AbstractSameServerSingleInstanceMessageSender<T extends MessageFrame, R extends ServerInstanceMessageFrameSender> extends AbstractSameServerMultipleInstanceMessageSender<T> implements SameServerSingleInstanceMessageSender {


    protected R defaultRemoteServer;

    public void setDefaultRemoteServer(R defaultRemoteServer) {
        this.defaultRemoteServer = defaultRemoteServer;
    }

    public R getDefaultRemoteServer() {
        return defaultRemoteServer;
    }

    @Override
    public void sendMessage(Message message) {

        defaultRemoteServer.sendMessage(createMessage(message));
    }

    @Override
    public void sendMessage(Message message, ResponseCallback callback) {
        defaultRemoteServer.sendMessage(createMessage(message, nextRequestId()), callback);
    }

    @Override
    public void sendMessage(Message message, ResponseCallback callback, int timeout) {
        defaultRemoteServer.sendMessage(createMessage(message, nextRequestId()), callback, timeout);
    }

    @Nonnull
    @Override
    public Response sendSyncMessage(Message message) {
        return defaultRemoteServer.sendSyncMessage(createMessage(message, nextRequestId()));
    }

    @Nonnull
    @Override
    public Response sendSyncMessage(Message message, int timeout) {
        return defaultRemoteServer.sendSyncMessage(createMessage(message, nextRequestId()), timeout);
    }

    @Override
    public void respondMessage(Message message) {


        defaultRemoteServer.sendMessage(createMessage(message, requestId()));
    }

    @Override
    public void respondMessage(Message message, int requestId) {
        defaultRemoteServer.sendMessage(createMessage(message, requestId));
    }

    @Override
    public ServerInstanceMessageFrameSender getFrameSender(Channel channel) {
        return defaultRemoteServer;
    }
}
