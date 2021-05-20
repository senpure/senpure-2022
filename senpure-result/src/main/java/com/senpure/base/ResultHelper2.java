package com.senpure.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

public class ResultHelper2 {

    private final Logger logger = LoggerFactory.getLogger(ResultHelper.class);
    private final Map<Integer, CodeResult> codeMap = new HashMap<>();
    private final Map<String, CodeResult> keyMap = new HashMap<>();

    public void register(CodeResult codeResult) {
        CodeResult lastCodeResult = codeMap.get(codeResult.getCode());
        Assert.isNull(lastCodeResult, () -> "code不能重复 [" + codeResult.getCode() + "]" + lastCodeResult.getSource() + "|" + codeResult.getSource());
        CodeResult lastKeyResult = keyMap.get(codeResult.getKey());
        Assert.isNull(lastCodeResult, () -> "key不能重复 [" + codeResult.getKey() + "]" + lastKeyResult.getSource() + "|" + lastKeyResult.getSource());
        codeMap.put(codeResult.getCode(), codeResult);
        keyMap.put(codeResult.getKey(), codeResult);
    }


    @NonNull
    public CodeResult getCodeResult(int code) {

        return codeMap.get(code);
    }
}
