package com.senpure.base.result;

import com.senpure.base.ActionResult;
import com.senpure.base.Result;
import com.senpure.base.model.Account;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Locale;

/**
 * 只记录账号信息相关信息
 *
 * @author senpure
 * @version 2020-5-22 16:52:02
 */
public class AccountPageResult extends ActionResult {
    private static final long serialVersionUID = 1379989838L;

    public static final String RECORDS_NAME = "accounts";

    @ApiModelProperty(position = 3, value = "结果集总数", example = "1086")
    private int total;
    @ApiModelProperty(position = 4, value = "一页数据")
    private List<Account> accounts ;

    public static AccountPageResult success() {
        return new AccountPageResult(Result.SUCCESS);
    }

    public static AccountPageResult dim() {
        return new AccountPageResult(Result.ERROR_DIM);
    }

    public static AccountPageResult failure() {
        return new AccountPageResult(Result.FAILURE);
    }

    public static AccountPageResult notExist() {
        return new AccountPageResult(Result.TARGET_NOT_EXIST);
    }

    public static AccountPageResult result(int code) {
        return new AccountPageResult(code);
    }

    public AccountPageResult() {
    }

    public AccountPageResult(int code) {
        super(code);
    }

    public int getTotal() {
        return total;
    }

    public AccountPageResult setTotal(int total) {
        this.total = total;
        return this;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public AccountPageResult setAccounts(List<Account> accounts) {
        this.accounts = accounts;
        return this;
    }

    @Override
    public AccountPageResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public AccountPageResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public AccountPageResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public AccountPageResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public AccountPageResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}