package com.senpure.dispatcher.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * RangeValue
 * 
 * @author senpure-generator
 * @version 2019-8-2 11:22:32
 */
@ApiModel(description = "RangeValue")
public class RangeValue implements Serializable {
    private static final long serialVersionUID = 963940855L;

    //(主键)
    private Integer id;
    //乐观锁，版本控制
    @ApiModelProperty(hidden = true )
    private Integer version;
    @ApiModelProperty(dataType = "int", example = "666666", position = 1)
    private Integer configId;
    @ApiModelProperty(example = "configName", position = 2)
    private String configName;
    @ApiModelProperty(example = "serverName", position = 3)
    private String serverName;
    @ApiModelProperty(example = "serverKey", position = 4)
    private String serverKey;
    @ApiModelProperty(dataType = "long", example = "666666", position = 5)
    private Long start;
    @ApiModelProperty(dataType = "long", example = "666666", position = 6)
    private Long end;

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
    public RangeValue setId(Integer id) {
        this.id = id;
        return this;
    }


    public Integer getConfigId() {
        return configId;
    }


    public RangeValue setConfigId(Integer configId) {
        this.configId = configId;
        return this;
    }


    public String getConfigName() {
        return configName;
    }


    public RangeValue setConfigName(String configName) {
        this.configName = configName;
        return this;
    }


    public String getServerName() {
        return serverName;
    }


    public RangeValue setServerName(String serverName) {
        this.serverName = serverName;
        return this;
    }


    public String getServerKey() {
        return serverKey;
    }


    public RangeValue setServerKey(String serverKey) {
        this.serverKey = serverKey;
        return this;
    }


    public Long getStart() {
        return start;
    }


    public RangeValue setStart(Long start) {
        this.start = start;
        return this;
    }


    public Long getEnd() {
        return end;
    }


    public RangeValue setEnd(Long end) {
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
    public RangeValue setVersion(Integer version) {
        this.version = version;
        return this;
    }


    @Override
    public String toString() {
        return "RangeValue{"
                + "id=" + id
                + ",version=" + version
                + ",configId=" + configId
                + ",configName=" + configName
                + ",serverName=" + serverName
                + ",serverKey=" + serverKey
                + ",start=" + start
                + ",end=" + end
                + "}";
    }

}