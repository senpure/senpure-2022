package com.senpure.dispatcher.result;

import com.senpure.base.result.ActionResult;
import com.senpure.base.result.Result;
import com.senpure.dispatcher.model.DispatcherLock;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Locale;

/**
 * SnowflakeLock
 *
 * @author senpure-generator
 * @version 2019-8-2 11:22:32
 */
public class DispatcherLockRecordResult extends ActionResult {
    private static final long serialVersionUID = 0L;

    public static final String RECORD_NAME = "dispatcherLock";

    @ApiModelProperty(position = 3)
    private DispatcherLock dispatcherLock;

    public static DispatcherLockRecordResult success() {
        return new DispatcherLockRecordResult(Result.SUCCESS);
    }

    public static DispatcherLockRecordResult dim() {
        return new DispatcherLockRecordResult(Result.ERROR_DIM);
    }

    public static DispatcherLockRecordResult failure() {
        return new DispatcherLockRecordResult(Result.FAILURE);
    }

    public static DispatcherLockRecordResult notExist() {
        return new DispatcherLockRecordResult(Result.TARGET_NOT_EXIST);
    }

    public static DispatcherLockRecordResult result(int code) {
        return new DispatcherLockRecordResult(code);
    }

    public DispatcherLockRecordResult() {
    }

    public DispatcherLockRecordResult(int code) {
        super(code);
    }

    public DispatcherLock getDispatcherLock() {
        return dispatcherLock;
    }

    public DispatcherLockRecordResult setDispatcherLock(DispatcherLock dispatcherLock) {
        this.dispatcherLock = dispatcherLock;
        return this;
    }

    @Override
    public DispatcherLockRecordResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public DispatcherLockRecordResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public DispatcherLockRecordResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public DispatcherLockRecordResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public DispatcherLockRecordResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}