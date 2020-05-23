package com.senpure.base.generator;

import com.senpure.base.AppEvn;
import com.senpure.base.generator.config.FindConfig;
import com.senpure.base.generator.config.JpaConfig;
import com.senpure.base.generator.config.ModelConfig;
import com.senpure.base.generator.config.RedundancyConfig;
import com.senpure.base.generator.method.*;
import com.senpure.base.util.Assert;
import com.senpure.base.util.StringUtil;
import com.senpure.template.FileUtil;
import com.senpure.template.Generator;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * JpaGenerator
 *
 * @author senpure
 * @time 2020-05-15 15:45:27
 */
public class JpaGenerator {

    static {
        AppEvn.markPid();
    }

    private final Logger logger = LoggerFactory.getLogger(getClass());


    private final List<String> exists = new ArrayList<>();
    private final List<String> springLocal = new ArrayList<>();

    public void generate(String part) {
        generate(part, new JpaConfig());
    }

    public void generate(String part, JpaConfig config) {
        prepareLog();
        List<Model> models = prepareModel(part, config);
        generate(models, part, config);
    }

    private void generate(List<Model> models, String part, JpaConfig config) {
        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        cfg.setSharedVariable("pluralize", new Pluralize());
        cfg.setSharedVariable("nameRule", new NameRule());
        cfg.setSharedVariable("space", new SpaceResidual());
        cfg.setSharedVariable("serial", new HashCode());
        cfg.setSharedVariable("labelFormat", new LabelFormat());
        cfg.setSharedVariable("mapperXmlTips", new MapperXmlTips());
        cfg.setSharedVariable("strAdd", new StringTempList.StringAdd());
        cfg.setSharedVariable("strGet", new StringTempList.StringGet());
        ApiModelProperty apiModelProperty = new ApiModelProperty();
        apiModelProperty.setJpaConfig(config);
        cfg.setSharedVariable("apiModelProperty", apiModelProperty);
        cfg.setClassForTemplateLoading(getClass(), "/");
        Template modelTemplate = null;
        Template serviceTemplate = null;
        Template serviceMapCacheTemplate = null;
        Template serviceSpringCacheTemplate = null;
        Template mapperJavaTemplate = null;
        Template mapperXmlTemplate = null;
        Template criteriaTemplate = null;
        Template criteriaTemplateStr = null;
        Template configurationTemplate = null;
        Template controllerTemplate = null;
        Template resultRecordTemplate = null;
        Template resultPageTemplate = null;
        try {
            modelTemplate = cfg.getTemplate(
                    config.getModelTemplate(),
                    "utf-8");
            serviceTemplate = cfg.getTemplate(
                    config.getServiceTemplate(),
                    "utf-8");
            serviceMapCacheTemplate = cfg.getTemplate(
                    config.getServiceMapCacheTemplate(),
                    "utf-8");
            serviceSpringCacheTemplate = cfg.getTemplate(
                    config.getServiceSpringCacheTemplate(),
                    "utf-8");
            mapperJavaTemplate = cfg.getTemplate(
                    config.getMapperJavaTemplate(),
                    "utf-8");
            mapperXmlTemplate = cfg.getTemplate(
                    config.getMapperXmlTemplate(),
                    "utf-8");
            criteriaTemplate = cfg.getTemplate(
                    config.getCriteriaTemplate(),
                    "utf-8");
            criteriaTemplateStr = cfg.getTemplate(
                    config.getCriteriaStrTemplate(),
                    "utf-8");
            configurationTemplate = cfg.getTemplate(
                    config.getConfigurationTemplate(),
                    "utf-8");
            resultRecordTemplate = cfg.getTemplate(
                    config.getResultRecordTemplate(),
                    "utf-8");

            resultPageTemplate = cfg.getTemplate(
                    config.getResultPageTemplate(),
                    "utf-8");
            controllerTemplate = cfg.getTemplate(
                    config.getControllerTemplate(),
                    "utf-8");

        } catch (IOException e) {
            logger.error("", e);
        }
        File file = new File(AppEvn.getClassRootPath());
        File project = file;
        for (int i = 0; i < config.getClassLevel(); i++) {
            project = project.getParentFile();
        }
        File javaPart = new File(project,
                config.getProjectJavaCodePath() + "/" + part.replace(".", "/"));
        for (Model model : models) {
            ModelConfig modelConfig = model.getModelConfig();
            if (modelConfig.isGenerateModel()) {
                int startPosition = apiModelProperty.getStartPosition();
                File modelFile = null;
                if (config.getModelRootPath() == null) {
                    modelFile = new File(javaPart, config.getModelPartName() + "/" + model.getName() + ".java");
                } else {
                    modelFile = new File(file(config.getModelRootPath(), project, part), config.getModelPartName() + "/" + model.getName() + ".java");
                }
                generateFile(modelTemplate, model, modelFile, modelConfig.isOverwriteModel());
                apiModelProperty.setStartPosition(startPosition);

            } else {
                logger.info("{} 不生成model", model.getName());
            }
            if (modelConfig.isGenerateMapperJava()) {
                File javaFile = new File(javaPart, config.getMapperPartName() + "/" + model.getName() + config.getMapperSuffix() + ".java");
                generateFile(mapperJavaTemplate, model, javaFile, modelConfig.isOverwriteMapperJava());
            } else {
                logger.info("{} 不生成mapperJava", model.getName());
            }
            if (modelConfig.isGenerateMapperXml()) {
                File xmlFile = new File(javaPart, config.getMapperPartName() + "/" + model.getName() + config.getMapperSuffix() + ".xml");
                generateFile(mapperXmlTemplate, model, xmlFile, modelConfig.isOverwriteMapperXml());
            } else {
                logger.info("{} 不生成mapperXml", model.getName());
            }

            if (modelConfig.isGenerateService()) {
                File serviceFile = new File(javaPart, config.getServicePartName() + "/" + model.getName() + config.getServiceSuffix() + ".java");
                Template template = null;
                if (modelConfig.isCache()) {
                    if (modelConfig.isRemoteCache()) {
                        template = serviceSpringCacheTemplate;
                    } else if (modelConfig.isLocalCache()) {
                        template = serviceSpringCacheTemplate;
                        springLocal.add(model.getName());
                    } else {
                        template = serviceMapCacheTemplate;
                    }
                } else {
                    template = serviceTemplate;
                }
                model.setCurrentService(true);
                generateFile(template, model, serviceFile, modelConfig.isOverwriteService());
                model.setCurrentService(false);
            } else {
                logger.info("{} 不生成service", model.getName());
            }
            if (modelConfig.isGenerateCriteria()) {
                File criteriaFile = null;
                File criteriaStrFile = null;
                if (config.getCriteriaRootPath() == null) {
                    criteriaFile = new File(javaPart, config.getCriteriaPartName() + "/" + model.getName() + config.getCriteriaSuffix() + ".java");
                    if (modelConfig.isUseCriteriaStr()) {
                        criteriaStrFile = new File(javaPart, config.getCriteriaPartName() + "/" + model.getName() + config.getCriteriaStrSuffix() + ".java");
                    }
                } else {
                    criteriaFile = new File(file(config.getCriteriaRootPath(), project, part), config.getCriteriaPartName() + "/" + model.getName() + config.getCriteriaSuffix() + ".java");
                    if (modelConfig.isUseCriteriaStr()) {
                        criteriaStrFile = new File(file(config.getCriteriaRootPath(), project, part), config.getCriteriaPartName() + "/" + model.getName() + config.getCriteriaStrSuffix() + ".java");
                    }
                }
                generateFile(criteriaTemplate, model, criteriaFile, modelConfig.isOverwriteCriteria());
                if (modelConfig.isUseCriteriaStr()) {
                    generateFile(criteriaTemplateStr, model, criteriaStrFile, modelConfig.isOverwriteCriteria());
                }
            } else {
                logger.info("{} 不生成criteria", model.getName());
            }
            if (modelConfig.isGenerateResult()) {
                File recordFile = null;
                File pageFile = null;
                if (config.getResultRootPath() == null) {
                    recordFile = new File(javaPart, config.getResultPartName() + "/" + model.getName() + config.getResultRecordSuffix() + ".java");
                    pageFile = new File(javaPart, config.getResultPartName() + "/" + model.getName() + config.getResultPageSuffix() + ".java");

                } else {
                    File p = file(config.getResultRootPath(), project, part);
                    recordFile = new File(p, config.getResultPartName() + "/" + model.getName() + config.getResultRecordSuffix() + ".java");
                    pageFile = new File(p, config.getResultPartName() + "/" + model.getName() + config.getResultPageSuffix() + ".java");

                }
                generateFile(resultRecordTemplate, model, recordFile, modelConfig.isOverwriteResult());
                generateFile(resultPageTemplate, model, pageFile, modelConfig.isOverwriteResult());

            } else {
                logger.info("{} 不生成Result", model.getName());
            }

            if (modelConfig.isGenerateController()) {
                File controllerFile = new File(javaPart, config.getControllerPartName() + "/" + model.getName() + config.getControllerSuffix() + ".java");
                generateFile(controllerTemplate, model, controllerFile, modelConfig.isOverwriteController());

            } else {
                logger.info("{} 不生成Controller", model.getName());
            }
        }
        generateSpringCacheConfiguration(part, javaPart, config, configurationTemplate);
        if (exists.size() > 0) {
            logger.warn(AnsiOutput.toString(new Object[]{AnsiColor.BRIGHT_RED, "↓↓↓↓↓↓↓↓↓↓以下文件存在没有生成↓↓↓↓↓↓↓↓↓↓"}));
            for (String name : exists) {
                logger.warn(AnsiOutput.toString(new Object[]{AnsiColor.BRIGHT_CYAN, name}));
            }
            logger.warn(AnsiOutput.toString(new Object[]{AnsiColor.BRIGHT_RED, "↑↑↑↑↑↑↑↑↑↑以上文件存在没有生成↑↑↑↑↑↑↑↑↑↑"}));
        }
    }


