package ${resultPackage};

import com.senpure.base.ActionResult;
import com.senpure.base.Result;
import ${modelPackage}.${name};
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Locale;

/**<#if hasExplain>
 * ${explain}
 *</#if>
${sovereignty}
 * @version ${.now?datetime}
 */
public class ${name}${config.resultPageSuffix} extends ActionResult {
    private static final long serialVersionUID = ${serial(modelFieldMap)}L;

    public static final String RECORDS_NAME = "${pluralize(nameRule(name))}";

    @ApiModelProperty(position = 3, value = "结果集总数", example = "1086")
    private int total;
    @ApiModelProperty(position = 4, value = "一页数据")
    private List<${name}> ${pluralize(nameRule(name))} ;

    public static ${name}${config.resultPageSuffix} success() {
        return new ${name}${config.resultPageSuffix}(Result.SUCCESS);
    }

    public static ${name}${config.resultPageSuffix} dim() {
        return new ${name}${config.resultPageSuffix}(Result.ERROR_DIM);
    }

    public static ${name}${config.resultPageSuffix} failure() {
        return new ${name}${config.resultPageSuffix}(Result.FAILURE);
    }

    public static ${name}${config.resultPageSuffix} notExist() {
        return new ${name}${config.resultPageSuffix}(Result.TARGET_NOT_EXIST);
    }

    public static ${name}${config.resultPageSuffix} result(int code) {
        return new ${name}${config.resultPageSuffix}(code);
    }

    public ${name}${config.resultPageSuffix}() {
    }

    public ${name}${config.resultPageSuffix}(int code) {
        super(code);
    }

    public int getTotal() {
        return total;
    }

    public ${name}${config.resultPageSuffix} setTotal(int total) {
        this.total = total;
        return this;
    }

    public List<${name}> get${pluralize(nameRule(name))?cap_first}() {
        return ${pluralize(nameRule(name))};
    }

    public ${name}${config.resultPageSuffix} set${pluralize(nameRule(name))?cap_first}(List<${name}> ${pluralize(nameRule(name))}) {
        this.${pluralize(nameRule(name))} = ${pluralize(nameRule(name))};
        return this;
    }

    @Override
    public ${name}${config.resultPageSuffix} setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public ${name}${config.resultPageSuffix} setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public ${name}${config.resultPageSuffix} setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public ${name}${config.resultPageSuffix} wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public ${name}${config.resultPageSuffix} wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}