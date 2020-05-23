package com.senpure.base.service;

import com.senpure.base.model.Sequence;
import com.senpure.base.result.SequencePageResult;
import com.senpure.base.criteria.SequenceCriteria;
import com.senpure.base.mapper.SequenceMapper;
import com.senpure.base.exception.OptimisticLockingFailureException;
import com.senpure.base.util.Assert;
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
@CacheConfig(cacheNames = "sequence")
public class SequenceService extends BaseService {

    @Resource
    private SequenceMapper sequenceMapper;

    @CacheEvict(key = "#id")
    public void clearCache(Long id) {
    }

    @CacheEvict(allEntries = true)
    public void clearCache() {
    }

    @Cacheable(key = "#id", unless = "#result == null")
    public Sequence find(Long id) {
        return sequenceMapper.find(id);
    }

    @Cacheable(key = "#id", unless = "#result == null")
    public Sequence findOnlyCache(Long id) {
        return null;
    }

    @CachePut(key = "#id", unless = "#result == null")
    public Sequence findSkipCache(Long id) {
        return sequenceMapper.find(id);
    }

    public int count() {
        return sequenceMapper.count();
    }

    public List<Sequence> findAll() {
        return sequenceMapper.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#id")
    public boolean delete(Long id) {
        int result = sequenceMapper.delete(id);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#criteria.id", allEntries = true)
    public int delete(SequenceCriteria criteria) {
        return sequenceMapper.deleteByCriteria(criteria);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(Sequence sequence) {
        sequence.setId(idGenerator.nextId());
        int result = sequenceMapper.save(sequence);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int save(List<Sequence> sequences) {
        if (sequences == null || sequences.size() == 0) {
            return 0;
        }
        for (Sequence sequence : sequences) {
            sequence.setId(idGenerator.nextId());
        }
        return sequenceMapper.saveList(sequences);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(SequenceCriteria criteria) {
        criteria.setId(idGenerator.nextId());
        int result = sequenceMapper.save(criteria.toSequence());
        return result == 1;
    }

    /**
     * 更新失败会抛出OptimisticLockingFailureException
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#sequence.id")
    public boolean update(Sequence sequence) {
        int updateCount = sequenceMapper.update(sequence);
        if (updateCount == 0) {
            throw new OptimisticLockingFailureException(sequence.getClass() + ",[" + sequence.getId() + "],版本号冲突,版本号[" + sequence.getVersion() + "]");
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
    public int update(SequenceCriteria criteria) {
        int updateCount = sequenceMapper.updateByCriteria(criteria);
        if (updateCount == 0 && criteria.getVersion() != null
                && criteria.getId() != null) {
            throw new OptimisticLockingFailureException(criteria.getClass() + ",[" + criteria.getId() + "],版本号冲突,版本号[" + criteria.getVersion() + "]");
        }
        return updateCount;
    }

    @Transactional(readOnly = true)
    public SequencePageResult findPage(SequenceCriteria criteria) {
        SequencePageResult result = SequencePageResult.success();
        //是否是主键查找
        if (criteria.getId() != null) {
            Sequence sequence = sequenceMapper.find(criteria.getId());
            if (sequence != null) {
                List<Sequence> sequences = new ArrayList<>(16);
                sequences.add(sequence);
                result.setTotal(1);
                result.setSequences(sequences);
            } else {
                result.setTotal(0);
            }
            return result;
        }
        int total = sequenceMapper.countByCriteria(criteria);
        result.setTotal(total);
        if (total == 0) {
            return result;
        }
        //检查页数是否合法
        checkPage(criteria, total);
        List<Sequence> sequences = sequenceMapper.findByCriteria(criteria);
        result.setSequences(sequences);
        return result;
    }

    public List<Sequence> find(SequenceCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            List<Sequence> sequences = new ArrayList<>(16);
            Sequence sequence = sequenceMapper.find(criteria.getId());
            if (sequence != null) {
                sequences.add(sequence);
            }
            return sequences;
        }
        return sequenceMapper.findByCriteria(criteria);
    }

    public Sequence findOne(SequenceCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            return sequenceMapper.find(criteria.getId());
        }
        List<Sequence> sequences = sequenceMapper.findByCriteria(criteria);
        if (sequences.size() == 0) {
            return null;
        }
        return sequences.get(0);
    }

    public List<Sequence> findByType(String type) {
        SequenceCriteria criteria = new SequenceCriteria();
        criteria.setUsePage(false);
        criteria.setType(type);
        return sequenceMapper.findByCriteria(criteria);
    }

    @Transactional
    public int currentSequence(String type) {
        SequenceCriteria criteria = new SequenceCriteria();
        criteria.setType(type);
        List<Sequence> sequences = sequenceMapper.findByCriteria(criteria);
        Sequence sequence = null;
        if (sequences.size() == 0) {
            sequence = new Sequence();
            sequence.setSequence(1);
            sequence.setType(type);
            sequence.setDigit(6);
            sequence.setPrefix("");
            sequence.setSuffix("");
            sequence.setSpan(1);
            Assert.error(type + "不存在，请检查仔细");
        } else {
            sequence = sequences.get(0);
        }
        int currentSequence = sequence.getSequence();
        sequence.setSequence(currentSequence + sequence.getSpan());
        update(sequence);
        return currentSequence;
    }
}