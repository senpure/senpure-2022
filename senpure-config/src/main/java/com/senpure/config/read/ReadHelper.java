package com.senpure.config.read;

import java.util.HashMap;
import java.util.Map;


public class ReadHelper {

    private static final String[] BASE_FIELDS;
    private static final Map<String, String> JAVA_LIST_TYPE_MAP = new HashMap<>();
    private static final Map<String, String> JAVA_TYPE_MAP = new HashMap<>();

    private static final Map<String, String> JAVA_DEFAULT_VALUE_MAP = new HashMap<>();

    private static final String[] BOOLEAN_TRUE = {"true", "1"};
    private static final String[] BOOLEAN_FALSE = {"false", "0"};

    static {
        BASE_FIELDS = new String[]{"int", "integer", "long", "double", "boolean", "String"};
        JAVA_TYPE_MAP.put("int", "int");
        JAVA_TYPE_MAP.put("integer", "int");
        JAVA_TYPE_MAP.put("long", "long");
        JAVA_TYPE_MAP.put("double", "double");
        JAVA_TYPE_MAP.put("boolean", "boolean");
        JAVA_TYPE_MAP.put("string", "String");
        JAVA_LIST_TYPE_MAP.put("int", "Integer");
        JAVA_LIST_TYPE_MAP.put("long", "Long");
        JAVA_LIST_TYPE_MAP.put("double", "Double");
        JAVA_LIST_TYPE_MAP.put("boolean", "Boolean");
        JAVA_LIST_TYPE_MAP.put("String", "String");

        JAVA_DEFAULT_VALUE_MAP.put("int", "0");
        JAVA_DEFAULT_VALUE_MAP.put("long", "0");
        JAVA_DEFAULT_VALUE_MAP.put("double", "0");
        JAVA_DEFAULT_VALUE_MAP.put("boolean", "false");
        JAVA_DEFAULT_VALUE_MAP.put("String", "");
    }

    private String[] baseFields;
    private Map<String, String> javaTypeMap;
    private Map<String, String> javaListTypeMap;
    private Map<String, String> javaDefaultValueMap;

    public ReadHelper() {
        baseFields = new String[BASE_FIELDS.length];
        for (int i = 0; i < BASE_FIELDS.length; i++) {
            baseFields[i] = BASE_FIELDS[i];
        }
        javaTypeMap = new HashMap<>();
        javaTypeMap.putAll(JAVA_TYPE_MAP);
        javaListTypeMap = new HashMap<>();
        javaListTypeMap.putAll(JAVA_LIST_TYPE_MAP);
        javaDefaultValueMap = new HashMap<>();
        javaDefaultValueMap.putAll(JAVA_DEFAULT_VALUE_MAP);
    }

    public String getJavaType(String type) {


        return javaTypeMap.get(type.toLowerCase());
    }

    public String getJavaListType(String javaType) {

        return javaListTypeMap.get(javaType);
    }

    public String getJavaDefaultValue(String javaType) {

        return javaDefaultValueMap.get(javaType);
    }

    public boolean isBaseField(String type) {
        if (type == null) {
            return false;
        }
        for (String str : baseFields) {
            if (str.equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }

    public boolean isBoolean(String value) {
        return isBooleanTrue(value) || isBooleanFalse(value);
    }

    public boolean isBooleanTrue(String value) {
        for (String str : BOOLEAN_TRUE) {
            if (value.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    public boolean isBooleanFalse(String value) {
        for (String str : BOOLEAN_FALSE) {
            if (value.equalsIgnoreCase(str)) {
                return true;
            }
        }

        return false;
    }

}
