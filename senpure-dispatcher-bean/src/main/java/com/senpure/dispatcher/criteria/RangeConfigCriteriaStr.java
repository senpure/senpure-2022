package com.senpure.dispatcher.criteria;

import com.senpure.base.criterion.CriteriaStr;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * RangeConfig
 *
 * @author senpure-generator
 * @version 2019-8-2 11:22:32
 */
public class RangeConfigCriteriaStr extends CriteriaStr implements Serializable {
    private static final long serialVersionUID = 2087653624L;

    //(主键)
    @ApiModelProperty(value = "(主键)", dataType = "int", example = "666666", position = 5)
    private String id;
    //乐观锁，版本控制
    @ApiModelProperty(hidden = true )
    private String version;
    @ApiModelProperty(example = "name", position = 6)
    private String name;
    @ApiModelProperty(dataType = "long", example = "666666", position = 7)
    private String start;
    @ApiModelProperty(dataType = "long", example = "666666", position = 8)
    private String end;
    @ApiModelProperty(dataType = "long", example = "666666", position = 9)
    private String step;
    //table [range_config][column = name] criteriaOrder
    @ApiModelProperty(value = "name 排序" , allowableValues = "ASC,DESC", position = 10)
    private String nameOrder;

    public RangeConfigCriteria toRangeConfigCriteria() {
        RangeConfigCriteria criteria = new RangeConfigCriteria();
        criteria.setPage(Integer.valueOf(getPage()));
        criteria.setPageSize(Integer.valueOf(getPageSize()));
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
        //table [range_config][column = name] criteriaOrder
        if (nameOrder != null) {
            criteria.setNameOrder(nameOrder);
        }
        if (start != null) {
            criteria.setStart(Long.valueOf(start));
        }
        if (end != null) {
            criteria.setEnd(Long.valueOf(end));
        }
        if (step != null) {
            criteria.setStep(Long.valueOf(step));
        }
        return criteria;
    }

    @Override
    protected void beforeStr(StringBuilder sb) {
        sb.append("RangeConfigCriteriaStr{");
        if (id != null) {
            sb.append("id=").append(id).append(",");
        }
        if (version != null) {
            sb.append("version=").append(version).append(",");
        }
        if (name != null) {
            sb.append("name=").append(name).append(",");
        }
        if (start != null) {
            sb.append("start=").append(start).append(",");
        }
        if (end != null) {
            sb.append("end=").append(end).append(",");
        }
        if (step != null) {
            sb.append("step=").append(step).append(",");
        }
    }

    @Override
    protected void afterStr(StringBuilder sb) {
        if (nameOrder != null) {
            sb.append("nameOrder=").append(nameOrder).append(",");
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
    public RangeConfigCriteriaStr setId(String id) {
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
    public RangeConfigCriteriaStr setVersion(String version) {
        if (version != null && version.trim().length() == 0) {
            return this;
        }
        this.version = version;
        return this;
    }

    public String getName() {
        return name;
    }


    public RangeConfigCriteriaStr setName(String name) {
        if (name != null && name.trim().length() == 0) {
            return this;
        }
        this.name = name;
        return this;
    }

    /**
     * get table [range_config][column = name] criteriaOrder
     *
     * @return
     */
    public String getNameOrder() {
        return nameOrder;
    }

    /**
     * set table [range_config][column = name] criteriaOrder DESC||ASC
     *
     * @return
     */
    public RangeConfigCriteriaStr setNameOrder(String nameOrder) {
        if (nameOrder != null && nameOrder.trim().length() == 0) {
            this.nameOrder = null;
            return this;
        }
        this.nameOrder = nameOrder;
        return this;
    }

    public String getStart() {
        return start;
    }


    public RangeConfigCriteriaStr setStart(String start) {
        if (start != null && start.trim().length() == 0) {
            return this;
        }
        this.start = start;
        return this;
    }

    public String getEnd() {
        return end;
    }


    public RangeConfigCriteriaStr setEnd(String end) {
        if (end != null && end.trim().length() == 0) {
            return this;
        }
        this.end = end;
        return this;
    }

    public String getStep() {
        return step;
    }


    public RangeConfigCriteriaStr setStep(String step) {
        if (step != null && step.trim().length() == 0) {
            return this;
        }
        this.step = step;
        return this;
    }

}