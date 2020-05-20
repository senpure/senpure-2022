package com.senpure.base.result;

import com.senpure.base.ActionResult;
import com.senpure.base.Result;
import com.senpure.base.model.Sequence;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Locale;

/**
 * @author senpure
 * @version 2020-5-20 18:21:45
 */
public class SequencePageResult extends ActionResult {
    private static final long serialVersionUID = 945199211L;

    public static final String RECORDS_NAME = "sequences";

    @ApiModelProperty(position = 3, value = "结果集总数", example = "1086")
    private int total;
    @ApiModelProperty(position = 4, value = "一页数据")
    private List<Sequence> sequences ;

    public static SequencePageResult success() {
        return new SequencePageResult(Result.SUCCESS);
    }

    public static SequencePageResult dim() {
        return new SequencePageResult(Result.ERROR_DIM);
    }

    public static SequencePageResult failure() {
        return new SequencePageResult(Result.FAILURE);
    }

    public static SequencePageResult notExist() {
        return new SequencePageResult(Result.TARGET_NOT_EXIST);
    }

    public static SequencePageResult result(int code) {
        return new SequencePageResult(code);
    }

    public SequencePageResult() {
    }

    public SequencePageResult(int code) {
        super(code);
    }

    public int getTotal() {
        return total;
    }

    public SequencePageResult setTotal(int total) {
        this.total = total;
        return this;
    }

    public List<Sequence> getSequences() {
        return sequences;
    }

    public SequencePageResult setSequences(List<Sequence> sequences) {
        this.sequences = sequences;
        return this;
    }

    @Override
    public SequencePageResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public SequencePageResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public SequencePageResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public SequencePageResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public SequencePageResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}