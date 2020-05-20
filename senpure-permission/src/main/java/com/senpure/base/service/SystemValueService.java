package com.senpure.base.service;

import com.senpure.base.model.SystemValue;
import com.senpure.base.criteria.SystemValueCriteria;
import com.senpure.base.mapper.SystemValueMapper;
import com.senpure.base.result.SystemValuePageResult;
import com.senpure.base.exception.OptimisticLockingFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;

/**
 * @author senpure
 * @version 2020-5-20 18:21:46
 */
@Service
public class SystemValueService extends BaseService {

    private SystemValueMapper systemValueMapper;

    @Autowired
    public void setSystemValueMapper(SystemValueMapper systemValueMapper) {
        this.systemValueMapper = systemValueMapper;
    }

    public SystemValue find(Long id) {
        return systemValueMapper.find(id);
    }

    public int count() {
        return systemValueMapper.count();
    }

    public List<SystemValue> findAll() {
        return systemValueMapper.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        int result = systemValueMapper.delete(id);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int delete(SystemValueCriteria criteria) {
        return systemValueMapper.deleteByCriteria(criteria);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(SystemValue systemValue) {
        systemValue.setId(idGenerator.nextId());
        int result = systemValueMapper.save(systemValue);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int save(List<SystemValue> systemValues) {
        if (systemValues == null || systemValues.size() == 0) {
            return 0;
        }
        for (SystemValue systemValue : systemValues) {
            systemValue.setId(idGenerator.nextId());
        }
        return systemValueMapper.saveList(systemValues);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(SystemValueCriteria criteria) {
        criteria.setId(idGenerator.nextId());
        int result = systemValueMapper.save(criteria.toSystemValue());
        return result == 1;
    }

    /**
     * 更新失败会抛出OptimisticLockingFailureException
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean update(SystemValue systemValue) {
        int updateCount = systemValueMapper.update(systemValue);
        if (updateCount == 0) {
            throw new OptimisticLockingFailureException(systemValue.getClass() + ",[" + systemValue.getId() + "],版本号冲突,版本号[" + systemValue.getVersion() + "]");
        }
        return true;
    }

    /**
     * 当版本号，和主键不为空时，更新失败会抛出OptimisticLockingFailureException
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int update(SystemValueCriteria criteria) {
        int updateCount = systemValueMapper.updateByCriteria(criteria);
        if (updateCount == 0 && criteria.getVersion() != null
                && criteria.getId() != null) {
            throw new OptimisticLockingFailureException(criteria.getClass() + ",[" + criteria.getId() + "],版本号冲突,版本号[" + criteria.getVersion() + "]");
        }
        return updateCount;
    }

    @Transactional(readOnly = true)
    public SystemValuePageResult findPage(SystemValueCriteria criteria) {
        SystemValuePageResult pageResult = SystemValuePageResult.success();
        //是否是主键查找
        if (criteria.getId() != null) {
            SystemValue systemValue = systemValueMapper.find(criteria.getId());
            if (systemValue != null) {
                List<SystemValue> systemValues = new ArrayList<>(16);
                systemValues.add(systemValue);
                pageResult.setTotal(1);
                pageResult.setSystemValues(systemValues);
            } else {
                pageResult.setTotal(0);
            }
            return pageResult;
        }
        int total = systemValueMapper.countByCriteria(criteria);
        pageResult.setTotal(total);
        if (total == 0) {
            return pageResult;
        }
        //检查页数是否合法
        checkPage(criteria, total);
        List<SystemValue> systemValues = systemValueMapper.findByCriteria(criteria);
        pageResult.setSystemValues(systemValues);
        return pageResult;
    }

    public List<SystemValue> find(SystemValueCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            List<SystemValue> systemValues = new ArrayList<>(16);
            SystemValue systemValue = systemValueMapper.find(criteria.getId());
            if (systemValue != null) {
                systemValues.add(systemValue);
            }
            return systemValues;
        }
        return systemValueMapper.findByCriteria(criteria);
    }

    public SystemValue findOne(SystemValueCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            return systemValueMapper.find(criteria.getId());
        }
        List<SystemValue> systemValues = systemValueMapper.findByCriteria(criteria);
        if (systemValues.size() == 0) {
            return null;
        }
        return systemValues.get(0);
    }

}