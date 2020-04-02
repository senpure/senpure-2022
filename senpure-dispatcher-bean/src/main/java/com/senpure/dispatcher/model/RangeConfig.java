package com.senpure.dispatcher.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * RangeConfig
 * 
 * @author senpure-generator
 * @version 2019-8-2 11:22:32
 */
@ApiModel(description = "RangeConfig")
public class RangeConfig implements Serializable {
    private static final long serialVersionUID = 2087653624L;

    //(主键)
    private Integer id;
    //乐观锁，版本控制
    @ApiModelProperty(hidden = true )
    private Integer version;
    @ApiModelProperty(example = "name", position = 1)
    private String name;
    @ApiModelProperty(dataType = "long", example = "666666", position = 2)
    private Long start;
    @ApiModelProperty(dataType = "long", example = "666666", position = 3)
    private Long end;
    @ApiModelProperty(dataType = "long", example = "666666", position = 4)
    private Long step;

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
    public RangeConfig setId(Integer id) {
        this.id = id;
        return this;
    }


    public String getName() {
        return name;
    }


    public RangeConfig setName(String name) {
        this.name = name;
        return this;
    }


    public Long getStart() {
        return start;
    }


    public RangeConfig setStart(Long start) {
        this.start = start;
        return this;
    }


    public Long getEnd() {
        return end;
    }


    public RangeConfig setEnd(Long end) {
        this.end = end;
        return this;
    }


    public Long getStep() {
        return step;
    }


    public RangeConfig setStep(Long step) {
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
    public RangeConfig setVersion(Integer version) {
        this.version = version;
        return this;
    }


    @Override
    public String toString() {
        return "RangeConfig{"
                + "id=" + id
                + ",version=" + version
                + ",name=" + name
                + ",start=" + start
                + ",end=" + end
                + ",step=" + step
                + "}";
    }

}