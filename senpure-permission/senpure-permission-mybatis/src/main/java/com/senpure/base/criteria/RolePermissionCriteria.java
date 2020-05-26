package com.senpure.base.criteria;

import com.senpure.base.criterion.Criteria;
import com.senpure.base.model.RolePermission;
import com.senpure.base.util.DateFormatUtil;

import java.util.Date;
import java.io.Serializable;

/**
 * @author senpure
 * @version 2020-5-22 16:52:02
 */
public class RolePermissionCriteria extends Criteria implements Serializable {
    private static final long serialVersionUID = 589858148L;

    //(主键)
    private Long id;
    //乐观锁，版本控制
    private Integer version;
    private Date expiryDate;
    private Date startExpiryDate;
    private Date endExpiryDate;
    //table [senpure_role_permission][column = expiry_date] criteriaOrder
    private String expiryDateOrder;
    //(外键,modelName:Role,tableName:senpure_role)
    private Long roleId;
    //table [senpure_role_permission][column = role_id] criteriaOrder
    private String roleIdOrder;
    //(外键,modelName:Permission,tableName:senpure_permission)
    private Long permissionId;
    //table [senpure_role_permission][column = permission_id] criteriaOrder
    private String permissionIdOrder;

    public static RolePermission toRolePermission(RolePermissionCriteria criteria, RolePermission rolePermission) {
        rolePermission.setId(criteria.getId());
        rolePermission.setExpiryDate(criteria.getExpiryDate());
        if (criteria.getExpiryDate() != null) {
            rolePermission.setExpiryTime(criteria.getExpiryDate().getTime());
        }
        rolePermission.setRoleId(criteria.getRoleId());
        rolePermission.setPermissionId(criteria.getPermissionId());
        rolePermission.setVersion(criteria.getVersion());
        return rolePermission;
    }

    public RolePermission toRolePermission() {
        RolePermission rolePermission = new RolePermission();
        return toRolePermission(this, rolePermission);
    }

    /**
     * 将RolePermissionCriteria 的有效值(不为空),赋值给 RolePermission
     *
     * @return RolePermission
     */
    public RolePermission effective(RolePermission rolePermission) {
        if (getId() != null) {
            rolePermission.setId(getId());
        }
        if (getExpiryDate() != null) {
            rolePermission.setExpiryDate(getExpiryDate());
        }
        if (getRoleId() != null) {
            rolePermission.setRoleId(getRoleId());
        }
        if (getPermissionId() != null) {
            rolePermission.setPermissionId(getPermissionId());
        }
        if (getVersion() != null) {
            rolePermission.setVersion(getVersion());
        }
        return rolePermission;
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
        sb.append("RolePermissionCriteria{");
        if (id != null) {
            sb.append("id=").append(id).append(",");
        }
        if (version != null) {
            sb.append("version=").append(version).append(",");
        }
        if (expiryDate != null) {
            sb.append("expiryDate=").append(expiryDate).append(",");
        }
        if (roleId != null) {
            sb.append("roleId=").append(roleId).append(",");
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
    public RolePermissionCriteria setId(Long id) {
        this.id = id;
        return this;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }


    public RolePermissionCriteria setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public Date getStartExpiryDate() {
        return startExpiryDate;
    }

    public RolePermissionCriteria setStartExpiryDate(Date startExpiryDate) {
        this.startExpiryDate = startExpiryDate;
        return this;
    }

    public Date getEndExpiryDate() {
        return endExpiryDate;
    }

    public RolePermissionCriteria setEndExpiryDate(Date endExpiryDate) {
        this.endExpiryDate = endExpiryDate;
        return this;
    }

    /**
     * get table [senpure_role_permission][column = expiry_date] criteriaOrder
     *
     * @return
     */
    public String getExpiryDateOrder() {
        return expiryDateOrder;
    }

    /**
     * set table [senpure_role_permission][column = expiry_date] criteriaOrder DESC||ASC
     *
     * @return
     */
    public RolePermissionCriteria setExpiryDateOrder(String expiryDateOrder) {
        this.expiryDateOrder = expiryDateOrder;
        putSort("expiry_date", expiryDateOrder);
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
    public RolePermissionCriteria setRoleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    /**
     * get table [senpure_role_permission][column = role_id] criteriaOrder
     *
     * @return
     */
    public String getRoleIdOrder() {
        return roleIdOrder;
    }

    /**
     * set table [senpure_role_permission][column = role_id] criteriaOrder DESC||ASC
     *
     * @return
     */
    public RolePermissionCriteria setRoleIdOrder(String roleIdOrder) {
        this.roleIdOrder = roleIdOrder;
        putSort("role_id", roleIdOrder);
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
    public RolePermissionCriteria setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
        return this;
    }

    /**
     * get table [senpure_role_permission][column = permission_id] criteriaOrder
     *
     * @return
     */
    public String getPermissionIdOrder() {
        return permissionIdOrder;
    }

    /**
     * set table [senpure_role_permission][column = permission_id] criteriaOrder DESC||ASC
     *
     * @return
     */
    public RolePermissionCriteria setPermissionIdOrder(String permissionIdOrder) {
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
    public RolePermissionCriteria setVersion(Integer version) {
        this.version = version;
        return this;
    }

}