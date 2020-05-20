package com.senpure.base.result;

import com.senpure.base.ActionResult;
import com.senpure.base.Result;
import com.senpure.base.model.PermissionMenu;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Locale;

/**
 * @author senpure
 * @version 2020-5-20 18:21:46
 */
public class PermissionMenuPageResult extends ActionResult {
    private static final long serialVersionUID = 343182696L;

    public static final String RECORDS_NAME = "permissionMenus";

    @ApiModelProperty(position = 3, value = "结果集总数", example = "1086")
    private int total;
    @ApiModelProperty(position = 4, value = "一页数据")
    private List<PermissionMenu> permissionMenus ;

    public static PermissionMenuPageResult success() {
        return new PermissionMenuPageResult(Result.SUCCESS);
    }

    public static PermissionMenuPageResult dim() {
        return new PermissionMenuPageResult(Result.ERROR_DIM);
    }

    public static PermissionMenuPageResult failure() {
        return new PermissionMenuPageResult(Result.FAILURE);
    }

    public static PermissionMenuPageResult notExist() {
        return new PermissionMenuPageResult(Result.TARGET_NOT_EXIST);
    }

    public static PermissionMenuPageResult result(int code) {
        return new PermissionMenuPageResult(code);
    }

    public PermissionMenuPageResult() {
    }

    public PermissionMenuPageResult(int code) {
        super(code);
    }

    public int getTotal() {
        return total;
    }

    public PermissionMenuPageResult setTotal(int total) {
        this.total = total;
        return this;
    }

    public List<PermissionMenu> getPermissionMenus() {
        return permissionMenus;
    }

    public PermissionMenuPageResult setPermissionMenus(List<PermissionMenu> permissionMenus) {
        this.permissionMenus = permissionMenus;
        return this;
    }

    @Override
    public PermissionMenuPageResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public PermissionMenuPageResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public PermissionMenuPageResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public PermissionMenuPageResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public PermissionMenuPageResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}