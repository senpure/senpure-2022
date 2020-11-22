package com.senpure.io.server.provider.handler;


import com.senpure.io.protocol.Message;
import com.senpure.io.server.provider.GatewayManager;
import com.senpure.io.server.provider.ProviderMessageHandlerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;


public abstract class AbstractProviderMessageHandler<T extends Message> implements ProviderMessageHandler<T>, InitializingBean {
    protected Logger logger;
   // protected Class<T> messageClass;
   // protected T IdMessage;
    @Resource
    protected GatewayManager gatewayManager;

    public AbstractProviderMessageHandler() {
        this.logger = LoggerFactory.getLogger(getClass());
       // ResolvableType resolvableType = ResolvableType.forClass(getClass());
      //  messageClass = (Class<T>) resolvableType.getSuperType().getGeneric(0).resolve();

    }



//    @Override
//    public T getEmptyMessage() {
//
//        try {
//            return messageClass.newInstance();
//        } catch (Exception e) {
//
//            Assert.error(e.toString());
//        }
//        return null;
//    }

//    @Override
//    public int handlerId() {
//        if (IdMessage != null) {
//            return IdMessage.getMessageId();
//        }
//        IdMessage = getEmptyMessage();
//        return IdMessage.getMessageId();
//    }

    @Override
    public void afterPropertiesSet() {
        ProviderMessageHandlerUtil.regMessageHandler(this);
        logger.info("gatewayManager {}",gatewayManager);
    }


    @Override
    public boolean direct() {
        return true;
    }


    @Override
    public boolean regToGateway() {
        return true;
    }
}
