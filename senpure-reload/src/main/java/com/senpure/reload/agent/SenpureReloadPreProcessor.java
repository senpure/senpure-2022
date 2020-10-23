package com.senpure.reload.agent;

import com.senpure.reload.ReloadContext;
import com.senpure.reload.ReloadableType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.security.CodeSource;
import java.security.ProtectionDomain;

/**
 * SenpureReloadPreProcessor
 *
 * @author senpure
 * @time 2020-10-19 12:17:59
 */
public class SenpureReloadPreProcessor {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    public byte[] preProcess(ClassLoader classLoader, String slashedClassName, ProtectionDomain protectionDomain,
                             byte[] bytes) {

        String className = slashedClassName.replace("/", ".");
        File watch = getWatchFile(protectionDomain, slashedClassName);
        if (watch != null) {
            ReloadableType reloadableType = new ReloadableType();
            reloadableType.setClassLoader(classLoader);
            reloadableType.setClassName(className);
            ReloadContext.getFileWatcher().watch(watch,reloadableType);
        }

      return bytes;
    }
    private  File  getWatchFile(ProtectionDomain protectionDomain,String slashedClassName)
    {
        if (protectionDomain != null) {
            CodeSource codeSource = protectionDomain.getCodeSource();
            if (codeSource == null || codeSource.getLocation() == null) {
                logger.warn("null codeSource for {}",slashedClassName);
            }
            else {
                File file = new File(codeSource.getLocation().getFile());
                if (file.isDirectory()) {
                    file = new File(file, slashedClassName + ".class");
                }
                return  file;
            }
        }
        return null;
    }
}
