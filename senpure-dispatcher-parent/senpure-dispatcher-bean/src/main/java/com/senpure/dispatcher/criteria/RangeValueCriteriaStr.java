package com.senpure.dispatcher.criteria;

import com.senpure.base.criterion.CriteriaStr;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * RangeValue
 *
 * @author senpure-generator
 * @version 2019-8-2 11:22:32
 */
public class RangeValueCriteriaStr extends CriteriaStr implements Serializable {
    private static final long serialVersionUID = 963940855L;

    //(主键)
    @ApiModelProperty(value = "(主键)", dataType = "int", example = "666666", position = 7)
    private String id;
    //乐观锁，版本控制
    @ApiModelProperty(hidden = true )
    private String version;
    @ApiModelProperty(dataType = "int", example = "666666", position = 8)
    private String configId;
    @ApiModelProperty(example = "configName", position = 9)
    private String configName;
    @ApiModelProperty(example = "serverName", position = 10)
    private String serverName;
    @ApiModelProperty(example = "serverKey", position = 11)
    private String serverKey;
    @ApiModelProperty(dataType = "long", example = "666666", position = 12)
    private String start;
    @ApiModelProperty(dataType = "long", example = "666666", position = 13)
    private String end;
    //table [range_value][column = config_id] criteriaOrder
    @ApiModelProperty(value = "configId 排序" , allowableValues = "ASC,DESC", position = 14)
    private String configIdOrder;
    //table [range_value][column = config_name] criteriaOrder
    @ApiModelProperty(value = "configName 排序" , allowableValues = "ASC,DESC", position = 15)
    private String configNameOrder;
    //table [range_value][column = server_name] criteriaOrder
    @ApiModelProperty(value = "serverName 排序" , allowableValues = "ASC,DESC", position = 16)
    private String serverNameOrder;
    //table [range_value][column = server_key] criteriaOrder
    @ApiModelProperty(value = "serverKey 排序" , allowableValues = "ASC,DESC", position = 17)
    private String serverKeyOrder;

    public RangeValueCriteria toRangeValueCriteria() {
        RangeValueCriteria criteria = new RangeValueCriteria();
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
        if (configId != null) {
            criteria.setConfigId(Integer.valueOf(configId));
        }
        //table [range_value][column = config_id] criteriaOrder
        if (configIdOrder != null) {
            criteria.setConfigIdOrder(configIdOrder);
        }
        if (configName != null) {
            criteria.setConfigName(configName);
        }
        //table [range_value][column = config_name] criteriaOrder
        if (configNameOrder != null) {
            criteria.setConfigNameOrder(configNameOrder);
        }
        if (serverName != null) {
            criteria.setServerName(serverName);
        }
        //table [range_value][column = server_name] criteriaOrder
        if (serverNameOrder != null) {
            criteria.setServerNameOrder(serverNameOrder);
        }
        if (serverKey != null) {
            criteria.setServerKey(serverKey);
        }
        //table [range_value][column = server_key] criteriaOrder
        if (serverKeyOrder != null) {
            criteria.setServerKeyOrder(serverKeyOrder);
        }
        if (start != null) {
            criteria.setStart(Long.valueOf(start));
        }
        if (end != null) {
            criteria.setEnd(Long.valueOf(end));
        }
        return criteria;
    }

    @Override
    protected void beforeStr(StringBuilder sb) {
        sb.append("RangeValueCriteriaStr{");
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

    @Override
    protected void afterStr(StringBuilder sb) {
        if (configIdOrder != null) {
            sb.append("configIdOrder=").append(configIdOrder).append(",");
        }
        if (configNameOrder != null) {
            sb.append("configNameOrder=").append(configNameOrder).append(",");
        }
        if (serverNameOrder != null) {
            sb.append("serverNameOrder=").append(serverNameOrder).append(",");
        }
        if (serverKeyOrder != null) {
            sb.append("serverKeyOrder=").append(serverKeyOrder).append(",");
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
    public RangeValueCriteriaStr setId(String id) {
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
    public RangeValueCriteriaStr setVersion(String version) {
        if (version != null && version.trim().length() == 0) {
            return this;
        }
        this.version = version;
        return this;
    }

    public String getConfigId() {
        return configId;
    }


    public RangeValueCriteriaStr setConfigId(String configId) {
        if (configId != null && configId.trim().length() == 0) {
            return this;
        }
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
    public RangeValueCriteriaStr setConfigIdOrder(String configIdOrder) {
        if (configIdOrder != null && configIdOrder.trim().length() == 0) {
            this.configIdOrder = null;
            return this;
        }
        this.configIdOrder = configIdOrder;
        return this;
    }

    public String getConfigName() {
        return configName;
    }


    public RangeValueCriteriaStr setConfigName(String configName) {
        if (configName != null && configName.trim().length() == 0) {
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
    public RangeValueCriteriaStr setConfigNameOrder(String configNameOrder) {
        if (configNameOrder != null && configNameOrder.trim().length() == 0) {
            this.configNameOrder = null;
            return this;
        }
        this.configNameOrder = configNameOrder;
        return this;
    }

    public String getServerName() {
        return serverName;
    }


    public RangeValueCriteriaStr setServerName(String serverName) {
        if (serverName != null && serverName.trim().length() == 0) {
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
    public RangeValueCriteriaStr setServerNameOrder(String serverNameOrder) {
        if (serverNameOrder != null && serverNameOrder.trim().length() == 0) {
            this.serverNameOrder = null;
            return this;
        }
        this.serverNameOrder = serverNameOrder;
        return this;
    }

    public String getServerKey() {
        return serverKey;
    }


    public RangeValueCriteriaStr setServerKey(String serverKey) {
        if (serverKey != null && serverKey.trim().length() == 0) {
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
    public RangeValueCriteriaStr setServerKeyOrder(String serverKeyOrder) {
        if (serverKeyOrder != null && serverKeyOrder.trim().length() == 0) {
            this.serverKeyOrder = null;
            return this;
        }
        this.serverKeyOrder = serverKeyOrder;
        return this;
    }

    public String getStart() {
        return start;
    }


    public RangeValueCriteriaStr setStart(String start) {
        if (start != null && start.trim().length() == 0) {
            return this;
        }
        this.start = start;
        return this;
    }

    public String getEnd() {
        return end;
    }


    public RangeValueCriteriaStr setEnd(String end) {
        if (end != null && end.trim().length() == 0) {
            return this;
        }
        this.end = end;
        return this;
    }

}