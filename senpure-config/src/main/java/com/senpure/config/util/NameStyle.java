package com.senpure.config.util;


import com.senpure.base.util.StringUtil;

public class NameStyle {

    static final char underLine = '_';

    public static String upCamelCaseName(String name) {

        int len = name.length();
        StringBuilder sb = new StringBuilder();
        boolean lastUnderLine = false;
        for (int i = 0; i < len; i++) {
            boolean append = true;
            char c = name.charAt(i);
            if (c == underLine) {
                append = false;
                lastUnderLine = true;
            } else {
                if (lastUnderLine||i==0) {
                    if (StringUtil.isLowerLetter(c)) {
                        c = (char) (c - 32);
                    }
                }
                lastUnderLine = false;
            }
            if (append) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {

        String input = "";
        String output = "";
        input = "_data_config";

        System.out.println("input:" + input + " output:" + upCamelCaseName(input));
        input = "_data__config_-book";
        System.out.println("input:" + input + " output:" + upCamelCaseName(input));
    }
}
