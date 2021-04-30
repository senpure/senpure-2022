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
public class URIPermissionRecordResult extends ActionResult {
    private static final long serialVersionUID = 1046435846L;

    public static final String RECORD_NAME = "uriPermission";

    @ApiModelProperty(position = 3)
    private URIPermission uriPermission;

    public static URIPermissionRecordResult success() {
        return new URIPermissionRecordResult(Result.SUCCESS);
    }

    public static URIPermissionRecordResult dim() {
        return new URIPermissionRecordResult(Result.ERROR_DIM);
    }

    public static URIPermissionRecordResult failure() {
        return new URIPermissionRecordResult(Result.FAILURE);
    }

    public static URIPermissionRecordResult notExist() {
        return new URIPermissionRecordResult(Result.TARGET_NOT_EXIST);
    }

    public static URIPermissionRecordResult result(int code) {
        return new URIPermissionRecordResult(code);
    }

    public URIPermissionRecordResult() {
    }

    public URIPermissionRecordResult(int code) {
        super(code);
    }

    public URIPermission getUriPermission() {
        return uriPermission;
    }

    public URIPermissionRecordResult setUriPermission(URIPermission uriPermission) {
        this.uriPermission = uriPermission;
        return this;
    }

    @Override
    public URIPermissionRecordResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public URIPermissionRecordResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public URIPermissionRecordResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public URIPermissionRecordResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public URIPermissionRecordResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}