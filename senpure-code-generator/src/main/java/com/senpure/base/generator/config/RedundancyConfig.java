package com.senpure.base.generator.config;

/**
 * RedundancyConfig
 *
 * @author senpure
 * @time 2020-05-18 11:32:41
 */
public class RedundancyConfig {

    private String showType = "Date";
    private String computeType = "Long";
    private String showSuffix = "Date";
    private String computeSuffix = "Time";
    //为true是必须保证显示的类型可以转换成另一个类型
    private boolean showOnlyOne = true;
    private boolean showShowType = true;
    private String transformMethod = "time";


    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getComputeType() {
        return computeType;
    }

    public void setComputeType(String computeType) {
        this.computeType = computeType;
    }

    public String getShowSuffix() {
        return showSuffix;
    }

    public void setShowSuffix(String showSuffix) {
        this.showSuffix = showSuffix;
    }

    public String getComputeSuffix() {
        return computeSuffix;
    }

    public void setComputeSuffix(String computeSuffix) {
        this.computeSuffix = computeSuffix;
    }


    public String getTransformMethod() {
        return transformMethod;
    }

    public void setTransformMethod(String transformMethod) {
        this.transformMethod = transformMethod;
    }

    public boolean isShowOnlyOne() {
        return showOnlyOne;
    }

    public void setShowOnlyOne(boolean showOnlyOne) {
        this.showOnlyOne = showOnlyOne;
    }

    public boolean isShowShowType() {
        return showShowType;
    }

    public void setShowShowType(boolean showShowType) {
        this.showShowType = showShowType;
    }
}
