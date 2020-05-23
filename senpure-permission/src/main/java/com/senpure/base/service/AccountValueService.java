package com.senpure.base.service;

import com.senpure.base.model.AccountValue;
import com.senpure.base.result.AccountValuePageResult;
import com.senpure.base.criteria.AccountValueCriteria;
import com.senpure.base.mapper.AccountValueMapper;
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
@CacheConfig(cacheNames = "accountValue")
public class AccountValueService extends BaseService {

    @Resource
    private AccountValueMapper accountValueMapper;

    @CacheEvict(key = "#id")
    public void clearCache(Long id) {
    }

    @CacheEvict(allEntries = true)
    public void clearCache() {
    }

    @Cacheable(key = "#id", unless = "#result == null")
    public AccountValue find(Long id) {
        return accountValueMapper.find(id);
    }

    @Cacheable(key = "#id", unless = "#result == null")
    public AccountValue findOnlyCache(Long id) {
        return null;
    }

    @CachePut(key = "#id", unless = "#result == null")
    public AccountValue findSkipCache(Long id) {
        return accountValueMapper.find(id);
    }

    public int count() {
        return accountValueMapper.count();
    }

    public List<AccountValue> findAll() {
        return accountValueMapper.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#id")
    public boolean delete(Long id) {
        int result = accountValueMapper.delete(id);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#criteria.id", allEntries = true)
    public int delete(AccountValueCriteria criteria) {
        return accountValueMapper.deleteByCriteria(criteria);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(AccountValue accountValue) {
        accountValue.setId(idGenerator.nextId());
        int result = accountValueMapper.save(accountValue);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int save(List<AccountValue> accountValues) {
        if (accountValues == null || accountValues.size() == 0) {
            return 0;
        }
        for (AccountValue accountValue : accountValues) {
            accountValue.setId(idGenerator.nextId());
        }
        return accountValueMapper.saveList(accountValues);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(AccountValueCriteria criteria) {
        criteria.setId(idGenerator.nextId());
        int result = accountValueMapper.save(criteria.toAccountValue());
        return result == 1;
    }

    /**
     * 更新失败会抛出OptimisticLockingFailureException
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#accountValue.id")
    public boolean update(AccountValue accountValue) {
        int updateCount = accountValueMapper.update(accountValue);
        if (updateCount == 0) {
            throw new OptimisticLockingFailureException(accountValue.getClass() + ",[" + accountValue.getId() + "],版本号冲突,版本号[" + accountValue.getVersion() + "]");
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
    public int update(AccountValueCriteria criteria) {
        int updateCount = accountValueMapper.updateByCriteria(criteria);
        if (updateCount == 0 && criteria.getVersion() != null
                && criteria.getId() != null) {
            throw new OptimisticLockingFailureException(criteria.getClass() + ",[" + criteria.getId() + "],版本号冲突,版本号[" + criteria.getVersion() + "]");
        }
        return updateCount;
    }

    @Transactional(readOnly = true)
    public AccountValuePageResult findPage(AccountValueCriteria criteria) {
        AccountValuePageResult result = AccountValuePageResult.success();
        //是否是主键查找
        if (criteria.getId() != null) {
            AccountValue accountValue = accountValueMapper.find(criteria.getId());
            if (accountValue != null) {
                List<AccountValue> accountValues = new ArrayList<>(16);
                accountValues.add(accountValue);
                result.setTotal(1);
                result.setAccountValues(accountValues);
            } else {
                result.setTotal(0);
            }
            return result;
        }
        int total = accountValueMapper.countByCriteria(criteria);
        result.setTotal(total);
        if (total == 0) {
            return result;
        }
        //检查页数是否合法
        checkPage(criteria, total);
        List<AccountValue> accountValues = accountValueMapper.findByCriteria(criteria);
        result.setAccountValues(accountValues);
        return result;
    }

    public List<AccountValue> find(AccountValueCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            List<AccountValue> accountValues = new ArrayList<>(16);
            AccountValue accountValue = accountValueMapper.find(criteria.getId());
            if (accountValue != null) {
                accountValues.add(accountValue);
            }
            return accountValues;
        }
        return accountValueMapper.findByCriteria(criteria);
    }

    public AccountValue findOne(AccountValueCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            return accountValueMapper.find(criteria.getId());
        }
        List<AccountValue> accountValues = accountValueMapper.findByCriteria(criteria);
        if (accountValues.size() == 0) {
            return null;
        }
        return accountValues.get(0);
    }

    public List<AccountValue> findByAccountId(Long accountId) {
        AccountValueCriteria criteria = new AccountValueCriteria();
        criteria.setUsePage(false);
        criteria.setAccountId(accountId);
        return accountValueMapper.findByCriteria(criteria);
    }

    public AccountValue findByKey(String key) {
        AccountValueCriteria criteria = new AccountValueCriteria();
        criteria.setUsePage(false);
        criteria.setKey(key);
        List<AccountValue> accountValues = accountValueMapper.findByCriteria(criteria);
        if (accountValues.size() == 0) {
            return null;
        }
        return accountValues.get(0);
    }

}