package com.senpure.base.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BaseAutoConfiguration
 *
 * @author senpure
 * @time 2020-05-13 14:33:38
 */
public class BaseAutoConfiguration {
    protected Logger logger;
    public BaseAutoConfiguration() {
        logger = LoggerFactory.getLogger(getClass());
    }
}
