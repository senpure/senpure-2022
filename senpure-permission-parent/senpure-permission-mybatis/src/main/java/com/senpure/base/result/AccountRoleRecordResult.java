package com.senpure.base.result;

import com.senpure.base.ActionResult;
import com.senpure.base.Result;
import com.senpure.base.model.AccountRole;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Locale;

/**
 * @author senpure
 * @version 2020-5-22 16:52:02
 */
public class AccountRoleRecordResult extends ActionResult {
    private static final long serialVersionUID = 220887430L;

    public static final String RECORD_NAME = "accountRole";

    @ApiModelProperty(position = 3)
    private AccountRole accountRole;

    public static AccountRoleRecordResult success() {
        return new AccountRoleRecordResult(Result.SUCCESS);
    }

    public static AccountRoleRecordResult dim() {
        return new AccountRoleRecordResult(Result.ERROR_DIM);
    }

    public static AccountRoleRecordResult failure() {
        return new AccountRoleRecordResult(Result.FAILURE);
    }

    public static AccountRoleRecordResult notExist() {
        return new AccountRoleRecordResult(Result.TARGET_NOT_EXIST);
    }

    public static AccountRoleRecordResult result(int code) {
        return new AccountRoleRecordResult(code);
    }

    public AccountRoleRecordResult() {
    }

    public AccountRoleRecordResult(int code) {
        super(code);
    }

    public AccountRole getAccountRole() {
        return accountRole;
    }

    public AccountRoleRecordResult setAccountRole(AccountRole accountRole) {
        this.accountRole = accountRole;
        return this;
    }

    @Override
    public AccountRoleRecordResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public AccountRoleRecordResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public AccountRoleRecordResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public AccountRoleRecordResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public AccountRoleRecordResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}