    private List<Model> prepareModel(String part, JpaConfig config) {
        JpaEntityReader reader = new JpaEntityReader(config);
        Map<String, Model> modelMap = reader.read(part + "." + config.getEntityPartName());
        List<Model> models = new ArrayList<>();
        for (Map.Entry<String, Model> entry : modelMap.entrySet()) {
            Model model = entry.getValue();
            if (model.getId() == null) {
                Assert.error(model.getName() + "没有主键");
            }
            ModelConfig modelConfig = config.getModelConfig(model.getName());
            model.setConfig(config);
            model.setModelConfig(modelConfig);
            models.add(model);
        }
        for (Model model : models) {
            revise(model);
        }
        int point = StringUtil.indexOf(part, ".", 1, true);
        String module = part;
        if (point > 0) {
            module = part.substring(point + 1);
        }
        int menuId = config.getMenuStartId();
        if (menuId == 0) {
            menuId = Math.abs(module.hashCode()) / 100 * 100;
        }
        for (Model model : models) {
            if (model.getMenuId() == 0) {
                model.setMenuId(menuId);
            }
            menuId += 100;
            ModelConfig modelConfig = model.getModelConfig();
            model.setGenerateMenu(config.isGenerateMenu());
            model.setGeneratePermission(config.isGeneratePermission());
            model.setUseCriteriaStr(modelConfig.isUseCriteriaStr());
            model.setModule(module);

            model.setModelPackage(part + "." + config.getModelPartName());
            model.setMapperPackage(part + "." + config.getMapperPartName());
            model.setCriteriaPackage(part + "." + config.getCriteriaPartName());
            model.setServicePackage(part + "." + config.getServicePartName());
            model.setControllerPackage(part + "." + config.getControllerPartName());
            model.setModelPackage(part + "." + config.getModelPartName());
            model.setResultPackage(part + "." + config.getResultPartName());
            model.setTableType(modelConfig.getTableType());

            if (!modelConfig.getTableType().equalsIgnoreCase(config.TABLE_TYPE_SINGLE)) {
                ModelField modelField = new ModelField();
                modelField.setAccessType("private");
                modelField.setClazzType("String");
                modelField.setName("tableName");
                model.setTable(modelField);
            }
        }
        return models;
    }

