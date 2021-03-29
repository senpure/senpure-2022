package com.senpure.io.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ServerProperties
 *
 * @author senpure
 * @time 2020-01-06 11:26:30
 */
@ConfigurationProperties("server.io")
//@EnableConfigurationProperties
public class ServerProperties {
    /**
     * 该值不用配置读取spring.application.name
     */
    @Value("${spring.application.name:}")
    private String name;
    private Consumer consumer = new Consumer();
    private Direct direct = new Direct();
    private Gateway gateway = new Gateway();
    private Provider provider = new Provider();


    public static class Direct {

        private boolean setReadableName = false;
        /**
         * 服务器名
         */
        private String readableName = "directServer";

        /**
         * 开启事件
         */
        private boolean enableEvent = true;
        /**
         * 处理事件的线程数
         */
        private int eventThreadPoolSize = 0;
        /**
         * netty boosGroup 线程数
         */
        private int ioBossThreadPoolSize = 1;
        /**
         * netty workGroup 线程数
         */
        private int ioWorkThreadPoolSize = 0;
        /**
         * 逻辑处理线程数
         */
        private int executorThreadPoolSize = 0;
        /**
         * 监听端口号
         */
        private int port = 2222;

        /**
         * 输出格式化
         */
        private boolean outFormat = true;
        /**
         * 输入格式化
         */
        private boolean inFormat = true;
        /**
         * 开启ssl
         */
        private boolean ssl = false;
        /**
         * 是否开启心跳检查
         */
        private boolean enableHeartCheck = false;
        /**
         * 心跳读入间隔毫秒
         */
        private long readerIdleTime = 10000;


        public boolean isSetReadableName() {
            return setReadableName;
        }


        public String getReadableName() {
            return readableName;
        }

        public void setReadableName(String readableName) {
            this.readableName = readableName;
            setReadableName = true;
        }


        public boolean isEnableEvent() {
            return enableEvent;
        }

        public void setEnableEvent(boolean enableEvent) {
            this.enableEvent = enableEvent;
        }

        public int getEventThreadPoolSize() {
            return eventThreadPoolSize;
        }

        public void setEventThreadPoolSize(int eventThreadPoolSize) {
            this.eventThreadPoolSize = eventThreadPoolSize;
        }

        public int getIoWorkThreadPoolSize() {
            return ioWorkThreadPoolSize;
        }

        public void setIoWorkThreadPoolSize(int ioWorkThreadPoolSize) {
            this.ioWorkThreadPoolSize = ioWorkThreadPoolSize;
        }

        public int getExecutorThreadPoolSize() {
            return executorThreadPoolSize;
        }

        public void setExecutorThreadPoolSize(int executorThreadPoolSize) {
            this.executorThreadPoolSize = executorThreadPoolSize;
        }

        public boolean isOutFormat() {
            return outFormat;
        }

        public void setOutFormat(boolean outFormat) {
            this.outFormat = outFormat;
        }

        public boolean isInFormat() {
            return inFormat;
        }

        public void setInFormat(boolean inFormat) {
            this.inFormat = inFormat;
        }

        public boolean isSsl() {
            return ssl;
        }

        public void setSsl(boolean ssl) {
            this.ssl = ssl;
        }

        public boolean isEnableHeartCheck() {
            return enableHeartCheck;
        }

        public void setEnableHeartCheck(boolean enableHeartCheck) {
            this.enableHeartCheck = enableHeartCheck;
        }


        public long getReaderIdleTime() {
            return readerIdleTime;
        }

        public void setReaderIdleTime(long readerIdleTime) {
            this.readerIdleTime = readerIdleTime;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public int getIoBossThreadPoolSize() {
            return ioBossThreadPoolSize;
        }

        public void setIoBossThreadPoolSize(int ioBossThreadPoolSize) {
            this.ioBossThreadPoolSize = ioBossThreadPoolSize;
        }
    }

    public static class Consumer {


