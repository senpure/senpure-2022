package com.senpure.base.result;

import com.senpure.base.ActionResult;
import com.senpure.base.Result;
import com.senpure.base.model.RoleValue;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Locale;

/**
 * @author senpure
 * @version 2020-5-22 16:52:02
 */
public class RoleValueRecordResult extends ActionResult {
    private static final long serialVersionUID = 1099570973L;

    public static final String RECORD_NAME = "roleValue";

    @ApiModelProperty(position = 3)
    private RoleValue roleValue;

    public static RoleValueRecordResult success() {
        return new RoleValueRecordResult(Result.SUCCESS);
    }

    public static RoleValueRecordResult dim() {
        return new RoleValueRecordResult(Result.ERROR_DIM);
    }

    public static RoleValueRecordResult failure() {
        return new RoleValueRecordResult(Result.FAILURE);
    }

    public static RoleValueRecordResult notExist() {
        return new RoleValueRecordResult(Result.TARGET_NOT_EXIST);
    }

    public static RoleValueRecordResult result(int code) {
        return new RoleValueRecordResult(code);
    }

    public RoleValueRecordResult() {
    }

    public RoleValueRecordResult(int code) {
        super(code);
    }

    public RoleValue getRoleValue() {
        return roleValue;
    }

    public RoleValueRecordResult setRoleValue(RoleValue roleValue) {
        this.roleValue = roleValue;
        return this;
    }

    @Override
    public RoleValueRecordResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public RoleValueRecordResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public RoleValueRecordResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public RoleValueRecordResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public RoleValueRecordResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}