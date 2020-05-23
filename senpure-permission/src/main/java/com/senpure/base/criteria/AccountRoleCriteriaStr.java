package com.senpure.base.criteria;

import com.senpure.base.criterion.CriteriaStr;
import com.senpure.base.struct.PatternDate;
import com.senpure.base.validator.DynamicDate;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author senpure
 * @version 2020-5-22 16:52:02
 */
public class AccountRoleCriteriaStr extends CriteriaStr implements Serializable {
    private static final long serialVersionUID = 220887430L;

    //(主键)
    @ApiModelProperty(value = "(主键)", dataType = "long", example = "666666", position = 5)
    private String id;
    //乐观锁，版本控制
    @ApiModelProperty(hidden = true )
    private String version;
    @ApiModelProperty(dataType = "date-time", example = "2020-05-22 00:00:00", position = 6)
    private String expiryDate;
    //expiryDate 时间格式
    @ApiModelProperty(value = "expiryDate 格式", example = "yyyy-MM-dd HH:mm:ss", position = 7)
    private String expiryDatePattern ;
    @DynamicDate
    private final PatternDate expiryDateValid = new PatternDate();
    //(外键,modelName:Account,tableName:senpure_account)
    @ApiModelProperty(value = "(外键,modelName:Account,tableName:senpure_account)", dataType = "long", example = "666666", position = 8)
    private String accountId;
    //(外键,modelName:Role,tableName:senpure_role)
    @ApiModelProperty(value = "(外键,modelName:Role,tableName:senpure_role)", dataType = "long", example = "666666", position = 9)
    private String roleId;
    @ApiModelProperty(value = "expiryDate 开始范围 (>=)", dataType = "date-time", example = "2020-05-22 00:00:00", position = 10)
    private String startExpiryDate;
    @ApiModelProperty(value = "expiryDate 结束范围 (<=)", dataType = "date-time", example = "2020-05-22 23:59:59", position = 11)
    private String endExpiryDate;
    @DynamicDate
    private final PatternDate startExpiryDateValid = new PatternDate();
    @DynamicDate
    private final PatternDate endExpiryDateValid = new PatternDate();
    //table [senpure_account_role][column = expiry_date] criteriaOrder
    @ApiModelProperty(value = "expiryDate 排序" , allowableValues = "ASC,DESC", position = 12)
    private String expiryDateOrder;
    //table [senpure_account_role][column = account_id] criteriaOrder
    @ApiModelProperty(value = "accountId 排序" , allowableValues = "ASC,DESC", position = 13)
    private String accountIdOrder;
    //table [senpure_account_role][column = role_id] criteriaOrder
    @ApiModelProperty(value = "roleId 排序" , allowableValues = "ASC,DESC", position = 14)
    private String roleIdOrder;

    public AccountRoleCriteria toAccountRoleCriteria() {
        AccountRoleCriteria criteria = new AccountRoleCriteria();
        criteria.setPage(Integer.parseInt(getPage()));
        criteria.setPageSize(Integer.parseInt(getPageSize()));
        //(主键)
        if (id != null) {
            criteria.setId(Long.valueOf(id));
        }
        //乐观锁，版本控制
        if (version != null) {
            criteria.setVersion(Integer.valueOf(version));
        }
        if (expiryDate != null) {
            criteria.setExpiryDate(expiryDateValid.getDate());
        }
        if (startExpiryDate != null) {
            criteria.setStartExpiryDate(startExpiryDateValid.getDate());
        }
        if (endExpiryDate != null) {
            criteria.setEndExpiryDate(endExpiryDateValid.getDate());
        }
        //table [senpure_account_role][column = expiry_date] criteriaOrder
        if (expiryDateOrder != null) {
            criteria.setExpiryDateOrder(expiryDateOrder);
        }
        //(外键,modelName:Account,tableName:senpure_account)
        if (accountId != null) {
            criteria.setAccountId(Long.valueOf(accountId));
        }
        //table [senpure_account_role][column = account_id] criteriaOrder
        if (accountIdOrder != null) {
            criteria.setAccountIdOrder(accountIdOrder);
        }
        //(外键,modelName:Role,tableName:senpure_role)
        if (roleId != null) {
            criteria.setRoleId(Long.valueOf(roleId));
        }
        //table [senpure_account_role][column = role_id] criteriaOrder
        if (roleIdOrder != null) {
            criteria.setRoleIdOrder(roleIdOrder);
        }
        return criteria;
    }

    @Override
    protected void rangeStr(StringBuilder sb) {
        if (startExpiryDate != null) {
            sb.append("startExpiryDate=").append(startExpiryDate).append(",");
        }
        if (endExpiryDate != null) {
            sb.append("endExpiryDate=").append(endExpiryDate).append(",");
        }
    }

