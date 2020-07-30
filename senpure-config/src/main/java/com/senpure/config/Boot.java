package com.senpure.config;

import com.senpure.base.AppEvn;
import com.senpure.config.configure.ConfigProperties;
import com.senpure.config.generate.ConfigGenerator;
import com.senpure.config.metadata.Bean;
import com.senpure.config.read.ExcelReader;
import com.senpure.config.util.ConfigCheckUtil;
import com.senpure.template.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class Boot implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(Boot.class);

    @Resource
    private ConfigProperties config;

    private ConfigProperties defaultConfig = new ConfigProperties();

    public static void main(String[] args) {

        // AppEvn.getClassRootPath();
        SpringApplication.run(Boot.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        ConfigCheckUtil.check(config);

        List<Bean> beans = new ArrayList<>();
        String[] paths = config.getExcelPath().split(",");
        for (String path : paths) {
            File file = FileUtil.file(path);
            logger.debug("excel path {}", path);
            if (!file.exists()) {
                logger.error("文件不存在{}_ ", file.getAbsolutePath());
                return;
            }
            beans.addAll(ExcelReader.read(file));
        }

        if (beans.size() == 0) {
            logger.info("没有找到excel文件");
        }
        for (Bean bean : beans) {
            bean.setConfig(config);
            //  logger.debug(bean.toString());
            if (config.isGenerateJava()) {
                ConfigGenerator.generateJava(bean);
            }

            if (config.isGenerateLua()) {
                ConfigGenerator.generateLua(bean);
            }


        }
    }

    public static void markClass() {
        AppEvn.markClassRootPath();
    }
}
