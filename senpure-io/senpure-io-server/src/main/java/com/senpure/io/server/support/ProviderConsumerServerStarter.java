package com.senpure.io.server.support;

import com.senpure.io.server.MessageDecoderContext;
import com.senpure.io.server.ServerProperties;
import com.senpure.io.server.provider.consumer.ConsumerManager;
import com.senpure.io.server.provider.consumer.ConsumerServer;
import com.senpure.io.server.protocol.bean.IdName;
import com.senpure.io.server.provider.ProviderMessageExecutor;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.List;

/**
 * DirectServerStarter
 *
 * @author senpure
 * @time 2019-09-18 10:01:46
 */
public class ProviderConsumerServerStarter {
    @Resource
    private ServerProperties properties;

    @Resource
    private ProviderMessageExecutor messageExecutor;
    @Resource
    private ConsumerManager consumerManager;
    @Resource
    private MessageDecoderContext decoderContext;

    private ConsumerServer consumerServer;


    @PostConstruct
    public void init() {

        ConsumerServer consumerServer = new ConsumerServer();
        consumerServer.setMessageExecutor(messageExecutor);
        consumerServer.setProperties(properties.getProvider());

        consumerServer.setClientManager(consumerManager);

        consumerServer.setDecoderContext(decoderContext);

        consumerServer.start();
        if (StringUtils.isNoneEmpty(properties.getProvider().getIdNamesPackage())) {
            List<IdName> idNames=  MessageScanner.scan(properties.getProvider().getIdNamesPackage());
            MessageIdReader.relation(idNames);

        }

        this.consumerServer = consumerServer;
    }


    @PreDestroy
    public void destroy() {
        if (consumerServer != null) {
            consumerServer.destroy();
        }


    }


}
