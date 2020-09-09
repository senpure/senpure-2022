package com.senpure.reload;

import java.lang.instrument.Instrumentation;

/**
 * AttachAgentEnvironment
 *
 * @author senpure
 * @time 2020-08-28 15:53:12
 */
public class ReloadEnvironment {
    private boolean closeByteCodeVerify = true;

    private Instrumentation instrumentation;

    public boolean isCloseByteCodeVerify() {
        return closeByteCodeVerify;
    }

    public void setCloseByteCodeVerify(boolean closeByteCodeVerify) {
        this.closeByteCodeVerify = closeByteCodeVerify;
    }


    public Instrumentation getInstrumentation() {
        return instrumentation;
    }

    public void setInstrumentation(Instrumentation instrumentation) {
        this.instrumentation = instrumentation;
    }
}
