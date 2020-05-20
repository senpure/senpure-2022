package com.senpure.base.service;

import com.senpure.base.model.RoleValue;
import com.senpure.base.criteria.RoleValueCriteria;
import com.senpure.base.mapper.RoleValueMapper;
import com.senpure.base.result.RoleValuePageResult;
import com.senpure.base.exception.OptimisticLockingFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;

/**
 * @author senpure
 * @version 2020-5-20 18:21:45
 */
@Service
public class RoleValueService extends BaseService {

    private RoleValueMapper roleValueMapper;

    @Autowired
    public void setRoleValueMapper(RoleValueMapper roleValueMapper) {
        this.roleValueMapper = roleValueMapper;
    }

    public RoleValue find(Long id) {
        return roleValueMapper.find(id);
    }

    public int count() {
        return roleValueMapper.count();
    }

    public List<RoleValue> findAll() {
        return roleValueMapper.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        int result = roleValueMapper.delete(id);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int delete(RoleValueCriteria criteria) {
        return roleValueMapper.deleteByCriteria(criteria);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(RoleValue roleValue) {
        roleValue.setId(idGenerator.nextId());
        int result = roleValueMapper.save(roleValue);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int save(List<RoleValue> roleValues) {
        if (roleValues == null || roleValues.size() == 0) {
            return 0;
        }
        for (RoleValue roleValue : roleValues) {
            roleValue.setId(idGenerator.nextId());
        }
        return roleValueMapper.saveList(roleValues);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(RoleValueCriteria criteria) {
        criteria.setId(idGenerator.nextId());
        int result = roleValueMapper.save(criteria.toRoleValue());
        return result == 1;
    }

    /**
     * 更新失败会抛出OptimisticLockingFailureException
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean update(RoleValue roleValue) {
        int updateCount = roleValueMapper.update(roleValue);
        if (updateCount == 0) {
            throw new OptimisticLockingFailureException(roleValue.getClass() + ",[" + roleValue.getId() + "],版本号冲突,版本号[" + roleValue.getVersion() + "]");
        }
        return true;
    }

    /**
     * 当版本号，和主键不为空时，更新失败会抛出OptimisticLockingFailureException
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int update(RoleValueCriteria criteria) {
        int updateCount = roleValueMapper.updateByCriteria(criteria);
        if (updateCount == 0 && criteria.getVersion() != null
                && criteria.getId() != null) {
            throw new OptimisticLockingFailureException(criteria.getClass() + ",[" + criteria.getId() + "],版本号冲突,版本号[" + criteria.getVersion() + "]");
        }
        return updateCount;
    }

    @Transactional(readOnly = true)
    public RoleValuePageResult findPage(RoleValueCriteria criteria) {
        RoleValuePageResult pageResult = RoleValuePageResult.success();
        //是否是主键查找
        if (criteria.getId() != null) {
            RoleValue roleValue = roleValueMapper.find(criteria.getId());
            if (roleValue != null) {
                List<RoleValue> roleValues = new ArrayList<>(16);
                roleValues.add(roleValue);
                pageResult.setTotal(1);
                pageResult.setRoleValues(roleValues);
            } else {
                pageResult.setTotal(0);
            }
            return pageResult;
        }
        int total = roleValueMapper.countByCriteria(criteria);
        pageResult.setTotal(total);
        if (total == 0) {
            return pageResult;
        }
        //检查页数是否合法
        checkPage(criteria, total);
        List<RoleValue> roleValues = roleValueMapper.findByCriteria(criteria);
        pageResult.setRoleValues(roleValues);
        return pageResult;
    }

    public List<RoleValue> find(RoleValueCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            List<RoleValue> roleValues = new ArrayList<>(16);
            RoleValue roleValue = roleValueMapper.find(criteria.getId());
            if (roleValue != null) {
                roleValues.add(roleValue);
            }
            return roleValues;
        }
        return roleValueMapper.findByCriteria(criteria);
    }

    public RoleValue findOne(RoleValueCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            return roleValueMapper.find(criteria.getId());
        }
        List<RoleValue> roleValues = roleValueMapper.findByCriteria(criteria);
        if (roleValues.size() == 0) {
            return null;
        }
        return roleValues.get(0);
    }

    public List<RoleValue> findByRoleId(Long roleId) {
        RoleValueCriteria criteria = new RoleValueCriteria();
        criteria.setUsePage(false);
        criteria.setRoleId(roleId);
        return roleValueMapper.findByCriteria(criteria);
    }

}