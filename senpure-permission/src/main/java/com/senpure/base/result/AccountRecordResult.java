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
public class AccountRecordResult extends ActionResult {
    private static final long serialVersionUID = 1379989838L;

    public static final String RECORD_NAME = "account";

    @ApiModelProperty(position = 3)
    private Account account;

    public static AccountRecordResult success() {
        return new AccountRecordResult(Result.SUCCESS);
    }

    public static AccountRecordResult dim() {
        return new AccountRecordResult(Result.ERROR_DIM);
    }

    public static AccountRecordResult failure() {
        return new AccountRecordResult(Result.FAILURE);
    }

    public static AccountRecordResult notExist() {
        return new AccountRecordResult(Result.TARGET_NOT_EXIST);
    }

    public static AccountRecordResult result(int code) {
        return new AccountRecordResult(code);
    }

    public AccountRecordResult() {
    }

    public AccountRecordResult(int code) {
        super(code);
    }

    public Account getAccount() {
        return account;
    }

    public AccountRecordResult setAccount(Account account) {
        this.account = account;
        return this;
    }

    @Override
    public AccountRecordResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public AccountRecordResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public AccountRecordResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public AccountRecordResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public AccountRecordResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}