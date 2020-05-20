package com.senpure.base.result;

import com.senpure.base.ActionResult;
import com.senpure.base.Result;
import com.senpure.base.model.Role;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Locale;

/**
 * @author senpure
 * @version 2020-5-20 18:21:46
 */
public class RoleRecordResult extends ActionResult {
    private static final long serialVersionUID = 1021021432L;

    public static final String RECORD_NAME = "role";

    @ApiModelProperty(position = 3)
    private Role role;

    public static RoleRecordResult success() {
        return new RoleRecordResult(Result.SUCCESS);
    }

    public static RoleRecordResult dim() {
        return new RoleRecordResult(Result.ERROR_DIM);
    }

    public static RoleRecordResult failure() {
        return new RoleRecordResult(Result.FAILURE);
    }

    public static RoleRecordResult notExist() {
        return new RoleRecordResult(Result.TARGET_NOT_EXIST);
    }

    public static RoleRecordResult result(int code) {
        return new RoleRecordResult(code);
    }

    public RoleRecordResult() {
    }

    public RoleRecordResult(int code) {
        super(code);
    }

    public Role getRole() {
        return role;
    }

    public RoleRecordResult setRole(Role role) {
        this.role = role;
        return this;
    }

    @Override
    public RoleRecordResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public RoleRecordResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public RoleRecordResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public RoleRecordResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public RoleRecordResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}