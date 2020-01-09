package com.senpure.template.sovereignty;

/**
 * TemplateBean
 *
 * @author senpure
 * @date 2018-05-08
 */
public class TemplateBean {

    private String sovereignty=Sovereignty.getInstance().sovereigntyJavaComment();

    private int beforeSovereigntyCount = 0;
    private int afterSovereigntyCount = 0;

    public final String getSovereignty() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        //确保通过bean调用,不是会被模板文件方法覆盖
        if (stackTraceElements[6].getClassName().
                equals("freemarker.ext.beans.BeansWrapper")||
                stackTraceElements[6].getClassName().
                        equals("freemarker.ext.beans.BeanModel")) {
            afterSovereigntyCount++;
        }
        return sovereignty;
    }

    public void setSovereignty(String sovereignty) {
        this.sovereignty = sovereignty;
    }

    public final boolean checkSovereignty() {
        boolean result = beforeSovereigntyCount < afterSovereigntyCount;
        afterSovereigntyCount = 0;
        beforeSovereigntyCount = 0;
        return result;
    }
}