    private void revise(Model model) {
        checkDateField(model);
        checkRedundancy(model);
        checkCriteria(model);
        checkFind(model);
    }

    private void checkFind(Model model) {
        ModelConfig modelConfig = model.getModelConfig();
        Collection<ModelField> modelFieldCollection = model.getModelFieldMap().values();
        for (ModelField modelField : modelFieldCollection) {
            if (modelField.isForeignKey()) {
                find(model, modelField, false);
            }
        }
        if (modelConfig.getFindConfigs().size() > 0) {
            for (ModelField modelField : modelFieldCollection) {
                if (modelConfig.getFindConfigs().size() > 0) {
                    findByConfig(model, modelField, modelConfig);
                }
            }
        } else {
            for (ModelField modelField : modelFieldCollection) {
                findBySystem(model, modelField);
            }
        }
    }

    private void findBySystem(Model model, ModelField modelField) {
        if (modelField.getName().equals("account")) {
            find(model, modelField, true);
        } else if (modelField.getName().equals("name")) {
            find(model, modelField, true);
        } else if (modelField.getName().endsWith("Name") && !modelField.getName().startsWith("readable")) {
            find(model, modelField, false);
        } else if (modelField.getName().equals("nick")) {
            find(model, modelField, false);
        } else if (modelField.getName().endsWith("Nick")) {
            find(model, modelField, false);
        } else if (modelField.getName().endsWith("Id")) {
            if (model.getName().endsWith("Info")
                    || model.getName().endsWith("info")
                    || model.getName().endsWith("Ext")
                    || model.getName().endsWith("ext")
            ) {
                find(model, modelField, true);
            } else {
                find(model, modelField, false);
            }
        } else if (modelField.getName().equals("type")) {
            find(model, modelField, false);
        } else if (modelField.getName().endsWith("Type")) {
            find(model, modelField, false);
        } else if (modelField.getName().equals("key")) {
            find(model, modelField, true);
        } else if (modelField.getName().endsWith("Key")) {
            find(model, modelField, true);
        } else if (modelField.getName().equals("uriAndMethod")) {
            find(model, modelField, false);
        } else if (modelField.getName().equalsIgnoreCase("gold")
                || modelField.getName().equalsIgnoreCase("golds")
                || modelField.getName().equalsIgnoreCase("diamond")
        ) {
            find(model, modelField, false);
        }
    }


