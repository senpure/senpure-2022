package com.senpure.reload.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * ClassPreProcessorAgentAdapter
 *
 * @author senpure
 * @time 2020-10-19 12:22:48
 */
public class ClassPreProcessorAgentAdapter implements ClassFileTransformer {
    private static final SenpureReloadPreProcessor preProcessor;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    static {
        preProcessor = new SenpureReloadPreProcessor();
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] bytes) throws IllegalClassFormatException {

        if (className.startsWith("com/senpure/reload/cases")) {

        }
        logger.debug("classLoader {} className {} classBeingRedefined {}  bytes {}",
                loader, className, classBeingRedefined, bytes == null ? 0 : bytes.length);
        if (classBeingRedefined != null) {


            return bytes;
        } else {
            return preProcessor.preProcess(loader, className, protectionDomain, bytes);
        }
    }
}
