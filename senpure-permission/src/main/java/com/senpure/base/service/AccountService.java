package com.senpure.base.service;

import com.senpure.base.PermissionConstant;
import com.senpure.base.criteria.AccountCriteria;
import com.senpure.base.exception.OptimisticLockingFailureException;
import com.senpure.base.mapper.AccountMapper;
import com.senpure.base.model.Account;
import com.senpure.base.result.AccountPageResult;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author senpure
 * @version 2020-5-22 16:52:02
 */
@Service
@CacheConfig(cacheNames = "account")
public class AccountService extends BaseService {

    @Resource
    private AccountMapper accountMapper;

    @CacheEvict(key = "#id")
    public void clearCache(Long id) {
    }

    @CacheEvict(allEntries = true)
    public void clearCache() {
    }

    @Cacheable(key = "#id", unless = "#result == null")
    public Account find(Long id) {
        return accountMapper.find(id);
    }

    @Cacheable(key = "#id", unless = "#result == null")
    public Account findOnlyCache(Long id) {
        return null;
    }

    @CachePut(key = "#id", unless = "#result == null")
    public Account findSkipCache(Long id) {
        return accountMapper.find(id);
    }

    public int count() {
        return accountMapper.count();
    }

    public List<Account> findAll() {
        return accountMapper.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#id")
    public boolean delete(Long id) {
        int result = accountMapper.delete(id);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#criteria.id", allEntries = true)
    public int delete(AccountCriteria criteria) {
        return accountMapper.deleteByCriteria(criteria);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(Account account) {
        account.setId(idGenerator.nextId());
        int result = accountMapper.save(account);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int save(List<Account> accounts) {
        if (accounts == null || accounts.size() == 0) {
            return 0;
        }
        for (Account account : accounts) {
            account.setId(idGenerator.nextId());
        }
        return accountMapper.saveList(accounts);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(AccountCriteria criteria) {
        criteria.setId(idGenerator.nextId());
        int result = accountMapper.save(criteria.toAccount());
        return result == 1;
    }

    /**
     * 更新失败会抛出OptimisticLockingFailureException
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#account.id")
    public boolean update(Account account) {
        int updateCount = accountMapper.update(account);
        if (updateCount == 0) {
            throw new OptimisticLockingFailureException(account.getClass() + ",[" + account.getId() + "],版本号冲突,版本号[" + account.getVersion() + "]");
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
    public int update(AccountCriteria criteria) {
        int updateCount = accountMapper.updateByCriteria(criteria);
        if (updateCount == 0 && criteria.getVersion() != null
                && criteria.getId() != null) {
            throw new OptimisticLockingFailureException(criteria.getClass() + ",[" + criteria.getId() + "],版本号冲突,版本号[" + criteria.getVersion() + "]");
        }
        return updateCount;
    }

    @Transactional(readOnly = true)
    public AccountPageResult findPage(AccountCriteria criteria) {
        AccountPageResult result = AccountPageResult.success();
        //是否是主键查找
        if (criteria.getId() != null) {
            Account account = accountMapper.find(criteria.getId());
            if (account != null) {
                List<Account> accounts = new ArrayList<>(16);
                accounts.add(account);
                result.setTotal(1);
                result.setAccounts(accounts);
            } else {
                result.setTotal(0);
            }
            return result;
        }
        int total = accountMapper.countByCriteria(criteria);
        result.setTotal(total);
        if (total == 0) {
            return result;
        }
        //检查页数是否合法
        checkPage(criteria, total);
        List<Account> accounts = accountMapper.findByCriteria(criteria);
        result.setAccounts(accounts);
        return result;
    }

    public List<Account> find(AccountCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            List<Account> accounts = new ArrayList<>(16);
            Account account = accountMapper.find(criteria.getId());
            if (account != null) {
                accounts.add(account);
            }
            return accounts;
        }
        return accountMapper.findByCriteria(criteria);
    }

    public Account findOne(AccountCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            return accountMapper.find(criteria.getId());
        }
        List<Account> accounts = accountMapper.findByCriteria(criteria);
        if (accounts.size() == 0) {
            return null;
        }
        return accounts.get(0);
    }

    public List<Account> findByContainerId(Integer containerId) {
        AccountCriteria criteria = new AccountCriteria();
        criteria.setUsePage(false);
        criteria.setContainerId(containerId);
        return accountMapper.findByCriteria(criteria);
    }

    public Account findByAccount(String account) {
        AccountCriteria criteria = new AccountCriteria();
        criteria.setUsePage(false);
        criteria.setAccount(account);
        List<Account> accounts = accountMapper.findByCriteria(criteria);
        if (accounts.size() == 0) {
            return null;
        }
        return accounts.get(0);
    }

    public Account findByName(String name) {
        AccountCriteria criteria = new AccountCriteria();
        criteria.setUsePage(false);
        criteria.setName(name);
        List<Account> accounts = accountMapper.findByCriteria(criteria);
        if (accounts.size() == 0) {
            return null;
        }
        return accounts.get(0);
    }

    public Account defaultAccount(long createTime) {

        Account account = new Account();

        return defaultAccount(createTime, account);
    }

    public Account defaultAccount(long createTime, Account account) {
        account.setVersion(0);
        account.setCreateDate(new Date(createTime));
        account.setCreateTime(createTime);
        account.setOnline(false);
        account.setAccountState(PermissionConstant.ACCOUNT_STATE_NORMAL);
        return account;
    }

    public List<Account> findByLoginType(String loginType) {
        AccountCriteria criteria = new AccountCriteria();
        criteria.setUsePage(false);
        criteria.setLoginType(loginType);
        return accountMapper.findByCriteria(criteria);
    }

    public List<Account> findByLastLoginType(String lastLoginType) {
        AccountCriteria criteria = new AccountCriteria();
        criteria.setUsePage(false);
        criteria.setLastLoginType(lastLoginType);
        return accountMapper.findByCriteria(criteria);
    }

    public List<Account> findByLastLogoutType(String lastLogoutType) {
        AccountCriteria criteria = new AccountCriteria();
        criteria.setUsePage(false);
        criteria.setLastLogoutType(lastLogoutType);
        return accountMapper.findByCriteria(criteria);
    }

}