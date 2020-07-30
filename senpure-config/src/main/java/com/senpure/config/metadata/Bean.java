package com.senpure.config.metadata;


import com.senpure.config.configure.ConfigProperties;

import java.util.ArrayList;
import java.util.List;


public class Bean {

    private String name;
    private String javaPack;
    private List<Field> fields = new ArrayList<>();
    private String explain;

    private boolean hasList;
    private Field id;
    private List<Record> records = new ArrayList<>();

    private ConfigProperties config;

    public boolean isHasExplain() {
        return explain != null && explain.length() > 0;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJavaPack() {
        return javaPack;
    }

    public void setJavaPack(String javaPack) {
        this.javaPack = javaPack;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public Field getId() {
        return id;
    }

    public void setId(Field id) {
        this.id = id;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }


    public ConfigProperties getConfig() {
        return config;
    }

    public void setConfig(ConfigProperties config) {
        this.config = config;
    }

    public boolean isHasList() {
        return hasList;
    }

    public void setHasList(boolean hasList) {
        this.hasList = hasList;
    }
}
