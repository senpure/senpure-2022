package com.senpure.base.service;

import com.senpure.base.model.Permission;
import com.senpure.base.criteria.PermissionCriteria;
import com.senpure.base.mapper.PermissionMapper;
import com.senpure.base.result.PermissionPageResult;
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
public class PermissionService extends BaseService {

    private PermissionMapper permissionMapper;

    @Autowired
    public void setPermissionMapper(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    public Permission find(Long id) {
        return permissionMapper.find(id);
    }

    public int count() {
        return permissionMapper.count();
    }

    public List<Permission> findAll() {
        return permissionMapper.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        int result = permissionMapper.delete(id);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int delete(PermissionCriteria criteria) {
        return permissionMapper.deleteByCriteria(criteria);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(Permission permission) {
        permission.setId(idGenerator.nextId());
        int result = permissionMapper.save(permission);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int save(List<Permission> permissions) {
        if (permissions == null || permissions.size() == 0) {
            return 0;
        }
        for (Permission permission : permissions) {
            permission.setId(idGenerator.nextId());
        }
        return permissionMapper.saveList(permissions);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(PermissionCriteria criteria) {
        criteria.setId(idGenerator.nextId());
        int result = permissionMapper.save(criteria.toPermission());
        return result == 1;
    }

    /**
     * 更新失败会抛出OptimisticLockingFailureException
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Permission permission) {
        int updateCount = permissionMapper.update(permission);
        if (updateCount == 0) {
            throw new OptimisticLockingFailureException(permission.getClass() + ",[" + permission.getId() + "],版本号冲突,版本号[" + permission.getVersion() + "]");
        }
        return true;
    }

    /**
     * 当版本号，和主键不为空时，更新失败会抛出OptimisticLockingFailureException
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int update(PermissionCriteria criteria) {
        int updateCount = permissionMapper.updateByCriteria(criteria);
        if (updateCount == 0 && criteria.getVersion() != null
                && criteria.getId() != null) {
            throw new OptimisticLockingFailureException(criteria.getClass() + ",[" + criteria.getId() + "],版本号冲突,版本号[" + criteria.getVersion() + "]");
        }
        return updateCount;
    }

    @Transactional(readOnly = true)
    public PermissionPageResult findPage(PermissionCriteria criteria) {
        PermissionPageResult pageResult = PermissionPageResult.success();
        //是否是主键查找
        if (criteria.getId() != null) {
            Permission permission = permissionMapper.find(criteria.getId());
            if (permission != null) {
                List<Permission> permissions = new ArrayList<>(16);
                permissions.add(permission);
                pageResult.setTotal(1);
                pageResult.setPermissions(permissions);
            } else {
                pageResult.setTotal(0);
            }
            return pageResult;
        }
        int total = permissionMapper.countByCriteria(criteria);
        pageResult.setTotal(total);
        if (total == 0) {
            return pageResult;
        }
        //检查页数是否合法
        checkPage(criteria, total);
        List<Permission> permissions = permissionMapper.findByCriteria(criteria);
        pageResult.setPermissions(permissions);
        return pageResult;
    }

    public List<Permission> find(PermissionCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            List<Permission> permissions = new ArrayList<>(16);
            Permission permission = permissionMapper.find(criteria.getId());
            if (permission != null) {
                permissions.add(permission);
            }
            return permissions;
        }
        return permissionMapper.findByCriteria(criteria);
    }

    public Permission findOne(PermissionCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            return permissionMapper.find(criteria.getId());
        }
        List<Permission> permissions = permissionMapper.findByCriteria(criteria);
        if (permissions.size() == 0) {
            return null;
        }
        return permissions.get(0);
    }

}