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
public class ContainerRecordResult extends ActionResult {
    private static final long serialVersionUID = 611392665L;

    public static final String RECORD_NAME = "container";

    @ApiModelProperty(position = 3)
    private Container container;

    public static ContainerRecordResult success() {
        return new ContainerRecordResult(Result.SUCCESS);
    }

    public static ContainerRecordResult dim() {
        return new ContainerRecordResult(Result.ERROR_DIM);
    }

    public static ContainerRecordResult failure() {
        return new ContainerRecordResult(Result.FAILURE);
    }

    public static ContainerRecordResult notExist() {
        return new ContainerRecordResult(Result.TARGET_NOT_EXIST);
    }

    public static ContainerRecordResult result(int code) {
        return new ContainerRecordResult(code);
    }

    public ContainerRecordResult() {
    }

    public ContainerRecordResult(int code) {
        super(code);
    }

    public Container getContainer() {
        return container;
    }

    public ContainerRecordResult setContainer(Container container) {
        this.container = container;
        return this;
    }

    @Override
    public ContainerRecordResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public ContainerRecordResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public ContainerRecordResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public ContainerRecordResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public ContainerRecordResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}