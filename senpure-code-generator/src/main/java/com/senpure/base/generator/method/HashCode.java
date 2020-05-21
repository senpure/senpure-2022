package com.senpure.base.generator.method;

import com.senpure.base.generator.ModelField;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateMethodModelEx;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 根据字段算出一个hash值
 */
public class HashCode implements TemplateMethodModelEx {
    @Override
    public Object exec(List list) {
        Map<String, ModelField> shortMap = new TreeMap<>();
        try {
            SimpleHash simpleHash = (SimpleHash) list.get(0);
            shortMap.putAll(simpleHash.toMap());
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        shortMap.values().forEach(modelField -> sb.append(modelField.getName()));
        int code=sb.toString().hashCode();
        if (code < 0) {
            code=code*-1;
        }
        return code+"";
    }
}
