package com.senpure.config.configure;

import com.senpure.base.AppEvn;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "config")
public class ConfigProperties {
    private String excelPath = AppEvn.getClassRootPath();
    private String javaFolder = AppEvn.getClassRootPath();
    private String javaManagerSuffix = "Manager";
    private String luaFolder = AppEvn.getClassRootPath();

    private boolean generateJava = true;
    private boolean generateLua = true;


    public String getJavaFolder() {
        return javaFolder;
    }

    public void setJavaFolder(String javaFolder) {
        this.javaFolder = javaFolder;
    }

    public String getJavaManagerSuffix() {
        return javaManagerSuffix;
    }

    public void setJavaManagerSuffix(String javaManagerSuffix) {
        this.javaManagerSuffix = javaManagerSuffix;
    }

    public String getLuaFolder() {
        return luaFolder;
    }

    public void setLuaFolder(String luaFolder) {
        this.luaFolder = luaFolder;
    }

    public String getExcelPath() {
        return excelPath;
    }

    public void setExcelPath(String excelPath) {
        this.excelPath = excelPath;
    }

    public boolean isGenerateJava() {
        return generateJava;
    }

    public void setGenerateJava(boolean generateJava) {
        this.generateJava = generateJava;
    }

    public boolean isGenerateLua() {
        return generateLua;
    }

    public void setGenerateLua(boolean generateLua) {
        this.generateLua = generateLua;
    }


    @Override
    public String toString() {
        return "ConfigProperties{" +
                "excelPath='" + excelPath + '\'' +
                ", javaFolder='" + javaFolder + '\'' +
                ", javaManagerSuffix='" + javaManagerSuffix + '\'' +
                ", luaFolder='" + luaFolder + '\'' +
                ", generateJava=" + generateJava +
                ", generateLua=" + generateLua +
                '}';
    }
}
