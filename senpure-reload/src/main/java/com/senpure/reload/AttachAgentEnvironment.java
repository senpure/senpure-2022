package com.senpure.reload;

/**
 * AttachAgentEnvironment
 *
 * @author senpure
 * @time 2020-08-28 15:53:12
 */
public class AttachAgentEnvironment {
    private boolean closeByteCodeVerify = true;

    private  boolean hasVirtualMachine;

    public boolean isCloseByteCodeVerify() {
        return closeByteCodeVerify;
    }

    public void setCloseByteCodeVerify(boolean closeByteCodeVerify) {
        this.closeByteCodeVerify = closeByteCodeVerify;
    }

    public boolean isHasVirtualMachine() {
        return hasVirtualMachine;
    }

    public void setHasVirtualMachine(boolean hasVirtualMachine) {
        this.hasVirtualMachine = hasVirtualMachine;
    }
}
