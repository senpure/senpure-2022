package com.senpure.base.criteria;

import com.senpure.base.criterion.Criteria;
import com.senpure.base.model.URIPermission;

import java.io.Serializable;

/**
 * @author senpure
 * @version 2020-5-20 18:21:45
 */
public class URIPermissionCriteria extends Criteria implements Serializable {
    private static final long serialVersionUID = 1046435846L;

    //(主键)
    private Long id;
    //乐观锁，版本控制
    private Integer version;
    private String uriAndMethod;
    //是否从数据库更新过
    private Boolean databaseUpdate;
    //(外键,modelName:Permission,tableName:senpure_permission)
    private Long permissionId;
    //table [senpure_uri_permission][column = permission_id] criteriaOrder
    private String permissionIdOrder;

    public static URIPermission toURIPermission(URIPermissionCriteria criteria, URIPermission uriPermission) {
        uriPermission.setId(criteria.getId());
        uriPermission.setUriAndMethod(criteria.getUriAndMethod());
        uriPermission.setDatabaseUpdate(criteria.getDatabaseUpdate());
        uriPermission.setPermissionId(criteria.getPermissionId());
        uriPermission.setVersion(criteria.getVersion());
        return uriPermission;
    }

    public URIPermission toURIPermission() {
        URIPermission uriPermission = new URIPermission();
        return toURIPermission(this, uriPermission);
    }

    /**
     * 将URIPermissionCriteria 的有效值(不为空),赋值给 URIPermission
     *
     * @return URIPermission
     */
    public URIPermission effective(URIPermission uriPermission) {
        if (getId() != null) {
            uriPermission.setId(getId());
        }
        if (getUriAndMethod() != null) {
            uriPermission.setUriAndMethod(getUriAndMethod());
        }
        if (getDatabaseUpdate() != null) {
            uriPermission.setDatabaseUpdate(getDatabaseUpdate());
        }
        if (getPermissionId() != null) {
            uriPermission.setPermissionId(getPermissionId());
        }
        if (getVersion() != null) {
            uriPermission.setVersion(getVersion());
        }
        return uriPermission;
    }

    @Override
    protected void beforeStr(StringBuilder sb) {
        sb.append("URIPermissionCriteria{");
        if (id != null) {
            sb.append("id=").append(id).append(",");
        }
        if (version != null) {
            sb.append("version=").append(version).append(",");
        }
        if (uriAndMethod != null) {
            sb.append("uriAndMethod=").append(uriAndMethod).append(",");
        }
        if (databaseUpdate != null) {
            sb.append("databaseUpdate=").append(databaseUpdate).append(",");
        }
        if (permissionId != null) {
            sb.append("permissionId=").append(permissionId).append(",");
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
    public URIPermissionCriteria setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUriAndMethod() {
        return uriAndMethod;
    }


    public URIPermissionCriteria setUriAndMethod(String uriAndMethod) {
        if (uriAndMethod != null && uriAndMethod.trim().length() == 0) {
            this.uriAndMethod = null;
            return this;
        }
        this.uriAndMethod = uriAndMethod;
        return this;
    }

    /**
     * get 是否从数据库更新过
     *
     * @return
     */
    public Boolean getDatabaseUpdate() {
        return databaseUpdate;
    }

    /**
     * set 是否从数据库更新过
     *
     * @return
     */
    public URIPermissionCriteria setDatabaseUpdate(Boolean databaseUpdate) {
        this.databaseUpdate = databaseUpdate;
        return this;
    }

    /**
     * get (外键,modelName:Permission,tableName:senpure_permission)
     *
     * @return
     */
    public Long getPermissionId() {
        return permissionId;
    }

    /**
     * set (外键,modelName:Permission,tableName:senpure_permission)
     *
     * @return
     */
    public URIPermissionCriteria setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
        return this;
    }

    /**
     * get table [senpure_uri_permission][column = permission_id] criteriaOrder
     *
     * @return
     */
    public String getPermissionIdOrder() {
        return permissionIdOrder;
    }

    /**
     * set table [senpure_uri_permission][column = permission_id] criteriaOrder DESC||ASC
     *
     * @return
     */
    public URIPermissionCriteria setPermissionIdOrder(String permissionIdOrder) {
        this.permissionIdOrder = permissionIdOrder;
        putSort("permission_id", permissionIdOrder);
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
    public URIPermissionCriteria setVersion(Integer version) {
        this.version = version;
        return this;
    }

}