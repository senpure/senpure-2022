package com.senpure.io.server.provider.gateway;

import com.senpure.executor.TaskLoopGroup;
import com.senpure.io.server.remoting.AbstractRemoteServer;

public class Gateway extends AbstractRemoteServer {

    private boolean frameworkVerifyProviderPassed;

    public Gateway(TaskLoopGroup service) {
        super(service);
    }

    public boolean isFrameworkVerifyProviderPassed() {
        return frameworkVerifyProviderPassed;
    }

    public void setFrameworkVerifyProviderPassed(boolean frameworkVerifyProviderPassed) {
        this.frameworkVerifyProviderPassed = frameworkVerifyProviderPassed;
    }
}
