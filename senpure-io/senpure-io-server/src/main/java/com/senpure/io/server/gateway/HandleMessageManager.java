package com.senpure.io.server.gateway;

import com.senpure.base.util.Assert;
import com.senpure.io.protocol.CompressBean;
import com.senpure.io.server.Constant;
import com.senpure.io.server.gateway.provider.Provider;
import com.senpure.io.server.protocol.message.CSAskHandleMessage;
import com.senpure.io.server.protocol.message.SCInnerErrorMessage;
import com.senpure.io.server.support.MessageIdReader;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class HandleMessageManager {


    private Logger logger = LoggerFactory.getLogger(getClass());
    private boolean direct;
    private List<ProviderManager> providerManagers = new ArrayList<>();
    private ProviderManager providerManager;
    private GatewayMessageExecutor messageExecutor;
    private int csAskHandleMessageId =  CSAskHandleMessage.MESSAGE_ID;
    //   private AtomicInteger atomicIndex = new AtomicInteger(-1);
    private int handId;

    public HandleMessageManager(int handId, boolean direct, GatewayMessageExecutor messageExecutor) {
        this.direct = direct;
        this.messageExecutor = messageExecutor;
        this.handId = handId;
    }

    public synchronized void addProducerManager(int handId, ProviderManager providerManager) {
        if (this.handId != handId) {
            Assert.error("handId 不匹配");
        }
        boolean add = true;
        for (ProviderManager manager : providerManagers) {
            if (manager.getServerName().equalsIgnoreCase(providerManager.getServerName())) {
                add = false;
                break;
            }
        }
        if (add) {
            //不同的服务处理相同的id,容易编码疏忽,取消这种模式
            if (providerManagers.size() >= 1 && direct) {
                Assert.error("不同的服务处理了相同的非ask消息id,该模式容易编码疏忽,产出bug,强制不允许  id:"+ MessageIdReader.read(handId));
            }
            providerManagers.add(providerManager);
        }
        if (direct) {
            this.providerManager = providerManager;
        }
    }

    public void execute(Client2GatewayMessage message) {
        if (direct) {
            providerManager.sendMessage(message);
        } else {
            ByteBuf buf = Unpooled.buffer(message.getData().length);
            buf.writeBytes(message.getData());
            String value;
            try {
               CompressBean.readTag(buf, buf.writerIndex());
                value =CompressBean.readString(buf);
            } catch (Exception e) {
                logger.error("读取询问值出错询问值只能是string 类型 messageId " + message.getMessageId(), e);
                // Assert.error("读取询问值出错 询问值只能是string 类型 messageId  " + getValue.getMessageId());
                SCInnerErrorMessage errorMessage = new SCInnerErrorMessage();
                errorMessage.setCode(Constant.ERROR_SERVER_ERROR);
                errorMessage.getArgs().add(String.valueOf(message.getMessageId()));
                errorMessage.setMessage("询问值只能是String类型" + MessageIdReader.read(message.getMessageId()));
                messageExecutor.sendMessage2Client(message.getRequestId(),errorMessage, message.getToken());
                return;
            }
            CSAskHandleMessage askHandleMessage = new CSAskHandleMessage();
            askHandleMessage.setFromMessageId(message.getMessageId());
            askHandleMessage.setAskToken(messageExecutor.idGenerator.nextId());
            askHandleMessage.setAskValue(value);
            Client2GatewayMessage temp = new Client2GatewayMessage();
            temp.setMessageId(csAskHandleMessageId);
            temp.setToken(message.getToken());
            temp.setUserId(message.getUserId());
            buf = Unpooled.buffer();
            buf.ensureWritable(askHandleMessage.getSerializedSize());
            askHandleMessage.write(buf);
            byte[] data = new byte[askHandleMessage.getSerializedSize()];
            buf.readBytes(data);
            temp.setData(data);
            WaitAskTask waitAskTask = new WaitAskTask(messageExecutor.getGateway().getAskMaxDelay());
            waitAskTask.setAskToken(askHandleMessage.getAskToken());
            waitAskTask.setRequestId(message.getRequestId());
            waitAskTask.setFromMessageId(askHandleMessage.getFromMessageId());
            waitAskTask.setValue(value);

            int askTimes = 0;
            for (ProviderManager serverManager : providerManagers) {
                askTimes += serverManager.getUseProviders().size();
            }
            waitAskTask.setAskTimes(askTimes);
            waitAskTask.setMessage(message);

            messageExecutor.waitAskMap.put(waitAskTask.getAskToken(), waitAskTask);
            for (ProviderManager providerManager : providerManagers) {
                for (Provider provider : providerManager.getUseProviders()) {
                    Channel channel = provider.nextChannel();
                    if (channel != null) {
                        channel.writeAndFlush(temp);
                    }
                }
            }
        }
    }


    public boolean isDirect() {
        return direct;
    }

    public void setDirect(boolean direct) {
        this.direct = direct;
    }



}
