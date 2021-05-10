package com.senpure.io.server.provider;

import com.senpure.executor.TaskLoopGroup;
import com.senpure.io.server.remoting.AbstractRemoteServer;

public class Gateway extends AbstractRemoteServer {

    public Gateway(TaskLoopGroup service) {
        super(service);
    }
}
