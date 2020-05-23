package com.senpure.base.service;

import com.senpure.base.model.Role;
import com.senpure.base.result.RolePageResult;
import com.senpure.base.criteria.RoleCriteria;
import com.senpure.base.mapper.RoleMapper;
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
@CacheConfig(cacheNames = "role")
public class RoleService extends BaseService {

    @Resource
    private RoleMapper roleMapper;

    @CacheEvict(key = "#id")
    public void clearCache(Long id) {
    }

    @CacheEvict(allEntries = true)
    public void clearCache() {
    }

    @Cacheable(key = "#id", unless = "#result == null")
    public Role find(Long id) {
        return roleMapper.find(id);
    }

    @Cacheable(key = "#id", unless = "#result == null")
    public Role findOnlyCache(Long id) {
        return null;
    }

    @CachePut(key = "#id", unless = "#result == null")
    public Role findSkipCache(Long id) {
        return roleMapper.find(id);
    }

    public int count() {
        return roleMapper.count();
    }

    public List<Role> findAll() {
        return roleMapper.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#id")
    public boolean delete(Long id) {
        int result = roleMapper.delete(id);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#criteria.id", allEntries = true)
    public int delete(RoleCriteria criteria) {
        return roleMapper.deleteByCriteria(criteria);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(Role role) {
        role.setId(idGenerator.nextId());
        int result = roleMapper.save(role);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int save(List<Role> roles) {
        if (roles == null || roles.size() == 0) {
            return 0;
        }
        for (Role role : roles) {
            role.setId(idGenerator.nextId());
        }
        return roleMapper.saveList(roles);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(RoleCriteria criteria) {
        criteria.setId(idGenerator.nextId());
        int result = roleMapper.save(criteria.toRole());
        return result == 1;
    }

    /**
     * 更新失败会抛出OptimisticLockingFailureException
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#role.id")
    public boolean update(Role role) {
        int updateCount = roleMapper.update(role);
        if (updateCount == 0) {
            throw new OptimisticLockingFailureException(role.getClass() + ",[" + role.getId() + "],版本号冲突,版本号[" + role.getVersion() + "]");
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
    public int update(RoleCriteria criteria) {
        int updateCount = roleMapper.updateByCriteria(criteria);
        if (updateCount == 0 && criteria.getVersion() != null
                && criteria.getId() != null) {
            throw new OptimisticLockingFailureException(criteria.getClass() + ",[" + criteria.getId() + "],版本号冲突,版本号[" + criteria.getVersion() + "]");
        }
        return updateCount;
    }

    @Transactional(readOnly = true)
    public RolePageResult findPage(RoleCriteria criteria) {
        RolePageResult result = RolePageResult.success();
        //是否是主键查找
        if (criteria.getId() != null) {
            Role role = roleMapper.find(criteria.getId());
            if (role != null) {
                List<Role> roles = new ArrayList<>(16);
                roles.add(role);
                result.setTotal(1);
                result.setRoles(roles);
            } else {
                result.setTotal(0);
            }
            return result;
        }
        int total = roleMapper.countByCriteria(criteria);
        result.setTotal(total);
        if (total == 0) {
            return result;
        }
        //检查页数是否合法
        checkPage(criteria, total);
        List<Role> roles = roleMapper.findByCriteria(criteria);
        result.setRoles(roles);
        return result;
    }

    public List<Role> find(RoleCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            List<Role> roles = new ArrayList<>(16);
            Role role = roleMapper.find(criteria.getId());
            if (role != null) {
                roles.add(role);
            }
            return roles;
        }
        return roleMapper.findByCriteria(criteria);
    }

    public Role findOne(RoleCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            return roleMapper.find(criteria.getId());
        }
        List<Role> roles = roleMapper.findByCriteria(criteria);
        if (roles.size() == 0) {
            return null;
        }
        return roles.get(0);
    }

    public List<Role> findByContainerId(Integer containerId) {
        RoleCriteria criteria = new RoleCriteria();
        criteria.setUsePage(false);
        criteria.setContainerId(containerId);
        return roleMapper.findByCriteria(criteria);
    }

    public Role findByName(String name) {
        RoleCriteria criteria = new RoleCriteria();
        criteria.setUsePage(false);
        criteria.setName(name);
        List<Role> roles = roleMapper.findByCriteria(criteria);
        if (roles.size() == 0) {
            return null;
        }
        return roles.get(0);
    }

}