    private void findByConfig(Model model, ModelField modelField, ModelConfig modelConfig) {
        for (FindConfig findConfig : modelConfig.getFindConfigs()) {
            if (findConfig.isEq()) {
                if (findConfig.getValue().equals(modelField.getName())) {
                    find(model, modelField, findConfig.isFindOne());
                }
            } else if (findConfig.isStartWith()) {
                if (findConfig.getValue().startsWith(modelField.getName())) {
                    find(model, modelField, findConfig.isFindOne());
                }
            } else if (findConfig.isEndWith()) {
                if (findConfig.getValue().equals(modelField.getName())) {
                    find(model, modelField, findConfig.isFindOne());
                }
            }
        }
    }

    private void checkCriteria(Model model) {
        for (Map.Entry<String, ModelField> entry : model.getModelFieldMap().entrySet()) {
            ModelField modelField = entry.getValue();
            if (modelField.isForeignKey()) {
                model.getCriteriaFieldMap().put(modelField.getName(), modelField);
            }
        }
        for (Map.Entry<String, ModelField> entry : model.getModelFieldMap().entrySet()) {
            ModelField modelField = entry.getValue();
            if (!modelField.isForeignKey()) {
                model.getCriteriaFieldMap().put(modelField.getName(), modelField);
            }
        }
    }

