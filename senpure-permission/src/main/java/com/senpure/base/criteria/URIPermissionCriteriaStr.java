package com.senpure.base.criteria;

import com.senpure.base.criterion.CriteriaStr;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author senpure
 * @version 2020-5-20 18:21:45
 */
public class URIPermissionCriteriaStr extends CriteriaStr implements Serializable {
    private static final long serialVersionUID = 1046435846L;

    //(主键)
    @ApiModelProperty(value = "(主键)", dataType = "long", example = "666666", position = 4)
    private String id;
    //乐观锁，版本控制
    @ApiModelProperty(hidden = true )
    private String version;
    @ApiModelProperty(example = "uriAndMethod", position = 5)
    private String uriAndMethod;
    //是否从数据库更新过
    @ApiModelProperty(value = "是否从数据库更新过", dataType = "boolean", position = 6)
    private String databaseUpdate;
    //(外键,modelName:Permission,tableName:senpure_permission)
    @ApiModelProperty(value = "(外键,modelName:Permission,tableName:senpure_permission)", dataType = "long", example = "666666", position = 7)
    private String permissionId;
    //table [senpure_uri_permission][column = permission_id] criteriaOrder
    @ApiModelProperty(value = "permissionId 排序" , allowableValues = "ASC,DESC", position = 8)
    private String permissionIdOrder;

    public URIPermissionCriteria toURIPermissionCriteria() {
        URIPermissionCriteria criteria = new URIPermissionCriteria();
        criteria.setPage(Integer.valueOf(getPage()));
        criteria.setPageSize(Integer.valueOf(getPageSize()));
        //(主键)
        if (id != null) {
            criteria.setId(Long.valueOf(id));
        }
        //乐观锁，版本控制
        if (version != null) {
            criteria.setVersion(Integer.valueOf(version));
        }
        if (uriAndMethod != null) {
            criteria.setUriAndMethod(uriAndMethod);
        }
        //是否从数据库更新过
        if (databaseUpdate != null) {
            criteria.setDatabaseUpdate(Boolean.valueOf(databaseUpdate));
        }
        //(外键,modelName:Permission,tableName:senpure_permission)
        if (permissionId != null) {
            criteria.setPermissionId(Long.valueOf(permissionId));
        }
        //table [senpure_uri_permission][column = permission_id] criteriaOrder
        if (permissionIdOrder != null) {
            criteria.setPermissionIdOrder(permissionIdOrder);
        }
        return criteria;
    }

    @Override
    protected void beforeStr(StringBuilder sb) {
        sb.append("URIPermissionCriteriaStr{");
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

    @Override
    protected void afterStr(StringBuilder sb) {
        if (permissionIdOrder != null) {
            sb.append("permissionIdOrder=").append(permissionIdOrder).append(",");
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
    public URIPermissionCriteriaStr setId(String id) {
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
    public URIPermissionCriteriaStr setVersion(String version) {
        if (version != null && version.trim().length() == 0) {
            return this;
        }
        this.version = version;
        return this;
    }

    public String getUriAndMethod() {
        return uriAndMethod;
    }


    public URIPermissionCriteriaStr setUriAndMethod(String uriAndMethod) {
        if (uriAndMethod != null && uriAndMethod.trim().length() == 0) {
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
    public String getDatabaseUpdate() {
        return databaseUpdate;
    }

    /**
     * set 是否从数据库更新过
     *
     * @return
     */
    public URIPermissionCriteriaStr setDatabaseUpdate(String databaseUpdate) {
        if (databaseUpdate != null && databaseUpdate.trim().length() == 0) {
            return this;
        }
        this.databaseUpdate = databaseUpdate;
        return this;
    }

    /**
     * get (外键,modelName:Permission,tableName:senpure_permission)
     *
     * @return
     */
    public String getPermissionId() {
        return permissionId;
    }

    /**
     * set (外键,modelName:Permission,tableName:senpure_permission)
     *
     * @return
     */
    public URIPermissionCriteriaStr setPermissionId(String permissionId) {
        if (permissionId != null && permissionId.trim().length() == 0) {
            return this;
        }
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
    public URIPermissionCriteriaStr setPermissionIdOrder(String permissionIdOrder) {
        if (permissionIdOrder != null && permissionIdOrder.trim().length() == 0) {
            this.permissionIdOrder = null;
            return this;
        }
        this.permissionIdOrder = permissionIdOrder;
        return this;
    }

}