        /**
         * 连接的服务目标服务名
         */
        private String remoteName = "gateway";
        /**
         * 使用直连模式
         */
        private boolean direct;

        /**
         * 直连模式时 直接连接的端口号
         */
        private int remotePort = 2222;
        private String remoteHost = "127.0.0.1";
        private boolean setReadableName = false;
        /**
         * 自动连接服务
         */
        private boolean autoConnect = true;
        /**
         * 连接目标失败后下一次连接间隔毫秒(手动连接时自己处理)
         */
        private long connectFailInterval = 20000;
        /**
         * 与目标建立的channel 数量(手动连接时自己处理)
         */
        private int remoteChannel = 1;
        /**
         * 服务器名
         */
        private String readableName = "consumerServer";
        /**
         * 同步请求超时时间
         */
        private int requestTimeout = 500;
        /**
         * 开启事件
         */
        private boolean enableEvent = false;

        /**
         * 处理事件的线程数
         */
        private int eventThreadPoolSize = 0;
        /**
         * netty workGroup 线程数
         */
        private int ioWorkThreadPoolSize = 1;
        /**
         * 逻辑处理线程数
         */
        private int executorThreadPoolSize = 0;

        /**
         * 没有channel时下一个可用channel重发消息的时间限制
         */
        private int messageRetryTimeLimit = 10000;

        /**
         * 输出格式化
         */
        private boolean outFormat = true;
        /**
         * 输入格式化
         */
        private boolean inFormat = true;
        /**
         * 开启ssl
         */
        private boolean ssl = false;

        /**
         * 是否开启心跳检查
         */
        private boolean enableHeartCheck = false;
        /**
         * 心跳写入间隔毫秒
         */
        private long writerIdleTime = 5000;
        /**
         * 服务器返回错误消息id
         */
        private int scErrorMessageId = 1000500;

        public String getRemoteHost() {
            return remoteHost;
        }

        public void setRemoteHost(String remoteHost) {
            this.remoteHost = remoteHost;
        }

        public boolean isOutFormat() {
            return outFormat;
        }

        public void setOutFormat(boolean outFormat) {
            this.outFormat = outFormat;
        }

        public boolean isInFormat() {
            return inFormat;
        }

        public void setInFormat(boolean inFormat) {
            this.inFormat = inFormat;
        }

        public boolean isSsl() {
            return ssl;
        }

        public void setSsl(boolean ssl) {
            this.ssl = ssl;
        }

        public boolean isEnableHeartCheck() {
            return enableHeartCheck;
        }

        public void setEnableHeartCheck(boolean enableHeartCheck) {
            this.enableHeartCheck = enableHeartCheck;
        }

        public long getWriterIdleTime() {
            return writerIdleTime;
        }

        public void setWriterIdleTime(long writerIdleTime) {
            this.writerIdleTime = writerIdleTime;
        }

        public String getRemoteName() {
            return remoteName;
        }

        public void setRemoteName(String remoteName) {
            this.remoteName = remoteName;
        }

        public boolean isSetReadableName() {
            return setReadableName;
        }

        public void setSetReadableName(boolean setReadableName) {
            this.setReadableName = setReadableName;
        }

        public String getReadableName() {
            return readableName;
        }

        public void setReadableName(String readableName) {
            this.readableName = readableName;
        }

        public boolean isEnableEvent() {
            return enableEvent;
        }

        public void setEnableEvent(boolean enableEvent) {
            this.enableEvent = enableEvent;
        }

        public int getRequestTimeout() {
            return requestTimeout;
        }

        public void setRequestTimeout(int requestTimeout) {
            this.requestTimeout = requestTimeout;
        }

        public int getScErrorMessageId() {
            return scErrorMessageId;
        }

        public void setScErrorMessageId(int scErrorMessageId) {
            this.scErrorMessageId = scErrorMessageId;
        }

        public int getEventThreadPoolSize() {
            return eventThreadPoolSize;
        }

