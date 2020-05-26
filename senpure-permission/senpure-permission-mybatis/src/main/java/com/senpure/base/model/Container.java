package com.senpure.base.model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author senpure
 * @version 2020-5-22 16:52:02
 */
@ApiModel
public class Container implements Serializable {
    private static final long serialVersionUID = 611392665L;

    //(主键)
    private Integer id;
    //乐观锁，版本控制
    @ApiModelProperty(hidden = true )
    private Integer version;
    @ApiModelProperty(example = "name", position = 1)
    private String name;
    @ApiModelProperty(example = "description", position = 2)
    private String description;
    @ApiModelProperty(dataType = "int", example = "666666", position = 3)
    private Integer level;
    @ApiModelProperty(dataType = "long", example = "666666", position = 4)
    private Long relation;
    @ApiModelProperty(dataType = "long", example = "1590076800000", position = 5)
    private Long createTime;
    @ApiModelProperty(dataType = "date-time", example = "2020-05-22 00:00:00", position = 6)
    private Date createDate;
    //所属容器层次结构,主要是为了查询,多有用like查询 -1-2-3-12-
    @ApiModelProperty(value = "所属容器层次结构,主要是为了查询,多有用like查询 -1-2-3-12-", example = "containerStructure", position = 7)
    private String containerStructure;
    //父容器，实体(外键,modelName:Container,tableName:senpure_container)
    @ApiModelProperty(value = "父容器，实体(外键,modelName:Container,tableName:senpure_container)", dataType = "int", example = "666666", position = 8)
    private Integer parentId;

    /**
     * get (主键)
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * set (主键)
     *
     * @return
     */
    public Container setId(Integer id) {
        this.id = id;
        return this;
    }


    public String getName() {
        return name;
    }


    public Container setName(String name) {
        this.name = name;
        return this;
    }


    public String getDescription() {
        return description;
    }


    public Container setDescription(String description) {
        this.description = description;
        return this;
    }


    public Integer getLevel() {
        return level;
    }


    public Container setLevel(Integer level) {
        this.level = level;
        return this;
    }


    public Long getRelation() {
        return relation;
    }


    public Container setRelation(Long relation) {
        this.relation = relation;
        return this;
    }


    public Long getCreateTime() {
        return createTime;
    }


    public Container setCreateTime(Long createTime) {
        this.createTime = createTime;
        return this;
    }


    public Date getCreateDate() {
        return createDate;
    }


    public Container setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }


    /**
     * get 所属容器层次结构,主要是为了查询,多有用like查询 -1-2-3-12-
     *
     * @return
     */
    public String getContainerStructure() {
        return containerStructure;
    }

    /**
     * set 所属容器层次结构,主要是为了查询,多有用like查询 -1-2-3-12-
     *
     * @return
     */
    public Container setContainerStructure(String containerStructure) {
        this.containerStructure = containerStructure;
        return this;
    }


    /**
     * get 父容器，实体(外键,modelName:Container,tableName:senpure_container)
     *
     * @return
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * set 父容器，实体(外键,modelName:Container,tableName:senpure_container)
     *
     * @return
     */
    public Container setParentId(Integer parentId) {
        this.parentId = parentId;
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
    public Container setVersion(Integer version) {
        this.version = version;
        return this;
    }


    @Override
    public String toString() {
        return "Container{"
                + "id=" + id
                + ",version=" + version
                + ",name=" + name
                + ",description=" + description
                + ",level=" + level
                + ",relation=" + relation
                + ",createTime=" + createTime
                + ",createDate=" + createDate
                + ",containerStructure=" + containerStructure
                + ",parentId=" + parentId
                + "}";
    }

}