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
public class ContainerCriteriaStr extends CriteriaStr implements Serializable {
    private static final long serialVersionUID = 611392665L;

    //(主键)
    @ApiModelProperty(value = "(主键)", dataType = "int", example = "666666", position = 9)
    private String id;
    //乐观锁，版本控制
    @ApiModelProperty(hidden = true )
    private String version;
    @ApiModelProperty(example = "name", position = 10)
    private String name;
    @ApiModelProperty(example = "description", position = 11)
    private String description;
    @ApiModelProperty(dataType = "int", example = "666666", position = 12)
    private String level;
    @ApiModelProperty(dataType = "long", example = "666666", position = 13)
    private String relation;
    @ApiModelProperty(dataType = "date-time", example = "2020-05-22 00:00:00", position = 14)
    private String createDate;
    //createDate 时间格式
    @ApiModelProperty(value = "createDate 格式", example = "yyyy-MM-dd HH:mm:ss", position = 15)
    private String createDatePattern ;
    @DynamicDate
    private final PatternDate createDateValid = new PatternDate();
    //所属容器层次结构,主要是为了查询,多有用like查询 -1-2-3-12-
    @ApiModelProperty(value = "所属容器层次结构,主要是为了查询,多有用like查询 -1-2-3-12-", example = "containerStructure", position = 16)
    private String containerStructure;
    //父容器，实体(外键,modelName:Container,tableName:senpure_container)
    @ApiModelProperty(value = "父容器，实体(外键,modelName:Container,tableName:senpure_container)", dataType = "int", example = "666666", position = 17)
    private String parentId;
    @ApiModelProperty(value = "createDate 开始范围 (>=)", dataType = "date-time", example = "2020-05-22 00:00:00", position = 18)
    private String startCreateDate;
    @ApiModelProperty(value = "createDate 结束范围 (<=)", dataType = "date-time", example = "2020-05-22 23:59:59", position = 19)
    private String endCreateDate;
    @DynamicDate
    private final PatternDate startCreateDateValid = new PatternDate();
    @DynamicDate
    private final PatternDate endCreateDateValid = new PatternDate();
    //table [senpure_container][column = name] criteriaOrder
    @ApiModelProperty(value = "name 排序" , allowableValues = "ASC,DESC", position = 20)
    private String nameOrder;
    //table [senpure_container][column = create_date] criteriaOrder
    @ApiModelProperty(value = "createDate 排序" , allowableValues = "ASC,DESC", position = 21)
    private String createDateOrder;
    //table [senpure_container][column = parent_id] criteriaOrder
    @ApiModelProperty(value = "parentId 排序" , allowableValues = "ASC,DESC", position = 22)
    private String parentIdOrder;

    public ContainerCriteria toContainerCriteria() {
        ContainerCriteria criteria = new ContainerCriteria();
        criteria.setPage(Integer.parseInt(getPage()));
        criteria.setPageSize(Integer.parseInt(getPageSize()));
        //(主键)
        if (id != null) {
            criteria.setId(Integer.valueOf(id));
        }
        //乐观锁，版本控制
        if (version != null) {
            criteria.setVersion(Integer.valueOf(version));
        }
        if (name != null) {
            criteria.setName(name);
        }
        //table [senpure_container][column = name] criteriaOrder
        if (nameOrder != null) {
            criteria.setNameOrder(nameOrder);
        }
        if (description != null) {
            criteria.setDescription(description);
        }
        if (level != null) {
            criteria.setLevel(Integer.valueOf(level));
        }
        if (relation != null) {
            criteria.setRelation(Long.valueOf(relation));
        }
        if (createDate != null) {
            criteria.setCreateDate(createDateValid.getDate());
        }
        if (startCreateDate != null) {
            criteria.setStartCreateDate(startCreateDateValid.getDate());
        }
        if (endCreateDate != null) {
            criteria.setEndCreateDate(endCreateDateValid.getDate());
        }
        //table [senpure_container][column = create_date] criteriaOrder
        if (createDateOrder != null) {
            criteria.setCreateDateOrder(createDateOrder);
        }
        //所属容器层次结构,主要是为了查询,多有用like查询 -1-2-3-12-
        if (containerStructure != null) {
            criteria.setContainerStructure(containerStructure);
        }
        //父容器，实体(外键,modelName:Container,tableName:senpure_container)
        if (parentId != null) {
            criteria.setParentId(Integer.valueOf(parentId));
        }
        //table [senpure_container][column = parent_id] criteriaOrder
        if (parentIdOrder != null) {
            criteria.setParentIdOrder(parentIdOrder);
        }
        return criteria;
    }

    @Override
    protected void rangeStr(StringBuilder sb) {
        if (startCreateDate != null) {
            sb.append("startCreateDate=").append(startCreateDate).append(",");
        }
        if (endCreateDate != null) {
            sb.append("endCreateDate=").append(endCreateDate).append(",");
        }
    }

    @Override
    protected void beforeStr(StringBuilder sb) {
        sb.append("ContainerCriteriaStr{");
        if (id != null) {
            sb.append("id=").append(id).append(",");
        }
        if (version != null) {
            sb.append("version=").append(version).append(",");
        }
        if (name != null) {
            sb.append("name=").append(name).append(",");
        }
        if (description != null) {
            sb.append("description=").append(description).append(",");
        }
        if (level != null) {
            sb.append("level=").append(level).append(",");
        }
        if (relation != null) {
            sb.append("relation=").append(relation).append(",");
        }
        if (createDate != null) {
            sb.append("createDate=").append(createDate).append(",");
        }
        if (containerStructure != null) {
            sb.append("containerStructure=").append(containerStructure).append(",");
        }
        if (parentId != null) {
            sb.append("parentId=").append(parentId).append(",");
        }
    }

