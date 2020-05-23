package com.senpure.base.model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author senpure
 * @version 2020-5-22 17:23:15
 */
@ApiModel
public class Permission implements Serializable {
    private static final long serialVersionUID = 2096486843L;

    //(主键)
    private Long id;
    //乐观锁，版本控制
    @ApiModelProperty(hidden = true )
    private Integer version;
    @ApiModelProperty(example = "name", position = 1)
    private String name;
    @ApiModelProperty(example = "readableName", position = 2)
    private String readableName;
    //是否从数据库更新过
    @ApiModelProperty(value = "是否从数据库更新过", dataType = "boolean", position = 3)
    private Boolean databaseUpdate;
    //服务名(多个服务可能共用一个数据库来存放权限)
    @ApiModelProperty(value = "服务名(多个服务可能共用一个数据库来存放权限)", example = "serverName", position = 4)
    private String serverName;
    //NORMAL 正常 ，OWNER 检查所有者，IGNORE 可以忽略(正常放行)
    @ApiModelProperty(value = "NORMAL 正常 ，OWNER 检查所有者，IGNORE 可以忽略(正常放行)", example = "type", position = 5)
    private String type;
    //'1,2' type为OWNER 配合verifyName使用
    @ApiModelProperty(value = "'1,2' type为OWNER 配合verifyName使用", example = "offset", position = 6)
    private String offset;
    //'containerResource',roleResource' type为OWNER 配合offset使用
    @ApiModelProperty(value = "'containerResource',roleResource' type为OWNER 配合offset使用", example = "verifyName", position = 7)
    private String verifyName;
    @ApiModelProperty(example = "description", position = 8)
    private String description;
    //排序
    @ApiModelProperty(value = "排序", dataType = "int", example = "666666", position = 9)
    private Integer sort;

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
    public Permission setId(Long id) {
        this.id = id;
        return this;
    }


    public String getName() {
        return name;
    }


    public Permission setName(String name) {
        this.name = name;
        return this;
    }


    public String getReadableName() {
        return readableName;
    }


    public Permission setReadableName(String readableName) {
        this.readableName = readableName;
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
    public Permission setDatabaseUpdate(Boolean databaseUpdate) {
        this.databaseUpdate = databaseUpdate;
        return this;
    }


    /**
     * get 服务名(多个服务可能共用一个数据库来存放权限)
     *
     * @return
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * set 服务名(多个服务可能共用一个数据库来存放权限)
     *
     * @return
     */
    public Permission setServerName(String serverName) {
        this.serverName = serverName;
        return this;
    }


    /**
     * get NORMAL 正常 ，OWNER 检查所有者，IGNORE 可以忽略(正常放行)
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * set NORMAL 正常 ，OWNER 检查所有者，IGNORE 可以忽略(正常放行)
     *
     * @return
     */
    public Permission setType(String type) {
        this.type = type;
        return this;
    }


    /**
     * get '1,2' type为OWNER 配合verifyName使用
     *
     * @return
     */
    public String getOffset() {
        return offset;
    }

    /**
     * set '1,2' type为OWNER 配合verifyName使用
     *
     * @return
     */
    public Permission setOffset(String offset) {
        this.offset = offset;
        return this;
    }


    /**
     * get 'containerResource',roleResource' type为OWNER 配合offset使用
     *
     * @return
     */
    public String getVerifyName() {
        return verifyName;
    }

    /**
     * set 'containerResource',roleResource' type为OWNER 配合offset使用
     *
     * @return
     */
    public Permission setVerifyName(String verifyName) {
        this.verifyName = verifyName;
        return this;
    }


    public String getDescription() {
        return description;
    }


    public Permission setDescription(String description) {
        this.description = description;
        return this;
    }


    /**
     * get 排序
     *
     * @return
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * set 排序
     *
     * @return
     */
    public Permission setSort(Integer sort) {
        this.sort = sort;
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
    public Permission setVersion(Integer version) {
        this.version = version;
        return this;
    }


    @Override
    public String toString() {
        return "Permission{"
                + "id=" + id
                + ",version=" + version
                + ",name=" + name
                + ",readableName=" + readableName
                + ",databaseUpdate=" + databaseUpdate
                + ",serverName=" + serverName
                + ",type=" + type
                + ",offset=" + offset
                + ",verifyName=" + verifyName
                + ",description=" + description
                + ",sort=" + sort
                + "}";
    }

}