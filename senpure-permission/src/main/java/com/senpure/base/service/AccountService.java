package com.senpure.base.service;

import com.senpure.base.model.Account;
import com.senpure.base.criteria.AccountCriteria;
import com.senpure.base.mapper.AccountMapper;
import com.senpure.base.result.AccountPageResult;
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
public class AccountService extends BaseService {

    private AccountMapper accountMapper;

    @Autowired
    public void setAccountMapper(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    public Account find(Long id) {
        return accountMapper.find(id);
    }

    public int count() {
        return accountMapper.count();
    }

    public List<Account> findAll() {
        return accountMapper.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        int result = accountMapper.delete(id);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
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
        AccountPageResult pageResult = AccountPageResult.success();
        //是否是主键查找
        if (criteria.getId() != null) {
            Account account = accountMapper.find(criteria.getId());
            if (account != null) {
                List<Account> accounts = new ArrayList<>(16);
                accounts.add(account);
                pageResult.setTotal(1);
                pageResult.setAccounts(accounts);
            } else {
                pageResult.setTotal(0);
            }
            return pageResult;
        }
        int total = accountMapper.countByCriteria(criteria);
        pageResult.setTotal(total);
        if (total == 0) {
            return pageResult;
        }
        //检查页数是否合法
        checkPage(criteria, total);
        List<Account> accounts = accountMapper.findByCriteria(criteria);
        pageResult.setAccounts(accounts);
        return pageResult;
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

}