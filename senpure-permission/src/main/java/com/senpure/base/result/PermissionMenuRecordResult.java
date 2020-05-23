package com.senpure.base.result;

import com.senpure.base.ActionResult;
import com.senpure.base.Result;
import com.senpure.base.model.PermissionMenu;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Locale;

/**
 * @author senpure
 * @version 2020-5-22 16:52:02
 */
public class PermissionMenuRecordResult extends ActionResult {
    private static final long serialVersionUID = 343182696L;

    public static final String RECORD_NAME = "permissionMenu";

    @ApiModelProperty(position = 3)
    private PermissionMenu permissionMenu;

    public static PermissionMenuRecordResult success() {
        return new PermissionMenuRecordResult(Result.SUCCESS);
    }

    public static PermissionMenuRecordResult dim() {
        return new PermissionMenuRecordResult(Result.ERROR_DIM);
    }

    public static PermissionMenuRecordResult failure() {
        return new PermissionMenuRecordResult(Result.FAILURE);
    }

    public static PermissionMenuRecordResult notExist() {
        return new PermissionMenuRecordResult(Result.TARGET_NOT_EXIST);
    }

    public static PermissionMenuRecordResult result(int code) {
        return new PermissionMenuRecordResult(code);
    }

    public PermissionMenuRecordResult() {
    }

    public PermissionMenuRecordResult(int code) {
        super(code);
    }

    public PermissionMenu getPermissionMenu() {
        return permissionMenu;
    }

    public PermissionMenuRecordResult setPermissionMenu(PermissionMenu permissionMenu) {
        this.permissionMenu = permissionMenu;
        return this;
    }

    @Override
    public PermissionMenuRecordResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public PermissionMenuRecordResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public PermissionMenuRecordResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public PermissionMenuRecordResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public PermissionMenuRecordResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}