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
public class DispatcherLockPageResult extends ActionResult {
    private static final long serialVersionUID = 0L;

    public static final String RECORDS_NAME = "dispatcherLocks";

    @ApiModelProperty(position = 3, value = "结果集总数", example = "1086")
    private int total;
    @ApiModelProperty(position = 4, value = "一页数据")
    private List<DispatcherLock> dispatcherLocks ;

    public static DispatcherLockPageResult success() {
        return new DispatcherLockPageResult(Result.SUCCESS);
    }

    public static DispatcherLockPageResult dim() {
        return new DispatcherLockPageResult(Result.ERROR_DIM);
    }

    public static DispatcherLockPageResult failure() {
        return new DispatcherLockPageResult(Result.FAILURE);
    }

    public static DispatcherLockPageResult notExist() {
        return new DispatcherLockPageResult(Result.TARGET_NOT_EXIST);
    }

    public static DispatcherLockPageResult result(int code) {
        return new DispatcherLockPageResult(code);
    }

    public DispatcherLockPageResult() {
    }

    public DispatcherLockPageResult(int code) {
        super(code);
    }

    public int getTotal() {
        return total;
    }

    public DispatcherLockPageResult setTotal(int total) {
        this.total = total;
        return this;
    }

    public List<DispatcherLock> getDispatcherLocks() {
        return dispatcherLocks;
    }

    public DispatcherLockPageResult setDispatcherLocks(List<DispatcherLock> dispatcherLocks) {
        this.dispatcherLocks = dispatcherLocks;
        return this;
    }

    @Override
    public DispatcherLockPageResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public DispatcherLockPageResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public DispatcherLockPageResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public DispatcherLockPageResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public DispatcherLockPageResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}