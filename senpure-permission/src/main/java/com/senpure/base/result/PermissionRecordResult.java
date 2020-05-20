package com.senpure.base.result;

import com.senpure.base.ActionResult;
import com.senpure.base.Result;
import com.senpure.base.model.Permission;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Locale;

/**
 * @author senpure
 * @version 2020-5-20 18:21:46
 */
public class PermissionRecordResult extends ActionResult {
    private static final long serialVersionUID = 534669811L;

    public static final String RECORD_NAME = "permission";

    @ApiModelProperty(position = 3)
    private Permission permission;

    public static PermissionRecordResult success() {
        return new PermissionRecordResult(Result.SUCCESS);
    }

    public static PermissionRecordResult dim() {
        return new PermissionRecordResult(Result.ERROR_DIM);
    }

    public static PermissionRecordResult failure() {
        return new PermissionRecordResult(Result.FAILURE);
    }

    public static PermissionRecordResult notExist() {
        return new PermissionRecordResult(Result.TARGET_NOT_EXIST);
    }

    public static PermissionRecordResult result(int code) {
        return new PermissionRecordResult(code);
    }

    public PermissionRecordResult() {
    }

    public PermissionRecordResult(int code) {
        super(code);
    }

    public Permission getPermission() {
        return permission;
    }

    public PermissionRecordResult setPermission(Permission permission) {
        this.permission = permission;
        return this;
    }

    @Override
    public PermissionRecordResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public PermissionRecordResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public PermissionRecordResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public PermissionRecordResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public PermissionRecordResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}