        public void setEventThreadPoolSize(int eventThreadPoolSize) {
            this.eventThreadPoolSize = eventThreadPoolSize;
        }

        public int getIoWorkThreadPoolSize() {
            return ioWorkThreadPoolSize;
        }

        public void setIoWorkThreadPoolSize(int ioWorkThreadPoolSize) {
            this.ioWorkThreadPoolSize = ioWorkThreadPoolSize;
        }

        public int getExecutorThreadPoolSize() {
            return executorThreadPoolSize;
        }

        public void setExecutorThreadPoolSize(int executorThreadPoolSize) {
            this.executorThreadPoolSize = executorThreadPoolSize;
        }

        public int getMessageRetryTimeLimit() {
            return messageRetryTimeLimit;
        }

        public void setMessageRetryTimeLimit(int messageRetryTimeLimit) {
            this.messageRetryTimeLimit = messageRetryTimeLimit;
        }

        public long getConnectFailInterval() {
            return connectFailInterval;
        }

        public void setConnectFailInterval(long connectFailInterval) {
            this.connectFailInterval = connectFailInterval;
        }

        public int getRemoteChannel() {
            return remoteChannel;
        }

        public void setRemoteChannel(int remoteChannel) {
            this.remoteChannel = remoteChannel;
        }

        public boolean isAutoConnect() {
            return autoConnect;
        }

        public void setAutoConnect(boolean autoConnect) {
            this.autoConnect = autoConnect;
        }

        public boolean isDirect() {
            return direct;
        }

        public void setDirect(boolean direct) {
            this.direct = direct;
        }

        public int getRemotePort() {
            return remotePort;
        }

        public void setRemotePort(int remotePort) {
            this.remotePort = remotePort;
        }
    }

    public static class Provider {
        public static enum MODEL {
            GATEWAY,
            DIRECT
        }

        /**
         * 网关服务名
         */
        private String gatewayName = "gateway";

        private MODEL model = MODEL.GATEWAY;

        private boolean setReadableName = false;

        private int port = 2222;
        /**
         * 服务器名
         */
        private String readableName = "realityServer";
        /**
         * 关联id与类名的包名多个用,逗号分割
         */
        private String idNamesPackage;
        /**
         * 开启事件
         */
        private boolean enableEvent = true;
        /**
         * 处理事件的线程数
         */
        private int eventThreadPoolSize = 0;
        /**
         * netty boosGroup 线程数
         */
        private int ioBossThreadPoolSize = 1;
        /**
         * netty workGroup 线程数
         */
        private int ioWorkThreadPoolSize = 0;
        /**
         * 逻辑处理线程数
         */
        private int executorThreadPoolSize = 0;
        /**
         * 连接网关失败后下一次连接间隔毫秒
         */
        private long connectFailInterval = 20000;
        /**
         * 与网关建立的channel 数量，多条channel在高并发下消息可能会有乱序问题
         */
        private int gatewayChannel = 1;
        /**
         * 没有channel时下一个可用channel重发消息的时间限制
         */
        private int messageRetryTimeLimit = 10000;

        /**
         * 输出格式化
         */
        private boolean outFormat = true;
        /**
         * 输入格式化
         */
        private boolean inFormat = true;
        /**
         * 开启ssl
         */
        private boolean ssl = false;
        /**
         * 是否开启心跳检查
         */
        private boolean enableHeartCheck = false;
        /**
         * 心跳写入间隔毫秒
         */
        private long writerIdleTime = 5000;
        private long readerIdleTime = 10000;

        public boolean isSetReadableName() {
            return setReadableName;
        }


        public String getReadableName() {
            return readableName;
        }

        public void setReadableName(String readableName) {
            this.readableName = readableName;
            setReadableName = true;
        }

        public int getIoBossThreadPoolSize() {
            return ioBossThreadPoolSize;
        }

