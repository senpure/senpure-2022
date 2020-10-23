package com.senpure.reload.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

/**
 * SenpureReloadAgent
 *
 * @author senpure
 * @time 2020-10-19 12:10:11
 */
public class SenpureReloadAgent {
    private static Instrumentation instrumentation;


    private static final ClassFileTransformer transformer = new ClassPreProcessorAgentAdapter();
    public static void premain(String options, Instrumentation inst) {
        if (instrumentation != null) {
            return;
        }
        instrumentation = inst;
        instrumentation.addTransformer(transformer);
    }

    public static void agentmain(String options, Instrumentation inst) {
        if (instrumentation != null) {
            return;
        }
        instrumentation = inst;
        instrumentation.addTransformer(transformer);
    }

    public static Instrumentation getInstrumentation() {
        return instrumentation;
    }
}
