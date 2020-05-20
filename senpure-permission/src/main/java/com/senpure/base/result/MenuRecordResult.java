package com.senpure.base.result;

import com.senpure.base.ActionResult;
import com.senpure.base.Result;
import com.senpure.base.model.Menu;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Locale;

/**
 * @author senpure
 * @version 2020-5-20 18:21:45
 */
public class MenuRecordResult extends ActionResult {
    private static final long serialVersionUID = 1493076390L;

    public static final String RECORD_NAME = "menu";

    @ApiModelProperty(position = 3)
    private Menu menu;

    public static MenuRecordResult success() {
        return new MenuRecordResult(Result.SUCCESS);
    }

    public static MenuRecordResult dim() {
        return new MenuRecordResult(Result.ERROR_DIM);
    }

    public static MenuRecordResult failure() {
        return new MenuRecordResult(Result.FAILURE);
    }

    public static MenuRecordResult notExist() {
        return new MenuRecordResult(Result.TARGET_NOT_EXIST);
    }

    public static MenuRecordResult result(int code) {
        return new MenuRecordResult(code);
    }

    public MenuRecordResult() {
    }

    public MenuRecordResult(int code) {
        super(code);
    }

    public Menu getMenu() {
        return menu;
    }

    public MenuRecordResult setMenu(Menu menu) {
        this.menu = menu;
        return this;
    }

    @Override
    public MenuRecordResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public MenuRecordResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public MenuRecordResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public MenuRecordResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public MenuRecordResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}