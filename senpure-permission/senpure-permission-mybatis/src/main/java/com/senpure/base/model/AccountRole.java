package com.senpure.base.model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author senpure
 * @version 2020-5-22 16:52:02
 */
@ApiModel
public class AccountRole implements Serializable {
    private static final long serialVersionUID = 220887430L;

    //(主键)
    private Long id;
    //乐观锁，版本控制
    @ApiModelProperty(hidden = true )
    private Integer version;
    @ApiModelProperty(dataType = "long", example = "1590076800000", position = 1)
    private Long expiryTime;
    @ApiModelProperty(dataType = "date-time", example = "2020-05-22 00:00:00", position = 2)
    private Date expiryDate;
    //(外键,modelName:Account,tableName:senpure_account)
    @ApiModelProperty(value = "(外键,modelName:Account,tableName:senpure_account)", dataType = "long", example = "666666", position = 3)
    private Long accountId;
    //(外键,modelName:Role,tableName:senpure_role)
    @ApiModelProperty(value = "(外键,modelName:Role,tableName:senpure_role)", dataType = "long", example = "666666", position = 4)
    private Long roleId;

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
    public AccountRole setId(Long id) {
        this.id = id;
        return this;
    }


    public Long getExpiryTime() {
        return expiryTime;
    }


    public AccountRole setExpiryTime(Long expiryTime) {
        this.expiryTime = expiryTime;
        return this;
    }


    public Date getExpiryDate() {
        return expiryDate;
    }


    public AccountRole setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
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
    public AccountRole setAccountId(Long accountId) {
        this.accountId = accountId;
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
    public AccountRole setRoleId(Long roleId) {
        this.roleId = roleId;
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
    public AccountRole setVersion(Integer version) {
        this.version = version;
        return this;
    }


    @Override
    public String toString() {
        return "AccountRole{"
                + "id=" + id
                + ",version=" + version
                + ",expiryTime=" + expiryTime
                + ",expiryDate=" + expiryDate
                + ",accountId=" + accountId
                + ",roleId=" + roleId
                + "}";
    }

}