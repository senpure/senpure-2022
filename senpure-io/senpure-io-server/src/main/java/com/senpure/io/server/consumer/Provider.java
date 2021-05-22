package com.senpure.io.server.consumer;

import com.senpure.executor.TaskLoopGroup;
import com.senpure.io.server.remoting.AbstractServerInstanceMessageFrameSender;

public class Provider  extends AbstractServerInstanceMessageFrameSender {
    public Provider(TaskLoopGroup service) {
        super(service);
    }
}
