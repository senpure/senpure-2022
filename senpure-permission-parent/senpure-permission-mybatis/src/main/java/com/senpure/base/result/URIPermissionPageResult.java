package com.senpure.base.result;

import com.senpure.base.ActionResult;
import com.senpure.base.Result;
import com.senpure.base.model.URIPermission;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Locale;

/**
 * @author senpure
 * @version 2020-5-22 14:19:27
 */
public class URIPermissionPageResult extends ActionResult {
    private static final long serialVersionUID = 1046435846L;

    public static final String RECORDS_NAME = "uriPermissions";

    @ApiModelProperty(position = 3, value = "结果集总数", example = "1086")
    private int total;
    @ApiModelProperty(position = 4, value = "一页数据")
    private List<URIPermission> uriPermissions ;

    public static URIPermissionPageResult success() {
        return new URIPermissionPageResult(Result.SUCCESS);
    }

    public static URIPermissionPageResult dim() {
        return new URIPermissionPageResult(Result.ERROR_DIM);
    }

    public static URIPermissionPageResult failure() {
        return new URIPermissionPageResult(Result.FAILURE);
    }

    public static URIPermissionPageResult notExist() {
        return new URIPermissionPageResult(Result.TARGET_NOT_EXIST);
    }

    public static URIPermissionPageResult result(int code) {
        return new URIPermissionPageResult(code);
    }

    public URIPermissionPageResult() {
    }

    public URIPermissionPageResult(int code) {
        super(code);
    }

    public int getTotal() {
        return total;
    }

    public URIPermissionPageResult setTotal(int total) {
        this.total = total;
        return this;
    }

    public List<URIPermission> getUriPermissions() {
        return uriPermissions;
    }

    public URIPermissionPageResult setUriPermissions(List<URIPermission> uriPermissions) {
        this.uriPermissions = uriPermissions;
        return this;
    }

    @Override
    public URIPermissionPageResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public URIPermissionPageResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public URIPermissionPageResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public URIPermissionPageResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public URIPermissionPageResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}