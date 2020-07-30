package com.senpure.config.generate;


import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


public class Generator {

    private static Logger logger = LoggerFactory.getLogger(Generator.class);

    public static void generate(Object object, Template template, File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            template.process(object, new OutputStreamWriter(fos, "utf-8"));
            fos.close();
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException", e);
        } catch (TemplateException e) {
            logger.error("TemplateException", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException", e);
        } catch (IOException e) {
            logger.error("IOException", e);
        }
    }


    public static void main(String[] args) throws IOException, TemplateException {


    }
}
