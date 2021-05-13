package com.senpure.io.server.remoting;

import com.senpure.io.protocol.Message;
import com.senpure.io.server.MessageFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractServerManager<T extends MessageFrame> implements RemoteServerManager {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    private final AtomicInteger atomicRequestId = new AtomicInteger(1);


    protected int nextRequestId() {
        int requestId = atomicRequestId.getAndIncrement();
        if (requestId < 0) {
            atomicRequestId.compareAndSet(requestId, 1);
            return nextRequestId();
        }
        return requestId;
    }

    protected abstract T createMessage(Message message);

    protected abstract T createMessage(Message message, int requestId);


    protected int requestId() {
        return REQUEST_ID.get();
    }
}
