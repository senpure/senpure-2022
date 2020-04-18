package com.senpure.dispatcher.criteria;

import com.senpure.base.criterion.Criteria;
import com.senpure.dispatcher.model.RangeValue;

import java.io.Serializable;

/**
 * RangeValue
 *
 * @author senpure-generator
 * @version 2019-8-2 11:22:32
 */
public class RangeValueCriteria extends Criteria implements Serializable {
    private static final long serialVersionUID = 963940855L;

    //(主键)
    private Integer id;
    //乐观锁，版本控制
    private Integer version;
    private Integer configId;
    //table [range_value][column = config_id] criteriaOrder
    private String configIdOrder;
    private String configName;
    //table [range_value][column = config_name] criteriaOrder
    private String configNameOrder;
    private String serverName;
    //table [range_value][column = server_name] criteriaOrder
    private String serverNameOrder;
    private String serverKey;
    //table [range_value][column = server_key] criteriaOrder
    private String serverKeyOrder;
    private Long start;
    private Long end;

    public static RangeValue toRangeValue(RangeValueCriteria criteria, RangeValue rangeValue) {
        rangeValue.setId(criteria.getId());
        rangeValue.setConfigId(criteria.getConfigId());
        rangeValue.setConfigName(criteria.getConfigName());
        rangeValue.setServerName(criteria.getServerName());
        rangeValue.setServerKey(criteria.getServerKey());
        rangeValue.setStart(criteria.getStart());
        rangeValue.setEnd(criteria.getEnd());
        rangeValue.setVersion(criteria.getVersion());
        return rangeValue;
    }

    public RangeValue toRangeValue() {
        RangeValue rangeValue = new RangeValue();
        return toRangeValue(this, rangeValue);
    }

    /**
     * 将RangeValueCriteria 的有效值(不为空),赋值给 RangeValue
     *
     * @return RangeValue
     */
    public RangeValue effective(RangeValue rangeValue) {
        if (getId() != null) {
            rangeValue.setId(getId());
        }
        if (getConfigId() != null) {
            rangeValue.setConfigId(getConfigId());
        }
        if (getConfigName() != null) {
            rangeValue.setConfigName(getConfigName());
        }
        if (getServerName() != null) {
            rangeValue.setServerName(getServerName());
        }
        if (getServerKey() != null) {
            rangeValue.setServerKey(getServerKey());
        }
        if (getStart() != null) {
            rangeValue.setStart(getStart());
        }
        if (getEnd() != null) {
            rangeValue.setEnd(getEnd());
        }
        if (getVersion() != null) {
            rangeValue.setVersion(getVersion());
        }
        return rangeValue;
    }

    @Override
    protected void beforeStr(StringBuilder sb) {
        sb.append("RangeValueCriteria{");
        if (id != null) {
            sb.append("id=").append(id).append(",");
        }
        if (version != null) {
            sb.append("version=").append(version).append(",");
        }
        if (configId != null) {
            sb.append("configId=").append(configId).append(",");
        }
        if (configName != null) {
            sb.append("configName=").append(configName).append(",");
        }
        if (serverName != null) {
            sb.append("serverName=").append(serverName).append(",");
        }
        if (serverKey != null) {
            sb.append("serverKey=").append(serverKey).append(",");
        }
        if (start != null) {
            sb.append("start=").append(start).append(",");
        }
        if (end != null) {
            sb.append("end=").append(end).append(",");
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
    public RangeValueCriteria setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getConfigId() {
        return configId;
    }


    public RangeValueCriteria setConfigId(Integer configId) {
        this.configId = configId;
        return this;
    }

    /**
     * get table [range_value][column = config_id] criteriaOrder
     *
     * @return
     */
    public String getConfigIdOrder() {
        return configIdOrder;
    }

    /**
     * set table [range_value][column = config_id] criteriaOrder DESC||ASC
     *
     * @return
     */
    public RangeValueCriteria setConfigIdOrder(String configIdOrder) {
        this.configIdOrder = configIdOrder;
        putSort("config_id", configIdOrder);
        return this;
    }

    public String getConfigName() {
        return configName;
    }


    public RangeValueCriteria setConfigName(String configName) {
        if (configName != null && configName.trim().length() == 0) {
            this.configName = null;
            return this;
        }
        this.configName = configName;
        return this;
    }

    /**
     * get table [range_value][column = config_name] criteriaOrder
     *
     * @return
     */
    public String getConfigNameOrder() {
        return configNameOrder;
    }

    /**
     * set table [range_value][column = config_name] criteriaOrder DESC||ASC
     *
     * @return
     */
    public RangeValueCriteria setConfigNameOrder(String configNameOrder) {
        this.configNameOrder = configNameOrder;
        putSort("config_name", configNameOrder);
        return this;
    }

    public String getServerName() {
        return serverName;
    }


    public RangeValueCriteria setServerName(String serverName) {
        if (serverName != null && serverName.trim().length() == 0) {
            this.serverName = null;
            return this;
        }
        this.serverName = serverName;
        return this;
    }

    /**
     * get table [range_value][column = server_name] criteriaOrder
     *
     * @return
     */
    public String getServerNameOrder() {
        return serverNameOrder;
    }

    /**
     * set table [range_value][column = server_name] criteriaOrder DESC||ASC
     *
     * @return
     */
    public RangeValueCriteria setServerNameOrder(String serverNameOrder) {
        this.serverNameOrder = serverNameOrder;
        putSort("server_name", serverNameOrder);
        return this;
    }

    public String getServerKey() {
        return serverKey;
    }


    public RangeValueCriteria setServerKey(String serverKey) {
        if (serverKey != null && serverKey.trim().length() == 0) {
            this.serverKey = null;
            return this;
        }
        this.serverKey = serverKey;
        return this;
    }

    /**
     * get table [range_value][column = server_key] criteriaOrder
     *
     * @return
     */
    public String getServerKeyOrder() {
        return serverKeyOrder;
    }

    /**
     * set table [range_value][column = server_key] criteriaOrder DESC||ASC
     *
     * @return
     */
    public RangeValueCriteria setServerKeyOrder(String serverKeyOrder) {
        this.serverKeyOrder = serverKeyOrder;
        putSort("server_key", serverKeyOrder);
        return this;
    }

    public Long getStart() {
        return start;
    }


    public RangeValueCriteria setStart(Long start) {
        this.start = start;
        return this;
    }

    public Long getEnd() {
        return end;
    }


    public RangeValueCriteria setEnd(Long end) {
        this.end = end;
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
    public RangeValueCriteria setVersion(Integer version) {
        this.version = version;
        return this;
    }

}