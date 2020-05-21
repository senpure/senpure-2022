package com.senpure.base.generator.method;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.ArrayList;
import java.util.List;

/**
 * ListCompute
 *
 * @author senpure
 * @time 2020-05-21 11:30:39
 */
public class StringTempList {
    private static List<Object> StringList = new ArrayList<>();




    public static class StringGet implements TemplateMethodModelEx {

        @Override
        public Object exec(List list) throws TemplateModelException {
            List<?> temp = new ArrayList<>(StringList);
            StringList.clear();
            return temp;
        }
    }

   public static class StringAdd implements TemplateMethodModelEx {

        @Override
        public Object exec(List list) throws TemplateModelException {
            SimpleScalar simpleScalar = (SimpleScalar) list.get(0);
            String value = simpleScalar.getAsString();
            StringList.add(value);
            return null;
        }
    }
}
