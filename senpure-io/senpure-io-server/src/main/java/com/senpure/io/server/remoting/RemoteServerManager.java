package com.senpure.io.server.remoting;

import io.netty.util.concurrent.FastThreadLocal;

public interface RemoteServerManager {
    FastThreadLocal<Integer> REQUEST_ID = new FastThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };


}
