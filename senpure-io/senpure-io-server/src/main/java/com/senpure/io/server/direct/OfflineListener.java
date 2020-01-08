package com.senpure.io.server.direct;

import io.netty.channel.Channel;

/**
 * OfflineListener
 *
 * @author senpure
 * @time 2019-09-18 09:50:31
 */
public interface OfflineListener {

    void  execute(Channel channel);
    String getName();
}
