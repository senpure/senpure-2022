package com.senpure.config.read;

import com.senpure.base.util.Assert;
import com.senpure.base.util.Pinyin;
import com.senpure.config.metadata.Bean;
import com.senpure.config.metadata.Field;
import com.senpure.config.metadata.Record;
import com.senpure.config.metadata.Value;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ExcelReader {


    private static final Logger logger = LoggerFactory.getLogger(ExcelReader.class);

    private static final ReadHelper readHelper = new ReadHelper();

    public static List<Bean> read(File file) {
        List<Bean> beans = new ArrayList<>();
        read(file, beans);
        return beans;
    }

    private static void read(File file, List<Bean> beans) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    read(f, beans);
                }
            }
        } else {
            if (!file.getName().startsWith("~$")) {
                if (file.getName().endsWith(".xls") || file.getName().endsWith(".xlsx")) {
                    beans.addAll(readFile(file));
                }
            }


        }
    }

    private static List<Bean> readFile(File file) {

        logger.debug("read File {}", file.getAbsoluteFile());
        Workbook wb = null;
        try {
            FileInputStream in = new FileInputStream(file);
            if (file.getName().endsWith(".xls")) {
                wb = new HSSFWorkbook(in);
            } else if (file.getName().endsWith(".xlsx")) {
                wb = new XSSFWorkbook(in);
            }
        } catch (IOException e) {
            logger.error("IOException", e);
        }


        List<Bean> beans = new ArrayList<>();
        int sheetNum = 0;
        if (wb != null) {
            sheetNum = wb.getNumberOfSheets();
        }
        for (int i = 0; i < sheetNum; i++) {
            Sheet sheet = wb.getSheetAt(i);
            int lastRow = sheet.getLastRowNum();
            if (lastRow < 3) {
                continue;
            }

            Row nameRow = sheet.getRow(1);
            Row classRow = sheet.getRow(2);
            Row explainRow = sheet.getRow(3);
            Row row = sheet.getRow(0);
            Bean bean = new Bean();
            beans.add(bean);
            String beanName = row.getCell(0).getStringCellValue();
            String javaPack = row.getCell(1).getStringCellValue();
            bean.setName(beanName);
            bean.setJavaPack(javaPack);

            int column = explainRow.getLastCellNum();
            for (int j = 0; j < column; j++) {
                Field field = new Field();
                field.setExplain(explainRow.getCell(j).getStringCellValue().trim());
                String nameTemp = null;
                if (nameRow != null) {
                    nameTemp = getCellStringValue(nameRow.getCell(j));

                }
                if (nameTemp == null) {
                    nameTemp = Pinyin.toAccount(field.getExplain())[0];
                }
                field.setName(nameTemp.trim());
                String type = null;
                if (classRow != null) {
                    type = getCellStringValue(classRow.getCell(j));

                }
                if (type == null) {
                    type = "String";
                } else {
                    type = type.trim();
                }
                int index = type.indexOf("[");
                if (index > 0) {
                    String list = type.substring(index + 1);
                    if (list.startsWith("list") && list.endsWith("]")) {
                        field.setList(true);
                        bean.setHasList(true);
                        list = list.replace("list", "").replace("]", "");
                        if (list.length() > 0) {
                            field.setListSeparator(list);

                        }
                    } else {
                        Assert.error("list 拼写错误 [" + type + "] 正常格式为 $type[list{$Separator}]");
                    }
                    type = type.substring(0, index);
                }

                field.setClassType(type);
                Assert.isTrue(readHelper.isBaseField(type), "不支持的数据类型 [" + type + "]");
                field.setJavaType(readHelper.getJavaType(type));
                field.setJavaListType(readHelper.getJavaListType(field.getJavaType()));
                if (j == 0) {

                    bean.setId(field);
                }
                bean.getFields().add(field);
            }
            boolean readData = true;
            for (int j = 4; j <= lastRow; j++) {
                row = sheet.getRow(j);
                if (row != null) {

                    column = row.getLastCellNum();
                    if (readData) {
                        Record record = new Record();
                        for (int k = 0; k <= column; k++) {
                            Value value = new Value();
                            if (bean.getFields().size() == k) {
                                break;
                            }
                            Field field = bean.getFields().get(k);
                            value.setField(field);
                            String str = getCellStringValue(row.getCell(k));

                            if (str == null) {
                                str = readHelper.getJavaDefaultValue(field.getJavaType());
                            }
                            value.setValue(str);
                            if (field.isList()) {
                                String[] strs = str.split(field.getListSeparator());
                                for (String s : strs) {
                                    checkValue(field, value, s);
                                }
                            } else {
                                checkValue(field, value, value.getValue());
                            }

                            record.getValues().add(value);
                        }
                        bean.getRecords().add(record);
                        logger.debug("{}", record);
                    } else {
                        for (int k = 0; k <= column; k++) {
                            String value = getCellStringValue(row.getCell(k));
                            if (value != null && value.length() > 0) {
                                value = value.trim();
                                if (bean.getExplain() != null && bean.getExplain().length() > 1) {
                                    bean.setExplain(bean.getExplain() + "\n" + value);
                                } else {
                                    bean.setExplain(value);
                                }
                            }
                        }
                    }
                } else {
                    readData = false;
                }

            }
        }
        return beans;
    }

    private static String getCellStringValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        return cell.getStringCellValue();
    }

    private static void checkValue(Field field, Value value, String str) {

        try {
            switch (field.getJavaType()) {
                case "int":
                case "long":
                    Long.parseLong(str);

                    break;
                case "double":
                    Double.parseDouble(value.getValue());
                    break;
                case "boolean":
                    Assert.isTrue(readHelper.isBoolean(value.getValue()));
                default:
            }
        } catch (Exception e) {
            Assert.error(field.getClassType() + ",[" + value.getValue() + "],不合法!");
        }

        if (field.getJavaType().equals("String")) {
            String s = "\"";
            if (field.getClassType().startsWith("\"") && field.getClassType().endsWith("\"")) {
                s = "'";
            }
            value.getInJavaCodeValues().add(s + str + s);
        } else if (field.getJavaType().equalsIgnoreCase("boolean")) {
            if (readHelper.isBooleanTrue(str)) {
                value.getInJavaCodeValues().add("true");
            } else {
                value.getInJavaCodeValues().add("false");
            }
        } else {
            value.getInJavaCodeValues().add(str);

        }

    }



    public static void main(String[] args) {

        File file = new File("F:\\房配置表.xlsx");
        read(file);


    }
}
