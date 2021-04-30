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
public class AccountRolePageResult extends ActionResult {
    private static final long serialVersionUID = 220887430L;

    public static final String RECORDS_NAME = "accountRoles";

    @ApiModelProperty(position = 3, value = "结果集总数", example = "1086")
    private int total;
    @ApiModelProperty(position = 4, value = "一页数据")
    private List<AccountRole> accountRoles ;

    public static AccountRolePageResult success() {
        return new AccountRolePageResult(Result.SUCCESS);
    }

    public static AccountRolePageResult dim() {
        return new AccountRolePageResult(Result.ERROR_DIM);
    }

    public static AccountRolePageResult failure() {
        return new AccountRolePageResult(Result.FAILURE);
    }

    public static AccountRolePageResult notExist() {
        return new AccountRolePageResult(Result.TARGET_NOT_EXIST);
    }

    public static AccountRolePageResult result(int code) {
        return new AccountRolePageResult(code);
    }

    public AccountRolePageResult() {
    }

    public AccountRolePageResult(int code) {
        super(code);
    }

    public int getTotal() {
        return total;
    }

    public AccountRolePageResult setTotal(int total) {
        this.total = total;
        return this;
    }

    public List<AccountRole> getAccountRoles() {
        return accountRoles;
    }

    public AccountRolePageResult setAccountRoles(List<AccountRole> accountRoles) {
        this.accountRoles = accountRoles;
        return this;
    }

    @Override
    public AccountRolePageResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public AccountRolePageResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public AccountRolePageResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public AccountRolePageResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public AccountRolePageResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}