    @Override
    protected void beforeStr(StringBuilder sb) {
        sb.append("AccountRoleCriteriaStr{");
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

    @Override
    protected void afterStr(StringBuilder sb) {
        if (expiryDateOrder != null) {
            sb.append("expiryDateOrder=").append(expiryDateOrder).append(",");
        }
        if (accountIdOrder != null) {
            sb.append("accountIdOrder=").append(accountIdOrder).append(",");
        }
        if (roleIdOrder != null) {
            sb.append("roleIdOrder=").append(roleIdOrder).append(",");
        }
        super.afterStr(sb);
    }

    /**
     * get (主键)
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * set (主键)
     *
     * @return
     */
    public AccountRoleCriteriaStr setId(String id) {
        if (id != null && id.trim().length() == 0) {
            return this;
        }
        this.id = id;
        return this;
    }

    /**
     * get 乐观锁，版本控制
     *
     * @return
     */
    public String getVersion() {
        return version;
    }

    /**
     * set 乐观锁，版本控制
     *
     * @return
     */
    public AccountRoleCriteriaStr setVersion(String version) {
        if (version != null && version.trim().length() == 0) {
            return this;
        }
        this.version = version;
        return this;
    }

    public String getExpiryDate() {
        return expiryDate;
    }


    public AccountRoleCriteriaStr setExpiryDate(String expiryDate) {
        if (expiryDate != null && expiryDate.trim().length() == 0) {
            return this;
        }
        this.expiryDate = expiryDate;
        this.expiryDateValid.setDateStr(expiryDate);
        return this;
    }

    public String getExpiryDatePattern() {
        return expiryDatePattern;
    }

    public AccountRoleCriteriaStr setExpiryDatePattern(String expiryDatePattern) {
        if (expiryDatePattern != null && expiryDatePattern.trim().length() == 0) {
            return this;
        }
        this.expiryDatePattern = expiryDatePattern;
        this.expiryDateValid.setPattern(expiryDatePattern);
        this.startExpiryDateValid.setPattern(expiryDatePattern);
        this.endExpiryDateValid.setPattern(expiryDatePattern);
        return this;
    }

    public String getStartExpiryDate() {
        return startExpiryDate;
    }

    public AccountRoleCriteriaStr setStartExpiryDate(String startExpiryDate) {
        if (startExpiryDate != null && startExpiryDate.trim().length() == 0) {
            return this;
        }
        this.startExpiryDate = startExpiryDate;
        this.startExpiryDateValid.setDateStr(startExpiryDate);
        return this;
    }

    public String getEndExpiryDate() {
        return endExpiryDate;
    }

    public AccountRoleCriteriaStr setEndExpiryDate(String endExpiryDate) {
        if (endExpiryDate != null && endExpiryDate.trim().length() == 0) {
            return this;
        }
        this.endExpiryDate = endExpiryDate;
        this.endExpiryDateValid.setDateStr(endExpiryDate);
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
    public AccountRoleCriteriaStr setExpiryDateOrder(String expiryDateOrder) {
        if (expiryDateOrder != null && expiryDateOrder.trim().length() == 0) {
            this.expiryDateOrder = null;
            return this;
        }
        this.expiryDateOrder = expiryDateOrder;
        return this;
    }

    /**
     * get (外键,modelName:Account,tableName:senpure_account)
     *
     * @return
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * set (外键,modelName:Account,tableName:senpure_account)
     *
     * @return
     */
    public AccountRoleCriteriaStr setAccountId(String accountId) {
        if (accountId != null && accountId.trim().length() == 0) {
            return this;
        }
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
    public AccountRoleCriteriaStr setAccountIdOrder(String accountIdOrder) {
        if (accountIdOrder != null && accountIdOrder.trim().length() == 0) {
            this.accountIdOrder = null;
            return this;
        }
        this.accountIdOrder = accountIdOrder;
        return this;
    }

    /**
     * get (外键,modelName:Role,tableName:senpure_role)
     *
     * @return
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * set (外键,modelName:Role,tableName:senpure_role)
     *
     * @return
     */
    public AccountRoleCriteriaStr setRoleId(String roleId) {
        if (roleId != null && roleId.trim().length() == 0) {
            return this;
        }
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
    public AccountRoleCriteriaStr setRoleIdOrder(String roleIdOrder) {
        if (roleIdOrder != null && roleIdOrder.trim().length() == 0) {
            this.roleIdOrder = null;
            return this;
        }
        this.roleIdOrder = roleIdOrder;
        return this;
    }

}