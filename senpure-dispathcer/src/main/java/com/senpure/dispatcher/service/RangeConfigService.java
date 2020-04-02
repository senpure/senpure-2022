package com.senpure.dispatcher.service;

import com.senpure.base.criterion.Criteria;
import com.senpure.base.exception.OptimisticLockingFailureException;
import com.senpure.dispatcher.criteria.RangeConfigCriteria;
import com.senpure.dispatcher.mapper.RangeConfigMapper;
import com.senpure.dispatcher.model.RangeConfig;
import com.senpure.dispatcher.result.RangeConfigPageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author senpure-generator
 * @version 2019-8-1 16:37:55
 */
@Service
public class RangeConfigService  implements ApplicationRunner {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public void checkPage(Criteria criteria, int total) {
        if (criteria.getPage() * criteria.getPageSize() > total) {
            int page = total / criteria.getPageSize();
            if (total % criteria.getPageSize() > 0) {
                page++;
            }
            criteria.setPage(page);
        }
    }
    @Autowired
    private RangeConfigMapper rangeConfigMapper;

    public RangeConfig find(Integer id) {
        return rangeConfigMapper.find(id);
    }

    public int count() {
        return rangeConfigMapper.count();
    }

    public List<RangeConfig> findAll() {
        return rangeConfigMapper.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Integer id) {
        int result = rangeConfigMapper.delete(id);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int delete(RangeConfigCriteria criteria) {
        return rangeConfigMapper.deleteByCriteria(criteria);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(RangeConfig rangeConfig) {
        int result = rangeConfigMapper.save(rangeConfig);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int save(List<RangeConfig> rangeConfigs) {
        if (rangeConfigs == null || rangeConfigs.size() == 0) {
            return 0;
        }
        return rangeConfigMapper.saveBatch(rangeConfigs);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(RangeConfigCriteria criteria) {
        int result = rangeConfigMapper.save(criteria.toRangeConfig());
        return result == 1;
    }

    /**
     * 更新失败会抛出OptimisticLockingFailureException
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean update(RangeConfig rangeConfig) {
        int updateCount = rangeConfigMapper.update(rangeConfig);
        if (updateCount == 0) {
            throw new OptimisticLockingFailureException(rangeConfig.getClass() + ",[" + rangeConfig.getId() + "],版本号冲突,版本号[" + rangeConfig.getVersion() + "]");
        }
        return true;
    }

    /**
     * 当版本号，和主键不为空时，更新失败会抛出OptimisticLockingFailureException
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int update(RangeConfigCriteria criteria) {
        int updateCount = rangeConfigMapper.updateByCriteria(criteria);
        if (updateCount == 0 && criteria.getVersion() != null
                && criteria.getId() != null) {
            throw new OptimisticLockingFailureException(criteria.getClass() + ",[" + criteria.getId() + "],版本号冲突,版本号[" + criteria.getVersion() + "]");
        }
        return updateCount;
    }

    @Transactional(readOnly = true)
    public RangeConfigPageResult findPage(RangeConfigCriteria criteria) {
        RangeConfigPageResult pageResult = RangeConfigPageResult.success();
        //是否是主键查找
        if (criteria.getId() != null) {
            RangeConfig rangeConfig = rangeConfigMapper.find(criteria.getId());
            if (rangeConfig != null) {
                List<RangeConfig> rangeConfigs = new ArrayList(16);
                rangeConfigs.add(rangeConfig);
                pageResult.setTotal(1);
                pageResult.setRangeConfigs(rangeConfigs);
            } else {
                pageResult.setTotal(0);
            }
            return pageResult;
        }
        int total = rangeConfigMapper.countByCriteria(criteria);
        pageResult.setTotal(total);
        if (total == 0) {
            return pageResult;
        }
        //检查页数是否合法
        checkPage(criteria, total);
        List<RangeConfig> rangeConfigs = rangeConfigMapper.findByCriteria(criteria);
        pageResult.setRangeConfigs(rangeConfigs);
        return pageResult;
    }

    public List<RangeConfig> find(RangeConfigCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            List<RangeConfig> rangeConfigs = new ArrayList<>(16);
            RangeConfig rangeConfig = rangeConfigMapper.find(criteria.getId());
            if (rangeConfig != null) {
                rangeConfigs.add(rangeConfig);
            }
            return rangeConfigs;
        }
        return rangeConfigMapper.findByCriteria(criteria);
    }

    public RangeConfig findOne(RangeConfigCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            return rangeConfigMapper.find(criteria.getId());
        }
        List<RangeConfig> rangeConfigs = rangeConfigMapper.findByCriteria(criteria);
        if (rangeConfigs.size() == 0) {
            return null;
        }
        return rangeConfigs.get(0);
    }

    public RangeConfig findByName(String name) {
        RangeConfigCriteria criteria = new RangeConfigCriteria();
        criteria.setUsePage(false);
        criteria.setName(name);
        List<RangeConfig> rangeConfigs = rangeConfigMapper.findByCriteria(criteria);
        if (rangeConfigs.size() == 0) {
            return null;
        }
        return rangeConfigs.get(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run(ApplicationArguments args) throws Exception {
        RangeConfig rangeConfig = rangeConfigMapper.find(1);
        if (rangeConfig == null) {
            rangeConfig = new RangeConfig();
            rangeConfig.setId(1);
            rangeConfig.setName("default");
            rangeConfig.setStart(100000L);
            rangeConfig.setEnd(1000000L);
            rangeConfig.setStep(200000L);
            rangeConfigMapper.save(rangeConfig);
        }
    }
}