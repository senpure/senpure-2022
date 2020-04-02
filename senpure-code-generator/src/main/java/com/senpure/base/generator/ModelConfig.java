package com.senpure.base.generator;

/**
 * ModelConfig
 *
 * @author senpure
 * @time 2019-01-04 15:44:13
 */
public class ModelConfig {


    public static ModelConfig getOverwriteAllInstance() {
        ModelConfig config = new ModelConfig();
        config.setOverwriteModel(true);
        config.setOverwriteService(true);
        config.setOverwriteCriteria(true);
        config.setOverwriteController(true);
        config.setOverwriteMapperJava(true);
        config.setOverwriteMapperXml(true);
        config.setOverwriteResult(true);
        return config;
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
    private boolean generateResult= true;



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
    private boolean cache=false;
    //cache =true 时生效 spring cache
    private boolean remoteCache=true;
    //cache =true 时生效 spring cache local混合
    private boolean localCache;
    //cache =true 时生效 本地map缓存
    private boolean mapCache;


    public boolean isGenerateMapperJava() {
        return generateMapperJava;
    }

    public void setGenerateMapperJava(boolean generateMapperJava) {
        this.generateMapperJava = generateMapperJava;
    }

    public boolean isGenerateMapperXml() {
        return generateMapperXml;
    }

    public void setGenerateMapperXml(boolean generateMapperXml) {
        this.generateMapperXml = generateMapperXml;
    }

    public boolean isOverwriteMapperJava() {
        return overwriteMapperJava;
    }

    public void setOverwriteMapperJava(boolean overwriteMapperJava) {
        this.overwriteMapperJava = overwriteMapperJava;
    }

    public boolean isOverwriteMapperXml() {
        return overwriteMapperXml;
    }

    public void setOverwriteMapperXml(boolean overwriteMapperXml) {
        this.overwriteMapperXml = overwriteMapperXml;
    }

    public boolean isGenerateService() {
        return generateService;
    }

    public void setGenerateService(boolean generateService) {
        this.generateService = generateService;
    }

    public boolean isGenerateController() {
        return generateController;
    }

    public void setGenerateController(boolean generateController) {
        this.generateController = generateController;
    }



    public boolean isGenerateModel() {
        return generateModel;
    }

    public void setGenerateModel(boolean generateModel) {
        this.generateModel = generateModel;
    }

    public boolean isOverwriteModel() {
        return overwriteModel;
    }

    public void setOverwriteModel(boolean overwriteModel) {
        this.overwriteModel = overwriteModel;
    }


    public boolean isOverwriteService() {
        return overwriteService;
    }

    public void setOverwriteService(boolean overwriteService) {
        this.overwriteService = overwriteService;
    }

    public boolean isOverwriteController() {
        return overwriteController;
    }

    public void setOverwriteController(boolean overwriteController) {
        this.overwriteController = overwriteController;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public boolean isGenerateCriteria() {
        return generateCriteria;
    }

    public boolean isOverwriteCriteria() {
        return overwriteCriteria;
    }

    public void setOverwriteCriteria(boolean overwriteCriteria) {
        this.overwriteCriteria = overwriteCriteria;
    }

    public void setGenerateCriteria(boolean generateCriteria) {
        this.generateCriteria = generateCriteria;
    }

    public boolean isUseCriteriaStr() {
        return useCriteriaStr;
    }

    public void setUseCriteriaStr(boolean useCriteriaStr) {
        this.useCriteriaStr = useCriteriaStr;
    }

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public boolean isRemoteCache() {
        return remoteCache;
    }

    public void setRemoteCache(boolean remoteCache) {
        this.remoteCache = remoteCache;
    }

    public boolean isLocalCache() {
        return localCache;
    }

    public void setLocalCache(boolean localCache) {
        this.localCache = localCache;
    }

    public boolean isMapCache() {
        return mapCache;
    }

    public void setMapCache(boolean mapCache) {
        this.mapCache = mapCache;
    }

    public boolean isGenerateResult() {
        return generateResult;
    }

    public void setGenerateResult(boolean generateResult) {
        this.generateResult = generateResult;
    }

    public boolean isOverwriteResult() {
        return overwriteResult;
    }

    public void setOverwriteResult(boolean overwriteResult) {
        this.overwriteResult = overwriteResult;
    }
}
