package com.senpure.io.server.gateway;

import com.senpure.base.util.Assert;
import com.senpure.base.util.IDGenerator;
import com.senpure.executor.DefaultTaskLoopGroup;
import com.senpure.executor.TaskLoopGroup;
import com.senpure.io.protocol.Message;
import com.senpure.io.server.ChannelAttributeUtil;
import com.senpure.io.server.Constant;
import com.senpure.io.server.ServerProperties;
import com.senpure.io.server.gateway.consumer.handler.ConsumerMessageHandler;
import com.senpure.io.server.gateway.provider.Provider;
import com.senpure.io.server.gateway.provider.handler.ProviderMessageHandler;
import com.senpure.io.server.protocol.message.SCFrameworkErrorMessage;
import com.senpure.io.server.support.MessageIdReader;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;


public class GatewayMessageExecutor {
    protected static Logger logger = LoggerFactory.getLogger(GatewayMessageExecutor.class);


    private final TaskLoopGroup service;
    private int serviceRefCount = 0;
    private int csLoginMessageId = 0;

    private ServerProperties.Gateway gateway;
    private int scLoginMessageId = 0;

    public final ConcurrentMap<Long, Channel> prepLoginChannels = new ConcurrentHashMap<>(2048);

    public final ConcurrentMap<Long, Channel> userClientChannel = new ConcurrentHashMap<>(32768);
    public final ConcurrentMap<Long, Channel> tokenChannel = new ConcurrentHashMap<>(32768);
    public final ConcurrentMap<String, ProviderManager> providerManagerMap = new ConcurrentHashMap<>(128);

    public ConcurrentMap<Integer, ProviderManager> messageHandleMap = new ConcurrentHashMap<>(2048);
    public ConcurrentMap<Integer, HandleMessageManager> handleMessageManagerMap = new ConcurrentHashMap<>(2048);


    protected IDGenerator idGenerator;
    public final ConcurrentHashMap<Long, WaitRelationTask> waitRelationMap = new ConcurrentHashMap<>(16);
    public final ConcurrentHashMap<Long, WaitAskTask> waitAskMap = new ConcurrentHashMap<>(16);

    private final Map<Integer, ProviderMessageHandler> p2gHandlerMap = new HashMap<>();
    private final Map<Integer, ConsumerMessageHandler> c2gHandlerMap = new HashMap<>();
    private boolean init = false;

    public GatewayMessageExecutor() {
        this(new DefaultTaskLoopGroup(Runtime.getRuntime().availableProcessors() * 2,
                new DefaultThreadFactory("gateway-executor")), new IDGenerator(0, 0));
    }

    public GatewayMessageExecutor(TaskLoopGroup service, IDGenerator idGenerator) {
        this.service = service;
        this.idGenerator = idGenerator;
        // init();
        // startCheck();

    }


    public void registerProviderMessageHandler(ProviderMessageHandler handler) {
        ProviderMessageHandler old = p2gHandlerMap.get(handler.handleMessageId());
        if (old != null) {
            Assert.error(handler.handleMessageId() + " -> " + MessageIdReader.read(handler.handleMessageId()) + "  处理程序已经存在"
                    + " 存在 " + old.getClass().getName() + " 注册 " + handler.getClass().getName());
        }
        p2gHandlerMap.put(handler.handleMessageId(), handler);
    }

    public void registerConsumerMessageHandler(ConsumerMessageHandler handler) {
        ConsumerMessageHandler old = c2gHandlerMap.get(handler.messageId());
        if (old != null) {
            Assert.error(handler.messageId() + " -> " + MessageIdReader.read(handler.messageId()) + "  处理程序已经存在"
                    + " 存在 " + old.getClass().getName() + " 注册 " + handler.getClass().getName());
        }
        c2gHandlerMap.put(handler.messageId(), handler);
    }

    /**
     * 引用计数+1
     */
    public void retainService() {
        serviceRefCount++;
    }

    public void releaseService() {
        serviceRefCount--;

    }

    public void releaseAndTryShutdownService() {
        serviceRefCount--;
        if (serviceRefCount <= 0) {
            service.shutdownGracefully();
        }
    }

    public void shutdownService() {
        if (serviceRefCount <= 0) {
            service.shutdownGracefully();
        } else {
            logger.warn("service 持有引用{}，请先释放后关闭", serviceRefCount);
        }
    }


    public void report() {
        logger.info("csLoginMessageId: {} scLoginMessageId:{} ", csLoginMessageId, scLoginMessageId);
    }

    public void channelActive(Channel channel) {
        Long token = idGenerator.nextId();
        ChannelAttributeUtil.setToken(channel, token);
        tokenChannel.putIfAbsent(token, channel);
        logger.debug("{} 绑定 token {}", channel, token);
    }

