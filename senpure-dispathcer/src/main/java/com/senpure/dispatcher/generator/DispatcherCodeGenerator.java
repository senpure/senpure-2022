package com.senpure.dispatcher.generator;

import com.senpure.base.generator.CodeGenerator;
import com.senpure.base.generator.GeneratorConfig;
import com.senpure.base.generator.ModelConfig;

/**
 * DispatcherCodeGenerator
 *
 * @author senpure
 * @time 2019-03-11 17:26:44
 */
public class DispatcherCodeGenerator {

    public static void main(String[] args) {
        CodeGenerator codeGenerator = new CodeGenerator();
        GeneratorConfig config = new GeneratorConfig();

        String path="../senpure-dispatcher-bean/src/main/java";
        config.setModelRootPath(path);
        config.setCriteriaRootPath(path);
        config.setResultRootPath(path);
        ModelConfig modelConfig = new ModelConfig();

        config.setDefaultModelConfig(modelConfig);
        ModelConfig  overwriteAllInstance = ModelConfig.getOverwriteAllInstance();
        config.getModelConfigMap().put("RangeValue", overwriteAllInstance);
        codeGenerator.generate("com.senpure.dispatcher",config);
    }
}
