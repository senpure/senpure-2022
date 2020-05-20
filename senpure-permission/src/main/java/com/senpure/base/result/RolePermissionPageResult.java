package com.senpure.base.result;

import com.senpure.base.ActionResult;
import com.senpure.base.Result;
import com.senpure.base.model.RolePermission;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Locale;

/**
 * @author senpure
 * @version 2020-5-20 18:21:45
 */
public class RolePermissionPageResult extends ActionResult {
    private static final long serialVersionUID = 589858148L;

    public static final String RECORDS_NAME = "rolePermissions";

    @ApiModelProperty(position = 3, value = "结果集总数", example = "1086")
    private int total;
    @ApiModelProperty(position = 4, value = "一页数据")
    private List<RolePermission> rolePermissions ;

    public static RolePermissionPageResult success() {
        return new RolePermissionPageResult(Result.SUCCESS);
    }

    public static RolePermissionPageResult dim() {
        return new RolePermissionPageResult(Result.ERROR_DIM);
    }

    public static RolePermissionPageResult failure() {
        return new RolePermissionPageResult(Result.FAILURE);
    }

    public static RolePermissionPageResult notExist() {
        return new RolePermissionPageResult(Result.TARGET_NOT_EXIST);
    }

    public static RolePermissionPageResult result(int code) {
        return new RolePermissionPageResult(code);
    }

    public RolePermissionPageResult() {
    }

    public RolePermissionPageResult(int code) {
        super(code);
    }

    public int getTotal() {
        return total;
    }

    public RolePermissionPageResult setTotal(int total) {
        this.total = total;
        return this;
    }

    public List<RolePermission> getRolePermissions() {
        return rolePermissions;
    }

    public RolePermissionPageResult setRolePermissions(List<RolePermission> rolePermissions) {
        this.rolePermissions = rolePermissions;
        return this;
    }

    @Override
    public RolePermissionPageResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public RolePermissionPageResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public RolePermissionPageResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public RolePermissionPageResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public RolePermissionPageResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}