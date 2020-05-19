package com.senpure.base.generator;

import com.senpure.template.sovereignty.TemplateBean;

import java.util.ArrayList;
import java.util.List;

/**
 * SpringCacheModel
 *
 * @author senpure
 * @time 2020-05-18 15:27:54
 */
public class SpringLocalCacheModel extends TemplateBean {
    private List<String> names= new ArrayList<>();
    private String fileName;
    private  String javaPackage;

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getJavaPackage() {
        return javaPackage;
    }

    public void setJavaPackage(String javaPackage) {
        this.javaPackage = javaPackage;
    }
}
