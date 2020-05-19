package com.senpure.base.generator;


import com.senpure.base.generator.config.RedundancyConfig;

import java.lang.reflect.Field;

public class ModelField {

    private boolean hasExplain;
    private String explain = "";
    //private public
    private String accessType = "";

    private String clazzType;
    private String name;
    private Field field;
    private String column;

    private boolean nullable;

    private boolean javaNullable;
    private String jdbcType;
    private boolean id;
    private boolean version;
    /**
     * 是否由数据库生成id
     */
    private boolean databaseId;
    /**
     * 是否是外键
     */
    private boolean foreignKey;

    /**
     * 是否有 范围条件判断
     */
    private boolean hasCriteriaRange;


    private boolean redundancy;
    private ModelField redundancyField;
    private RedundancyConfig redundancyConfig;

    /**
     * 是否对外显示，两个字段表示一个意思是，只显示一个 如 date 和long型的时间戳
     * 兼容 strShow
     */
    private boolean criteriaShow = true;

    private boolean findOne = false;
    /**
     * 是否是日期类型的字段 java.lang.Date
     */
    private boolean date;
    /**
     * 数字表示时间
     */
    private boolean longTime;
    private boolean useSimpleDate = false;

    /**
     * 是否可以条件排序
     */
    private boolean criteriaOrder = false;

    /**
     * Date 类型的 long类型字段
     */
    private ModelField longDate;


    public int getXmlLen() {
        String str = name + column + jdbcType;
        return str.length();

    }


    public int getColumnLen() {
        return column.length();
    }

    public int getNameLen() {
        return name.length();
    }

    public int getJdbcLen() {
        return jdbcType.length();
    }


    public boolean isHasCriteriaRange() {
        return hasCriteriaRange;
    }

    public boolean isStrShow() {
        return criteriaShow;
    }


    public void setHasCriteriaRange(boolean hasCriteriaRange) {
        this.hasCriteriaRange = hasCriteriaRange;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getClazzType() {
        return clazzType;
    }

    public void setClazzType(String clazzType) {
        this.clazzType = clazzType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public boolean isId() {
        return id;
    }

    public void setId(boolean id) {
        this.id = id;
    }


    public boolean isVersion() {
        return version;
    }

    public void setVersion(boolean version) {
        this.version = version;
    }

    public boolean isHasExplain() {
        return hasExplain;
    }


    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
        hasExplain = true;
    }


    public boolean isLongTime() {
        return longTime;
    }

    public void setLongTime(boolean longTime) {
        this.longTime = longTime;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isJavaNullable() {
        return javaNullable;
    }

    public void setJavaNullable(boolean javaNullable) {
        this.javaNullable = javaNullable;
    }

    public boolean isDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(boolean databaseId) {
        this.databaseId = databaseId;
    }

    public boolean isFindOne() {
        return findOne;
    }

    public void setFindOne(boolean findOne) {
        this.findOne = findOne;
    }

    public boolean isDate() {
        return date;
    }


    public void setDate(boolean date) {
        this.date = date;
    }


    public boolean isCriteriaOrder() {
        return criteriaOrder;
    }

    public void setCriteriaOrder(boolean criteriaOrder) {
        this.criteriaOrder = criteriaOrder;
    }


    public ModelField getLongDate() {
        return longDate;
    }

    public void setLongDate(ModelField longDate) {
        this.longDate = longDate;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }


    public void setHasExplain(boolean hasExplain) {
        this.hasExplain = hasExplain;
    }

    public RedundancyConfig getRedundancyConfig() {
        return redundancyConfig;
    }

    public void setRedundancyConfig(RedundancyConfig redundancyConfig) {
        this.redundancyConfig = redundancyConfig;
    }

    public boolean isRedundancy() {
        return redundancy;
    }

    public void setRedundancy(boolean redundancy) {
        this.redundancy = redundancy;
    }

    public boolean isCriteriaShow() {
        return criteriaShow;
    }

    public void setCriteriaShow(boolean criteriaShow) {
        this.criteriaShow = criteriaShow;
    }

    public boolean isForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(boolean foreignKey) {
        this.foreignKey = foreignKey;
    }

    public boolean isUseSimpleDate() {
        return useSimpleDate;
    }

    public void setUseSimpleDate(boolean useSimpleDate) {
        this.useSimpleDate = useSimpleDate;
    }

    public ModelField getRedundancyField() {
        return redundancyField;
    }

    public void setRedundancyField(ModelField redundancyField) {
        this.redundancyField = redundancyField;
    }

    @Override
    public String toString() {
        return "ModelField{" +
                "accessType='" + accessType + '\'' +
                ", clazzType='" + clazzType + '\'' +
                ", name='" + name + '\'' +
                ", column='" + column + '\'' +
                ", jdbcType='" + jdbcType + '\'' + (id ?
                ", id='" + id : "") + '\'' +
                (version ?
                        ", version='" + version : "") + '\'' +
                '}';
    }


}