        public void setIoBossThreadPoolSize(int ioBossThreadPoolSize) {
            this.ioBossThreadPoolSize = ioBossThreadPoolSize;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public long getReaderIdleTime() {
            return readerIdleTime;
        }

        public void setReaderIdleTime(long readerIdleTime) {
            this.readerIdleTime = readerIdleTime;
        }

        public int getExecutorThreadPoolSize() {
            return executorThreadPoolSize;
        }

        public void setExecutorThreadPoolSize(int executorThreadPoolSize) {
            this.executorThreadPoolSize = executorThreadPoolSize;
        }

        public long getConnectFailInterval() {
            return connectFailInterval;
        }

        public void setConnectFailInterval(long connectFailInterval) {
            this.connectFailInterval = connectFailInterval;
        }

        public int getIoWorkThreadPoolSize() {
            return ioWorkThreadPoolSize;
        }

        public void setIoWorkThreadPoolSize(int ioWorkThreadPoolSize) {
            this.ioWorkThreadPoolSize = ioWorkThreadPoolSize;
        }

        public boolean isEnableHeartCheck() {
            return enableHeartCheck;
        }

        public void setEnableHeartCheck(boolean enableHeartCheck) {
            this.enableHeartCheck = enableHeartCheck;
        }

        public long getWriterIdleTime() {
            return writerIdleTime;
        }

        public void setWriterIdleTime(long writerIdleTime) {
            this.writerIdleTime = writerIdleTime;
        }

        public String getGatewayName() {
            return gatewayName;
        }

        public void setGatewayName(String gatewayName) {
            this.gatewayName = gatewayName;
        }

        public boolean isEnableEvent() {
            return enableEvent;
        }

        public void setEnableEvent(boolean enableEvent) {
            this.enableEvent = enableEvent;
        }

        public int getEventThreadPoolSize() {
            return eventThreadPoolSize;
        }

        public void setEventThreadPoolSize(int eventThreadPoolSize) {
            this.eventThreadPoolSize = eventThreadPoolSize;
        }

        public int getGatewayChannel() {
            return gatewayChannel;
        }

        public void setGatewayChannel(int gatewayChannel) {
            this.gatewayChannel = gatewayChannel;
        }

        public boolean isOutFormat() {
            return outFormat;
        }

        public void setOutFormat(boolean outFormat) {
            this.outFormat = outFormat;
        }

        public boolean isInFormat() {
            return inFormat;
        }

        public void setInFormat(boolean inFormat) {
            this.inFormat = inFormat;
        }

        public boolean isSsl() {
            return ssl;
        }

        public void setSsl(boolean ssl) {
            this.ssl = ssl;
        }

        public String getIdNamesPackage() {
            return idNamesPackage;
        }

        public void setIdNamesPackage(String idNamesPackage) {
            this.idNamesPackage = idNamesPackage;
        }

        public int getMessageRetryTimeLimit() {
            return messageRetryTimeLimit;
        }

        public void setMessageRetryTimeLimit(int messageRetryTimeLimit) {
            this.messageRetryTimeLimit = messageRetryTimeLimit;
        }

        public MODEL getModel() {
            return model;
        }

        public void setModel(MODEL model) {
            this.model = model;
        }
    }

    public static class Gateway {

        private boolean setReadableName = false;
        /**
         * 服务名不是唯一
         */
        private String readableName = "gateway";
        /**
         * 工作线程数量
         */
        private int executorThreadPoolSize = 0;
        /**
         * cs模块 netty boosGroup 线程数
         */
        private int ioCsBossThreadPoolSize = 1;
        /**
         * sc模块 netty boosGroup 线程数
         */
        private int ioScBossThreadPoolSize = 1;
        /**
         * cs模块 netty workGroup 线程数
         */
        private int ioCsWorkThreadPoolSize = 0;
        /**
         * sc模块 netty workGroup 线程数
         */
        private int ioScWorkThreadPoolSize = 0;
        /**
         * cs 是否开启ssl
         */
        private boolean csSsl = false;
        /**
         * sc 是否开启ssl
         */
        private boolean scSsl = false;
        /**
         * cs 模块端口号
         */
        private int csPort = 2222;
        /**
         * sc 模块端口号
         */
        private int scPort = 3333;

