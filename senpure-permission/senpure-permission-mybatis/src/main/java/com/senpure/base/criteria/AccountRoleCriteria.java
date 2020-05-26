package com.senpure.base.criteria;

import com.senpure.base.criterion.Criteria;
import com.senpure.base.model.AccountRole;
import com.senpure.base.util.DateFormatUtil;

import java.util.Date;
import java.io.Serializable;

/**
 * @author senpure
 * @version 2020-5-22 16:52:02
 */
public class AccountRoleCriteria extends Criteria implements Serializable {
    private static final long serialVersionUID = 220887430L;

    //(主键)
    private Long id;
    //乐观锁，版本控制
    private Integer version;
    private Date expiryDate;
    private Date startExpiryDate;
    private Date endExpiryDate;
    //table [senpure_account_role][column = expiry_date] criteriaOrder
    private String expiryDateOrder;
    //(外键,modelName:Account,tableName:senpure_account)
    private Long accountId;
    //table [senpure_account_role][column = account_id] criteriaOrder
    private String accountIdOrder;
    //(外键,modelName:Role,tableName:senpure_role)
    private Long roleId;
    //table [senpure_account_role][column = role_id] criteriaOrder
    private String roleIdOrder;

    public static AccountRole toAccountRole(AccountRoleCriteria criteria, AccountRole accountRole) {
        accountRole.setId(criteria.getId());
        accountRole.setExpiryDate(criteria.getExpiryDate());
        if (criteria.getExpiryDate() != null) {
            accountRole.setExpiryTime(criteria.getExpiryDate().getTime());
        }
        accountRole.setAccountId(criteria.getAccountId());
        accountRole.setRoleId(criteria.getRoleId());
        accountRole.setVersion(criteria.getVersion());
        return accountRole;
    }

    public AccountRole toAccountRole() {
        AccountRole accountRole = new AccountRole();
        return toAccountRole(this, accountRole);
    }

    /**
     * 将AccountRoleCriteria 的有效值(不为空),赋值给 AccountRole
     *
     * @return AccountRole
     */
    public AccountRole effective(AccountRole accountRole) {
        if (getId() != null) {
            accountRole.setId(getId());
        }
        if (getExpiryDate() != null) {
            accountRole.setExpiryDate(getExpiryDate());
        }
        if (getAccountId() != null) {
            accountRole.setAccountId(getAccountId());
        }
        if (getRoleId() != null) {
            accountRole.setRoleId(getRoleId());
        }
        if (getVersion() != null) {
            accountRole.setVersion(getVersion());
        }
        return accountRole;
    }

    @Override
    protected void rangeStr(StringBuilder sb) {
        if (startExpiryDate != null) {
            sb.append("startExpiryDate=").append(DateFormatUtil.getDateFormat(datePattern).format(startExpiryDate)).append(",");
        }
        if (endExpiryDate != null) {
            sb.append("endExpiryDate=").append(DateFormatUtil.getDateFormat(datePattern).format(endExpiryDate)).append(",");
        }
    }

    @Override
    protected void beforeStr(StringBuilder sb) {
        sb.append("AccountRoleCriteria{");
        if (id != null) {
            sb.append("id=").append(id).append(",");
        }
        if (version != null) {
            sb.append("version=").append(version).append(",");
        }
        if (expiryDate != null) {
            sb.append("expiryDate=").append(expiryDate).append(",");
        }
        if (accountId != null) {
            sb.append("accountId=").append(accountId).append(",");
        }
        if (roleId != null) {
            sb.append("roleId=").append(roleId).append(",");
        }
    }

    /**
     * get (主键)
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * set (主键)
     *
     * @return
     */
    public AccountRoleCriteria setId(Long id) {
        this.id = id;
        return this;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }


    public AccountRoleCriteria setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public Date getStartExpiryDate() {
        return startExpiryDate;
    }

    public AccountRoleCriteria setStartExpiryDate(Date startExpiryDate) {
        this.startExpiryDate = startExpiryDate;
        return this;
    }

    public Date getEndExpiryDate() {
        return endExpiryDate;
    }

    public AccountRoleCriteria setEndExpiryDate(Date endExpiryDate) {
        this.endExpiryDate = endExpiryDate;
        return this;
    }

    /**
     * get table [senpure_account_role][column = expiry_date] criteriaOrder
     *
     * @return
     */
    public String getExpiryDateOrder() {
        return expiryDateOrder;
    }

    /**
     * set table [senpure_account_role][column = expiry_date] criteriaOrder DESC||ASC
     *
     * @return
     */
    public AccountRoleCriteria setExpiryDateOrder(String expiryDateOrder) {
        this.expiryDateOrder = expiryDateOrder;
        putSort("expiry_date", expiryDateOrder);
        return this;
    }

    /**
     * get (外键,modelName:Account,tableName:senpure_account)
     *
     * @return
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * set (外键,modelName:Account,tableName:senpure_account)
     *
     * @return
     */
    public AccountRoleCriteria setAccountId(Long accountId) {
        this.accountId = accountId;
        return this;
    }

    /**
     * get table [senpure_account_role][column = account_id] criteriaOrder
     *
     * @return
     */
    public String getAccountIdOrder() {
        return accountIdOrder;
    }

    /**
     * set table [senpure_account_role][column = account_id] criteriaOrder DESC||ASC
     *
     * @return
     */
    public AccountRoleCriteria setAccountIdOrder(String accountIdOrder) {
        this.accountIdOrder = accountIdOrder;
        putSort("account_id", accountIdOrder);
        return this;
    }

    /**
     * get (外键,modelName:Role,tableName:senpure_role)
     *
     * @return
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * set (外键,modelName:Role,tableName:senpure_role)
     *
     * @return
     */
    public AccountRoleCriteria setRoleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    /**
     * get table [senpure_account_role][column = role_id] criteriaOrder
     *
     * @return
     */
    public String getRoleIdOrder() {
        return roleIdOrder;
    }

    /**
     * set table [senpure_account_role][column = role_id] criteriaOrder DESC||ASC
     *
     * @return
     */
    public AccountRoleCriteria setRoleIdOrder(String roleIdOrder) {
        this.roleIdOrder = roleIdOrder;
        putSort("role_id", roleIdOrder);
        return this;
    }

    /**
     * get 乐观锁，版本控制
     *
     * @return
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * set 乐观锁，版本控制
     *
     * @return
     */
    public AccountRoleCriteria setVersion(Integer version) {
        this.version = version;
        return this;
    }

}