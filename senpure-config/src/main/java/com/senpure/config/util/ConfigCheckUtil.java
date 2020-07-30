package com.senpure.config.util;

import com.senpure.config.configure.ConfigProperties;

/**
 * ConfigCheckUtil
 *
 * @author senpure
 * @time 2020-07-29 18:54:08
 */
public class ConfigCheckUtil {
    private static final ConfigProperties defaultConfig = new ConfigProperties();

    public static void check(ConfigProperties config) {
        if (config.getExcelPath() == null || config.getExcelPath().trim().length() == 0) {
            config.setExcelPath(defaultConfig.getExcelPath());
        }
        if (config.getJavaFolder() == null || config.getJavaFolder().trim().length() == 0) {
            config.setJavaFolder(defaultConfig.getJavaFolder());
        }

        if (config.getJavaManagerSuffix() == null || config.getJavaManagerSuffix().trim().length() == 0) {
            config.setJavaManagerSuffix(defaultConfig.getJavaManagerSuffix());
        }
        if (config.getLuaFolder() == null || config.getJavaFolder().trim().length() == 0) {
            config.setLuaFolder(defaultConfig.getLuaFolder());
        }
    }
}
