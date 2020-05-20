package com.senpure.base.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author senpure
 * @version 2020-5-20 18:21:45
 */
@ApiModel
public class PermissionMenu implements Serializable {
    private static final long serialVersionUID = 343182696L;

    //(主键)
    private Long id;
    //乐观锁，版本控制
    @ApiModelProperty(hidden = true )
    private Integer version;
    @ApiModelProperty(dataType = "int", example = "666666", position = 1)
    private Integer menuId;
    @ApiModelProperty(example = "permissionName", position = 2)
    private String permissionName;
    @ApiModelProperty(dataType = "boolean", position = 3)
    private Boolean dataBaseUpdate;

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
    public PermissionMenu setId(Long id) {
        this.id = id;
        return this;
    }


    public Integer getMenuId() {
        return menuId;
    }


    public PermissionMenu setMenuId(Integer menuId) {
        this.menuId = menuId;
        return this;
    }


    public String getPermissionName() {
        return permissionName;
    }


    public PermissionMenu setPermissionName(String permissionName) {
        this.permissionName = permissionName;
        return this;
    }


    public Boolean getDataBaseUpdate() {
        return dataBaseUpdate;
    }


    public PermissionMenu setDataBaseUpdate(Boolean dataBaseUpdate) {
        this.dataBaseUpdate = dataBaseUpdate;
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
    public PermissionMenu setVersion(Integer version) {
        this.version = version;
        return this;
    }


    @Override
    public String toString() {
        return "PermissionMenu{"
                + "id=" + id
                + ",version=" + version
                + ",menuId=" + menuId
                + ",permissionName=" + permissionName
                + ",dataBaseUpdate=" + dataBaseUpdate
                + "}";
    }

}