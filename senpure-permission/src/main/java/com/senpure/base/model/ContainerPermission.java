package com.senpure.base.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author senpure
 * @version 2020-5-20 18:21:46
 */
@ApiModel
public class ContainerPermission implements Serializable {
    private static final long serialVersionUID = 1444113447L;

    //(主键)
    private Long id;
    //乐观锁，版本控制
    @ApiModelProperty(hidden = true )
    private Integer version;
    @ApiModelProperty(dataType = "long", example = "1589904000000", position = 1)
    private Long expiryTime;
    @ApiModelProperty(dataType = "date-time", example = "2020-05-20 00:00:00", position = 2)
    private Date expiryDate;
    //(外键,modelName:Container,tableName:senpure_container)
    @ApiModelProperty(value = "(外键,modelName:Container,tableName:senpure_container)", dataType = "int", example = "666666", position = 3)
    private Integer containerId;
    //(外键,modelName:Permission,tableName:senpure_permission)
    @ApiModelProperty(value = "(外键,modelName:Permission,tableName:senpure_permission)", dataType = "long", example = "666666", position = 4)
    private Long permissionId;

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
    public ContainerPermission setId(Long id) {
        this.id = id;
        return this;
    }


    public Long getExpiryTime() {
        return expiryTime;
    }


    public ContainerPermission setExpiryTime(Long expiryTime) {
        this.expiryTime = expiryTime;
        return this;
    }


    public Date getExpiryDate() {
        return expiryDate;
    }


    public ContainerPermission setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
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
    public ContainerPermission setContainerId(Integer containerId) {
        this.containerId = containerId;
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
    public ContainerPermission setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
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
    public ContainerPermission setVersion(Integer version) {
        this.version = version;
        return this;
    }


    @Override
    public String toString() {
        return "ContainerPermission{"
                + "id=" + id
                + ",version=" + version
                + ",expiryTime=" + expiryTime
                + ",expiryDate=" + expiryDate
                + ",containerId=" + containerId
                + ",permissionId=" + permissionId
                + "}";
    }

}