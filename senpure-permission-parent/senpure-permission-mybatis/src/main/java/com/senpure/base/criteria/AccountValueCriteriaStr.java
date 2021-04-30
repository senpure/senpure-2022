package com.senpure.base.criteria;

import com.senpure.base.criterion.CriteriaStr;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author senpure
 * @version 2020-5-22 16:52:02
 */
public class AccountValueCriteriaStr extends CriteriaStr implements Serializable {
    private static final long serialVersionUID = 846733638L;

    //(主键)
    @ApiModelProperty(value = "(主键)", dataType = "long", example = "666666", position = 5)
    private String id;
    //乐观锁，版本控制
    @ApiModelProperty(hidden = true )
    private String version;
    @ApiModelProperty(example = "key", position = 6)
    private String key;
    @ApiModelProperty(example = "value", position = 7)
    private String value;
    @ApiModelProperty(example = "description", position = 8)
    private String description;
    //(外键,modelName:Account,tableName:senpure_account)
    @ApiModelProperty(value = "(外键,modelName:Account,tableName:senpure_account)", dataType = "long", example = "666666", position = 9)
    private String accountId;
    //table [senpure_account_value][column = account_key] criteriaOrder
    @ApiModelProperty(value = "key 排序" , allowableValues = "ASC,DESC", position = 10)
    private String keyOrder;
    //table [senpure_account_value][column = account_id] criteriaOrder
    @ApiModelProperty(value = "accountId 排序" , allowableValues = "ASC,DESC", position = 11)
    private String accountIdOrder;

    public AccountValueCriteria toAccountValueCriteria() {
        AccountValueCriteria criteria = new AccountValueCriteria();
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
        if (key != null) {
            criteria.setKey(key);
        }
        //table [senpure_account_value][column = account_key] criteriaOrder
        if (keyOrder != null) {
            criteria.setKeyOrder(keyOrder);
        }
        if (value != null) {
            criteria.setValue(value);
        }
        if (description != null) {
            criteria.setDescription(description);
        }
        //(外键,modelName:Account,tableName:senpure_account)
        if (accountId != null) {
            criteria.setAccountId(Long.valueOf(accountId));
        }
        //table [senpure_account_value][column = account_id] criteriaOrder
        if (accountIdOrder != null) {
            criteria.setAccountIdOrder(accountIdOrder);
        }
        return criteria;
    }

    @Override
    protected void beforeStr(StringBuilder sb) {
        sb.append("AccountValueCriteriaStr{");
        if (id != null) {
            sb.append("id=").append(id).append(",");
        }
        if (version != null) {
            sb.append("version=").append(version).append(",");
        }
        if (key != null) {
            sb.append("key=").append(key).append(",");
        }
        if (value != null) {
            sb.append("value=").append(value).append(",");
        }
        if (description != null) {
            sb.append("description=").append(description).append(",");
        }
        if (accountId != null) {
            sb.append("accountId=").append(accountId).append(",");
        }
    }

    @Override
    protected void afterStr(StringBuilder sb) {
        if (keyOrder != null) {
            sb.append("keyOrder=").append(keyOrder).append(",");
        }
        if (accountIdOrder != null) {
            sb.append("accountIdOrder=").append(accountIdOrder).append(",");
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
    public AccountValueCriteriaStr setId(String id) {
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
    public AccountValueCriteriaStr setVersion(String version) {
        if (version != null && version.trim().length() == 0) {
            return this;
        }
        this.version = version;
        return this;
    }

    public String getKey() {
        return key;
    }


    public AccountValueCriteriaStr setKey(String key) {
        if (key != null && key.trim().length() == 0) {
            return this;
        }
        this.key = key;
        return this;
    }

    /**
     * get table [senpure_account_value][column = account_key] criteriaOrder
     *
     * @return
     */
    public String getKeyOrder() {
        return keyOrder;
    }

    /**
     * set table [senpure_account_value][column = account_key] criteriaOrder DESC||ASC
     *
     * @return
     */
    public AccountValueCriteriaStr setKeyOrder(String keyOrder) {
        if (keyOrder != null && keyOrder.trim().length() == 0) {
            this.keyOrder = null;
            return this;
        }
        this.keyOrder = keyOrder;
        return this;
    }

    public String getValue() {
        return value;
    }


    public AccountValueCriteriaStr setValue(String value) {
        if (value != null && value.trim().length() == 0) {
            return this;
        }
        this.value = value;
        return this;
    }

    public String getDescription() {
        return description;
    }


    public AccountValueCriteriaStr setDescription(String description) {
        if (description != null && description.trim().length() == 0) {
            return this;
        }
        this.description = description;
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
    public AccountValueCriteriaStr setAccountId(String accountId) {
        if (accountId != null && accountId.trim().length() == 0) {
            return this;
        }
        this.accountId = accountId;
        return this;
    }

    /**
     * get table [senpure_account_value][column = account_id] criteriaOrder
     *
     * @return
     */
    public String getAccountIdOrder() {
        return accountIdOrder;
    }

    /**
     * set table [senpure_account_value][column = account_id] criteriaOrder DESC||ASC
     *
     * @return
     */
    public AccountValueCriteriaStr setAccountIdOrder(String accountIdOrder) {
        if (accountIdOrder != null && accountIdOrder.trim().length() == 0) {
            this.accountIdOrder = null;
            return this;
        }
        this.accountIdOrder = accountIdOrder;
        return this;
    }

}