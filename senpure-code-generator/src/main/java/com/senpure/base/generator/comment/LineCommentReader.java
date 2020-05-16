package com.senpure.base.generator.comment;

import com.senpure.base.AppEvn;
import com.senpure.grammar.antlr.GrammarModel;
import com.senpure.grammar.antlr.java.Java8Parser;
import com.senpure.grammar.java.JavaReader;
import com.senpure.grammar.java.model.ClassModel;
import com.senpure.grammar.java.model.FieldModel;
import com.senpure.grammar.java.model.JavaModel;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LineCommentReader
 *
 * @author senpure
 * @time 2020-05-16 11:34:25
 */
public class LineCommentReader {


    private static Logger logger = LoggerFactory.getLogger(LineCommentReader.class);

    //className_fieldKey comment
    private static Map<String, String> comments = new HashMap<>();

    public static String getComment(String key) {
        return comments.get(key);
    }

    public static void readComment(List<String> javaSourceFiles) {
        List<JavaModel> javaModels = new ArrayList<>();
        for (String javaSourceFile : javaSourceFiles) {
            JavaReader javaReader = new JavaReader();
            File file = new File(javaSourceFile);
            String fileName = file.getName().replace(".java", "");
            GrammarModel<Java8Parser.CompilationUnitContext> grammarModel = javaReader.read(javaSourceFile);

            JavaModel javaModel = new JavaModel();
            javaModels.add(javaModel);
            javaModel.setFilePath(javaSourceFile);
            javaModel.setPackageName(readPackageName(grammarModel));
            List<Java8Parser.TypeDeclarationContext> typeDeclarationContexts =
                    grammarModel.getRuleContext().typeDeclaration();
            for (Java8Parser.TypeDeclarationContext typeDeclarationContext : typeDeclarationContexts) {
                Java8Parser.NormalClassDeclarationContext normalClassDeclarationContext = typeDeclarationContext.classDeclaration().normalClassDeclaration();
                ClassModel classModel = new ClassModel();
                classModel.setName(readClassName(normalClassDeclarationContext));
                if (classModel.getName().equals(fileName)) {
                    classModel.setMain(true);
                    javaModel.setClassModel(classModel);
                    logger.debug("main class");
                } else {
                    logger.debug("not main class");
                    classModel.setMain(false);
                    javaModel.getOutModels().add(classModel);
                }
                Java8Parser.ClassBodyContext classBodyContext = normalClassDeclarationContext.classBody();

                classModel.getFields().addAll(readFields(grammarModel.getTokenStream(), classBodyContext));
            }
        }
        putMap(javaModels);
    }

    private static void putMap(List<JavaModel> javaModels) {
        for (JavaModel javaModel : javaModels) {
            String key = javaModel.getPackageName() + "." + javaModel.getClassModel().getName();
            for (FieldModel field : javaModel.getClassModel().getFields()) {
                StringBuilder value = new StringBuilder();
                for (String lineComment : field.getLineComments()) {
                    value.append(lineComment);
                }
                comments.put(key + "." + field.getFieldKey(), value.toString());
            }
        }
        for (Map.Entry<String, String> entry : comments.entrySet()) {

            logger.debug("{} = {}", entry.getKey(), entry.getValue());
        }
    }

    public static String readPackageName(GrammarModel<Java8Parser.CompilationUnitContext> grammarModel) {
        Java8Parser.CompilationUnitContext compilationUnitContext = grammarModel.getRuleContext();
        Java8Parser.PackageDeclarationContext packageDeclarationContext = compilationUnitContext.packageDeclaration();
        logger.debug("{}", packageDeclarationContext.packageName().getText());
        return packageDeclarationContext.packageName().getText();
    }

    public static String readClassName(Java8Parser.NormalClassDeclarationContext normalClassDeclarationContext) {

        String name = normalClassDeclarationContext.Identifier().getText();
        logger.debug("className {}", name);
        return name;
    }

    public static List<FieldModel> readFields(CommonTokenStream tokenStream, Java8Parser.ClassBodyContext classBodyContext) {
        List<FieldModel> fieldModels = new ArrayList<>();
        int lastFieldLine = -1;
        for (Java8Parser.ClassBodyDeclarationContext classBodyDeclarationContext : classBodyContext.classBodyDeclaration()) {
            //logger.debug("classBodyDeclarationContext {}", classBodyDeclarationContext.getText());
            Java8Parser.ClassMemberDeclarationContext classMemberDeclarationContext = classBodyDeclarationContext.classMemberDeclaration();
            if (classMemberDeclarationContext != null) {
                //logger.debug("classMemberDeclarationContext {}", classMemberDeclarationContext.getText());
                Java8Parser.FieldDeclarationContext fieldDeclarationContext = classMemberDeclarationContext.
                        fieldDeclaration();

                if (fieldDeclarationContext != null) {
                    FieldModel fieldModel = new FieldModel();
                    fieldModels.add(fieldModel);
                    List<Java8Parser.FieldModifierContext> fieldModifierContexts = fieldDeclarationContext.fieldModifier();
                    int fieldLine = fieldDeclarationContext.getStart().getLine();
                    for (Java8Parser.FieldModifierContext modifierContext : fieldModifierContexts) {
                        String modifier = modifierContext.getText();
                        if (modifier.equals("static")) {
                            fieldModel.setStaticModifier(true);
                        } else if (modifier.equals("final")) {
                            fieldModel.setFinalModifier(true);
                        } else {
                            fieldModel.setAccessType(modifier);
                        }
                    }
                    fieldModel.setClazzType(fieldDeclarationContext.unannType().getText());
                    //logger.debug(fieldModel.getClazzType());
                    Java8Parser.VariableDeclaratorListContext variableDeclaratorListContext = fieldDeclarationContext.variableDeclaratorList();

                    Java8Parser.VariableDeclaratorContext variableDeclaratorContext = variableDeclaratorListContext.variableDeclarator(0);

                    fieldModel.setName(variableDeclaratorContext.variableDeclaratorId().getText());

                    List<Token> lineComments = tokenStream.getHiddenTokensToLeft(fieldDeclarationContext.getStart().getTokenIndex(), 1);
                    if (lineComments != null) {
                        for (Token token : lineComments) {
                            if (token.getLine() != lastFieldLine) {
                                //skip //
                                String l = token.getText().substring(2).trim();
                                fieldModel.getLineComments().add(l);
                            }

                        }
                    }
                    lineComments = tokenStream.getHiddenTokensToRight(fieldDeclarationContext.getStop().getTokenIndex(), 1);
                    if (lineComments != null) {
                        for (Token token : lineComments) {
                            // logger.info("{} line {} .. {}", token.getText(), token.getLine(), fieldDeclarationContext.getStart().getLine());
                            if (token.getLine() == fieldDeclarationContext.getStart().getLine()) {
                                //skip //
                                String l = token.getText().substring(2);
                                fieldModel.getLineComments().add(l);
                            }
                        }

                    }
                    lastFieldLine = fieldLine;
                    logger.debug("{}", fieldModel);
                }
            }
        }
        return fieldModels;
    }

    public static void main(String[] args) {
        AppEvn.tryMarkClassRootPath();
        List<String> str = new ArrayList<>();

        str.add("E:\\Projects\\senpure\\senpure-grammar\\src\\main\\java\\com\\senpure\\grammar\\java\\GrammarModel.java");

        readComment(str);
    }
}
