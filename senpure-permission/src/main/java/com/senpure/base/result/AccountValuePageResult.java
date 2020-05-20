package com.senpure.base.result;

import com.senpure.base.ActionResult;
import com.senpure.base.Result;
import com.senpure.base.model.AccountValue;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Locale;

/**
 * @author senpure
 * @version 2020-5-20 18:21:46
 */
public class AccountValuePageResult extends ActionResult {
    private static final long serialVersionUID = 846733638L;

    public static final String RECORDS_NAME = "accountValues";

    @ApiModelProperty(position = 3, value = "结果集总数", example = "1086")
    private int total;
    @ApiModelProperty(position = 4, value = "一页数据")
    private List<AccountValue> accountValues ;

    public static AccountValuePageResult success() {
        return new AccountValuePageResult(Result.SUCCESS);
    }

    public static AccountValuePageResult dim() {
        return new AccountValuePageResult(Result.ERROR_DIM);
    }

    public static AccountValuePageResult failure() {
        return new AccountValuePageResult(Result.FAILURE);
    }

    public static AccountValuePageResult notExist() {
        return new AccountValuePageResult(Result.TARGET_NOT_EXIST);
    }

    public static AccountValuePageResult result(int code) {
        return new AccountValuePageResult(code);
    }

    public AccountValuePageResult() {
    }

    public AccountValuePageResult(int code) {
        super(code);
    }

    public int getTotal() {
        return total;
    }

    public AccountValuePageResult setTotal(int total) {
        this.total = total;
        return this;
    }

    public List<AccountValue> getAccountValues() {
        return accountValues;
    }

    public AccountValuePageResult setAccountValues(List<AccountValue> accountValues) {
        this.accountValues = accountValues;
        return this;
    }

    @Override
    public AccountValuePageResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public AccountValuePageResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public AccountValuePageResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public AccountValuePageResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public AccountValuePageResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}