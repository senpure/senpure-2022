package com.senpure.base.result;

import com.senpure.base.ActionResult;
import com.senpure.base.Result;
import com.senpure.base.model.Sequence;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Locale;

/**
 * @author senpure
 * @version 2020-5-22 16:52:02
 */
public class SequenceRecordResult extends ActionResult {
    private static final long serialVersionUID = 945199211L;

    public static final String RECORD_NAME = "sequence";

    @ApiModelProperty(position = 3)
    private Sequence sequence;

    public static SequenceRecordResult success() {
        return new SequenceRecordResult(Result.SUCCESS);
    }

    public static SequenceRecordResult dim() {
        return new SequenceRecordResult(Result.ERROR_DIM);
    }

    public static SequenceRecordResult failure() {
        return new SequenceRecordResult(Result.FAILURE);
    }

    public static SequenceRecordResult notExist() {
        return new SequenceRecordResult(Result.TARGET_NOT_EXIST);
    }

    public static SequenceRecordResult result(int code) {
        return new SequenceRecordResult(code);
    }

    public SequenceRecordResult() {
    }

    public SequenceRecordResult(int code) {
        super(code);
    }

    public Sequence getSequence() {
        return sequence;
    }

    public SequenceRecordResult setSequence(Sequence sequence) {
        this.sequence = sequence;
        return this;
    }

    @Override
    public SequenceRecordResult setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public SequenceRecordResult setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public SequenceRecordResult setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public SequenceRecordResult wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public SequenceRecordResult wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}