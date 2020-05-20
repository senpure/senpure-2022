package com.senpure.base.entity;


import com.senpure.base.PermissionConstant;

import javax.persistence.*;


@Entity
@Table(name = PermissionConstant.DATA_BASE_PREFIX + "_URI_PERMISSION")
public class URIPermission extends LongAndVersionEntity {

    @Column
    private String uriAndMethod;
    //是否从数据库更新过
    private Boolean databaseUpdate = false;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "permissionId")
    private Permission permission;

    public String getUriAndMethod() {
        return uriAndMethod;
    }

    public void setUriAndMethod(String uriAndMethod) {
        this.uriAndMethod = uriAndMethod;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Boolean getDatabaseUpdate() {
        return databaseUpdate;
    }

    public void setDatabaseUpdate(Boolean databaseUpdate) {
        this.databaseUpdate = databaseUpdate;
    }
}
