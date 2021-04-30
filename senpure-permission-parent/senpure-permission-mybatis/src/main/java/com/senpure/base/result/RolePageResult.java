package com.senpure.base.result;

import com.senpure.base.ActionResult;
import com.senpure.base.Result;
import com.senpure.base.model.Role;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Locale;

/**
 * @author senpure
 * @version 2020-5-22 16:52:02
 */
public class RolePageResult extends ActionResult {
    private static final long serialVersionUID = 1021021432L;

    public static final String RECORDS_NAME = "roles";

    @ApiModelProperty(position = 3, value = "结果集总数", example = "1086")
    private int total;
    @ApiModelProperty(position = 4, value = "一页数据")
    private List<Role> roles ;

    public static RolePageResult success() {
        return new RolePageResult(Result.SUCCESS);
    }

    public static RolePageResult dim() {
        return new RolePageResult(Result.ERROR_DIM);
    }

    public static RolePageResult failure() {
        return new RolePageResult(Result.FAILURE);
    }

    public static RolePageResult notExist() {
        return new RolePageResult(Result.TARGET_NOT_EXIST);
    }

    public static RolePageResult result(int code) {
        return new RolePageResult(code);
    }

    public RolePageResult() {
    }

    public RolePageResult(int code) {
        super(code);
    }

    public int getTotal() {
        return total;
    }

    public RolePageResult setTotal(int total) {
        this.total = total;
        return this;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public RolePageResult setRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    @Override
    public RolePageResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public RolePageResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public RolePageResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public RolePageResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public RolePageResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}