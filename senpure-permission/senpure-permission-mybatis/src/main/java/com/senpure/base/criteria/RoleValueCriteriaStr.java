package com.senpure.base.criteria;

import com.senpure.base.criterion.CriteriaStr;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author senpure
 * @version 2020-5-22 16:52:02
 */
public class RoleValueCriteriaStr extends CriteriaStr implements Serializable {
    private static final long serialVersionUID = 1099570973L;

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
    //(外键,modelName:Role,tableName:senpure_role)
    @ApiModelProperty(value = "(外键,modelName:Role,tableName:senpure_role)", dataType = "long", example = "666666", position = 9)
    private String roleId;
    //table [senpure_role_value][column = role_key] criteriaOrder
    @ApiModelProperty(value = "key 排序" , allowableValues = "ASC,DESC", position = 10)
    private String keyOrder;
    //table [senpure_role_value][column = role_id] criteriaOrder
    @ApiModelProperty(value = "roleId 排序" , allowableValues = "ASC,DESC", position = 11)
    private String roleIdOrder;

    public RoleValueCriteria toRoleValueCriteria() {
        RoleValueCriteria criteria = new RoleValueCriteria();
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
        //table [senpure_role_value][column = role_key] criteriaOrder
        if (keyOrder != null) {
            criteria.setKeyOrder(keyOrder);
        }
        if (value != null) {
            criteria.setValue(value);
        }
        if (description != null) {
            criteria.setDescription(description);
        }
        //(外键,modelName:Role,tableName:senpure_role)
        if (roleId != null) {
            criteria.setRoleId(Long.valueOf(roleId));
        }
        //table [senpure_role_value][column = role_id] criteriaOrder
        if (roleIdOrder != null) {
            criteria.setRoleIdOrder(roleIdOrder);
        }
        return criteria;
    }

    @Override
    protected void beforeStr(StringBuilder sb) {
        sb.append("RoleValueCriteriaStr{");
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
        if (roleId != null) {
            sb.append("roleId=").append(roleId).append(",");
        }
    }

    @Override
    protected void afterStr(StringBuilder sb) {
        if (keyOrder != null) {
            sb.append("keyOrder=").append(keyOrder).append(",");
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
    public RoleValueCriteriaStr setId(String id) {
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
    public RoleValueCriteriaStr setVersion(String version) {
        if (version != null && version.trim().length() == 0) {
            return this;
        }
        this.version = version;
        return this;
    }

    public String getKey() {
        return key;
    }


    public RoleValueCriteriaStr setKey(String key) {
        if (key != null && key.trim().length() == 0) {
            return this;
        }
        this.key = key;
        return this;
    }

    /**
     * get table [senpure_role_value][column = role_key] criteriaOrder
     *
     * @return
     */
    public String getKeyOrder() {
        return keyOrder;
    }

    /**
     * set table [senpure_role_value][column = role_key] criteriaOrder DESC||ASC
     *
     * @return
     */
    public RoleValueCriteriaStr setKeyOrder(String keyOrder) {
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


    public RoleValueCriteriaStr setValue(String value) {
        if (value != null && value.trim().length() == 0) {
            return this;
        }
        this.value = value;
        return this;
    }

    public String getDescription() {
        return description;
    }


    public RoleValueCriteriaStr setDescription(String description) {
        if (description != null && description.trim().length() == 0) {
            return this;
        }
        this.description = description;
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
    public RoleValueCriteriaStr setRoleId(String roleId) {
        if (roleId != null && roleId.trim().length() == 0) {
            return this;
        }
        this.roleId = roleId;
        return this;
    }

    /**
     * get table [senpure_role_value][column = role_id] criteriaOrder
     *
     * @return
     */
    public String getRoleIdOrder() {
        return roleIdOrder;
    }

    /**
     * set table [senpure_role_value][column = role_id] criteriaOrder DESC||ASC
     *
     * @return
     */
    public RoleValueCriteriaStr setRoleIdOrder(String roleIdOrder) {
        if (roleIdOrder != null && roleIdOrder.trim().length() == 0) {
            this.roleIdOrder = null;
            return this;
        }
        this.roleIdOrder = roleIdOrder;
        return this;
    }

}