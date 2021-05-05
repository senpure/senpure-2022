package com.senpure.io.server.remoting;

import com.senpure.io.protocol.Message;
import io.netty.channel.Channel;

public interface FutureService {

    ResponseFuture future(int timeout, Channel channel, int requestId, Message message);

    void received(int requestId);
}
