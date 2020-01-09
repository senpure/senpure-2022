package com.senpure.template;

import com.senpure.base.util.Assert;
import com.senpure.template.sovereignty.Sovereignty;
import com.senpure.template.sovereignty.TemplateBean;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * Generator
 *
 * @author senpure
 * @date 2018-05-08
 */
public class Generator {

    private static Logger logger = LoggerFactory.getLogger(Generator.class);


    public final static void generate(TemplateBean bean, Template template, File file, boolean checkSovereignty) {

        if (checkSovereignty && !Sovereignty.getInstance().checkSovereignty(template)) {
            logger.error("{} 缺少 ${sovereignty}", template.getName());
            System.exit(0);
        }

        generateFile(bean, template, file);
        if (checkSovereignty && !bean.checkSovereignty()) {
            logger.error("{} 没有调用 ${sovereignty} 或者没有${sovereignty}没有执行", template.getSourceName());
            if (file.exists()) {
                file.delete();
            }
            System.exit(0);
        }
    }

    public final static void generate(TemplateBean bean, Template template, File file) {
        generate(bean, template, file, true);
    }

    private static void generateFile(Object bean, Template template, File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            template.process(bean, new OutputStreamWriter(fos, "utf-8"));
            fos.close();
        } catch (Exception e) {
            Assert.error(e);
        }
    }

}
