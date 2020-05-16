package com.senpure.base.generator.hibernate;

import java.util.HashMap;
import java.util.Map;

/**
 * HibernateSettings
 * 相关参数查看 org.hibernate.cfg.AvailableSettings
 *
 * @see org.hibernate.cfg.AvailableSettings
 * @time 2020-05-14 15:00:37
 */
public class HibernateSettings {

    private Map<String, String> settings = new HashMap<>();

    public Map<String, String> getSettings() {
        return settings;
    }

    public void setSettings(Map<String, String> settings) {
        this.settings = settings;
    }
}
