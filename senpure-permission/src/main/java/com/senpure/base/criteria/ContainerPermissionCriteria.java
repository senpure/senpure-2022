package com.senpure.base.criteria;

import com.senpure.base.criterion.Criteria;
import com.senpure.base.model.ContainerPermission;
import com.senpure.base.util.DateFormatUtil;

import java.util.Date;
import java.io.Serializable;

/**
 * @author senpure
 * @version 2020-5-22 16:52:02
 */
public class ContainerPermissionCriteria extends Criteria implements Serializable {
    private static final long serialVersionUID = 1444113447L;

    //(主键)
    private Long id;
    //乐观锁，版本控制
    private Integer version;
    private Date expiryDate;
    private Date startExpiryDate;
    private Date endExpiryDate;
    //table [senpure_container_permission][column = expiry_date] criteriaOrder
    private String expiryDateOrder;
    //(外键,modelName:Container,tableName:senpure_container)
    private Integer containerId;
    //table [senpure_container_permission][column = container_id] criteriaOrder
    private String containerIdOrder;
    //(外键,modelName:Permission,tableName:senpure_permission)
    private Long permissionId;
    //table [senpure_container_permission][column = permission_id] criteriaOrder
    private String permissionIdOrder;

    public static ContainerPermission toContainerPermission(ContainerPermissionCriteria criteria, ContainerPermission containerPermission) {
        containerPermission.setId(criteria.getId());
        containerPermission.setExpiryDate(criteria.getExpiryDate());
        if (criteria.getExpiryDate() != null) {
            containerPermission.setExpiryTime(criteria.getExpiryDate().getTime());
        }
        containerPermission.setContainerId(criteria.getContainerId());
        containerPermission.setPermissionId(criteria.getPermissionId());
        containerPermission.setVersion(criteria.getVersion());
        return containerPermission;
    }

    public ContainerPermission toContainerPermission() {
        ContainerPermission containerPermission = new ContainerPermission();
        return toContainerPermission(this, containerPermission);
    }

    /**
     * 将ContainerPermissionCriteria 的有效值(不为空),赋值给 ContainerPermission
     *
     * @return ContainerPermission
     */
    public ContainerPermission effective(ContainerPermission containerPermission) {
        if (getId() != null) {
            containerPermission.setId(getId());
        }
        if (getExpiryDate() != null) {
            containerPermission.setExpiryDate(getExpiryDate());
        }
        if (getContainerId() != null) {
            containerPermission.setContainerId(getContainerId());
        }
        if (getPermissionId() != null) {
            containerPermission.setPermissionId(getPermissionId());
        }
        if (getVersion() != null) {
            containerPermission.setVersion(getVersion());
        }
        return containerPermission;
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
        sb.append("ContainerPermissionCriteria{");
        if (id != null) {
            sb.append("id=").append(id).append(",");
        }
        if (version != null) {
            sb.append("version=").append(version).append(",");
        }
        if (expiryDate != null) {
            sb.append("expiryDate=").append(expiryDate).append(",");
        }
        if (containerId != null) {
            sb.append("containerId=").append(containerId).append(",");
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
    public ContainerPermissionCriteria setId(Long id) {
        this.id = id;
        return this;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }


    public ContainerPermissionCriteria setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public Date getStartExpiryDate() {
        return startExpiryDate;
    }

    public ContainerPermissionCriteria setStartExpiryDate(Date startExpiryDate) {
        this.startExpiryDate = startExpiryDate;
        return this;
    }

    public Date getEndExpiryDate() {
        return endExpiryDate;
    }

    public ContainerPermissionCriteria setEndExpiryDate(Date endExpiryDate) {
        this.endExpiryDate = endExpiryDate;
        return this;
    }

    /**
     * get table [senpure_container_permission][column = expiry_date] criteriaOrder
     *
     * @return
     */
    public String getExpiryDateOrder() {
        return expiryDateOrder;
    }

    /**
     * set table [senpure_container_permission][column = expiry_date] criteriaOrder DESC||ASC
     *
     * @return
     */
    public ContainerPermissionCriteria setExpiryDateOrder(String expiryDateOrder) {
        this.expiryDateOrder = expiryDateOrder;
        putSort("expiry_date", expiryDateOrder);
        return this;
    }

    /**
     * get (外键,modelName:Container,tableName:senpure_container)
     *
     * @return
     */
    public Integer getContainerId() {
        return containerId;
    }

    /**
     * set (外键,modelName:Container,tableName:senpure_container)
     *
     * @return
     */
    public ContainerPermissionCriteria setContainerId(Integer containerId) {
        this.containerId = containerId;
        return this;
    }

    /**
     * get table [senpure_container_permission][column = container_id] criteriaOrder
     *
     * @return
     */
    public String getContainerIdOrder() {
        return containerIdOrder;
    }

    /**
     * set table [senpure_container_permission][column = container_id] criteriaOrder DESC||ASC
     *
     * @return
     */
    public ContainerPermissionCriteria setContainerIdOrder(String containerIdOrder) {
        this.containerIdOrder = containerIdOrder;
        putSort("container_id", containerIdOrder);
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
    public ContainerPermissionCriteria setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
        return this;
    }

    /**
     * get table [senpure_container_permission][column = permission_id] criteriaOrder
     *
     * @return
     */
    public String getPermissionIdOrder() {
        return permissionIdOrder;
    }

    /**
     * set table [senpure_container_permission][column = permission_id] criteriaOrder DESC||ASC
     *
     * @return
     */
    public ContainerPermissionCriteria setPermissionIdOrder(String permissionIdOrder) {
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
    public ContainerPermissionCriteria setVersion(Integer version) {
        this.version = version;
        return this;
    }

}