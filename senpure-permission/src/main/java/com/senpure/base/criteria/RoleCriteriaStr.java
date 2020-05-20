package com.senpure.base.criteria;

import com.senpure.base.criterion.CriteriaStr;
import com.senpure.base.struct.PatternDate;
import com.senpure.base.validator.DynamicDate;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author senpure
 * @version 2020-5-20 18:21:46
 */
public class RoleCriteriaStr extends CriteriaStr implements Serializable {
    private static final long serialVersionUID = 1021021432L;

    //(主键)
    @ApiModelProperty(value = "(主键)", dataType = "long", example = "666666", position = 6)
    private String id;
    //乐观锁，版本控制
    @ApiModelProperty(hidden = true )
    private String version;
    @ApiModelProperty(example = "name", position = 7)
    private String name;
    @ApiModelProperty(dataType = "date-time", example = "2020-05-20 00:00:00", position = 8)
    private String createDate;
    //createDate 时间格式
    @ApiModelProperty(value = "createDate 格式", example = "yyyy-MM-dd HH:mm:ss", position = 9)
    private String createDatePattern ;
    @DynamicDate
    private PatternDate createDateValid = new PatternDate();
    @ApiModelProperty(example = "description", position = 10)
    private String description;
    //(外键,modelName:Container,tableName:senpure_container)
    @ApiModelProperty(value = "(外键,modelName:Container,tableName:senpure_container)", dataType = "int", example = "666666", position = 11)
    private String containerId;
    @ApiModelProperty(value = "createDate 开始范围 (>=)", dataType = "date-time", example = "2020-05-20 00:00:00", position = 12)
    private String startCreateDate;
    @ApiModelProperty(value = "createDate 结束范围 (<=)", dataType = "date-time", example = "2020-05-20 23:59:59", position = 13)
    private String endCreateDate;
    @DynamicDate
    private PatternDate startCreateDateValid = new PatternDate();
    @DynamicDate
    private PatternDate endCreateDateValid = new PatternDate();
    //table [senpure_role][column = create_date] criteriaOrder
    @ApiModelProperty(value = "createDate 排序" , allowableValues = "ASC,DESC", position = 14)
    private String createDateOrder;
    //table [senpure_role][column = container_id] criteriaOrder
    @ApiModelProperty(value = "containerId 排序" , allowableValues = "ASC,DESC", position = 15)
    private String containerIdOrder;

    public RoleCriteria toRoleCriteria() {
        RoleCriteria criteria = new RoleCriteria();
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
        if (name != null) {
            criteria.setName(name);
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
        //table [senpure_role][column = create_date] criteriaOrder
        if (createDateOrder != null) {
            criteria.setCreateDateOrder(createDateOrder);
        }
        if (description != null) {
            criteria.setDescription(description);
        }
        //(外键,modelName:Container,tableName:senpure_container)
        if (containerId != null) {
            criteria.setContainerId(Integer.valueOf(containerId));
        }
        //table [senpure_role][column = container_id] criteriaOrder
        if (containerIdOrder != null) {
            criteria.setContainerIdOrder(containerIdOrder);
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
        sb.append("RoleCriteriaStr{");
        if (id != null) {
            sb.append("id=").append(id).append(",");
        }
        if (version != null) {
            sb.append("version=").append(version).append(",");
        }
        if (name != null) {
            sb.append("name=").append(name).append(",");
        }
        if (createDate != null) {
            sb.append("createDate=").append(createDate).append(",");
        }
        if (description != null) {
            sb.append("description=").append(description).append(",");
        }
        if (containerId != null) {
            sb.append("containerId=").append(containerId).append(",");
        }
    }

    @Override
    protected void afterStr(StringBuilder sb) {
        if (createDateOrder != null) {
            sb.append("createDateOrder=").append(createDateOrder).append(",");
        }
        if (containerIdOrder != null) {
            sb.append("containerIdOrder=").append(containerIdOrder).append(",");
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
    public RoleCriteriaStr setId(String id) {
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
    public RoleCriteriaStr setVersion(String version) {
        if (version != null && version.trim().length() == 0) {
            return this;
        }
        this.version = version;
        return this;
    }

    public String getName() {
        return name;
    }


    public RoleCriteriaStr setName(String name) {
        if (name != null && name.trim().length() == 0) {
            return this;
        }
        this.name = name;
        return this;
    }

    public String getCreateDate() {
        return createDate;
    }


    public RoleCriteriaStr setCreateDate(String createDate) {
        if (createDate != null && createDate.trim().length() == 0) {
            return this;
        }
        this.createDate = createDate;
        return this;
    }

    public String getCreateDatePattern() {
        return createDatePattern;
    }

    public RoleCriteriaStr setCreateDatePattern(String createDatePattern) {
        if (createDatePattern != null && createDatePattern.trim().length() == 0) {
            return this;
        }
        this.createDatePattern = createDatePattern;
        this.startCreateDateValid.setPattern(createDatePattern);
        this.endCreateDateValid.setPattern(createDatePattern);
        return this;
    }

    public String getStartCreateDate() {
        return startCreateDate;
    }

    public RoleCriteriaStr setStartCreateDate(String startCreateDate) {
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

    public RoleCriteriaStr setEndCreateDate(String endCreateDate) {
        if (endCreateDate != null && endCreateDate.trim().length() == 0) {
            return this;
        }
        this.endCreateDate = endCreateDate;
        this.endCreateDateValid.setDateStr(endCreateDate);
        return this;
    }

    /**
     * get table [senpure_role][column = create_date] criteriaOrder
     *
     * @return
     */
    public String getCreateDateOrder() {
        return createDateOrder;
    }

    /**
     * set table [senpure_role][column = create_date] criteriaOrder DESC||ASC
     *
     * @return
     */
    public RoleCriteriaStr setCreateDateOrder(String createDateOrder) {
        if (createDateOrder != null && createDateOrder.trim().length() == 0) {
            this.createDateOrder = null;
            return this;
        }
        this.createDateOrder = createDateOrder;
        return this;
    }

    public String getDescription() {
        return description;
    }


    public RoleCriteriaStr setDescription(String description) {
        if (description != null && description.trim().length() == 0) {
            return this;
        }
        this.description = description;
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
    public RoleCriteriaStr setContainerId(String containerId) {
        if (containerId != null && containerId.trim().length() == 0) {
            return this;
        }
        this.containerId = containerId;
        return this;
    }

    /**
     * get table [senpure_role][column = container_id] criteriaOrder
     *
     * @return
     */
    public String getContainerIdOrder() {
        return containerIdOrder;
    }

    /**
     * set table [senpure_role][column = container_id] criteriaOrder DESC||ASC
     *
     * @return
     */
    public RoleCriteriaStr setContainerIdOrder(String containerIdOrder) {
        if (containerIdOrder != null && containerIdOrder.trim().length() == 0) {
            this.containerIdOrder = null;
            return this;
        }
        this.containerIdOrder = containerIdOrder;
        return this;
    }

}