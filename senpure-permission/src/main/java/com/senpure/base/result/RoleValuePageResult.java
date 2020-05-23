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
public class RoleValuePageResult extends ActionResult {
    private static final long serialVersionUID = 1099570973L;

    public static final String RECORDS_NAME = "roleValues";

    @ApiModelProperty(position = 3, value = "结果集总数", example = "1086")
    private int total;
    @ApiModelProperty(position = 4, value = "一页数据")
    private List<RoleValue> roleValues ;

    public static RoleValuePageResult success() {
        return new RoleValuePageResult(Result.SUCCESS);
    }

    public static RoleValuePageResult dim() {
        return new RoleValuePageResult(Result.ERROR_DIM);
    }

    public static RoleValuePageResult failure() {
        return new RoleValuePageResult(Result.FAILURE);
    }

    public static RoleValuePageResult notExist() {
        return new RoleValuePageResult(Result.TARGET_NOT_EXIST);
    }

    public static RoleValuePageResult result(int code) {
        return new RoleValuePageResult(code);
    }

    public RoleValuePageResult() {
    }

    public RoleValuePageResult(int code) {
        super(code);
    }

    public int getTotal() {
        return total;
    }

    public RoleValuePageResult setTotal(int total) {
        this.total = total;
        return this;
    }

    public List<RoleValue> getRoleValues() {
        return roleValues;
    }

    public RoleValuePageResult setRoleValues(List<RoleValue> roleValues) {
        this.roleValues = roleValues;
        return this;
    }

    @Override
    public RoleValuePageResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public RoleValuePageResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public RoleValuePageResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public RoleValuePageResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public RoleValuePageResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}