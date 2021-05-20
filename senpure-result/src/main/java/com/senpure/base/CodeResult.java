package com.senpure.base;

public class CodeResult {
    /**
     * code定义
     */
    private int code;
    /**
     * 国际化key
     */
    private String key;
    /**
     * 默认消息
     */

    private String defaultMessage;
    /**
     * http状态码
     */
    private int httpStatus;
    /**
     * 来源
     */
    private String source;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
