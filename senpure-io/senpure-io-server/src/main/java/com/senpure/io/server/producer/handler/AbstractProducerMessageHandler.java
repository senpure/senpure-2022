package com.senpure.io.server.producer.handler;


import com.senpure.io.producer.GatewayManager;
import com.senpure.io.producer.ProducerMessageHandlerUtil;
import com.senpure.io.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class AbstractProducerMessageHandler<T extends Message> implements ProducerMessageHandler<T>, InitializingBean {
    protected Logger logger;
   // protected Class<T> messageClass;
   // protected T IdMessage;
    @Autowired
    protected GatewayManager gatewayManager;

    public AbstractProducerMessageHandler() {
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
    public void afterPropertiesSet() throws Exception {
        ProducerMessageHandlerUtil.regMessageHandler(this);

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