    private void checkDateField(Model model) {
        for (Map.Entry<String, ModelField> entry : model.getModelFieldMap().entrySet()) {
            checkDateField(model, entry.getValue());
        }
    }

    private void checkDateField(Model model, ModelField modelField) {
        Field field = modelField.getField();
        if (field.getType().isAssignableFrom(Date.class)) {
            model.setHasDate(true);
            model.setHasRange(true);
            model.getDateFieldMap().put(modelField.getName(), modelField);
            modelField.setDate(true);
            modelField.setCriteriaOrder(true);
            modelField.setHasCriteriaRange(true);

        } else {
            ModelConfig modelConfig = model.getModelConfig();
            if (StringUtil.isExist(modelConfig.getLongDateSuffix())) {
                if (modelField.getName().endsWith(modelConfig.getLongDateSuffix())) {
                    if (modelField.getClazzType().equalsIgnoreCase("long")) {

                        model.setHasLongDate(true);
                        modelField.setLongTime(true);
                        modelField.setHasCriteriaRange(true);
                        model.setHasRange(true);
                        model.getDateFieldMap().put(modelField.getName(), modelField);
                    }
                }
            }
        }
    }

    private void checkRedundancy(Model model) {
        List<ModelField> shows = new ArrayList<>(model.getModelFieldMap().values());
        List<ModelField> computes = new ArrayList<>(shows);
        for (ModelField show : shows) {
            for (RedundancyConfig redundancyConfig : model.getModelConfig().getRedundancyConfigs()) {
                if (show.getClazzType().equals(redundancyConfig.getShowType())
                        && show.getName().endsWith(redundancyConfig.getShowSuffix())
                ) {
                    for (ModelField compute : computes) {
                        if (compute.getClazzType().equals(redundancyConfig.getComputeType())
                                && compute.getName().endsWith(redundancyConfig.getComputeSuffix())
                        ) {

                            if (redundancyConfig.isShowOnlyOne()) {
                                if (redundancyConfig.isShowShowType()) {
                                    compute.setCriteriaShow(false);

                                } else {
                                    show.setCriteriaShow(false);

                                }

                            }
                            show.setRedundancy(true);
                            show.setRedundancyField(compute);
                            show.setRedundancyConfig(redundancyConfig);
                            compute.setRedundancy(true);
                            compute.setRedundancyField(show);
                            compute.setRedundancyConfig(redundancyConfig);
                            break;
                        }
                    }
                }
            }

        }

    }

    private void prepareLog() {
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
        AppEvn.tryMarkClassRootPath();
    }

    private void find(Model model, ModelField modelField, boolean findOne) {
        if (model.getFindModeFields().contains(modelField)) {
            logger.info("change {} to {},{}  {}", modelField.isFindOne(), findOne, model, modelField);
            modelField.setFindOne(findOne);
            modelField.setCriteriaOrder(true);
        } else {
            logger.info("find  {}  {}", model, modelField);
            modelField.setFindOne(findOne);
            modelField.setCriteriaOrder(true);
            model.getFindModeFields().add(modelField);
        }
    }

    public static File file(String path, File project, String part) {
        File file = FileUtil.file(path, project.getAbsolutePath());
        file = new File(file, part.replace(".", "/"));
        return file;
    }