        /**
         * 客户端登录消息id
         */
        private int csLoginMessageId = 1000101;
        /**
         * 服务器登录成功返回消息id
         */
        private int scLoginMessageId = 1000102;
        /**
         * 是否开启cs 模块的心跳检查
         */
        private boolean enableCSHeartCheck = true;
        /**
         * cs模块的的读心跳时间毫秒
         */
        private long csReaderIdleTime = 10000;
        /**
         * 是否开启sc 模块的心跳检查
         */
        private boolean enableSCHeartCheck = false;
        /**
         * sc模块的的心跳读时间毫秒
         */
        private long scReaderIdleTime = 10000;
        /**
         * 雪花算法的服务名
         */
        private String snowflakeDispatcherName = "dispatcher";
        /**
         * 没有找到雪花服务直接使用配置的dataCenterId与workId
         */
        private boolean notFoundSnowflakeUseCode=false;
        /**
         * 是否直接是配置文件中的雪花算法的dataCenterId与workId
         */
        private boolean snowflakeUseCode = false;
        /**
         * 雪花算法 dataCenterId
         */
        private int snowflakeDataCenterId = 0;
        /**
         * 雪花算法 workId
         */
        private int snowflakeWorkId = 0;

        /**
         * 询问处理最多延迟毫秒
         */
        private long askMaxDelay = 1000;

        public int getCsPort() {
            return csPort;
        }

        public void setCsPort(int csPort) {
            this.csPort = csPort;
        }

        public int getScPort() {
            return scPort;
        }

        public void setScPort(int scPort) {
            this.scPort = scPort;
        }


        public int getCsLoginMessageId() {
            return csLoginMessageId;
        }

        public void setCsLoginMessageId(int csLoginMessageId) {
            this.csLoginMessageId = csLoginMessageId;
        }

        public int getScLoginMessageId() {
            return scLoginMessageId;
        }

        public void setScLoginMessageId(int scLoginMessageId) {
            this.scLoginMessageId = scLoginMessageId;
        }


        public long getAskMaxDelay() {
            return askMaxDelay;
        }

        public void setAskMaxDelay(long askMaxDelay) {
            this.askMaxDelay = askMaxDelay;
        }

        public int getExecutorThreadPoolSize() {
            return executorThreadPoolSize;
        }

        public void setExecutorThreadPoolSize(int executorThreadPoolSize) {
            this.executorThreadPoolSize = executorThreadPoolSize;
        }

        public String getSnowflakeDispatcherName() {
            return snowflakeDispatcherName;
        }

        public boolean isSnowflakeUseCode() {
            return snowflakeUseCode;
        }

        public void setSnowflakeUseCode(boolean snowflakeUseCode) {
            this.snowflakeUseCode = snowflakeUseCode;
        }

        public int getSnowflakeDataCenterId() {
            return snowflakeDataCenterId;
        }

        public void setSnowflakeDataCenterId(int snowflakeDataCenterId) {
            this.snowflakeDataCenterId = snowflakeDataCenterId;
        }

        public int getSnowflakeWorkId() {
            return snowflakeWorkId;
        }

        public void setSnowflakeWorkId(int snowflakeWorkId) {
            this.snowflakeWorkId = snowflakeWorkId;
        }

        public void setSnowflakeDispatcherName(String snowflakeDispatcherName) {
            this.snowflakeDispatcherName = snowflakeDispatcherName;
        }

        public boolean isSetReadableName() {
            return setReadableName;
        }

        public String getReadableName() {
            return readableName;
        }

        public void setReadableName(String readableName) {
            this.readableName = readableName;
            setReadableName = true;
        }

