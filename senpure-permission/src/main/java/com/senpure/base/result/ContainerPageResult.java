package com.senpure.base.result;

import com.senpure.base.ActionResult;
import com.senpure.base.Result;
import com.senpure.base.model.Container;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Locale;

/**
 * @author senpure
 * @version 2020-5-20 18:21:45
 */
public class ContainerPageResult extends ActionResult {
    private static final long serialVersionUID = 611392665L;

    public static final String RECORDS_NAME = "containers";

    @ApiModelProperty(position = 3, value = "结果集总数", example = "1086")
    private int total;
    @ApiModelProperty(position = 4, value = "一页数据")
    private List<Container> containers ;

    public static ContainerPageResult success() {
        return new ContainerPageResult(Result.SUCCESS);
    }

    public static ContainerPageResult dim() {
        return new ContainerPageResult(Result.ERROR_DIM);
    }

    public static ContainerPageResult failure() {
        return new ContainerPageResult(Result.FAILURE);
    }

    public static ContainerPageResult notExist() {
        return new ContainerPageResult(Result.TARGET_NOT_EXIST);
    }

    public static ContainerPageResult result(int code) {
        return new ContainerPageResult(code);
    }

    public ContainerPageResult() {
    }

    public ContainerPageResult(int code) {
        super(code);
    }

    public int getTotal() {
        return total;
    }

    public ContainerPageResult setTotal(int total) {
        this.total = total;
        return this;
    }

    public List<Container> getContainers() {
        return containers;
    }

    public ContainerPageResult setContainers(List<Container> containers) {
        this.containers = containers;
        return this;
    }

    @Override
    public ContainerPageResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public ContainerPageResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public ContainerPageResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public ContainerPageResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public ContainerPageResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}