    private void generateFile(Template template, SpringLocalCacheModel args, File file, boolean cover) {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (file.exists()) {
            if (cover) {
                logger.debug("{}{}", AnsiOutput.toString(AnsiColor.BRIGHT_RED, "覆盖生成"), file.getAbsolutePath());
                Generator.generate(args, template, file, false);
            } else {

                logger.warn("{}存在无法生成", file.getAbsolutePath());
            }
        } else {
            logger.debug("生成{}", file.getAbsolutePath());
            Generator.generate(args, template, file, false);
        }
    }

    private void generateFile(Template template, Model model, File file, boolean cover) {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (file.exists()) {
            if (cover) {
                if (model.isCurrentService()) {
                    logger.debug("{}{} useCache:{}", AnsiOutput.toString(AnsiColor.BRIGHT_RED, "覆盖生成"), file.getAbsolutePath(), AnsiOutput.toString(new Object[]{AnsiColor.BRIGHT_RED, getCache(model.getModelConfig())}));

                } else {
                    logger.debug("{}{}", AnsiOutput.toString(AnsiColor.BRIGHT_RED, "覆盖生成"), file.getAbsolutePath());
                }
                Generator.generate(model, template, file, false);
            } else {
                exists.add(file.getAbsolutePath());
                if (model.isCurrentService()) {
                    springLocal.remove(model.getName());
                }
                logger.warn("{}存在无法生成", file.getAbsolutePath());
            }

        } else {
            if (model.isCurrentService()) {
                logger.debug("生成{} useCache:{}", file.getAbsolutePath(), AnsiOutput.toString(AnsiColor.BRIGHT_RED, getCache(model.getModelConfig())));
            } else {
                logger.debug("生成{}", file.getAbsolutePath());
            }
            Generator.generate(model, template, file, false);

        }
    }

    private void generateSpringCacheConfiguration(String part, File javaPart, JpaConfig config, Template template) {
        if (springLocal.size() > 0) {
            logger.debug("springLocal = {}", springLocal);
        }

        String configName = "LocalCacheConfiguration";
        if ("com.senpure.base".equals(part)) {
            configName = "LocalCacheConfiguration";
        } else {
            int index = StringUtil.indexOf(part, ".", 1, true);
            String tempPart;
            if (index > 0) {
                tempPart = part.substring(index + 1);
            } else {
                tempPart = part;
            }
            configName = StringUtil.toUpperFirstLetter(tempPart) + "LocalCache" + config.getConfigureSuffix();
        }
        File configFile = new File(javaPart, config.getConfigurePartName() + "/" + configName + ".java");
        if (springLocal.size() > 0) {
            Class clazz = null;
            try {
                clazz = Class.forName("org.springframework.data.redis.core.RedisTemplate");
            } catch (ClassNotFoundException e) {
                logger.debug("没有找到{} 不用生成redis的本地缓存配置", "org.springframework.data.redis.core.RedisTemplate");
            }
            if (clazz != null) {
                SpringLocalCacheModel cacheModel = new SpringLocalCacheModel();
                Map<String, Object> args = new HashMap<>(16);
                args.put("configName", configName);
                args.put("names", springLocal);
                args.put("package", part + ".configuration");
                cacheModel.setFileName(configName);
                cacheModel.setNames(springLocal);

                cacheModel.setJavaPackage(part + "." + config.getConfigurePartName());
                if (template != null) {
                    generateFile(template, cacheModel, configFile, true);
                }
            }
        } else {

            if (configFile.exists()) {
                logger.info("{} {}", AnsiOutput.toString(AnsiColor.BRIGHT_RED, "删除"), configFile.getAbsolutePath());
                configFile.delete();
            }

        }
    }

    private String getCache(ModelConfig config) {
        if (config.isCache()) {
            if (config.isRemoteCache()) {
                return "true:springCache";
            } else if (config.isLocalCache()) {
                return "true:springLocal";
            } else if (config.isMapCache()) {
                return "true:localMap";
            }
        }
        return "false";
    }

}
