package com.senpure.io.server.gateway.provider.handler;

import com.senpure.io.server.gateway.SCHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AbxSCHandler
 *
 * @author senpure
 * @time 2020-06-10 14:24:12
 */
public abstract class AbstractSCHandler implements SCHandler {
    protected Logger logger = LoggerFactory.getLogger(getClass());
}
