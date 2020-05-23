package com.senpure.base.criteria;

import com.senpure.base.criterion.CriteriaStr;
import com.senpure.base.struct.PatternDate;
import com.senpure.base.validator.DynamicDate;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author senpure
 * @version 2020-5-22 16:52:02
 */
public class ContainerPermissionCriteriaStr extends CriteriaStr implements Serializable {
    private static final long serialVersionUID = 1444113447L;

    //(主键)
    @ApiModelProperty(value = "(主键)", dataType = "long", example = "666666", position = 5)
    private String id;
    //乐观锁，版本控制
    @ApiModelProperty(hidden = true )
    private String version;
    @ApiModelProperty(dataType = "date-time", example = "2020-05-22 00:00:00", position = 6)
    private String expiryDate;
    //expiryDate 时间格式
    @ApiModelProperty(value = "expiryDate 格式", example = "yyyy-MM-dd HH:mm:ss", position = 7)
    private String expiryDatePattern ;
    @DynamicDate
    private final PatternDate expiryDateValid = new PatternDate();
    //(外键,modelName:Container,tableName:senpure_container)
    @ApiModelProperty(value = "(外键,modelName:Container,tableName:senpure_container)", dataType = "int", example = "666666", position = 8)
    private String containerId;
    //(外键,modelName:Permission,tableName:senpure_permission)
    @ApiModelProperty(value = "(外键,modelName:Permission,tableName:senpure_permission)", dataType = "long", example = "666666", position = 9)
    private String permissionId;
    @ApiModelProperty(value = "expiryDate 开始范围 (>=)", dataType = "date-time", example = "2020-05-22 00:00:00", position = 10)
    private String startExpiryDate;
    @ApiModelProperty(value = "expiryDate 结束范围 (<=)", dataType = "date-time", example = "2020-05-22 23:59:59", position = 11)
    private String endExpiryDate;
    @DynamicDate
    private final PatternDate startExpiryDateValid = new PatternDate();
    @DynamicDate
    private final PatternDate endExpiryDateValid = new PatternDate();
    //table [senpure_container_permission][column = expiry_date] criteriaOrder
    @ApiModelProperty(value = "expiryDate 排序" , allowableValues = "ASC,DESC", position = 12)
    private String expiryDateOrder;
    //table [senpure_container_permission][column = container_id] criteriaOrder
    @ApiModelProperty(value = "containerId 排序" , allowableValues = "ASC,DESC", position = 13)
    private String containerIdOrder;
    //table [senpure_container_permission][column = permission_id] criteriaOrder
    @ApiModelProperty(value = "permissionId 排序" , allowableValues = "ASC,DESC", position = 14)
    private String permissionIdOrder;

    public ContainerPermissionCriteria toContainerPermissionCriteria() {
        ContainerPermissionCriteria criteria = new ContainerPermissionCriteria();
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
        if (expiryDate != null) {
            criteria.setExpiryDate(expiryDateValid.getDate());
        }
        if (startExpiryDate != null) {
            criteria.setStartExpiryDate(startExpiryDateValid.getDate());
        }
        if (endExpiryDate != null) {
            criteria.setEndExpiryDate(endExpiryDateValid.getDate());
        }
        //table [senpure_container_permission][column = expiry_date] criteriaOrder
        if (expiryDateOrder != null) {
            criteria.setExpiryDateOrder(expiryDateOrder);
        }
        //(外键,modelName:Container,tableName:senpure_container)
        if (containerId != null) {
            criteria.setContainerId(Integer.valueOf(containerId));
        }
        //table [senpure_container_permission][column = container_id] criteriaOrder
        if (containerIdOrder != null) {
            criteria.setContainerIdOrder(containerIdOrder);
        }
        //(外键,modelName:Permission,tableName:senpure_permission)
        if (permissionId != null) {
            criteria.setPermissionId(Long.valueOf(permissionId));
        }
        //table [senpure_container_permission][column = permission_id] criteriaOrder
        if (permissionIdOrder != null) {
            criteria.setPermissionIdOrder(permissionIdOrder);
        }
        return criteria;
    }

