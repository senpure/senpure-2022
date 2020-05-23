package com.senpure.base.model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author senpure
 * @version 2020-5-22 16:52:02
 */
@ApiModel
public class RoleValue implements Serializable {
    private static final long serialVersionUID = 1099570973L;

    //(主键)
    private Long id;
    //乐观锁，版本控制
    @ApiModelProperty(hidden = true )
    private Integer version;
    @ApiModelProperty(example = "key", position = 1)
    private String key;
    @ApiModelProperty(example = "value", position = 2)
    private String value;
    @ApiModelProperty(example = "description", position = 3)
    private String description;
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
    public RoleValue setId(Long id) {
        this.id = id;
        return this;
    }


    public String getKey() {
        return key;
    }


    public RoleValue setKey(String key) {
        this.key = key;
        return this;
    }


    public String getValue() {
        return value;
    }


    public RoleValue setValue(String value) {
        this.value = value;
        return this;
    }


    public String getDescription() {
        return description;
    }


    public RoleValue setDescription(String description) {
        this.description = description;
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
    public RoleValue setRoleId(Long roleId) {
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
    public RoleValue setVersion(Integer version) {
        this.version = version;
        return this;
    }


    @Override
    public String toString() {
        return "RoleValue{"
                + "id=" + id
                + ",version=" + version
                + ",key=" + key
                + ",value=" + value
                + ",description=" + description
                + ",roleId=" + roleId
                + "}";
    }

}