        public boolean isCsSsl() {
            return csSsl;
        }

        public void setCsSsl(boolean csSsl) {
            this.csSsl = csSsl;
        }

        public boolean isScSsl() {
            return scSsl;
        }

        public void setScSsl(boolean scSsl) {
            this.scSsl = scSsl;
        }

        public int getIoCsBossThreadPoolSize() {
            return ioCsBossThreadPoolSize;
        }

        public void setIoCsBossThreadPoolSize(int ioCsBossThreadPoolSize) {
            this.ioCsBossThreadPoolSize = ioCsBossThreadPoolSize;
        }

        public int getIoCsWorkThreadPoolSize() {
            return ioCsWorkThreadPoolSize;
        }

        public void setIoCsWorkThreadPoolSize(int ioCsWorkThreadPoolSize) {
            this.ioCsWorkThreadPoolSize = ioCsWorkThreadPoolSize;
        }

        public int getIoScBossThreadPoolSize() {
            return ioScBossThreadPoolSize;
        }

        public void setIoScBossThreadPoolSize(int ioScBossThreadPoolSize) {
            this.ioScBossThreadPoolSize = ioScBossThreadPoolSize;
        }

        public int getIoScWorkThreadPoolSize() {
            return ioScWorkThreadPoolSize;
        }

        public void setIoScWorkThreadPoolSize(int ioScWorkThreadPoolSize) {
            this.ioScWorkThreadPoolSize = ioScWorkThreadPoolSize;
        }

        public long getCsReaderIdleTime() {
            return csReaderIdleTime;
        }

        public void setCsReaderIdleTime(long csReaderIdleTime) {
            this.csReaderIdleTime = csReaderIdleTime;
        }

        public long getScReaderIdleTime() {
            return scReaderIdleTime;
        }

        public void setScReaderIdleTime(long scReaderIdleTime) {
            this.scReaderIdleTime = scReaderIdleTime;
        }

        public boolean isEnableSCHeartCheck() {
            return enableSCHeartCheck;
        }

        public void setEnableSCHeartCheck(boolean enableSCHeartCheck) {
            this.enableSCHeartCheck = enableSCHeartCheck;
        }

        public boolean isEnableCSHeartCheck() {
            return enableCSHeartCheck;
        }

        public void setEnableCSHeartCheck(boolean enableCSHeartCheck) {
            this.enableCSHeartCheck = enableCSHeartCheck;
        }

        public boolean isNotFoundSnowflakeUseCode() {
            return notFoundSnowflakeUseCode;
        }

        public void setNotFoundSnowflakeUseCode(boolean notFoundSnowflakeUseCode) {
            this.notFoundSnowflakeUseCode = notFoundSnowflakeUseCode;
        }

        @Override
        public String toString() {
            return "Gateway{" +
                    ", readableName='" + readableName + '\'' +
                    ", executorThreadPoolSize=" + executorThreadPoolSize +
                    ", ioCsBossThreadPoolSize=" + ioCsBossThreadPoolSize +
                    ", ioScBossThreadPoolSize=" + ioScBossThreadPoolSize +
                    ", ioCsWorkThreadPoolSize=" + ioCsWorkThreadPoolSize +
                    ", ioScWorkThreadPoolSize=" + ioScWorkThreadPoolSize +
                    ", csSsl=" + csSsl +
                    ", ScSsl=" + scSsl +
                    ", csPort=" + csPort +
                    ", scPort=" + scPort +
                    ", csLoginMessageId=" + csLoginMessageId +
                    ", scLoginMessageId=" + scLoginMessageId +
                    '}';
        }
    }


    public Gateway getGateway() {
        return gateway;
    }

    public void setGateway(Gateway gateway) {
        this.gateway = gateway;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    public Direct getDirect() {
        return direct;
    }

    public void setDirect(Direct direct) {
        this.direct = direct;
    }
}
