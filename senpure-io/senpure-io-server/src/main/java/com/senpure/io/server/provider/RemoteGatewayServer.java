package com.senpure.io.server.provider;

import com.senpure.executor.TaskLoopGroup;
import com.senpure.io.server.ServerProperties;
import com.senpure.io.server.remoting.AbstractRemoteServer;
import com.senpure.io.server.remoting.ChannelService;

public class RemoteGatewayServer extends AbstractRemoteServer {

    public RemoteGatewayServer(String remoteKey, String remoteHost, String remotePort, ServerProperties.Provider provider, TaskLoopGroup service) {

        setRemoteServerKey(remoteKey);
        setRemoteHost(remoteHost);
        setRemotePort(remotePort);
        if (provider.getGatewayChannel() == 1) {
            setChannelService(new ChannelService.SingleChannelService(remoteServerKey));

        }
    }
}
