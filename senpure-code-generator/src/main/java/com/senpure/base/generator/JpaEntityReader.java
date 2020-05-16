package com.senpure.base.generator;

import com.senpure.base.AppEvn;
import com.senpure.base.annotation.Explain;
import com.senpure.base.entity.LongAndVersionEntity;
import com.senpure.base.generator.comment.CommentReader;
import com.senpure.base.generator.comment.LineCommentReader;
import com.senpure.base.generator.hibernate.ImplicitBasicColumnNameSourceImpl;
import com.senpure.base.generator.hibernate.ImplicitEntityNameSourceImpl;
import com.senpure.base.generator.hibernate.ImplicitJoinColumnNameSourceImpl;
import com.senpure.base.util.Assert;
import com.senpure.base.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.internal.InFlightMetadataCollectorImpl;
import org.hibernate.boot.internal.MetadataBuilderImpl;
import org.hibernate.boot.internal.MetadataBuildingContextRootImpl;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.spi.BootstrapContext;
import org.hibernate.boot.spi.MetadataBuildingContext;
import org.hibernate.boot.spi.MetadataBuildingOptions;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.type.BasicType;
import org.hibernate.type.BasicTypeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * JpaEntityReader
 *
 * @author senpure
 * @time 2020-05-14 14:34:44
 */
public class JpaEntityReader {

    private GeneratorConfig config;
    private JdbcEnvironment jdbcEnvironment;
    private MetadataBuildingContext metadataBuildingContext;
    private Logger logger = LoggerFactory.getLogger(getClass());
    private PhysicalNamingStrategy physicalNamingStrategy;
    private ImplicitNamingStrategy implicitNamingStrategy;
    private Column defaultColumn;

    private BasicTypeRegistry typeRegistry;
    private String[] unullable = {"int", "char", "short", "byte", "float", "double", "boolean", "long"};
    private Map<String, Model> modelMap = new HashMap<>();