    //将客户端消息转发给具体的服务器
    public void execute(final Channel channel, final GatewayReceiveConsumerMessage message) {
//        long token = ChannelAttributeUtil.getToken(channel);
//        message.setToken(token);
//        Long userId = ChannelAttributeUtil.getUserId(channel);
//        if (userId != null) {
//            message.setUserId(userId);
//        }
        long token = message.token();
        service.get(token).execute(() -> {
            try {
                logger.debug("new gateway=============");
                ConsumerMessageHandler handler = c2gHandlerMap.get(message.messageId());
                if (handler != null) {
                    handler.execute(channel, message);
                    if (handler.stopForward()) {
                        return;
                    }
                }
                //转发到具体的子服务器
                HandleMessageManager handleMessageManager = handleMessageManagerMap.get(message.messageId());
                if (handleMessageManager == null) {
                    logger.warn("没有找到消息的接收服务器{}", message.messageId());
                    SCFrameworkErrorMessage errorMessage = new SCFrameworkErrorMessage();

                    errorMessage.setCode(Constant.ERROR_NOT_FOUND_SERVER);
                    errorMessage.getArgs().add(String.valueOf(message.messageId()));
                    errorMessage.setMessage("没有服务器处理" + MessageIdReader.read(message.messageId()));
                    sendMessage2Consumer(message.requestId(), message.token(), errorMessage);
                    return;
                }

                handleMessageManager.execute(message);
            } catch (Exception e) {
                logger.error("转发消息出错 " + message.messageId(), e);
                SCFrameworkErrorMessage errorMessage = new SCFrameworkErrorMessage();

                errorMessage.setCode(Constant.ERROR_SERVER_ERROR);
                errorMessage.getArgs().add(String.valueOf(message.messageId()));
                errorMessage.setMessage(MessageIdReader.read(message.messageId()) + "," + e.getMessage());
                sendMessage2Consumer(message.requestId(), message.token(), errorMessage);
            }
        });
    }

    public void sendMessage2Consumer(int requestId, Long token, Message message) {
        Channel consumerChannel = tokenChannel.get(token);
        if (consumerChannel == null) {
            logger.warn("没有找到channel token {}", token);
        } else {
            GatewayReceiveProviderMessage m = new GatewayReceiveProviderMessage();
            m.setRequestId(requestId);
            ByteBuf buf = Unpooled.buffer(message.serializedSize());
            message.write(buf);
            byte[] data = new byte[message.serializedSize()];
            buf.readBytes(data);
            m.setToken(token);
            m.setData(data);
            m.setMessageId(message.messageId());
            if (consumerChannel.isWritable()) {
                consumerChannel.writeAndFlush(m);
            }

        }
    }

    public void sendMessage2Consumer(Long token, int messageId, byte[] data) {
        Channel consumerChannel  = tokenChannel.get(token);
        if (consumerChannel  == null) {
            logger.warn("没有找到channel token {}", token);
        } else {
            GatewayReceiveProviderMessage m = new GatewayReceiveProviderMessage();
            m.setRequestId(0);
            m.setToken(token);
            m.setData(data);
            m.setMessageId(messageId);
            if (consumerChannel .isWritable()) {
                consumerChannel .writeAndFlush(m);
            }

        }
    }


    public void sendMessage2Producer(Channel channel, Message message) {
        GatewaySendProviderMessage toMessage = createMessage(message);
        if (channel.isWritable()) {
            channel.writeAndFlush(toMessage);
        }


    }
    public void init() {
        if (init) {
            logger.warn("messageExecutor 已经初始化");
            return;
        }
        Assert.notNull(gateway, "gateway 配置文件不能为空");
        init = true;
        Assert.isTrue(csLoginMessageId > 0 && scLoginMessageId > 0, "登录消息未设置");
        startCheck();
    }

