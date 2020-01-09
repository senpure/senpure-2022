package com.senpure.io.server.consumer;

import com.senpure.base.AppEvn;
import com.senpure.base.util.Assert;
import com.senpure.io.server.ChannelAttributeUtil;
import com.senpure.io.server.ServerProperties;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class ConsumerServer {
    protected static Logger logger = LoggerFactory.getLogger(ConsumerServer.class);
    private ServerProperties.Consumer properties;

    private ChannelFuture channelFuture;
    private String serverName = "consumerServer";
    private String readableServerName = "consumerServer";
    private boolean setReadableServerName = false;
    private ConsumerMessageExecutor messageExecutor;

    private Channel channel;
    private RemoteServerManager remoteServerManager;
    private boolean addLoggingHandler = true;

    private List<ChannelHandler> extHandlers = new ArrayList<>();

    private static EventLoopGroup group;
    private static Bootstrap bootstrap;
    private static final Object groupLock = new Object();

    private static int serverRefCont = 0;


    public final boolean start(String host, int port) {
        Assert.notNull(remoteServerManager);
        Assert.notNull(properties);
        Assert.notNull(messageExecutor);
        // Configure SSL.
        if (group == null || group.isShuttingDown() || group.isShutdown()) {
            synchronized (groupLock) {
                if (group == null || group.isShuttingDown() || group.isShutdown()) {
                    group = new NioEventLoopGroup(properties.getIoWorkThreadPoolSize());
                    SslContext sslCtx = null;
                    try {
                        if (properties.isSsl()) {
                            SelfSignedCertificate ssc = new SelfSignedCertificate();
                            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
                        }
                    } catch (Exception e) {
                        logger.error("使用ssl出错", e);
                    }
                    bootstrap = new Bootstrap();
                    SslContext finalSslCtx = sslCtx;
                    bootstrap.group(group)
                            .channel(NioSocketChannel.class)
                            .option(ChannelOption.TCP_NODELAY, true)
                            .handler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                public void initChannel(SocketChannel ch) {
                                    ChannelPipeline p = ch.pipeline();
                                    if (finalSslCtx != null) {
                                        p.addLast(finalSslCtx.newHandler(ch.alloc(), host, port));
                                    }
                                    p.addLast(new ConsumerMessageDecoder());
                                    p.addLast(new ConsumerMessageEncoder());
                                    if (addLoggingHandler) {
                                        p.addLast(new ConsumerLoggingHandler(LogLevel.DEBUG, properties.isInFormat(), properties.isOutFormat()));
                                    }
                                    for (ChannelHandler extHandler : extHandlers) {
                                        p.addLast(extHandler);
                                    }

                                    if (properties.isEnableHeartCheck()) {
                                        p.addLast(new IdleStateHandler(0, properties.getWriterIdleTime(), 0, TimeUnit.MILLISECONDS));
                                    }
                                    p.addLast(new ConsumerServerHandler(messageExecutor, remoteServerManager));
                                }
                            });

                }
            }
        }
        // Start the client.
        try {
            logger.debug("启动{}，远程服务器地址 {}", properties.getReadableName(), host + ":" + port);
            readableServerName = properties.getReadableName() + "->[" + host + ":" + port + "]";
            channelFuture = bootstrap.connect(host, port).sync();
            channel = channelFuture.channel();
            synchronized (groupLock) {
                serverRefCont++;
            }
            String remoteServerKey = remoteServerManager.getRemoteServerKey(host, port);
            InetSocketAddress address = (InetSocketAddress) channel.localAddress();
            String path;
            if (AppEvn.classInJar(AppEvn.getStartClass())) {
                path = AppEvn.getClassPath(AppEvn.getStartClass());
            } else {
                path = AppEvn.getClassRootPath();
            }
            String serverKey = address.getAddress().getHostAddress() + "->" + path;

            ChannelAttributeUtil.setRemoteServerKey(channel, remoteServerKey);
            ChannelAttributeUtil.setLocalServerKey(channel, serverKey);
            logger.info("{}启动完成 localServerKey {} address {}", getReadableServerName(), serverKey, address);
        } catch (Exception e) {
            logger.error("启动" + getReadableServerName() + " 失败", e);
            destroy();
            return false;
        }
        return true;

    }


    public void setProperties(ServerProperties.Consumer properties) {
        this.properties = properties;
    }

    public Channel getChannel() {
        return channel;
    }

    public String getReadableServerName() {
        return readableServerName;
    }

    public void destroy() {
        if (channelFuture != null) {
            channelFuture.channel().close();
            synchronized (groupLock) {
                serverRefCont--;
            }
        }
        logger.debug("关闭{}并释放资源 ", getReadableServerName());
        tryDestroyGroup(getReadableServerName());
    }

    private synchronized static void tryDestroyGroup(String readableServerName) {
        synchronized (groupLock) {
            if (serverRefCont == 0) {
                if (group != null && (!group.isShutdown() | !group.isShuttingDown())) {
                    logger.debug("{} 关闭 group 并释放资源 ", readableServerName);
                    group.shutdownGracefully();
                }
            }

        }


    }


    public void setRemoteServerManager(RemoteServerManager remoteServerManager) {
        this.remoteServerManager = remoteServerManager;
    }

    public String getServerName() {
        return serverName;
    }


    public void setMessageExecutor(ConsumerMessageExecutor messageExecutor) {
        this.messageExecutor = messageExecutor;
    }


    public void setServerName(String serverName) {
        this.serverName = serverName;
        if (!setReadableServerName) {
            readableServerName = serverName;
        }
    }


    public void setReadableServerName(String readableServerName) {
        this.readableServerName = readableServerName;
        setReadableServerName = true;
    }

    public void setAddLoggingHandler(boolean addLoggingHandler) {
        this.addLoggingHandler = addLoggingHandler;
    }

    public void addExtHandler(ChannelHandler extHandler) {
        extHandlers.add(extHandler);
    }

    public static void main(String[] args) {

        InetSocketAddress address = new InetSocketAddress(8111);

        System.out.println(address.getAddress().getCanonicalHostName());
    }
}
