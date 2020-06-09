package com.senpure.io.server.provider;

import io.netty.util.concurrent.FastThreadLocalThread;

/**
 * ProducerHelper
 *
 * @author senpure
 * @time 2019-12-30 17:40:10
 */
public class ProviderHelper {

    /**
     * 包装一个可传递requestId的 Runnable 释放该 requestId 依赖 FastThreadLocalThread 的包装
     *
     * @param runnable
     * @return
     * @see FastThreadLocalThread
     */
    public static Runnable getRequestIdRunnable(Runnable runnable) {

        return new RequestIdRunnable(runnable);
    }

    private static class RequestIdRunnable implements Runnable {

        private Runnable runnable;

        private Integer requestId;

        public RequestIdRunnable(Runnable runnable) {
            this.runnable = runnable;
            requestId = GatewayManager.getRequestId();
        }

        @Override
        public void run() {
            GatewayManager.setRequestId(requestId);
            try {
                runnable.run();
            } finally {
                GatewayManager.clearRequestId();
            }

        }
    }
}
