package com.senpure.base.service;

import com.senpure.base.model.AccountRole;
import com.senpure.base.criteria.AccountRoleCriteria;
import com.senpure.base.mapper.AccountRoleMapper;
import com.senpure.base.result.AccountRolePageResult;
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
public class AccountRoleService extends BaseService {

    private AccountRoleMapper accountRoleMapper;

    @Autowired
    public void setAccountRoleMapper(AccountRoleMapper accountRoleMapper) {
        this.accountRoleMapper = accountRoleMapper;
    }

    public AccountRole find(Long id) {
        return accountRoleMapper.find(id);
    }

    public int count() {
        return accountRoleMapper.count();
    }

    public List<AccountRole> findAll() {
        return accountRoleMapper.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        int result = accountRoleMapper.delete(id);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int delete(AccountRoleCriteria criteria) {
        return accountRoleMapper.deleteByCriteria(criteria);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(AccountRole accountRole) {
        accountRole.setId(idGenerator.nextId());
        int result = accountRoleMapper.save(accountRole);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int save(List<AccountRole> accountRoles) {
        if (accountRoles == null || accountRoles.size() == 0) {
            return 0;
        }
        for (AccountRole accountRole : accountRoles) {
            accountRole.setId(idGenerator.nextId());
        }
        return accountRoleMapper.saveList(accountRoles);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(AccountRoleCriteria criteria) {
        criteria.setId(idGenerator.nextId());
        int result = accountRoleMapper.save(criteria.toAccountRole());
        return result == 1;
    }

    /**
     * 更新失败会抛出OptimisticLockingFailureException
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean update(AccountRole accountRole) {
        int updateCount = accountRoleMapper.update(accountRole);
        if (updateCount == 0) {
            throw new OptimisticLockingFailureException(accountRole.getClass() + ",[" + accountRole.getId() + "],版本号冲突,版本号[" + accountRole.getVersion() + "]");
        }
        return true;
    }

    /**
     * 当版本号，和主键不为空时，更新失败会抛出OptimisticLockingFailureException
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int update(AccountRoleCriteria criteria) {
        int updateCount = accountRoleMapper.updateByCriteria(criteria);
        if (updateCount == 0 && criteria.getVersion() != null
                && criteria.getId() != null) {
            throw new OptimisticLockingFailureException(criteria.getClass() + ",[" + criteria.getId() + "],版本号冲突,版本号[" + criteria.getVersion() + "]");
        }
        return updateCount;
    }

    @Transactional(readOnly = true)
    public AccountRolePageResult findPage(AccountRoleCriteria criteria) {
        AccountRolePageResult pageResult = AccountRolePageResult.success();
        //是否是主键查找
        if (criteria.getId() != null) {
            AccountRole accountRole = accountRoleMapper.find(criteria.getId());
            if (accountRole != null) {
                List<AccountRole> accountRoles = new ArrayList<>(16);
                accountRoles.add(accountRole);
                pageResult.setTotal(1);
                pageResult.setAccountRoles(accountRoles);
            } else {
                pageResult.setTotal(0);
            }
            return pageResult;
        }
        int total = accountRoleMapper.countByCriteria(criteria);
        pageResult.setTotal(total);
        if (total == 0) {
            return pageResult;
        }
        //检查页数是否合法
        checkPage(criteria, total);
        List<AccountRole> accountRoles = accountRoleMapper.findByCriteria(criteria);
        pageResult.setAccountRoles(accountRoles);
        return pageResult;
    }

    public List<AccountRole> find(AccountRoleCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            List<AccountRole> accountRoles = new ArrayList<>(16);
            AccountRole accountRole = accountRoleMapper.find(criteria.getId());
            if (accountRole != null) {
                accountRoles.add(accountRole);
            }
            return accountRoles;
        }
        return accountRoleMapper.findByCriteria(criteria);
    }

    public AccountRole findOne(AccountRoleCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            return accountRoleMapper.find(criteria.getId());
        }
        List<AccountRole> accountRoles = accountRoleMapper.findByCriteria(criteria);
        if (accountRoles.size() == 0) {
            return null;
        }
        return accountRoles.get(0);
    }

    public List<AccountRole> findByAccountId(Long accountId) {
        AccountRoleCriteria criteria = new AccountRoleCriteria();
        criteria.setUsePage(false);
        criteria.setAccountId(accountId);
        return accountRoleMapper.findByCriteria(criteria);
    }

    public List<AccountRole> findByRoleId(Long roleId) {
        AccountRoleCriteria criteria = new AccountRoleCriteria();
        criteria.setUsePage(false);
        criteria.setRoleId(roleId);
        return accountRoleMapper.findByCriteria(criteria);
    }

}