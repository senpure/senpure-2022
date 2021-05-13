package com.senpure.io.server.consumer;

import com.senpure.executor.TaskLoopGroup;
import com.senpure.io.server.remoting.AbstractRemoteServer;

public class Provider  extends AbstractRemoteServer {
    public Provider(TaskLoopGroup service) {
        super(service);
    }
}
