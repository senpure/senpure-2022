package com.senpure.config.metadata;


public class Field {

    //注释
    private String explain;
    //定义的类型
    private String classType;
    //定义的类型对应的java类型
    private String javaType;
    private String javaListType;
    //字段名
    private String name;
    private String listSeparator = ",";
    private boolean list;


    public boolean isHasExplain() {
        return explain != null && explain.length() > 0;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isList() {
        return list;
    }

    public void setList(boolean list) {
        this.list = list;
    }

    public String getJavaListType() {
        return javaListType;
    }

    public void setJavaListType(String javaListType) {
        this.javaListType = javaListType;
    }

    public String getListSeparator() {
        return listSeparator;
    }

    public void setListSeparator(String listSeparator) {
        this.listSeparator = listSeparator;
    }

    @Override
    public String toString() {
        return "Field{" +
                "explain='" + explain + '\'' +
                ", classType='" + classType + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
