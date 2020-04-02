package com.senpure.dispatcher.result;

import com.senpure.base.result.ActionResult;
import com.senpure.base.result.Result;
import com.senpure.dispatcher.model.RangeConfig;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Locale;

/**
 * RangeConfig
 *
 * @author senpure-generator
 * @version 2019-8-2 11:22:32
 */
public class RangeConfigPageResult extends ActionResult {
    private static final long serialVersionUID = 2087653624L;

    public static final String RECORDS_NAME = "rangeConfigs";

    @ApiModelProperty(position = 3, value = "结果集总数", example = "1086")
    private int total;
    @ApiModelProperty(position = 4, value = "一页数据")
    private List<RangeConfig> rangeConfigs ;

    public static RangeConfigPageResult success() {
        return new RangeConfigPageResult(Result.SUCCESS);
    }

    public static RangeConfigPageResult dim() {
        return new RangeConfigPageResult(Result.ERROR_DIM);
    }

    public static RangeConfigPageResult failure() {
        return new RangeConfigPageResult(Result.FAILURE);
    }

    public static RangeConfigPageResult notExist() {
        return new RangeConfigPageResult(Result.TARGET_NOT_EXIST);
    }

    public static RangeConfigPageResult result(int code) {
        return new RangeConfigPageResult(code);
    }

    public RangeConfigPageResult() {
    }

    public RangeConfigPageResult(int code) {
        super(code);
    }

    public int getTotal() {
        return total;
    }

    public RangeConfigPageResult setTotal(int total) {
        this.total = total;
        return this;
    }

    public List<RangeConfig> getRangeConfigs() {
        return rangeConfigs;
    }

    public RangeConfigPageResult setRangeConfigs(List<RangeConfig> rangeConfigs) {
        this.rangeConfigs = rangeConfigs;
        return this;
    }

    @Override
    public RangeConfigPageResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public RangeConfigPageResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public RangeConfigPageResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public RangeConfigPageResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public RangeConfigPageResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}