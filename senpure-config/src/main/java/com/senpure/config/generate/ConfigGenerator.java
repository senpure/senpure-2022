package com.senpure.config.generate;


import com.senpure.config.configure.ConfigProperties;
import com.senpure.config.metadata.Bean;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;


public class ConfigGenerator {

    private static Logger logger = LoggerFactory.getLogger(ConfigGenerator.class);

    public static void generateJava(Bean bean) throws IOException {
        ConfigProperties config = bean.getConfig();
        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        cfg.setDirectoryForTemplateLoading(new File(TemplateUtil.templateDir(), "java"));
        Template template = cfg.getTemplate("bean.ftl");
        File file = new File(config.getJavaFolder(), bean.getJavaPack().replace(".", File.separator)
                //  + File.separator + "bean"
                + File.separator + bean.getName() + ".java");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        logger.debug("生成 {}", file.getAbsoluteFile());
        Generator.generate(bean, template, file);
        template = cfg.getTemplate("beanManager.ftl");
        file = new File(config.getJavaFolder(), bean.getJavaPack().replace(".", File.separator)
                //  + File.separator + "bean"
                + File.separator + bean.getName() + config.getJavaManagerSuffix() + ".java");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        logger.debug("生成 {}", file.getAbsoluteFile());
        Generator.generate(bean, template, file);


    }

    public static void generateLua(Bean bean) throws IOException {
        ConfigProperties config = bean.getConfig();
        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        cfg.setDirectoryForTemplateLoading(new File(TemplateUtil.templateDir(), "lua"));
        Template template = cfg.getTemplate("bean.ftl");
        File file = new File(config.getLuaFolder(), File.separator + bean.getName() + ".lua");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        logger.debug("生成 {}", file.getAbsoluteFile());
        Generator.generate(bean, template, file);
    }
}
