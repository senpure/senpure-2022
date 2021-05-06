package com.senpure.io.server.provider;

import com.senpure.executor.TaskLoopGroup;
import com.senpure.io.server.ServerProperties;
import com.senpure.io.server.remoting.AbstractRemoteServer;
import com.senpure.io.server.remoting.ChannelService;

public class Gateway extends AbstractRemoteServer {

    private final TaskLoopGroup service;

    public Gateway(String remoteKey, String remoteHost, String remotePort, ServerProperties.Provider provider,
                   TaskLoopGroup service) {
        this.service = service;
        setRemoteServerKey(remoteKey);
        setRemoteHost(remoteHost);
        setRemotePort(remotePort);
        if (provider.getGatewayChannel() == 1) {
            setChannelService(new ChannelService.SingleChannelService(remoteServerKey));

        } else {
            setChannelService(new ChannelService.MultipleChannelService(remoteServerKey));
        }


    }

    @Override
    public void checkWaitSendMessage() {
        synchronized (waitSendMessages) {
            if (waitSendMessages.size() > 0) {
                TaskLoopGroup executor = service;
                if (executor != null) {
                    executor.execute(this::sendWaitMessage);
                } else {
                    sendWaitMessage();
                }
            }
        }
    }
}
