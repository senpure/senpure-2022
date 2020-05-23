package com.senpure.base.result;

import com.senpure.base.ActionResult;
import com.senpure.base.Result;
import com.senpure.base.model.Menu;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Locale;

/**
 * @author senpure
 * @version 2020-5-22 17:34:33
 */
public class MenuPageResult extends ActionResult {
    private static final long serialVersionUID = 32275000L;

    public static final String RECORDS_NAME = "menus";

    @ApiModelProperty(position = 3, value = "结果集总数", example = "1086")
    private int total;
    @ApiModelProperty(position = 4, value = "一页数据")
    private List<Menu> menus ;

    public static MenuPageResult success() {
        return new MenuPageResult(Result.SUCCESS);
    }

    public static MenuPageResult dim() {
        return new MenuPageResult(Result.ERROR_DIM);
    }

    public static MenuPageResult failure() {
        return new MenuPageResult(Result.FAILURE);
    }

    public static MenuPageResult notExist() {
        return new MenuPageResult(Result.TARGET_NOT_EXIST);
    }

    public static MenuPageResult result(int code) {
        return new MenuPageResult(code);
    }

    public MenuPageResult() {
    }

    public MenuPageResult(int code) {
        super(code);
    }

    public int getTotal() {
        return total;
    }

    public MenuPageResult setTotal(int total) {
        this.total = total;
        return this;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public MenuPageResult setMenus(List<Menu> menus) {
        this.menus = menus;
        return this;
    }

    @Override
    public MenuPageResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public MenuPageResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public MenuPageResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public MenuPageResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public MenuPageResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}