    private  void executeSCMessage()
    {

    }
    //处理服务器发过来的消息
    public void execute(Channel channel, final GatewayReceiveProviderMessage message) {
        long token = message.getToken();
        service.get(token).execute(() -> {
            try {
                ProviderMessageHandler handler = p2gHandlerMap.get(message.getMessageId());
                if (handler != null) {
                    handler.execute(channel, message);
                    if (handler.stopResponse()) {
                        return;
                    }
                }
                if (message.getUserIds().length == 0) {
                    Channel consumerChannel = tokenChannel.get(token);
                    if (consumerChannel == null) {
                        logger.warn("没有找到channel token:{}", token);
                    } else {
                        if (consumerChannel.isWritable()) {
                            consumerChannel.writeAndFlush(message);
                        }

                    }
                } else {
                    for (Long userId : message.getUserIds()) {
                        //全消息
                        if (userId == 0L) {
                            for (Map.Entry<Long, Channel> entry : userClientChannel.entrySet()) {
                                Channel consumerChannel = entry.getValue();
                                if (consumerChannel.isWritable()) {
                                    consumerChannel.writeAndFlush(message);
                                }
                            }
                            break;
                        } else {
                            Channel consumerChannel = userClientChannel.get(userId);
                            if (consumerChannel == null) {
                                logger.warn("没有找到用户 :{}", userId);
                            } else {
                                if (consumerChannel.isWritable()) {
                                    consumerChannel.writeAndFlush(message);
                                }

                            }
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("处理服务器到网关的消息出错", e);
            }
        });
    }

    public void execute(Runnable runnable) {
        service.execute(runnable);
    }


    private void consumerOffline(Channel channel, Long token, Long userId) {
        for (Map.Entry<String, ProviderManager> entry : providerManagerMap.entrySet()) {
            ProviderManager providerManager = entry.getValue();
            providerManager.consumerOffline(channel, token, userId);

        }
    }

    /**
     * 登陆认证服务已经有了该账户的关联，不能取消token关联
     *
     * @param channel
     * @param token
     * @param userId
     */
    public void consumerUserChange(Channel channel, Long token, Long userId) {
        for (Map.Entry<String, ProviderManager> entry : providerManagerMap.entrySet()) {
            ProviderManager providerManager = entry.getValue();
            providerManager.consumerUserChange(channel, token, userId, csLoginMessageId);
        }
    }


    /**
     * 消费方离线
     *
     * @param channel
     */
    public void consumerOffline(Channel channel) {
        service.execute(() -> {
            Long token = ChannelAttributeUtil.getToken(channel);
            Long userId = ChannelAttributeUtil.getUserId(channel);
            userId = userId == null ? 0 : userId;
            tokenChannel.remove(token);
            consumerOffline(channel, token, userId);
        });
    }

    public void readMessage(Message message, GatewayReceiveProviderMessage gatewayReceiveProviderMessage) {
        ByteBuf buf = Unpooled.buffer(gatewayReceiveProviderMessage.getData().length);
        buf.writeBytes(gatewayReceiveProviderMessage.getData());
        message.read(buf, buf.writerIndex());
    }
    public GatewayLocalSendProviderMessage createMessage(Message message) {

        return new GatewayLocalSendProviderMessage(message);
    }

    public GatewayLocalSendProviderMessage createMessage(long token,long userId,Message message) {
        GatewayLocalSendProviderMessage frame = new GatewayLocalSendProviderMessage(message);
        frame.setToken(token);
        frame.setUserId(userId);
        return frame;
    }


    public void sendMessage(Provider provider, Message message) {
        GatewaySendProviderMessage toMessage = createMessage(message);
        provider.sendMessage(toMessage);
    }





    private void checkWaitRelationTask() {
        List<Long> tokens = new ArrayList<>();
        for (Map.Entry<Long, WaitRelationTask> entry : waitRelationMap.entrySet()) {
            WaitRelationTask task = entry.getValue();
            if (task.check()) {
                tokens.add(entry.getKey());
                service.get(task.getToken()).execute(task::sendMessage);
            } else {
                if (task.cancel()) {
                    tokens.add(entry.getKey());
                    service.get(task.getToken()).execute(() -> task.sendCancelMessage(this));
                }
            }
        }
        for (Long token : tokens) {
            waitRelationMap.remove(token);
        }
    }

    private void checkWaitAskTask() {
        List<Long> tokens = new ArrayList<>();
        long now = System.currentTimeMillis();
        for (Map.Entry<Long, WaitAskTask> entry : waitAskMap.entrySet()) {
            WaitAskTask waitAskTask = entry.getValue();
            if (waitAskTask.getProvider() != null) {
                tokens.add(entry.getKey());
                service.get(waitAskTask.getToken()).execute(waitAskTask::sendMessage);

            } else {
                //超时
                if (now - waitAskTask.getStartTime() > waitAskTask.getMaxDelay()) {
                    logger.debug("没有服务器处理 {} 值位{} 的请求 询问服务器 {} 应答服务器 {}",
                            MessageIdReader.read(waitAskTask.getFromMessageId()), waitAskTask.getValue(),
                            waitAskTask.getAskTimes(), waitAskTask.getAnswerTimes());
                    tokens.add(entry.getKey());
                    SCFrameworkErrorMessage errorMessage = new SCFrameworkErrorMessage();
                    errorMessage.setCode(Constant.ERROR_NOT_HANDLE_VALUE_REQUEST);
                    errorMessage.getArgs().add(String.valueOf(waitAskTask.getFromMessageId()));
                    errorMessage.setMessage(MessageIdReader.read(waitAskTask.getFromMessageId()));
                    errorMessage.getArgs().add(Arrays.toString(waitAskTask.getValue()));
                    sendMessage2Consumer(waitAskTask.getRequestId(), waitAskTask.getMessage().token(), errorMessage);
                }
            }
        }
        for (Long token : tokens) {
            waitAskMap.remove(token);
        }
    }

    private void startCheck() {
        service.scheduleWithFixedDelay(() -> {
            checkWaitRelationTask();
            checkWaitAskTask();
        }, 0, 10, TimeUnit.MILLISECONDS);
    }

    public void destroy() {
        service.shutdownGracefully();
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

    public ServerProperties.Gateway getGateway() {
        return gateway;
    }

    public void setGateway(ServerProperties.Gateway gateway) {
        this.gateway = gateway;
    }


    public static void main(String[] args) {

    }
}
