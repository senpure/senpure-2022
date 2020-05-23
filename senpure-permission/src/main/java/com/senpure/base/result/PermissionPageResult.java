package com.senpure.base.result;

import com.senpure.base.ActionResult;
import com.senpure.base.Result;
import com.senpure.base.model.Permission;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Locale;

/**
 * @author senpure
 * @version 2020-5-22 17:23:15
 */
public class PermissionPageResult extends ActionResult {
    private static final long serialVersionUID = 2096486843L;

    public static final String RECORDS_NAME = "permissions";

    @ApiModelProperty(position = 3, value = "结果集总数", example = "1086")
    private int total;
    @ApiModelProperty(position = 4, value = "一页数据")
    private List<Permission> permissions ;

    public static PermissionPageResult success() {
        return new PermissionPageResult(Result.SUCCESS);
    }

    public static PermissionPageResult dim() {
        return new PermissionPageResult(Result.ERROR_DIM);
    }

    public static PermissionPageResult failure() {
        return new PermissionPageResult(Result.FAILURE);
    }

    public static PermissionPageResult notExist() {
        return new PermissionPageResult(Result.TARGET_NOT_EXIST);
    }

    public static PermissionPageResult result(int code) {
        return new PermissionPageResult(code);
    }

    public PermissionPageResult() {
    }

    public PermissionPageResult(int code) {
        super(code);
    }

    public int getTotal() {
        return total;
    }

    public PermissionPageResult setTotal(int total) {
        this.total = total;
        return this;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public PermissionPageResult setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
        return this;
    }

    @Override
    public PermissionPageResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public PermissionPageResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public PermissionPageResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public PermissionPageResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public PermissionPageResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}