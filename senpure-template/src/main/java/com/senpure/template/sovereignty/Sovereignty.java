package com.senpure.template.sovereignty;

import freemarker.template.Template;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class Sovereignty {


    private static Sovereignty sovereignty = new Sovereignty();

    public static Sovereignty getInstance() {
        return sovereignty;
    }

    private List<String> sovereignties = new ArrayList<>();
    private List<String> javaSovereignties = new ArrayList<>();
    private Map<Template, Boolean> checks = new HashMap<>();

    private Sovereignty() {
        sovereignties.add(byteToStr("YXV0aG9yICAgIHNlbnB1cmU="));
        javaSovereignties.add(byteToStr("YXV0aG9yIHNlbnB1cmU="));

    }

    public String sovereignty() {
        return sovereignty("", false, sovereignties);
    }

    public String sovereigntyJavaComment() {
        return sovereignty("* @", true, javaSovereignties);
    }

    public String sovereigntyLuaComment() {
        return sovereignty("");
    }


    private String sovereignty(String prefix) {

        return sovereignty(prefix, true, sovereignties);
    }

    private String sovereignty(String prefix, boolean newLine, List<String> sovereignties) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sovereignties.size(); i++) {
            if (i > 0 && newLine) {
                if (newLine) {
                    sb.append("\n");
                } else {
                    sb.append("    ");
                }
            }
            sb.append(prefix).append(sovereignties.get(i));
        }
        return sb.toString();
    }

    public boolean checkSovereignty(Template template) {
        Boolean result = checks.get(template);
        if (result != null) {
            return result;
        }
        result = template.toString().contains("${sovereignty}");
        checks.put(template, result);
        return result.booleanValue();
    }

    private static String strToByte(String str) {
        try {
            byte[] bytes = str.getBytes("utf-8");
            return Base64.getEncoder().encodeToString(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String byteToStr(String data) {
        try {
            return new String(Base64.getDecoder().decode(data), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {

        String str="hello world!";
        String data = strToByte(str);
        System.out.println(data);

        System.out.println(byteToStr(data));
    }
}