    public JpaEntityReader(GeneratorConfig config) {
        this.config = config;
        Map<String, String> jpaSetting = config.getHibernateSettings().getSettings();
        jpaSetting.putIfAbsent(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        jpaSetting.putIfAbsent(AvailableSettings.PHYSICAL_NAMING_STRATEGY, "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        jpaSetting.putIfAbsent(AvailableSettings.IMPLICIT_NAMING_STRATEGY, "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
        for (Map.Entry<String, String> entry : jpaSetting.entrySet()) {
            registryBuilder.applySetting(entry.getKey(), entry.getValue());
        }
        StandardServiceRegistry registry = registryBuilder.build();
        jdbcEnvironment = registry.getService(JdbcEnvironment.class);
        MetadataSources metadataSources = new MetadataSources(registry);
        MetadataBuilderImpl metadataBuilderImpl = new MetadataBuilderImpl(metadataSources);
        BootstrapContext bootstrapContext = metadataBuilderImpl.getBootstrapContext();
        MetadataBuildingOptions metadataBuildingOptions = metadataBuilderImpl.getMetadataBuildingOptions();

        physicalNamingStrategy = metadataBuildingOptions.getPhysicalNamingStrategy();
        implicitNamingStrategy = metadataBuildingOptions.getImplicitNamingStrategy();

        InFlightMetadataCollectorImpl metadataCollector = new InFlightMetadataCollectorImpl(
                bootstrapContext,
                metadataBuildingOptions
        );
        metadataBuildingContext = new MetadataBuildingContextRootImpl(
                bootstrapContext,
                metadataBuildingOptions,
                metadataCollector
        );
        typeRegistry = bootstrapContext.getTypeConfiguration().getBasicTypeRegistry();
        try {
            defaultColumn = DefaultEntity.class.getDeclaredField("column").getAnnotation(Column.class);
        } catch (NoSuchFieldException e) {
            logger.error("", e);
        }
    }

    public Map<String, Model> read(String packageName) {
        File file = new File(AppEvn.getClassRootPath(), packageName.replace(".", File.separator));
        return read(file);
    }

    public Map<String, Model> read(File file) {
        List<File> entityClassFiles = findEntityClassFile(file);
        readComment(entityClassFiles);
        List<Class<?>> classes = toClass(entityClassFiles);
        for (Class<?> clazz : classes) {
            if (clazz.getAnnotation(Entity.class) != null) {
                readEntity(clazz);
            } else {
                logger.debug("跳过 {}", clazz.getName());
            }
        }
        return modelMap;
    }


    /**
     * 找出所有class文件（最多一层文件夹）
     *
     * @param file
     * @return
     */
    private List<File> findEntityClassFile(File file) {
        File[] files;
        if (file.isDirectory()) {
            files = file.listFiles();
        } else {
            files = new File[1];
            files[0] = file;
        }
        List<File> classFiles = new ArrayList<>();
        if (files != null) {
            for (File f : files) {
                if (f.getName().endsWith(".class")) {
                    classFiles.add(f);
                }
            }
        }
        return classFiles;
    }

    private List<Class<?>> toClass(List<File> entityClassFiles) {
        String classRootPath = AppEvn.getClassRootPath();
        List<Class<?>> classes = new ArrayList<>();
        for (File file : entityClassFiles) {
            String classPath = file.getAbsolutePath().replace(classRootPath, "");
            classPath = classPath.replace(".class", "");
            classPath = classPath.replace(File.separatorChar, '.');
            if (classPath.startsWith(".")) {
                classPath = classPath.replaceFirst("\\.", "");
            }
            logger.debug("classPath {}", classPath);
            try {
                Class<?> clazz = Class.forName(classPath);
                classes.add(clazz);
            } catch (ClassNotFoundException e) {
                logger.error("", e);
            }
        }
        return classes;
    }

    /**
     * 读取注释
     *
     * @param entityClassFiles
     */
    private void readComment(List<File> entityClassFiles) {
        List<String> javaSourceFiles = new ArrayList<>();
        for (File entity : entityClassFiles) {
            String path = entity.getAbsolutePath();
            path = path.replace(config.getClass2SourceClass(), config.getClass2SourceSource());
            path = path.replace(".class", ".java");
            javaSourceFiles.add(path);
        }
        PrintStream out = System.out;
        File htmlFile = null;
        try {
            htmlFile = File.createTempFile("temp", ".html");
            logger.debug(htmlFile.getAbsolutePath());
            System.setOut(new PrintStream(htmlFile));
        } catch (IOException e) {
            logger.error("", e);
        }
        if (htmlFile != null) {
            htmlFile.delete();
        }
        CommentReader.readComment(javaSourceFiles);
        System.setOut(out);
        LineCommentReader.readComment(javaSourceFiles);
    }

    public Model readEntity(Class<?> clazz) {
        Model model = new Model();
        modelMap.put(clazz.getName(), model);
        logger.info("read {}", clazz.getName());
        readName(model, clazz);
        readFields(model, clazz);
        readJoin();
        return model;
    }

    private void readName(Model model, Class<?> clazz) {
        model.setEntityPackage(clazz.getPackage().getName());
        Identifier identifier;
        Table table = clazz.getAnnotation(Table.class);
        if (table == null || table.name().length() == 0) {
            identifier = implicitNamingStrategy.determinePrimaryTableName(new ImplicitEntityNameSourceImpl(clazz, metadataBuildingContext));
        } else {
            identifier = Identifier.toIdentifier(table.name());
        }
        String tableName = identifier.getText();
        logger.debug("{} tableName {}", clazz, tableName);
        model.setTableName(tableName);
        Explain explain = clazz.getAnnotation(Explain.class);
        if (explain != null) {
            if (explain.value().length() > 0) {
                model.setExplain(explain.value());
            }
        } else {
            String explainStr = CommentReader.getExplain(clazz);
            if (explainStr != null && explainStr.length() > 0) {
                model.setExplain(explainStr);
            }
        }
        String name = clazz.getSimpleName();
        if (StringUtils.isNotEmpty(config.getEntityPrefix())) {
            if (name.startsWith(config.getEntityPrefix())) {
                name = StringUtils.replaceOnce(name, config.getEntityPrefix(), "");
            }
        }
        if (StringUtils.isNotEmpty(config.getEntitySuffix())) {
            if (name.endsWith(config.getEntitySuffix())) {
                name = StringUtils.substring(name, 0, name.length() - config.getEntitySuffix().length());
            }
        }
        if (StringUtils.isNotEmpty(config.getModelPrefix())) {
            name = config.getModelPrefix() + name;
        }
        if (StringUtils.isNotEmpty(config.getModelSuffix())) {
            name += config.getEntitySuffix();
        }
        model.setName(name);

    }

    private void readFields(Model model, Class<?> clazz) {
        model.setClazz(clazz);
        readSuperClass(model, clazz.getSuperclass());
        model.getClazzs().add(clazz.getName());
        Field[] fields = clazz.getDeclaredFields();
        readFields(model, fields);
    }

    private void readFields(Model model, Field[] fields) {
        for (Field field : fields) {
            //排除static
            if ((field.getModifiers() & Modifier.STATIC) == Modifier.STATIC) {
                logger.info("跳过{}", field.getName());
            } else if (field.getType().isAssignableFrom(Collection.class)) {
                logger.info("跳过{}", field.getName());

            } else {
                Transient tr = field.getAnnotation(Transient.class);
                if (tr == null) {
                    ModelField modelField = new ModelField();
                    modelField.setField(field);
                    modelField.setClazzType(field.getType().getSimpleName());
                    modelField.setAccessType(Modifier.toString(field.getModifiers()));
                    modelField.setName(field.getName());
                    Explain explain = field.getAnnotation(Explain.class);
                    if (explain != null) {
                        if (explain.value().length() > 0) {
                            modelField.setExplain(explain.value());
                        }
                    } else {
                        String explainStr = CommentReader.getExplain(model.getClazz(), field);
                        if (explainStr != null && explainStr.length() > 0) {
                            modelField.setExplain(explainStr);
                        }
                    }
                    String key = model.getClazz().getName() + "." + modelField.getAccessType() + "." + modelField.getName();
                    String comment = LineCommentReader.getComment(key);
                    if (comment != null) {
                        modelField.setExplain(modelField.getExplain() + comment);
                    }
                    ManyToOne manyToOne = field.getAnnotation(ManyToOne.class);
                    if (manyToOne != null) {
                        //  JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
                        logger.debug("{}暂时不处理字段{} {}", model.getName(), field.getType().getName(), field.getName());
                        //暂时用全类名表示
                        modelField.setName(field.getType().getName());
                        model.getAfterReadColumn().getTargetClass().add(field.getType().getName());
                        model.getAfterReadColumn().getModelFields().add(modelField);
                    } else {
                        Column column = field.getAnnotation(Column.class);
                        if (column == null) {
                            column = defaultColumn;
                        }
                        Identifier identifier = null;
                        if (column.name().length() == 0) {
                            identifier = implicitNamingStrategy.determineBasicColumnName(new ImplicitBasicColumnNameSourceImpl(field.getName(), metadataBuildingContext));
                        } else {
                            identifier = Identifier.toIdentifier(column.name());
                        }
                        identifier = physicalNamingStrategy.toPhysicalColumnName(identifier, jdbcEnvironment);
                        modelField.setColumn(identifier.getText());
                        modelField.setNullable(column.nullable());
                        modelField.setJavaNullable(javaNullable(modelField.getClazzType()));

                        BasicType basicType = typeRegistry.getRegisteredType(field.getType().getName());
                        if (basicType == null) {
                            Assert.error("不支持的java类型：" + field.getType());
                        }
                        int[] sqlTypes = basicType.sqlTypes(null);
                        String jdbcType = jdbcEnvironment.getDialect().getTypeName(sqlTypes[0], column.length(), column.precision(), column.scale()).toUpperCase();
                        int index = jdbcType.indexOf("(");
                        if (index > 0) {
                            jdbcType = jdbcType.substring(0, index);
                        }
                        modelField.setJdbcType(jdbcType);
                        checkDateField(model, modelField);

                        // 主键判断
                        if (field.getAnnotation(Id.class) == null) {
                            model.getModelFieldMap().put(modelField.getName(), modelField);
                        } else {
                            modelField.setNullable(false);
                            if (modelField.getExplain() == null) {
                                modelField.setExplain("主键");
                            } else if (!modelField.getExplain().contains("主键")) {
                                modelField.setExplain(modelField.getExplain() + "(主键)");
                            }
                            modelField.setId(true);
                            model.setId(modelField);
                            GenericGenerator generatedValue = field.getAnnotation(GenericGenerator.class);
                            if (generatedValue == null) {
                                modelField.setDatabaseId(true);
                            } else {
                                modelField.setDatabaseId(false);
                            }
                            model.getModelFieldMap().remove(modelField.getName());
                        }

                        //version
                        if (field.getAnnotation(Version.class) != null) {
                            modelField.setNullable(false);
                            modelField.setVersion(true);
                            modelField.setExplain("乐观锁，版本控制");
                            model.setVersion(modelField);
                            model.getModelFieldMap().remove(modelField.getName());
                        }
                        logger.debug(modelField.toString());
                    }
                }
            }
        }
    }

    private void checkDateField(Model model, ModelField modelField) {
        Field field = modelField.getField();
        if (field.getType().isAssignableFrom(Date.class)) {
            model.setHasDate(true);
            model.getDateFieldMap().put(modelField.getName(), modelField);
            modelField.setDate(true);
            modelField.setCriteriaOrder(true);
        }
    }

    private void readSuperClass(Model model, Class<?> superClazz) {
        if (superClazz == null) {
            return;
        }
        readSuperClass(model, superClazz.getSuperclass());
        if (superClazz.getAnnotation(MappedSuperclass.class) != null) {
            model.getClazzs().add(superClazz.getName());
            Field[] fields = superClazz.getDeclaredFields();
            readFields(model, fields);
        }

    }

    private void readJoin() {
        for (Map.Entry<String, Model> entry : modelMap.entrySet()) {
            Model model = entry.getValue();
            List<String> targets = model.getAfterReadColumn().getTargetClass();
            //检查引用是否存在
            if (targets.size() > 0) {
                for (String name : targets) {
                    Model target = modelMap.get(name);
                    if (target == null) {
                        Assert.error(name + "不存在 ");
                    }
                }
            }
            List<ModelField> modelFields = model.getAfterReadColumn().getModelFields();
            //完成外键信息补充
            for (ModelField modelField : modelFields) {
                Model target = modelMap.get(modelField.getName());
                if (target == null) {
                    Assert.error(modelField.getName() + "不存在 ");
                }
                logger.debug(target.toString());
                target.setChild(model);
                target.setChildField(modelField);

                //改写name 之前是Target全类名
                modelField.setName(StringUtil.toLowerFirstLetter(target.getName()
                        + StringUtil.toUpperFirstLetter(target.getId().getName())));
                modelField.setClazzType(target.getId().getClazzType());
                modelField.setJdbcType(target.getId().getJdbcType());
                String joinName = readJoin(modelField.getField(), target);
                modelField.setColumn(joinName);
                String ex = "外键," + "modelName:" + target.getName() + ",tableName:" + target.getTableName();
                if (modelField.getExplain() == null) {
                    modelField.setExplain(ex);
                } else if (!modelField.getExplain().contains("外键")) {
                    modelField.setExplain(modelField.getExplain() + "(" + ex + ")");
                }
                modelField.setJavaNullable(javaNullable(target.getId().getClazzType()));
                model.getModelFieldMap().put(modelField.getName(), modelField);
                model.getCriteriaFieldMap().put(modelField.getName(), modelField);
                find(model, modelField, false);
            }
        }

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

    public String readJoin(Field field, Model referencedModel) {

        Identifier identifier;
        JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
        if (joinColumn == null || joinColumn.name().length() == 0) {
            identifier = implicitNamingStrategy.determineJoinColumnName(new ImplicitJoinColumnNameSourceImpl(field.getName(), referencedModel, metadataBuildingContext));
        } else {
            identifier = Identifier.toIdentifier(joinColumn.name());

        }
        identifier = physicalNamingStrategy.toPhysicalColumnName(identifier, jdbcEnvironment);
        String joinColumnName = identifier.getText();
        logger.debug("joinColumnName {} ", joinColumnName);
        return joinColumnName;
    }

    private boolean javaNullable(String type) {
        for (String str : unullable) {
            if (str.equals(type)) {

                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        JpaEntityReader jpaEntityReader = new JpaEntityReader(new GeneratorConfig());
        jpaEntityReader.readEntity(Book.class);


    }


    @Entity(name = "Book2")
    private static class Book extends LongAndVersionEntity {

        @JoinColumn(nullable = false)
        private Author authorSelf;
    }

    @Entity
    private static class Author {
        @Id
        @GenericGenerator(name = "idGenerator", strategy = "com.senpure.base.util.IDGeneratorHibernate")
        @GeneratedValue(generator = "idGenerator")
        private Long myAuId;

        public Long getMyAuId() {
            return myAuId;
        }

        public void setMyAuId(Long myAuId) {
            this.myAuId = myAuId;
        }
    }
}
