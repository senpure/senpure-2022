package com.senpure.dispatcher.service;

import com.senpure.base.util.Assert;
import com.senpure.dispatcher.criteria.RangeValueCriteria;
import com.senpure.dispatcher.mapper.DispatcherLockMapper;
import com.senpure.dispatcher.mapper.RangeValueMapper;
import com.senpure.dispatcher.model.DispatcherLock;
import com.senpure.dispatcher.model.RangeConfig;
import com.senpure.dispatcher.model.RangeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 需要保证mysql的隔离级别避免脏读
 *
 * @author senpure-generator
 * @version 2019-8-1 16:37:55
 */
@Service
public class RangeValueService implements ApplicationRunner {

    protected Logger logger = LoggerFactory.getLogger(getClass());


    private long lockValue = 2L;
    @Autowired
    private DispatcherLockMapper lockMapper;

    @Autowired
    private RangeValueMapper rangeValueMapper;
    @Autowired
    private RangeConfigService rangeConfigService;
    private ThreadLocal<Integer> retry = ThreadLocal.withInitial(() -> 0);

    public RangeValue dispatch(Integer configId, String serverName, String serverKey) {
        DispatcherLock lock = lockMapper.find(lockValue);
        if (lock == null) {
            logger.info("调度服务还没有准备好");
            return null;
        }
        RangeValueCriteria criteria = new RangeValueCriteria();
        criteria.setServerName(serverName);
        boolean saveFind = false;
        RangeValue find = null;
        List<RangeValue> rangeValues = rangeValueMapper.findByCriteria(criteria);
        if (rangeValues.size() == 0) {

            find = new RangeValue();
            RangeConfig rangeConfig = rangeConfigService.find(configId);
            value(find, serverName, serverKey, rangeConfig.getStart(), rangeConfig);
            saveFind = true;
        } else {
            for (RangeValue rangeValue : rangeValues) {
                if (rangeValue.getServerKey().equals(serverKey)) {
                    find = rangeValue;
                    break;
                }
            }
            if (find == null) {
                RangeValue last = rangeValues.get(rangeValues.size() - 1);
                find = new RangeValue();
                RangeConfig rangeConfig = rangeConfigService.find(configId);
                if (last.getEnd() >= rangeConfig.getEnd()) {
                    logger.info(" {} {} {} : {}  没有可用范围了", rangeConfig.getId(), rangeConfig.getName(), serverName, serverKey);
                    return null;
                }
                value(find, serverName, serverKey, last.getEnd(), rangeConfig);
                saveFind = true;
            }
        }
        if (saveFind) {
            rangeValueMapper.save(find);
        }
        RangeValue result = new RangeValue();

        result.setServerName(serverName);
        result.setServerKey(serverKey);
        result.setStart(find.getStart());
        result.setEnd(find.getEnd());

        int i = lockMapper.update(lock);
        if (i == 1) {
            retry.set(0);
            return result;
        } else {
            int times = retry.get();
            if (times < 3) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    logger.error("", e);
                }
                retry.set(times + 1);
                logger.info(" {} : {} 第 {} 次重试", serverName, serverKey, retry.get());
                return dispatch(configId, serverName, serverKey);
            }
            logger.info(" {} : {} 重试{}次失败", serverName, serverKey, retry.get());
            //抛出异常，进行事务回滚
            retry.set(0);
            Assert.error("lock 更新失败 version:" + lock.getVersion());
            return null;
        }

    }

    private void value(RangeValue rangeValue, String serverName, String serverKey, Long start, RangeConfig rangeConfig) {

        rangeValue.setConfigId(rangeConfig.getId());
        rangeValue.setConfigName(rangeConfig.getName());
        rangeValue.setServerName(serverName);
        rangeValue.setServerKey(serverKey);
        rangeValue.setStart(start);
        rangeValue.setEnd(rangeValue.getStart() + rangeConfig.getStep());
        if (rangeValue.getEnd() > rangeConfig.getEnd()) {
            rangeValue.setEnd(rangeConfig.getEnd());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void run(ApplicationArguments args) throws Exception {
        DispatcherLock lock = lockMapper.find(lockValue);
        if (lock == null) {
            lock = new DispatcherLock();
            lock.setId(lockValue);
            lockMapper.save(lock);
        }
    }
}