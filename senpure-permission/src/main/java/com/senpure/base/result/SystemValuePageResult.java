package com.senpure.base.result;

import com.senpure.base.ActionResult;
import com.senpure.base.Result;
import com.senpure.base.model.SystemValue;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Locale;

/**
 * @author senpure
 * @version 2020-5-20 18:21:46
 */
public class SystemValuePageResult extends ActionResult {
    private static final long serialVersionUID = 748356364L;

    public static final String RECORDS_NAME = "systemValues";

    @ApiModelProperty(position = 3, value = "结果集总数", example = "1086")
    private int total;
    @ApiModelProperty(position = 4, value = "一页数据")
    private List<SystemValue> systemValues ;

    public static SystemValuePageResult success() {
        return new SystemValuePageResult(Result.SUCCESS);
    }

    public static SystemValuePageResult dim() {
        return new SystemValuePageResult(Result.ERROR_DIM);
    }

    public static SystemValuePageResult failure() {
        return new SystemValuePageResult(Result.FAILURE);
    }

    public static SystemValuePageResult notExist() {
        return new SystemValuePageResult(Result.TARGET_NOT_EXIST);
    }

    public static SystemValuePageResult result(int code) {
        return new SystemValuePageResult(code);
    }

    public SystemValuePageResult() {
    }

    public SystemValuePageResult(int code) {
        super(code);
    }

    public int getTotal() {
        return total;
    }

    public SystemValuePageResult setTotal(int total) {
        this.total = total;
        return this;
    }

    public List<SystemValue> getSystemValues() {
        return systemValues;
    }

    public SystemValuePageResult setSystemValues(List<SystemValue> systemValues) {
        this.systemValues = systemValues;
        return this;
    }

    @Override
    public SystemValuePageResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public SystemValuePageResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public SystemValuePageResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public SystemValuePageResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public SystemValuePageResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}