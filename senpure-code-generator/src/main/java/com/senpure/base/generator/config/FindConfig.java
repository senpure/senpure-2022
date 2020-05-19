package com.senpure.base.generator.config;

/**
 * FindConfig
 *
 * @author senpure
 * @time 2020-05-19 17:58:51
 */
public class FindConfig {
    private String value;
    private boolean eq;
    private boolean startWith;
    private boolean endWith;
    private boolean findOne;



    public String getValue() {
        return value;
    }

    public FindConfig setValue(String value) {
        this.value = value;
        return this;
    }

    public boolean isEq() {
        return eq;
    }

    public FindConfig setEq(boolean eq) {
        this.eq = eq;
        return this;
    }

    public boolean isStartWith() {
        return startWith;
    }

    public FindConfig setStartWith(boolean startWith) {
        this.startWith = startWith;
        return this;
    }

    public boolean isEndWith() {
        return endWith;
    }

    public FindConfig setEndWith(boolean endWith) {
        this.endWith = endWith;
        return this;
    }

    public boolean isFindOne() {
        return findOne;
    }

    public FindConfig setFindOne(boolean findOne) {
        this.findOne = findOne;
        return this;
    }
}
