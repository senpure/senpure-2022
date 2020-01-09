package com.senpure.io.generator.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class ProtocolUtil {
    public static final int WIRETYPE_VARINT = 0;
    public static final int WIRETYPE_FIXED32 = 1;
    public static final int WIRETYPE_FIXED64 = 2;
    public static final int WIRETYPE_LENGTH_DELIMITED = 3;
    public static final String BYTES_FIELD_TYPE = "bytes";
    public static String[] baseFields = {"int", "long", "sint", "slong", "fixed32", "fixed64", "float", "double", "boolean", "string", "String", "byte", "short", "bytes"};

    /**
     * 不含兼容
     */
    public static String[] standardBaseFields = {"int", "long", "sint", "slong", "fixed32", "fixed64", "float", "double", "boolean", "String",  "bytes"};

    public static Map<String, Integer> writeType = new HashMap<>();
    public static Map<String, String> javaType = new HashMap<>();
    public static Set<String> listPacked = new HashSet<>();

    static {
        writeType.put("int", WIRETYPE_VARINT);
        writeType.put("long", WIRETYPE_VARINT);
        writeType.put("byte", WIRETYPE_VARINT);
        writeType.put("short", WIRETYPE_VARINT);
        writeType.put("boolean", WIRETYPE_VARINT);
        writeType.put("sint", WIRETYPE_VARINT);
        writeType.put("slong", WIRETYPE_VARINT);

        writeType.put("float", WIRETYPE_FIXED32);
        writeType.put("fixed32", WIRETYPE_FIXED32);


        writeType.put("double", WIRETYPE_FIXED64);
        writeType.put("fixed64", WIRETYPE_FIXED64);
        writeType.put("string", WIRETYPE_LENGTH_DELIMITED);
        writeType.put("String", WIRETYPE_LENGTH_DELIMITED);
        writeType.put("bytes", WIRETYPE_LENGTH_DELIMITED);

        javaType.put("int", "int");
        javaType.put("long", "long");
        javaType.put("sint", "int");
        javaType.put("slong", "long");
        javaType.put("byte", "int");
        javaType.put("short", "int");

        javaType.put("fixed32", "int");
        javaType.put("fixed64", "long");


        javaType.put("boolean", "boolean");
        javaType.put("String", "String");
        javaType.put("string", "String");
        javaType.put("float", "float");
        javaType.put("double", "double");
        javaType.put("bytes", "byte []");

        listPacked.add("int");
        listPacked.add("long");
        listPacked.add("sint");
        listPacked.add("slong");
        listPacked.add("byte");
        listPacked.add("short");
        listPacked.add("fixed32");
        listPacked.add("fixed64");
        listPacked.add("boolean");
        listPacked.add("float");
        listPacked.add("double");

    }


    public static int getWriteType(String type) {

        return writeType.get(type);
    }

    public static String getJavaType(String type) {

        return javaType.get(type);
    }

    public static boolean isBaseField(String type) {
        for (String str : baseFields) {
            if (str.equals(type)) {
                return true;
            }
        }
        return false;
    }
    public static boolean isListPacked(String type) {
        return listPacked.contains(type);
    }

    public static void main(String[] args) {
        System.out.println("".getBytes().length);
    }
}
