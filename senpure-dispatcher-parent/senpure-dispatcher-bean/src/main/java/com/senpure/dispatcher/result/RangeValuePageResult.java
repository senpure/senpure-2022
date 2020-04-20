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
public class RangeValuePageResult extends ActionResult {
    private static final long serialVersionUID = 963940855L;

    public static final String RECORDS_NAME = "rangeValues";

    @ApiModelProperty(position = 3, value = "结果集总数", example = "1086")
    private int total;
    @ApiModelProperty(position = 4, value = "一页数据")
    private List<RangeValue> rangeValues ;

    public static RangeValuePageResult success() {
        return new RangeValuePageResult(Result.SUCCESS);
    }

    public static RangeValuePageResult dim() {
        return new RangeValuePageResult(Result.ERROR_DIM);
    }

    public static RangeValuePageResult failure() {
        return new RangeValuePageResult(Result.FAILURE);
    }

    public static RangeValuePageResult notExist() {
        return new RangeValuePageResult(Result.TARGET_NOT_EXIST);
    }

    public static RangeValuePageResult result(int code) {
        return new RangeValuePageResult(code);
    }

    public RangeValuePageResult() {
    }

    public RangeValuePageResult(int code) {
        super(code);
    }

    public int getTotal() {
        return total;
    }

    public RangeValuePageResult setTotal(int total) {
        this.total = total;
        return this;
    }

    public List<RangeValue> getRangeValues() {
        return rangeValues;
    }

    public RangeValuePageResult setRangeValues(List<RangeValue> rangeValues) {
        this.rangeValues = rangeValues;
        return this;
    }

    @Override
    public RangeValuePageResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public RangeValuePageResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public RangeValuePageResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public RangeValuePageResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public RangeValuePageResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}