package com.senpure.dispatcher.criteria;

import com.senpure.base.criterion.Criteria;
import com.senpure.dispatcher.model.RangeConfig;

import java.io.Serializable;

/**
 * RangeConfig
 *
 * @author senpure-generator
 * @version 2019-8-2 11:22:32
 */
public class RangeConfigCriteria extends Criteria implements Serializable {
    private static final long serialVersionUID = 2087653624L;

    //(主键)
    private Integer id;
    //乐观锁，版本控制
    private Integer version;
    private String name;
    //table [range_config][column = name] criteriaOrder
    private String nameOrder;
    private Long start;
    private Long end;
    private Long step;

    public static RangeConfig toRangeConfig(RangeConfigCriteria criteria, RangeConfig rangeConfig) {
        rangeConfig.setId(criteria.getId());
        rangeConfig.setName(criteria.getName());
        rangeConfig.setStart(criteria.getStart());
        rangeConfig.setEnd(criteria.getEnd());
        rangeConfig.setStep(criteria.getStep());
        rangeConfig.setVersion(criteria.getVersion());
        return rangeConfig;
    }

    public RangeConfig toRangeConfig() {
        RangeConfig rangeConfig = new RangeConfig();
        return toRangeConfig(this, rangeConfig);
    }

    /**
     * 将RangeConfigCriteria 的有效值(不为空),赋值给 RangeConfig
     *
     * @return RangeConfig
     */
    public RangeConfig effective(RangeConfig rangeConfig) {
        if (getId() != null) {
            rangeConfig.setId(getId());
        }
        if (getName() != null) {
            rangeConfig.setName(getName());
        }
        if (getStart() != null) {
            rangeConfig.setStart(getStart());
        }
        if (getEnd() != null) {
            rangeConfig.setEnd(getEnd());
        }
        if (getStep() != null) {
            rangeConfig.setStep(getStep());
        }
        if (getVersion() != null) {
            rangeConfig.setVersion(getVersion());
        }
        return rangeConfig;
    }

    @Override
    protected void beforeStr(StringBuilder sb) {
        sb.append("RangeConfigCriteria{");
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
    public RangeConfigCriteria setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }


    public RangeConfigCriteria setName(String name) {
        if (name != null && name.trim().length() == 0) {
            this.name = null;
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
    public RangeConfigCriteria setNameOrder(String nameOrder) {
        this.nameOrder = nameOrder;
        putSort("name", nameOrder);
        return this;
    }

    public Long getStart() {
        return start;
    }


    public RangeConfigCriteria setStart(Long start) {
        this.start = start;
        return this;
    }

    public Long getEnd() {
        return end;
    }


    public RangeConfigCriteria setEnd(Long end) {
        this.end = end;
        return this;
    }

    public Long getStep() {
        return step;
    }


    public RangeConfigCriteria setStep(Long step) {
        this.step = step;
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
    public RangeConfigCriteria setVersion(Integer version) {
        this.version = version;
        return this;
    }

}