package com.senpure.base.service;

import com.senpure.base.PermissionConstant;
import com.senpure.base.criteria.ContainerCriteria;
import com.senpure.base.exception.OptimisticLockingFailureException;
import com.senpure.base.mapper.ContainerMapper;
import com.senpure.base.model.Container;
import com.senpure.base.result.ContainerPageResult;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author senpure
 * @version 2020-5-22 16:52:02
 */
@Service
@CacheConfig(cacheNames = "container")
public class ContainerService extends BaseService {

    @Resource
    private ContainerMapper containerMapper;
    @Resource
    private SequenceService sequenceService;

    @CacheEvict(key = "#id")
    public void clearCache(Integer id) {
    }

    @CacheEvict(allEntries = true)
    public void clearCache() {
    }

    @Cacheable(key = "#id", unless = "#result == null")
    public Container find(Integer id) {
        return containerMapper.find(id);
    }

    @Cacheable(key = "#id", unless = "#result == null")
    public Container findOnlyCache(Integer id) {
        return null;
    }

    @CachePut(key = "#id", unless = "#result == null")
    public Container findSkipCache(Integer id) {
        return containerMapper.find(id);
    }

    public int count() {
        return containerMapper.count();
    }

    public List<Container> findAll() {
        return containerMapper.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#id")
    public boolean delete(Integer id) {
        int result = containerMapper.delete(id);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#criteria.id", allEntries = true)
    public int delete(ContainerCriteria criteria) {
        return containerMapper.deleteByCriteria(criteria);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(Container container) {
        container.setId(sequenceService.currentSequence(PermissionConstant.SEQUENCE_CONTAINER_ID));
        int result = containerMapper.save(container);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int save(List<Container> containers) {
        if (containers == null || containers.size() == 0) {
            return 0;
        }
        for (Container container : containers) {
            //container.setId();
            checkPrimaryKey(container, container.getId());
        }
        return containerMapper.saveList(containers);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(ContainerCriteria criteria) {
        checkPrimaryKey(criteria, criteria.getId());
        int result = containerMapper.save(criteria.toContainer());
        return result == 1;
    }

    /**
     * 更新失败会抛出OptimisticLockingFailureException
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#container.id")
    public boolean update(Container container) {
        int updateCount = containerMapper.update(container);
        if (updateCount == 0) {
            throw new OptimisticLockingFailureException(container.getClass() + ",[" + container.getId() + "],版本号冲突,版本号[" + container.getVersion() + "]");
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
    public int update(ContainerCriteria criteria) {
        int updateCount = containerMapper.updateByCriteria(criteria);
        if (updateCount == 0 && criteria.getVersion() != null
                && criteria.getId() != null) {
            throw new OptimisticLockingFailureException(criteria.getClass() + ",[" + criteria.getId() + "],版本号冲突,版本号[" + criteria.getVersion() + "]");
        }
        return updateCount;
    }

    @Transactional(readOnly = true)
    public ContainerPageResult findPage(ContainerCriteria criteria) {
        ContainerPageResult result = ContainerPageResult.success();
        //是否是主键查找
        if (criteria.getId() != null) {
            Container container = containerMapper.find(criteria.getId());
            if (container != null) {
                List<Container> containers = new ArrayList<>(16);
                containers.add(container);
                result.setTotal(1);
                result.setContainers(containers);
            } else {
                result.setTotal(0);
            }
            return result;
        }
        int total = containerMapper.countByCriteria(criteria);
        result.setTotal(total);
        if (total == 0) {
            return result;
        }
        //检查页数是否合法
        checkPage(criteria, total);
        List<Container> containers = containerMapper.findByCriteria(criteria);
        result.setContainers(containers);
        return result;
    }

    public List<Container> find(ContainerCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            List<Container> containers = new ArrayList<>(16);
            Container container = containerMapper.find(criteria.getId());
            if (container != null) {
                containers.add(container);
            }
            return containers;
        }
        return containerMapper.findByCriteria(criteria);
    }

    public Container findOne(ContainerCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            return containerMapper.find(criteria.getId());
        }
        List<Container> containers = containerMapper.findByCriteria(criteria);
        if (containers.size() == 0) {
            return null;
        }
        return containers.get(0);
    }

    public List<Container> findByParentId(Integer parentId) {
        ContainerCriteria criteria = new ContainerCriteria();
        criteria.setUsePage(false);
        criteria.setParentId(parentId);
        return containerMapper.findByCriteria(criteria);
    }

    public Container findByName(String name) {
        ContainerCriteria criteria = new ContainerCriteria();
        criteria.setUsePage(false);
        criteria.setName(name);
        List<Container> containers = containerMapper.findByCriteria(criteria);
        if (containers.size() == 0) {
            return null;
        }
        return containers.get(0);
    }

}