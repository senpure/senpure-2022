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
public class RangeConfigRecordResult extends ActionResult {
    private static final long serialVersionUID = 2087653624L;

    public static final String RECORD_NAME = "rangeConfig";

    @ApiModelProperty(position = 3)
    private RangeConfig rangeConfig;

    public static RangeConfigRecordResult success() {
        return new RangeConfigRecordResult(Result.SUCCESS);
    }

    public static RangeConfigRecordResult dim() {
        return new RangeConfigRecordResult(Result.ERROR_DIM);
    }

    public static RangeConfigRecordResult failure() {
        return new RangeConfigRecordResult(Result.FAILURE);
    }

    public static RangeConfigRecordResult notExist() {
        return new RangeConfigRecordResult(Result.TARGET_NOT_EXIST);
    }

    public static RangeConfigRecordResult result(int code) {
        return new RangeConfigRecordResult(code);
    }

    public RangeConfigRecordResult() {
    }

    public RangeConfigRecordResult(int code) {
        super(code);
    }

    public RangeConfig getRangeConfig() {
        return rangeConfig;
    }

    public RangeConfigRecordResult setRangeConfig(RangeConfig rangeConfig) {
        this.rangeConfig = rangeConfig;
        return this;
    }

    @Override
    public RangeConfigRecordResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public RangeConfigRecordResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public RangeConfigRecordResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public RangeConfigRecordResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public RangeConfigRecordResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}