package com.senpure.io.server.provider;

import com.senpure.executor.TaskLoopGroup;
import com.senpure.io.server.remoting.AbstractRemoteServer;

public class Gateway extends AbstractRemoteServer {

    private final TaskLoopGroup service;

    public Gateway(TaskLoopGroup service) {
        this.service = service;
    }

    @Override
    public void checkWaitSendMessage() {
        synchronized (waitSendMessages) {
            if (waitSendMessages.size() > 0) {
                TaskLoopGroup executor = service;
                if (executor != null) {
                    executor.execute(this::sendWaitMessage);
                } else {
                    sendWaitMessage();
                }
            }
        }
    }
}
