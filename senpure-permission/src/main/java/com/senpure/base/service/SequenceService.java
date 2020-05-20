package com.senpure.base.service;

import com.senpure.base.model.Sequence;
import com.senpure.base.criteria.SequenceCriteria;
import com.senpure.base.mapper.SequenceMapper;
import com.senpure.base.result.SequencePageResult;
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
public class SequenceService extends BaseService {

    private SequenceMapper sequenceMapper;

    @Autowired
    public void setSequenceMapper(SequenceMapper sequenceMapper) {
        this.sequenceMapper = sequenceMapper;
    }

    public Sequence find(Long id) {
        return sequenceMapper.find(id);
    }

    public int count() {
        return sequenceMapper.count();
    }

    public List<Sequence> findAll() {
        return sequenceMapper.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        int result = sequenceMapper.delete(id);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
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
        SequencePageResult pageResult = SequencePageResult.success();
        //是否是主键查找
        if (criteria.getId() != null) {
            Sequence sequence = sequenceMapper.find(criteria.getId());
            if (sequence != null) {
                List<Sequence> sequences = new ArrayList<>(16);
                sequences.add(sequence);
                pageResult.setTotal(1);
                pageResult.setSequences(sequences);
            } else {
                pageResult.setTotal(0);
            }
            return pageResult;
        }
        int total = sequenceMapper.countByCriteria(criteria);
        pageResult.setTotal(total);
        if (total == 0) {
            return pageResult;
        }
        //检查页数是否合法
        checkPage(criteria, total);
        List<Sequence> sequences = sequenceMapper.findByCriteria(criteria);
        pageResult.setSequences(sequences);
        return pageResult;
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

}