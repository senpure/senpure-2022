package com.senpure.reload;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * AttachAgentProperties
 *
 * @author senpure
 * @time 2020-08-31 10:29:06
 */
@ConfigurationProperties(prefix = "senpure.reload.springloaded")
public class ReloadProperties {
    /**
     * 开启默认的jar监听
     */
    private boolean defaultJars = true;
    /**
     * 监听的jar的名字 如 a.1.0.release.jar
     */
    private List<String> watchJars = new ArrayList<>();
    /**
     * springloaded 参数
     */
    private boolean verbose = true;
    /**
     * springloaded 参数
     */
    private boolean logging = true;

    public boolean isDefaultJars() {
        return defaultJars;
    }

    public void setDefaultJars(boolean defaultJars) {
        this.defaultJars = defaultJars;
    }

    public List<String> getWatchJars() {
        return watchJars;
    }

    public void setWatchJars(List<String> watchJars) {
        this.watchJars = watchJars;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public boolean isLogging() {
        return logging;
    }

    public void setLogging(boolean logging) {
        this.logging = logging;
    }
}
