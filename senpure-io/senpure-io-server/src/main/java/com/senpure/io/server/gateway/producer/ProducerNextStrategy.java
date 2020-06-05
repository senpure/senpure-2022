package com.senpure.io.server.gateway.producer;

import java.util.List;

/**
 * NextProducer
 *
 * @author senpure
 * @time 2020-06-05 15:03:10
 */
public interface ProducerNextStrategy {
    String strategyName();
    Producer next(List<Producer> producers);
}
