package com.senpure.io.server.gateway;

import io.netty.channel.Channel;

/**
 * SCHandler
 *
 * @author senpure
 * @time 2020-06-10 14:13:53
 */
public interface SCHandler {
    /**
     * @param messageExecutor
     * @param channel
     * @param server2GatewayMessage
     * @return true 中断该消息
     */
    boolean execute(GatewayMessageExecutor messageExecutor, Channel channel, Server2GatewayMessage server2GatewayMessage);

    int handleMessageId();
}
