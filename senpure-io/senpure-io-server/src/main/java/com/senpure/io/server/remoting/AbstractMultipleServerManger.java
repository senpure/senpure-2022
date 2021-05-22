package com.senpure.io.server.remoting;

import com.senpure.io.protocol.Message;
import com.senpure.io.server.MessageFrame;

import javax.annotation.Nonnull;

public abstract class AbstractMultipleServerManger<T extends MessageFrame> extends AbstractServerManager<T> implements MultipleServerManager {


    public String getRemoteServerKey(String host, int port) {
        return host + ":" + port;
    }

    @Override
    public void sendMessage(ServerMessageSender server, Message message) {

        server.sendMessage(createMessage(message));
    }

    @Override
    public void sendMessage(ServerMessageSender server, Message message, ResponseCallback callback) {
        server.sendMessage(createMessage(message, nextRequestId()), callback);
    }

    @Override
    public void sendMessage(ServerMessageSender server, Message message, ResponseCallback callback, int timeout) {
        server.sendMessage(createMessage(message, nextRequestId()), callback, timeout);
    }

    @Nonnull
    @Override
    public Response sendSyncMessage(ServerMessageSender server, Message message) {
        return server.sendSyncMessage(createMessage(message, nextRequestId()));
    }

    @Nonnull
    @Override
    public Response sendSyncMessage(ServerMessageSender server, Message message, int timeout) {
        return server.sendSyncMessage(createMessage(message, nextRequestId()), timeout);
    }

    @Override
    public void respondMessage(ServerMessageSender server, Message message) {

        server.sendMessage(createMessage(message, requestId()));
    }

    @Override
    public void respondMessage(ServerMessageSender server, Message message, int requestId) {
        server.sendMessage(createMessage(message, requestId));
    }


}
