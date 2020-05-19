package ${resultPackage};

import com.senpure.base.result.ActionResult;
import com.senpure.base.result.Result;
import ${modelPackage}.${name};
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Locale;

/**<#if hasExplain>
 * ${explain}
 *</#if>
 * @author senpure-generator
 * @version ${.now?datetime}
 */
public class ${name}${config.resultRecordSuffix} extends ActionResult {
    private static final long serialVersionUID = ${serial(modelFieldMap)}L;

    public static final String RECORD_NAME = "${nameRule(name)}";

    <#--这里的注释swagger 不会读取，不用注释-->
    @ApiModelProperty(position = 3)
    private ${name} ${nameRule(name)};

    public static ${name}${config.resultRecordSuffix} success() {
        return new ${name}${config.resultRecordSuffix}(Result.SUCCESS);
    }

    public static ${name}${config.resultRecordSuffix} dim() {
        return new ${name}${config.resultRecordSuffix}(Result.ERROR_DIM);
    }

    public static ${name}${config.resultRecordSuffix} failure() {
        return new ${name}${config.resultRecordSuffix}(Result.FAILURE);
    }

    public static ${name}${config.resultRecordSuffix} notExist() {
        return new ${name}${config.resultRecordSuffix}(Result.TARGET_NOT_EXIST);
    }

    public static ${name}${config.resultRecordSuffix} result(int code) {
        return new ${name}${config.resultRecordSuffix}(code);
    }

    public ${name}${config.resultRecordSuffix}() {
    }

    public ${name}${config.resultRecordSuffix}(int code) {
        super(code);
    }

    public ${name} get${nameRule(name)?cap_first}() {
        return ${nameRule(name)};
    }

    public ${name}${config.resultRecordSuffix} set${nameRule(name)?cap_first}(${name} ${nameRule(name)}) {
        this.${nameRule(name)} = ${nameRule(name)};
        return this;
    }

    @Override
    public ${name}${config.resultRecordSuffix} setClientFormat(boolean clientFormat) {
        super.setClientFormat(clientFormat);
        return this;
    }

    @Override
    public ${name}${config.resultRecordSuffix} setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    @Override
    public ${name}${config.resultRecordSuffix} setMessageArgs(List<String> messageArgs) {
        super.setMessageArgs(messageArgs);
        return this;
    }

    @Override
    public ${name}${config.resultRecordSuffix} wrapMessage(Locale locale) {
        super.wrapMessage(locale);
        return this;
    }

    @Override
    public ${name}${config.resultRecordSuffix} wrapMessage(Locale locale, Object... args) {
        super.wrapMessage(locale, args);
        return this;
    }
}