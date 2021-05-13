package com.senpure.io.server.consumer;

import com.senpure.io.protocol.Message;
import com.senpure.io.server.remoting.AbstractSingleServerManager;
import com.senpure.io.server.remoting.RemoteServer;

public class ProviderManager  extends AbstractSingleServerManager<ConsumerMessage> {

    public ProviderManager(RemoteServer remoteServer) {
        super(remoteServer);
    }

    @Override
    protected ConsumerMessage createMessage(Message message) {

        return new ConsumerMessage(message);
    }

    @Override
    protected ConsumerMessage createMessage(Message message, int requestId) {
        ConsumerMessage frame = new ConsumerMessage(message);
        frame.setRequestId(requestId);
        return frame;
    }
}
