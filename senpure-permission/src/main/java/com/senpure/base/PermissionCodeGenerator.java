package com.senpure.base;

import com.senpure.base.generator.JpaGenerator;
import com.senpure.base.generator.config.JpaConfig;
import com.senpure.base.generator.config.ModelConfig;

/**
 * PermissionCodeGenerator
 *
 * @author senpure
 * @time 2020-05-20 14:23:28
 */
public class PermissionCodeGenerator {
    public static void main(String[] args) {


        JpaConfig jpaConfig = new JpaConfig();
        jpaConfig.getDefaultModelConfig().overwriteAll().setGenerateService(true);
        ModelConfig modelConfig = new ModelConfig();
        modelConfig.setGenerateService(true).overwriteAll();
        modelConfig.setCache(true);
      //  jpaConfig.getModelConfigMap().put("Menu", modelConfig);
        JpaGenerator generator = new JpaGenerator();
        generator.generate("com.senpure.base", jpaConfig);
    }
}
