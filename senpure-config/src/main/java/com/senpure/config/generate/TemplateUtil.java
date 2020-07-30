package com.senpure.config.generate;


import com.senpure.base.AppEvn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;


public class TemplateUtil {
    private static Logger logger
            = LoggerFactory.getLogger(TemplateUtil.class);
    private static File templateDir;

    public static File templateDir() {
        if (templateDir != null) {
            return templateDir;
        }
        String root = AppEvn.getCallerRootPath();

        if (AppEvn.callerInJar()) {

            templateDir = new File(root, "template");
        } else {
            templateDir = new File(root, "template");
        }
        logger.debug("模板文件路径{}",templateDir.getAbsolutePath());
        return templateDir;
    }

    public static void main(String[] args) {


    }
}