    @Override
    protected void rangeStr(StringBuilder sb) {
        if (startExpiryDate != null) {
            sb.append("startExpiryDate=").append(startExpiryDate).append(",");
        }
        if (endExpiryDate != null) {
            sb.append("endExpiryDate=").append(endExpiryDate).append(",");
        }
    }

    @Override
    protected void beforeStr(StringBuilder sb) {
        sb.append("ContainerPermissionCriteriaStr{");
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

    @Override
    protected void afterStr(StringBuilder sb) {
        if (expiryDateOrder != null) {
            sb.append("expiryDateOrder=").append(expiryDateOrder).append(",");
        }
        if (containerIdOrder != null) {
            sb.append("containerIdOrder=").append(containerIdOrder).append(",");
        }
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
    public ContainerPermissionCriteriaStr setId(String id) {
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
    public ContainerPermissionCriteriaStr setVersion(String version) {
        if (version != null && version.trim().length() == 0) {
            return this;
        }
        this.version = version;
        return this;
    }

    public String getExpiryDate() {
        return expiryDate;
    }


    public ContainerPermissionCriteriaStr setExpiryDate(String expiryDate) {
        if (expiryDate != null && expiryDate.trim().length() == 0) {
            return this;
        }
        this.expiryDate = expiryDate;
        this.expiryDateValid.setDateStr(expiryDate);
        return this;
    }

    public String getExpiryDatePattern() {
        return expiryDatePattern;
    }

    public ContainerPermissionCriteriaStr setExpiryDatePattern(String expiryDatePattern) {
        if (expiryDatePattern != null && expiryDatePattern.trim().length() == 0) {
            return this;
        }
        this.expiryDatePattern = expiryDatePattern;
        this.expiryDateValid.setPattern(expiryDatePattern);
        this.startExpiryDateValid.setPattern(expiryDatePattern);
        this.endExpiryDateValid.setPattern(expiryDatePattern);
        return this;
    }

    public String getStartExpiryDate() {
        return startExpiryDate;
    }

    public ContainerPermissionCriteriaStr setStartExpiryDate(String startExpiryDate) {
        if (startExpiryDate != null && startExpiryDate.trim().length() == 0) {
            return this;
        }
        this.startExpiryDate = startExpiryDate;
        this.startExpiryDateValid.setDateStr(startExpiryDate);
        return this;
    }

    public String getEndExpiryDate() {
        return endExpiryDate;
    }

    public ContainerPermissionCriteriaStr setEndExpiryDate(String endExpiryDate) {
        if (endExpiryDate != null && endExpiryDate.trim().length() == 0) {
            return this;
        }
        this.endExpiryDate = endExpiryDate;
        this.endExpiryDateValid.setDateStr(endExpiryDate);
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
    public ContainerPermissionCriteriaStr setExpiryDateOrder(String expiryDateOrder) {
        if (expiryDateOrder != null && expiryDateOrder.trim().length() == 0) {
            this.expiryDateOrder = null;
            return this;
        }
        this.expiryDateOrder = expiryDateOrder;
        return this;
    }

    /**
     * get (外键,modelName:Container,tableName:senpure_container)
     *
     * @return
     */
    public String getContainerId() {
        return containerId;
    }

    /**
     * set (外键,modelName:Container,tableName:senpure_container)
     *
     * @return
     */
    public ContainerPermissionCriteriaStr setContainerId(String containerId) {
        if (containerId != null && containerId.trim().length() == 0) {
            return this;
        }
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
    public ContainerPermissionCriteriaStr setContainerIdOrder(String containerIdOrder) {
        if (containerIdOrder != null && containerIdOrder.trim().length() == 0) {
            this.containerIdOrder = null;
            return this;
        }
        this.containerIdOrder = containerIdOrder;
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
    public ContainerPermissionCriteriaStr setPermissionId(String permissionId) {
        if (permissionId != null && permissionId.trim().length() == 0) {
            return this;
        }
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
    public ContainerPermissionCriteriaStr setPermissionIdOrder(String permissionIdOrder) {
        if (permissionIdOrder != null && permissionIdOrder.trim().length() == 0) {
            this.permissionIdOrder = null;
            return this;
        }
        this.permissionIdOrder = permissionIdOrder;
        return this;
    }

}