package com.senpure.base.model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author senpure
 * @version 2020-5-22 16:52:02
 */
@ApiModel
public class Sequence implements Serializable {
    private static final long serialVersionUID = 945199211L;

    //(主键)
    private Long id;
    //乐观锁，版本控制
    @ApiModelProperty(hidden = true )
    private Integer version;
    //标识
    @ApiModelProperty(value = "标识", example = "type", position = 1)
    private String type;
    @ApiModelProperty(example = "prefix", position = 2)
    private String prefix;
    @ApiModelProperty(example = "suffix", position = 3)
    private String suffix;
    @ApiModelProperty(dataType = "int", example = "666666", position = 4)
    private Integer sequence;
    @ApiModelProperty(dataType = "int", example = "666666", position = 5)
    private Integer digit;
    @ApiModelProperty(dataType = "int", example = "666666", position = 6)
    private Integer span;

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
    public Sequence setId(Long id) {
        this.id = id;
        return this;
    }


    /**
     * get 标识
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * set 标识
     *
     * @return
     */
    public Sequence setType(String type) {
        this.type = type;
        return this;
    }


    public String getPrefix() {
        return prefix;
    }


    public Sequence setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }


    public String getSuffix() {
        return suffix;
    }


    public Sequence setSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }


    public Integer getSequence() {
        return sequence;
    }


    public Sequence setSequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }


    public Integer getDigit() {
        return digit;
    }


    public Sequence setDigit(Integer digit) {
        this.digit = digit;
        return this;
    }


    public Integer getSpan() {
        return span;
    }


    public Sequence setSpan(Integer span) {
        this.span = span;
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
    public Sequence setVersion(Integer version) {
        this.version = version;
        return this;
    }


    @Override
    public String toString() {
        return "Sequence{"
                + "id=" + id
                + ",version=" + version
                + ",type=" + type
                + ",prefix=" + prefix
                + ",suffix=" + suffix
                + ",sequence=" + sequence
                + ",digit=" + digit
                + ",span=" + span
                + "}";
    }

}