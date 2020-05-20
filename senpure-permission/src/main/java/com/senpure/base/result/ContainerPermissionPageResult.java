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
public class ContainerPermissionPageResult extends ActionResult {
    private static final long serialVersionUID = 1444113447L;

    public static final String RECORDS_NAME = "containerPermissions";

    @ApiModelProperty(position = 3, value = "结果集总数", example = "1086")
    private int total;
    @ApiModelProperty(position = 4, value = "一页数据")
    private List<ContainerPermission> containerPermissions ;

    public static ContainerPermissionPageResult success() {
        return new ContainerPermissionPageResult(Result.SUCCESS);
    }

    public static ContainerPermissionPageResult dim() {
        return new ContainerPermissionPageResult(Result.ERROR_DIM);
    }

    public static ContainerPermissionPageResult failure() {
        return new ContainerPermissionPageResult(Result.FAILURE);
    }

    public static ContainerPermissionPageResult notExist() {
        return new ContainerPermissionPageResult(Result.TARGET_NOT_EXIST);
    }

    public static ContainerPermissionPageResult result(int code) {
        return new ContainerPermissionPageResult(code);
    }

    public ContainerPermissionPageResult() {
    }

    public ContainerPermissionPageResult(int code) {
        super(code);
    }

    public int getTotal() {
        return total;
    }

    public ContainerPermissionPageResult setTotal(int total) {
        this.total = total;
        return this;
    }

    public List<ContainerPermission> getContainerPermissions() {
        return containerPermissions;
    }

    public ContainerPermissionPageResult setContainerPermissions(List<ContainerPermission> containerPermissions) {
        this.containerPermissions = containerPermissions;
        return this;
    }

    @Override
    public ContainerPermissionPageResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public ContainerPermissionPageResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public ContainerPermissionPageResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public ContainerPermissionPageResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public ContainerPermissionPageResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}