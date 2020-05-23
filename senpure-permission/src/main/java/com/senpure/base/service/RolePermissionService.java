package com.senpure.base.service;

import com.senpure.base.model.RolePermission;
import com.senpure.base.result.RolePermissionPageResult;
import com.senpure.base.criteria.RolePermissionCriteria;
import com.senpure.base.mapper.RolePermissionMapper;
import com.senpure.base.exception.OptimisticLockingFailureException;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.ArrayList;

/**
 * @author senpure
 * @version 2020-5-22 16:52:02
 */
@Service
@CacheConfig(cacheNames = "rolePermission")
public class RolePermissionService extends BaseService {

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @CacheEvict(key = "#id")
    public void clearCache(Long id) {
    }

    @CacheEvict(allEntries = true)
    public void clearCache() {
    }

    @Cacheable(key = "#id", unless = "#result == null")
    public RolePermission find(Long id) {
        return rolePermissionMapper.find(id);
    }

    @Cacheable(key = "#id", unless = "#result == null")
    public RolePermission findOnlyCache(Long id) {
        return null;
    }

    @CachePut(key = "#id", unless = "#result == null")
    public RolePermission findSkipCache(Long id) {
        return rolePermissionMapper.find(id);
    }

    public int count() {
        return rolePermissionMapper.count();
    }

    public List<RolePermission> findAll() {
        return rolePermissionMapper.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#id")
    public boolean delete(Long id) {
        int result = rolePermissionMapper.delete(id);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#criteria.id", allEntries = true)
    public int delete(RolePermissionCriteria criteria) {
        return rolePermissionMapper.deleteByCriteria(criteria);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(RolePermission rolePermission) {
        rolePermission.setId(idGenerator.nextId());
        int result = rolePermissionMapper.save(rolePermission);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int save(List<RolePermission> rolePermissions) {
        if (rolePermissions == null || rolePermissions.size() == 0) {
            return 0;
        }
        for (RolePermission rolePermission : rolePermissions) {
            rolePermission.setId(idGenerator.nextId());
        }
        return rolePermissionMapper.saveList(rolePermissions);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(RolePermissionCriteria criteria) {
        criteria.setId(idGenerator.nextId());
        int result = rolePermissionMapper.save(criteria.toRolePermission());
        return result == 1;
    }

    /**
     * 更新失败会抛出OptimisticLockingFailureException
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#rolePermission.id")
    public boolean update(RolePermission rolePermission) {
        int updateCount = rolePermissionMapper.update(rolePermission);
        if (updateCount == 0) {
            throw new OptimisticLockingFailureException(rolePermission.getClass() + ",[" + rolePermission.getId() + "],版本号冲突,版本号[" + rolePermission.getVersion() + "]");
        }
        return true;
    }

    /**
     * 当版本号，和主键不为空时，更新失败会抛出OptimisticLockingFailureException
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#criteria.id", allEntries = true)
    public int update(RolePermissionCriteria criteria) {
        int updateCount = rolePermissionMapper.updateByCriteria(criteria);
        if (updateCount == 0 && criteria.getVersion() != null
                && criteria.getId() != null) {
            throw new OptimisticLockingFailureException(criteria.getClass() + ",[" + criteria.getId() + "],版本号冲突,版本号[" + criteria.getVersion() + "]");
        }
        return updateCount;
    }

    @Transactional(readOnly = true)
    public RolePermissionPageResult findPage(RolePermissionCriteria criteria) {
        RolePermissionPageResult result = RolePermissionPageResult.success();
        //是否是主键查找
        if (criteria.getId() != null) {
            RolePermission rolePermission = rolePermissionMapper.find(criteria.getId());
            if (rolePermission != null) {
                List<RolePermission> rolePermissions = new ArrayList<>(16);
                rolePermissions.add(rolePermission);
                result.setTotal(1);
                result.setRolePermissions(rolePermissions);
            } else {
                result.setTotal(0);
            }
            return result;
        }
        int total = rolePermissionMapper.countByCriteria(criteria);
        result.setTotal(total);
        if (total == 0) {
            return result;
        }
        //检查页数是否合法
        checkPage(criteria, total);
        List<RolePermission> rolePermissions = rolePermissionMapper.findByCriteria(criteria);
        result.setRolePermissions(rolePermissions);
        return result;
    }

    public List<RolePermission> find(RolePermissionCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            List<RolePermission> rolePermissions = new ArrayList<>(16);
            RolePermission rolePermission = rolePermissionMapper.find(criteria.getId());
            if (rolePermission != null) {
                rolePermissions.add(rolePermission);
            }
            return rolePermissions;
        }
        return rolePermissionMapper.findByCriteria(criteria);
    }

    public RolePermission findOne(RolePermissionCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            return rolePermissionMapper.find(criteria.getId());
        }
        List<RolePermission> rolePermissions = rolePermissionMapper.findByCriteria(criteria);
        if (rolePermissions.size() == 0) {
            return null;
        }
        return rolePermissions.get(0);
    }

    public List<RolePermission> findByRoleId(Long roleId) {
        RolePermissionCriteria criteria = new RolePermissionCriteria();
        criteria.setUsePage(false);
        criteria.setRoleId(roleId);
        return rolePermissionMapper.findByCriteria(criteria);
    }

    public List<RolePermission> findByPermissionId(Long permissionId) {
        RolePermissionCriteria criteria = new RolePermissionCriteria();
        criteria.setUsePage(false);
        criteria.setPermissionId(permissionId);
        return rolePermissionMapper.findByCriteria(criteria);
    }

}