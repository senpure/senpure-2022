package com.senpure.io.server.gateway.producer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 选取服务实例分数最高的一个实例
 *
 * @author senpure
 * @time 2020-06-05 15:23:27
 */
public class ProducerDefaultNextStrategy implements ProducerNextStrategy {
    private final Comparator<Producer> comparator=new StatisticComparator();
    @Override
    public String strategyName() {
        return "default";
    }


    @Override
    public Producer next(List<Producer> producers) {
        List<Producer> sortedProducers = new ArrayList<>(producers);
        sortedProducers.sort(comparator);
        return sortedProducers.get(0);
    }
    static class  StatisticComparator implements  Comparator<Producer>{

        @Override
        public int compare(Producer x, Producer y) {
            return Integer.compare(y.getStatistic().getScore(), x.getStatistic().getScore());
        }
    }
}
