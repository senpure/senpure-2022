package com.senpure.io.server.consumer.remoting;

/**
 * ResponseFuture
 *
 * @author senpure
 * @time 2019-06-27 20:05:38
 */
public interface ResponseFuture {
    Response get() throws Exception;

    Response get(int timeout) throws Exception;

    void setCallback(ResponseCallback callback);

    boolean isDone();
}
