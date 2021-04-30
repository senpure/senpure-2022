package com.senpure.base.result;

import com.senpure.base.ActionResult;
import com.senpure.base.Result;
import com.senpure.base.model.AccountValue;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Locale;

/**
 * @author senpure
 * @version 2020-5-22 16:52:02
 */
public class AccountValueRecordResult extends ActionResult {
    private static final long serialVersionUID = 846733638L;

    public static final String RECORD_NAME = "accountValue";

    @ApiModelProperty(position = 3)
    private AccountValue accountValue;

    public static AccountValueRecordResult success() {
        return new AccountValueRecordResult(Result.SUCCESS);
    }

    public static AccountValueRecordResult dim() {
        return new AccountValueRecordResult(Result.ERROR_DIM);
    }

    public static AccountValueRecordResult failure() {
        return new AccountValueRecordResult(Result.FAILURE);
    }

    public static AccountValueRecordResult notExist() {
        return new AccountValueRecordResult(Result.TARGET_NOT_EXIST);
    }

    public static AccountValueRecordResult result(int code) {
        return new AccountValueRecordResult(code);
    }

    public AccountValueRecordResult() {
    }

    public AccountValueRecordResult(int code) {
        super(code);
    }

    public AccountValue getAccountValue() {
        return accountValue;
    }

    public AccountValueRecordResult setAccountValue(AccountValue accountValue) {
        this.accountValue = accountValue;
        return this;
    }

    @Override
    public AccountValueRecordResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public AccountValueRecordResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public AccountValueRecordResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public AccountValueRecordResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public AccountValueRecordResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}