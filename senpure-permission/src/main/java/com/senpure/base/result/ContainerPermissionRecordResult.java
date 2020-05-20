package com.senpure.base.result;

import com.senpure.base.ActionResult;
import com.senpure.base.Result;
import com.senpure.base.model.ContainerPermission;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Locale;

/**
 * @author senpure
 * @version 2020-5-20 18:21:46
 */
public class ContainerPermissionRecordResult extends ActionResult {
    private static final long serialVersionUID = 1444113447L;

    public static final String RECORD_NAME = "containerPermission";

    @ApiModelProperty(position = 3)
    private ContainerPermission containerPermission;

    public static ContainerPermissionRecordResult success() {
        return new ContainerPermissionRecordResult(Result.SUCCESS);
    }

    public static ContainerPermissionRecordResult dim() {
        return new ContainerPermissionRecordResult(Result.ERROR_DIM);
    }

    public static ContainerPermissionRecordResult failure() {
        return new ContainerPermissionRecordResult(Result.FAILURE);
    }

    public static ContainerPermissionRecordResult notExist() {
        return new ContainerPermissionRecordResult(Result.TARGET_NOT_EXIST);
    }

    public static ContainerPermissionRecordResult result(int code) {
        return new ContainerPermissionRecordResult(code);
    }

    public ContainerPermissionRecordResult() {
    }

    public ContainerPermissionRecordResult(int code) {
        super(code);
    }

    public ContainerPermission getContainerPermission() {
        return containerPermission;
    }

    public ContainerPermissionRecordResult setContainerPermission(ContainerPermission containerPermission) {
        this.containerPermission = containerPermission;
        return this;
    }

    @Override
    public ContainerPermissionRecordResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public ContainerPermissionRecordResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public ContainerPermissionRecordResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public ContainerPermissionRecordResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public ContainerPermissionRecordResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}