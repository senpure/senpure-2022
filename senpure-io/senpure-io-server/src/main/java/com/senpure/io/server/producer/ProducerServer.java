package com.senpure.io.server.producer;

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
import java.util.concurrent.TimeUnit;


public class ProducerServer {
    protected static Logger logger = LoggerFactory.getLogger(ProducerServer.class);
    private ServerProperties.Producer properties;

    private ChannelFuture channelFuture;
    private String serverName = "producerServer";
    private String readableServerName = "producerServer";
    private boolean setReadableServerName = false;
    private ProducerMessageExecutor messageExecutor;
    private int httpPort = 0;
    private boolean addLoggingHandler = true;
    private Channel channel;
    private GatewayManager gatewayManager;


    private static EventLoopGroup group;
    private static Bootstrap bootstrap;
    private static final Object groupLock = new Object();

    private static int serverRefCont = 0;
    private static int firstPort;

    public final boolean start(String host, int port) {
        Assert.notNull(gatewayManager);
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
                                    p.addLast(new ProducerMessageDecoder());
                                    p.addLast(new ProducerMessageEncoder());
                                    if (addLoggingHandler) {
                                        p.addLast(new ProducerLoggingHandler(LogLevel.DEBUG, properties.isInFormat(), properties.isOutFormat()));
                                    }
                                    if (properties.isEnableHeartCheck()) {
                                        p.addLast(new IdleStateHandler(0, properties.getWriterIdleTime(), 0, TimeUnit.MILLISECONDS));
                                    }
                                    p.addLast(new ProducerServerHandler(messageExecutor, gatewayManager));
                                }
                            });

                }
            }
        }
        // Start the client.
        try {
            logger.debug("启动{}，网关地址 {}", properties.getReadableName(), host + ":" + port);
            readableServerName = properties.getReadableName() + "->[" + host + ":" + port + "]";
            channelFuture = bootstrap.connect(host, port).sync();
            channel = channelFuture.channel();
            synchronized (groupLock) {
                serverRefCont++;
            }
            InetSocketAddress address = (InetSocketAddress) channel.localAddress();

            int localPort = address.getPort();
            markFirstPort(localPort);

            String gatewayKey = gatewayManager.getGatewayKey(host, port);
//            String path;
//            if (AppEvn.classInJar(AppEvn.getStartClass())) {
//                path = AppEvn.getClassPath(AppEvn.getStartClass());
//            } else {
//                path = AppEvn.getClassRootPath();
//            }
            String serverKey = serverName + " " + address.getAddress().getHostAddress() + ":" + (httpPort > 0 ? httpPort : firstPort);
            ChannelAttributeUtil.setRemoteServerKey(channel, gatewayKey);
            ChannelAttributeUtil.setLocalServerKey(channel, serverKey);
            logger.info("{}启动完成 localServerKey {} address {}", getReadableServerName(), serverKey, address);
        } catch (Exception e) {
            logger.error("启动" + getReadableServerName() + " 失败", e);
            destroy();
            return false;
        }
        return true;

    }


    public void setProperties(ServerProperties.Producer properties) {
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

    public boolean isAddLoggingHandler() {
        return addLoggingHandler;
    }

    public void setAddLoggingHandler(boolean addLoggingHandler) {
        this.addLoggingHandler = addLoggingHandler;
    }

    public void setGatewayManager(GatewayManager gatewayManager) {
        this.gatewayManager = gatewayManager;
    }


    public String getServerName() {
        return serverName;
    }


    public void setMessageExecutor(ProducerMessageExecutor messageExecutor) {
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

    public void setHttpPort(int httpPort) {

        this.httpPort = httpPort;
    }

    private static synchronized void markFirstPort(int port) {
        if (firstPort > 0) {
            return;
        }
        firstPort = port;
    }

    public static void main(String[] args) {

        InetSocketAddress address = new InetSocketAddress(8111);

        System.out.println(address.getAddress().getCanonicalHostName());
    }
}
