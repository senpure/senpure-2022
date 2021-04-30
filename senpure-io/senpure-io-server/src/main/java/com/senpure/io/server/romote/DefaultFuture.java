package com.senpure.io.server.romote;



import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DefaultFuture implements ResponseFuture {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final int requestId;

    // private final int messageId;

    private final int timeout;
    private final Channel channel;
    private final Lock lock = new ReentrantLock();
    private final Condition done = lock.newCondition();
    private final long start = System.currentTimeMillis();
    private Response response;
    private Callback callback;

    public DefaultFuture(int timeout, Channel channel, int requestId) {
        this.timeout = timeout;
        this.channel = channel;
        this.requestId = requestId;

    }

    public void doReceived(Response response) {
        lock.lock();
        try {
            this.response = response;
            done.signal();
        } finally {
            lock.unlock();
        }
        if (callback != null) {
            invokeCallback(callback);
        }
    }

    @Override
    public void setCallback(@Nonnull Callback callback) {
        if (isDone()) {
            invokeCallback(callback);
        } else {
            boolean isdone = false;
            lock.lock();
            try {
                if (!isDone()) {
                    this.callback = callback;
                } else {
                    isdone = true;
                }
            } finally {
                lock.unlock();
            }
            if (isdone) {
                invokeCallback(callback);
            }
        }
    }

    private void invokeCallback(Callback callback) {

        if (callback == null) {
            throw new NullPointerException("回调不能为空");
        }
        if (response == null) {
            throw new NullPointerException("结果不能为空");
        }

        try {
            callback.execute(response);
        } catch (Exception e) {
            logger.error("执行回调出错", e);
        }

    }

    @Nonnull
    @Override
    public Response get() {
        return null;
    }

    @Nonnull
    @Override
    public Response get(int timeout) {
        return null;
    }



    @Override
    public boolean isDone() {
        return false;
    }
}