    @Override
    protected void afterStr(StringBuilder sb) {
        if (nameOrder != null) {
            sb.append("nameOrder=").append(nameOrder).append(",");
        }
        if (createDateOrder != null) {
            sb.append("createDateOrder=").append(createDateOrder).append(",");
        }
        if (parentIdOrder != null) {
            sb.append("parentIdOrder=").append(parentIdOrder).append(",");
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
    public ContainerCriteriaStr setId(String id) {
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
    public ContainerCriteriaStr setVersion(String version) {
        if (version != null && version.trim().length() == 0) {
            return this;
        }
        this.version = version;
        return this;
    }

    public String getName() {
        return name;
    }


    public ContainerCriteriaStr setName(String name) {
        if (name != null && name.trim().length() == 0) {
            return this;
        }
        this.name = name;
        return this;
    }

    /**
     * get table [senpure_container][column = name] criteriaOrder
     *
     * @return
     */
    public String getNameOrder() {
        return nameOrder;
    }

    /**
     * set table [senpure_container][column = name] criteriaOrder DESC||ASC
     *
     * @return
     */
    public ContainerCriteriaStr setNameOrder(String nameOrder) {
        if (nameOrder != null && nameOrder.trim().length() == 0) {
            this.nameOrder = null;
            return this;
        }
        this.nameOrder = nameOrder;
        return this;
    }

    public String getDescription() {
        return description;
    }


    public ContainerCriteriaStr setDescription(String description) {
        if (description != null && description.trim().length() == 0) {
            return this;
        }
        this.description = description;
        return this;
    }

    public String getLevel() {
        return level;
    }


    public ContainerCriteriaStr setLevel(String level) {
        if (level != null && level.trim().length() == 0) {
            return this;
        }
        this.level = level;
        return this;
    }

    public String getRelation() {
        return relation;
    }


    public ContainerCriteriaStr setRelation(String relation) {
        if (relation != null && relation.trim().length() == 0) {
            return this;
        }
        this.relation = relation;
        return this;
    }

    public String getCreateDate() {
        return createDate;
    }


    public ContainerCriteriaStr setCreateDate(String createDate) {
        if (createDate != null && createDate.trim().length() == 0) {
            return this;
        }
        this.createDate = createDate;
        this.createDateValid.setDateStr(createDate);
        return this;
    }

    public String getCreateDatePattern() {
        return createDatePattern;
    }

    public ContainerCriteriaStr setCreateDatePattern(String createDatePattern) {
        if (createDatePattern != null && createDatePattern.trim().length() == 0) {
            return this;
        }
        this.createDatePattern = createDatePattern;
        this.createDateValid.setPattern(createDatePattern);
        this.startCreateDateValid.setPattern(createDatePattern);
        this.endCreateDateValid.setPattern(createDatePattern);
        return this;
    }

    public String getStartCreateDate() {
        return startCreateDate;
    }

    public ContainerCriteriaStr setStartCreateDate(String startCreateDate) {
        if (startCreateDate != null && startCreateDate.trim().length() == 0) {
            return this;
        }
        this.startCreateDate = startCreateDate;
        this.startCreateDateValid.setDateStr(startCreateDate);
        return this;
    }

    public String getEndCreateDate() {
        return endCreateDate;
    }

    public ContainerCriteriaStr setEndCreateDate(String endCreateDate) {
        if (endCreateDate != null && endCreateDate.trim().length() == 0) {
            return this;
        }
        this.endCreateDate = endCreateDate;
        this.endCreateDateValid.setDateStr(endCreateDate);
        return this;
    }

    /**
     * get table [senpure_container][column = create_date] criteriaOrder
     *
     * @return
     */
    public String getCreateDateOrder() {
        return createDateOrder;
    }

    /**
     * set table [senpure_container][column = create_date] criteriaOrder DESC||ASC
     *
     * @return
     */
    public ContainerCriteriaStr setCreateDateOrder(String createDateOrder) {
        if (createDateOrder != null && createDateOrder.trim().length() == 0) {
            this.createDateOrder = null;
            return this;
        }
        this.createDateOrder = createDateOrder;
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
    public ContainerCriteriaStr setContainerStructure(String containerStructure) {
        if (containerStructure != null && containerStructure.trim().length() == 0) {
            return this;
        }
        this.containerStructure = containerStructure;
        return this;
    }

    /**
     * get 父容器，实体(外键,modelName:Container,tableName:senpure_container)
     *
     * @return
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * set 父容器，实体(外键,modelName:Container,tableName:senpure_container)
     *
     * @return
     */
    public ContainerCriteriaStr setParentId(String parentId) {
        if (parentId != null && parentId.trim().length() == 0) {
            return this;
        }
        this.parentId = parentId;
        return this;
    }

    /**
     * get table [senpure_container][column = parent_id] criteriaOrder
     *
     * @return
     */
    public String getParentIdOrder() {
        return parentIdOrder;
    }

    /**
     * set table [senpure_container][column = parent_id] criteriaOrder DESC||ASC
     *
     * @return
     */
    public ContainerCriteriaStr setParentIdOrder(String parentIdOrder) {
        if (parentIdOrder != null && parentIdOrder.trim().length() == 0) {
            this.parentIdOrder = null;
            return this;
        }
        this.parentIdOrder = parentIdOrder;
        return this;
    }

}