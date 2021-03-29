package com.senpure.io.server.consumer.remoting;

import com.senpure.base.util.Spring;
import com.senpure.io.server.Constant;
import com.senpure.io.server.consumer.ConsumerMessage;
import com.senpure.io.server.consumer.ConsumerMessageExecutor;
import com.senpure.io.server.protocol.message.SCInnerErrorMessage;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * DefaultFuture
 *
 * @author senpure
 * @time 2019-06-27 20:13:58
 */
public class DefaultFuture implements ResponseFuture {

    private static final Logger logger = LoggerFactory.getLogger(DefaultFuture.class);
    private static final Map<Integer, DefaultFuture> FUTURES = new ConcurrentHashMap<>();


    private final int requestId;

    private final int messageId;

    private final int timeout;
    private final Channel channel;

    private final Lock lock = new ReentrantLock();
    private final Condition done = lock.newCondition();
    private final long start = System.currentTimeMillis();


    private volatile Response response;
    private volatile ResponseCallback callback;


    public DefaultFuture(ConsumerMessage frame, Channel channel, int timeout) {

        this.requestId = frame.getRequestId();
        this.messageId = frame.getMessage().messageId();
        this.timeout = timeout;
        this.channel = channel;
        FUTURES.put(requestId, this);
    }

    public static DefaultFuture received(int requestId) {

        return FUTURES.remove(requestId);
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
    public Response get() {
        return get(timeout);
    }


    @Override
    public Response get(int timeout) {
        if (timeout <= 0) {
            timeout = 500;
        }
        if (!isDone()) {
            long start = System.currentTimeMillis();
            lock.lock();
            try {
                while (!isDone()) {
                    done.await(timeout, TimeUnit.MILLISECONDS);
                    if (isDone() || System.currentTimeMillis() - start > timeout) {
                        break;
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
            if (!isDone()) {
                throw new RuntimeException("ResponseFuture get() 超时" + timeout);
            }
        }
        return response;

    }


    @Override
    public void setCallback(ResponseCallback callback) {
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


    static {
        //单独的线程检查超时,不受线程池调度影响
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    for (DefaultFuture future : FUTURES.values()) {
                        if (future == null || future.isDone()) {
                            continue;
                        }
                        if (System.currentTimeMillis() - future.getStartTime() > future.getTimeout()) {
                            SCInnerErrorMessage errorMessage = new SCInnerErrorMessage();
                            errorMessage.setCode(Constant.ERROR_TIMEOUT);
                            errorMessage.setMessage("同步请求超时["+future.messageId+"] :" + future.getTimeout());
                            errorMessage.getArgs().add(String.valueOf(future.getMessageId()));
                            ConsumerMessage frame = new ConsumerMessage();
                            frame.setRequestId(future.getRequestId());
                            frame.setMessage(errorMessage);
                            ConsumerMessageExecutor messageExecutor = Spring.getBean(ConsumerMessageExecutor.class);
                            if (messageExecutor != null) {
                                messageExecutor.execute(future.channel, frame);
                            } else {
                                logger.warn("没有从spring 容器中找到  ConsumerMessageExecutor");
                            }

                        }
                    }
                    Thread.sleep(30);
                } catch (Exception e) {
                    logger.error("远程消息返回 超时检查线程 出错", e);
                }
            }
        }, "ConsumerResponseTimeoutScanTimer");
        thread.setDaemon(true);
        thread.start();
    }


    @Override
    public boolean isDone() {
        return response != null;
    }


    private void invokeCallback(ResponseCallback callback) {

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

//        if (response.isSuccess()) {
//            try {
//                callback.success(response.getValue());
//            } catch (Exception e) {
//                logger.error("执行成功回调出错", e);
//            }
//        } else {
//            try {
//                callback.fail(response.getError());
//            } catch (Exception e) {
//                logger.error("执行失败回调出错", e);
//            }
    }


    public int getTimeout() {
        return timeout;
    }

    public long getStartTime() {
        return start;
    }

    public int getRequestId() {
        return requestId;
    }

    public int getMessageId() {
        return messageId;
    }
}
