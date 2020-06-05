package com.senpure.io.server.gateway.producer;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 轮询可用的服务实例
 *
 * @author senpure
 * @time 2020-06-05 15:07:34
 */
public class ProducerRoundRobinNextStrategy implements ProducerNextStrategy {

    private final AtomicInteger atomicIndex = new AtomicInteger(-1);

    @Override
    public String strategyName() {
        return "round robin";
    }

    @Override
    public Producer next(List<Producer> producers) {
        int index = atomicIndex.incrementAndGet();
        return producers.get(Math.abs(index % producers.size()));
    }
}
