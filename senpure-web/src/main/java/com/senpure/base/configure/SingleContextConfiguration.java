package com.senpure.base.configure;

import com.senpure.base.spring.SpringContextChecker;

/**
 * SingleContextConfiguration
 *
 * @author senpure
 * @time 2019-01-29 10:50:22
 */
public class SingleContextConfiguration {


    public SingleContextConfiguration() {
        SpringContextChecker.ENABLE_MIX = false;
    }
}
