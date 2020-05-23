package com.senpure.base.generator.config;

import com.senpure.base.generator.GeneratorConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * ModelConfig
 *
 * @author senpure
 * @time 2019-01-04 15:44:13
 */
public class ModelConfig {


    public static ModelConfig getOverwriteAllInstance() {
        ModelConfig config = new ModelConfig();
        return config.overwriteAll();
    }


    public ModelConfig() {
        redundancyConfigs.add(new RedundancyConfig());
    }

    public ModelConfig overwriteAll() {
        setOverwriteModel(true);
        setOverwriteService(true);
        setOverwriteCriteria(true);
        setOverwriteController(true);
        setOverwriteMapperJava(true);
        setOverwriteMapperXml(true);
        setOverwriteResult(true);
        return this;
    }

    public ModelConfig generateAll() {
        setGenerateModel(true);
        setGenerateService(true);
        setGenerateCriteria(true);
        setGenerateController(true);
        setGenerateMapperJava(true);
        setGenerateMapperXml(true);
        setGenerateResult(true);
        return this;
    }

    private boolean generateModel = true;
    //生成mapper
    private boolean generateMapperJava = true;
    private boolean generateMapperXml = true;
    //生成service
    private boolean generateService = false;
    //生成controller
    private boolean generateController = false;


    private boolean generateCriteria = true;
    private boolean generateResult = true;


    private boolean useCriteriaStr = true;

    //覆盖model
    private boolean overwriteModel = false;
    //覆盖mapper
    private boolean overwriteMapperJava = false;
    private boolean overwriteMapperXml = false;
    //覆盖service
    private boolean overwriteService = false;
    //覆盖controller
    private boolean overwriteController = false;
    //覆盖criteria
    private boolean overwriteCriteria = false;
    //覆盖result
    private boolean overwriteResult = false;

    //表类型 mix single
    private String tableType = GeneratorConfig.TABLE_TYPE_SINGLE;
    //service 开启缓存
    private boolean cache = true;
    //cache =true 时生效 spring cache
    private boolean remoteCache = true;
    //cache =true 时生效 spring cache local混合
    private boolean localCache;
    //cache =true 时生效 本地map缓存
    private boolean mapCache;

    //字段匹配为时间类型
    private String longDateSuffix = "Time";

    private List<RedundancyConfig> redundancyConfigs = new ArrayList<>();
    private List<FindConfig> findConfigs = new ArrayList<>();

    public boolean isGenerateModel() {
        return generateModel;
    }

    public ModelConfig setGenerateModel(boolean generateModel) {
        this.generateModel = generateModel;
        return this;
    }

    public boolean isGenerateMapperJava() {
        return generateMapperJava;
    }

    public ModelConfig setGenerateMapperJava(boolean generateMapperJava) {
        this.generateMapperJava = generateMapperJava;
        return this;
    }

    public boolean isGenerateMapperXml() {
        return generateMapperXml;
    }

    public ModelConfig setGenerateMapperXml(boolean generateMapperXml) {
        this.generateMapperXml = generateMapperXml;
        return this;
    }

    public boolean isGenerateService() {
        return generateService;
    }

    public ModelConfig setGenerateService(boolean generateService) {
        this.generateService = generateService;
        return this;
    }

    public boolean isGenerateController() {
        return generateController;
    }

    public ModelConfig setGenerateController(boolean generateController) {
        this.generateController = generateController;
        return this;
    }

    public boolean isGenerateCriteria() {
        return generateCriteria;
    }

    public ModelConfig setGenerateCriteria(boolean generateCriteria) {
        this.generateCriteria = generateCriteria;
        return this;
    }

    public boolean isGenerateResult() {
        return generateResult;
    }

    public ModelConfig setGenerateResult(boolean generateResult) {
        this.generateResult = generateResult;
        return this;
    }

    public boolean isUseCriteriaStr() {
        return useCriteriaStr;
    }

    public ModelConfig setUseCriteriaStr(boolean useCriteriaStr) {
        this.useCriteriaStr = useCriteriaStr;
        return this;
    }

    public boolean isOverwriteModel() {
        return overwriteModel;
    }

    public ModelConfig setOverwriteModel(boolean overwriteModel) {
        this.overwriteModel = overwriteModel;
        return this;
    }

    public boolean isOverwriteMapperJava() {
        return overwriteMapperJava;
    }

    public ModelConfig setOverwriteMapperJava(boolean overwriteMapperJava) {
        this.overwriteMapperJava = overwriteMapperJava;
        return this;
    }

    public boolean isOverwriteMapperXml() {
        return overwriteMapperXml;
    }

    public ModelConfig setOverwriteMapperXml(boolean overwriteMapperXml) {
        this.overwriteMapperXml = overwriteMapperXml;
        return this;
    }

    public boolean isOverwriteService() {
        return overwriteService;
    }

    public ModelConfig setOverwriteService(boolean overwriteService) {
        this.overwriteService = overwriteService;
        return this;
    }

    public boolean isOverwriteController() {
        return overwriteController;
    }

    public ModelConfig setOverwriteController(boolean overwriteController) {
        this.overwriteController = overwriteController;
        return this;
    }

    public boolean isOverwriteCriteria() {
        return overwriteCriteria;
    }

    public ModelConfig setOverwriteCriteria(boolean overwriteCriteria) {
        this.overwriteCriteria = overwriteCriteria;
        return this;
    }

    public boolean isOverwriteResult() {
        return overwriteResult;
    }

    public ModelConfig setOverwriteResult(boolean overwriteResult) {
        this.overwriteResult = overwriteResult;
        return this;
    }

    public String getTableType() {
        return tableType;
    }

    public ModelConfig setTableType(String tableType) {
        this.tableType = tableType;
        return this;
    }

    public boolean isCache() {
        return cache;
    }

    public ModelConfig setCache(boolean cache) {
        this.cache = cache;
        return this;
    }

    public boolean isRemoteCache() {
        return remoteCache;
    }

    public ModelConfig setRemoteCache(boolean remoteCache) {
        this.remoteCache = remoteCache;
        return this;
    }

    public boolean isLocalCache() {
        return localCache;
    }

    public ModelConfig setLocalCache(boolean localCache) {
        this.localCache = localCache;
        return this;
    }

    public boolean isMapCache() {
        return mapCache;
    }

    public ModelConfig setMapCache(boolean mapCache) {
        this.mapCache = mapCache;
        return this;
    }

    public String getLongDateSuffix() {
        return longDateSuffix;
    }

    public ModelConfig setLongDateSuffix(String longDateSuffix) {
        this.longDateSuffix = longDateSuffix;
        return this;
    }

    public List<RedundancyConfig> getRedundancyConfigs() {
        return redundancyConfigs;
    }

    public ModelConfig setRedundancyConfigs(List<RedundancyConfig> redundancyConfigs) {
        this.redundancyConfigs = redundancyConfigs;
        return this;
    }

    public List<FindConfig> getFindConfigs() {
        return findConfigs;
    }

    public ModelConfig setFindConfigs(List<FindConfig> findConfigs) {
        this.findConfigs = findConfigs;
        return this;
    }
}
