package com.senpure.io.server.direct;

/**
 * DirectHelper
 *
 * @author senpure
 * @time 2019-12-31 16:02:44
 */
public class DirectHelper {

    /**
     * 传递requestId的Runnable
     *
     * @param runnable
     * @return
     */
    public static Runnable WrapRequestIdRunnable(Runnable runnable) {
        return new RequestIdRunnable(runnable);
    }

    private static class RequestIdRunnable implements Runnable {

        private Runnable runnable;

        Integer requestId;

        public RequestIdRunnable(Runnable runnable) {
            this.runnable = runnable;
            requestId = ClientManager.getRequestId();
        }

        @Override
        public void run() {

            ClientManager.setRequestId(requestId);
            try {
                runnable.run();
            } finally {
                ClientManager.clearRequestId();
            }
        }
    }
}
