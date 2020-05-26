package com.senpure.base.result;

import com.senpure.base.ActionResult;
import com.senpure.base.Result;
import com.senpure.base.model.RolePermission;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Locale;

/**
 * @author senpure
 * @version 2020-5-22 16:52:02
 */
public class RolePermissionRecordResult extends ActionResult {
    private static final long serialVersionUID = 589858148L;

    public static final String RECORD_NAME = "rolePermission";

    @ApiModelProperty(position = 3)
    private RolePermission rolePermission;

    public static RolePermissionRecordResult success() {
        return new RolePermissionRecordResult(Result.SUCCESS);
    }

    public static RolePermissionRecordResult dim() {
        return new RolePermissionRecordResult(Result.ERROR_DIM);
    }

    public static RolePermissionRecordResult failure() {
        return new RolePermissionRecordResult(Result.FAILURE);
    }

    public static RolePermissionRecordResult notExist() {
        return new RolePermissionRecordResult(Result.TARGET_NOT_EXIST);
    }

    public static RolePermissionRecordResult result(int code) {
        return new RolePermissionRecordResult(code);
    }

    public RolePermissionRecordResult() {
    }

    public RolePermissionRecordResult(int code) {
        super(code);
    }

    public RolePermission getRolePermission() {
        return rolePermission;
    }

    public RolePermissionRecordResult setRolePermission(RolePermission rolePermission) {
        this.rolePermission = rolePermission;
        return this;
    }

    @Override
    public RolePermissionRecordResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public RolePermissionRecordResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public RolePermissionRecordResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public RolePermissionRecordResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public RolePermissionRecordResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}