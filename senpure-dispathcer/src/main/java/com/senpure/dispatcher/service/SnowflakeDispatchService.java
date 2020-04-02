package com.senpure.dispatcher.service;

import com.senpure.dispatcher.model.ServerCenterAndWork;

/**
 * SnowflakeDispatchService
 *
 * @author senpure
 * @time 2019-03-12 10:04:00
 */
public interface SnowflakeDispatchService {

    ServerCenterAndWork dispatch(String serverName, String serverKey);
}
