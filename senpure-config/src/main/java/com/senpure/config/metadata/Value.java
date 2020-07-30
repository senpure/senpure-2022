package com.senpure.config.metadata;

import java.util.ArrayList;
import java.util.List;


public class Value {

    private String value;
    private String inJavaCodeValue;
    private Field field;

    private List<String> inJavaCodeValues = new ArrayList<>();

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getInJavaCodeValue() {
        return inJavaCodeValue;
    }

    public void setInJavaCodeValue(String inJavaCodeValue) {
        this.inJavaCodeValue = inJavaCodeValue;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public List<String> getInJavaCodeValues() {
        return inJavaCodeValues;
    }

    public void setInJavaCodeValues(List<String> inJavaCodeValues) {
        this.inJavaCodeValues = inJavaCodeValues;
    }
}
