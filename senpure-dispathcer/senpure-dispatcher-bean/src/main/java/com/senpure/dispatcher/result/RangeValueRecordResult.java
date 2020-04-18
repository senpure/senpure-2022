package com.senpure.dispatcher.result;

import com.senpure.base.result.ActionResult;
import com.senpure.base.result.Result;
import com.senpure.dispatcher.model.RangeValue;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Locale;

/**
 * RangeValue
 *
 * @author senpure-generator
 * @version 2019-8-2 11:22:32
 */
public class RangeValueRecordResult extends ActionResult {
    private static final long serialVersionUID = 963940855L;

    public static final String RECORD_NAME = "rangeValue";

    @ApiModelProperty(position = 3)
    private RangeValue rangeValue;

    public static RangeValueRecordResult success() {
        return new RangeValueRecordResult(Result.SUCCESS);
    }

    public static RangeValueRecordResult dim() {
        return new RangeValueRecordResult(Result.ERROR_DIM);
    }

    public static RangeValueRecordResult failure() {
        return new RangeValueRecordResult(Result.FAILURE);
    }

    public static RangeValueRecordResult notExist() {
        return new RangeValueRecordResult(Result.TARGET_NOT_EXIST);
    }

    public static RangeValueRecordResult result(int code) {
        return new RangeValueRecordResult(code);
    }

    public RangeValueRecordResult() {
    }

    public RangeValueRecordResult(int code) {
        super(code);
    }

    public RangeValue getRangeValue() {
        return rangeValue;
    }

    public RangeValueRecordResult setRangeValue(RangeValue rangeValue) {
        this.rangeValue = rangeValue;
        return this;
    }

    @Override
    public RangeValueRecordResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public RangeValueRecordResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public RangeValueRecordResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public RangeValueRecordResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public RangeValueRecordResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}