package com.senpure.base.result;

import com.senpure.base.ActionResult;
import com.senpure.base.Result;
import com.senpure.base.model.SystemValue;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Locale;

/**
 * @author senpure
 * @version 2020-5-22 16:52:02
 */
public class SystemValueRecordResult extends ActionResult {
    private static final long serialVersionUID = 748356364L;

    public static final String RECORD_NAME = "systemValue";

    @ApiModelProperty(position = 3)
    private SystemValue systemValue;

    public static SystemValueRecordResult success() {
        return new SystemValueRecordResult(Result.SUCCESS);
    }

    public static SystemValueRecordResult dim() {
        return new SystemValueRecordResult(Result.ERROR_DIM);
    }

    public static SystemValueRecordResult failure() {
        return new SystemValueRecordResult(Result.FAILURE);
    }

    public static SystemValueRecordResult notExist() {
        return new SystemValueRecordResult(Result.TARGET_NOT_EXIST);
    }

    public static SystemValueRecordResult result(int code) {
        return new SystemValueRecordResult(code);
    }

    public SystemValueRecordResult() {
    }

    public SystemValueRecordResult(int code) {
        super(code);
    }

    public SystemValue getSystemValue() {
        return systemValue;
    }

    public SystemValueRecordResult setSystemValue(SystemValue systemValue) {
        this.systemValue = systemValue;
        return this;
    }

    @Override
    public SystemValueRecordResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public SystemValueRecordResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public SystemValueRecordResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public SystemValueRecordResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public SystemValueRecordResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}