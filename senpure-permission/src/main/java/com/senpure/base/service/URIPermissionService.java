package com.senpure.base.service;

import com.senpure.base.model.URIPermission;
import com.senpure.base.criteria.URIPermissionCriteria;
import com.senpure.base.mapper.URIPermissionMapper;
import com.senpure.base.result.URIPermissionPageResult;
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
public class URIPermissionService extends BaseService {

    private URIPermissionMapper uriPermissionMapper;

    @Autowired
    public void setURIPermissionMapper(URIPermissionMapper uriPermissionMapper) {
        this.uriPermissionMapper = uriPermissionMapper;
    }

    public URIPermission find(Long id) {
        return uriPermissionMapper.find(id);
    }

    public int count() {
        return uriPermissionMapper.count();
    }

    public List<URIPermission> findAll() {
        return uriPermissionMapper.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        int result = uriPermissionMapper.delete(id);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int delete(URIPermissionCriteria criteria) {
        return uriPermissionMapper.deleteByCriteria(criteria);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(URIPermission uriPermission) {
        uriPermission.setId(idGenerator.nextId());
        int result = uriPermissionMapper.save(uriPermission);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int save(List<URIPermission> uriPermissions) {
        if (uriPermissions == null || uriPermissions.size() == 0) {
            return 0;
        }
        for (URIPermission uriPermission : uriPermissions) {
            uriPermission.setId(idGenerator.nextId());
        }
        return uriPermissionMapper.saveList(uriPermissions);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(URIPermissionCriteria criteria) {
        criteria.setId(idGenerator.nextId());
        int result = uriPermissionMapper.save(criteria.toURIPermission());
        return result == 1;
    }

    /**
     * 更新失败会抛出OptimisticLockingFailureException
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean update(URIPermission uriPermission) {
        int updateCount = uriPermissionMapper.update(uriPermission);
        if (updateCount == 0) {
            throw new OptimisticLockingFailureException(uriPermission.getClass() + ",[" + uriPermission.getId() + "],版本号冲突,版本号[" + uriPermission.getVersion() + "]");
        }
        return true;
    }

    /**
     * 当版本号，和主键不为空时，更新失败会抛出OptimisticLockingFailureException
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int update(URIPermissionCriteria criteria) {
        int updateCount = uriPermissionMapper.updateByCriteria(criteria);
        if (updateCount == 0 && criteria.getVersion() != null
                && criteria.getId() != null) {
            throw new OptimisticLockingFailureException(criteria.getClass() + ",[" + criteria.getId() + "],版本号冲突,版本号[" + criteria.getVersion() + "]");
        }
        return updateCount;
    }

    @Transactional(readOnly = true)
    public URIPermissionPageResult findPage(URIPermissionCriteria criteria) {
        URIPermissionPageResult pageResult = URIPermissionPageResult.success();
        //是否是主键查找
        if (criteria.getId() != null) {
            URIPermission uriPermission = uriPermissionMapper.find(criteria.getId());
            if (uriPermission != null) {
                List<URIPermission> uriPermissions = new ArrayList<>(16);
                uriPermissions.add(uriPermission);
                pageResult.setTotal(1);
                pageResult.setUriPermissions(uriPermissions);
            } else {
                pageResult.setTotal(0);
            }
            return pageResult;
        }
        int total = uriPermissionMapper.countByCriteria(criteria);
        pageResult.setTotal(total);
        if (total == 0) {
            return pageResult;
        }
        //检查页数是否合法
        checkPage(criteria, total);
        List<URIPermission> uriPermissions = uriPermissionMapper.findByCriteria(criteria);
        pageResult.setUriPermissions(uriPermissions);
        return pageResult;
    }

    public List<URIPermission> find(URIPermissionCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            List<URIPermission> uriPermissions = new ArrayList<>(16);
            URIPermission uriPermission = uriPermissionMapper.find(criteria.getId());
            if (uriPermission != null) {
                uriPermissions.add(uriPermission);
            }
            return uriPermissions;
        }
        return uriPermissionMapper.findByCriteria(criteria);
    }

    public URIPermission findOne(URIPermissionCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            return uriPermissionMapper.find(criteria.getId());
        }
        List<URIPermission> uriPermissions = uriPermissionMapper.findByCriteria(criteria);
        if (uriPermissions.size() == 0) {
            return null;
        }
        return uriPermissions.get(0);
    }

    public List<URIPermission> findByPermissionId(Long permissionId) {
        URIPermissionCriteria criteria = new URIPermissionCriteria();
        criteria.setUsePage(false);
        criteria.setPermissionId(permissionId);
        return uriPermissionMapper.findByCriteria(criteria);
    }

}