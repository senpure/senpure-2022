package ${criteriaPackage};

import com.senpure.base.criterion.Criteria;
import ${modelPackage}.${name};
<#if hasDate>
import com.senpure.base.util.DateFormatUtil;
</#if>

<#if table??>
import javax.validation.constraints.Null;
</#if>
<#if hasDate>
import java.util.Date;
</#if>
import java.io.Serializable;

/**<#if hasExplain>
 * ${explain}
 *</#if>
${sovereignty}
 * @version ${.now?datetime}
 */
public class ${name}${config.criteriaSuffix} extends Criteria implements Serializable {
    private static final long serialVersionUID = ${serial(modelFieldMap)}L;

<#if id.hasExplain>
    //${id.explain}
</#if>
    ${id.accessType} ${id.clazzType} ${id.name};
<#if version??>
    <#if version.hasExplain>
    //${version.explain}
    </#if>
    ${version.accessType} ${version.clazzType} ${version.name};
</#if>
<#list modelFieldMap?values as field>
    <#if field.criteriaShow>
<#if field.hasExplain>
    //${field.explain}
</#if>
    ${field.accessType} ${field.clazzType} ${field.name};
    <#if field.hasCriteriaRange>
    ${field.accessType} ${field.clazzType} ${config.startRangePrefix}${field.name?cap_first};
    ${field.accessType} ${field.clazzType} ${config.endRangePrefix}${field.name?cap_first};
    </#if>
    <#if field.criteriaOrder>
    //table [${tableName}][column = ${field.column}] criteriaOrder
    ${field.accessType} String ${field.name}Order;
    </#if>
    </#if>
</#list>
<#if table??>
    <#if table.hasExplain>
    //${table.explain}
    </#if>
    //前端值必须无效
    @Null
    ${table.accessType} ${table.clazzType} ${table.name};
</#if>

    public static ${name} to${name}(${name}${config.criteriaSuffix} criteria, ${name} ${nameRule(name)}) {
        ${nameRule(name)}.set${id.name?cap_first}(criteria.get${id.name?cap_first}());
    <#list modelFieldMap?values as field>
    <#if field.criteriaShow>
        ${nameRule(name)}.set${field.name?cap_first}(criteria.<#if field.clazzType=="boolean">is<#else>get</#if>${field.name?cap_first}());
        <#if field.redundancyField??&&field.redundancyConfig.showOnlyOne>
        if (criteria.get${field.name?cap_first}() != null) {
            ${nameRule(name)}.set${field.redundancyField.name?cap_first}(criteria.get${field.name?cap_first}().get${field.redundancyConfig.transformMethod?cap_first}());
        }
        </#if>
    </#if>
    </#list>
<#if version??>
    <#assign field = version />
        ${nameRule(name)}.set${field.name?cap_first}(criteria.<#if field.clazzType=="boolean">is<#else>get</#if>${field.name?cap_first}());
</#if>
        return ${nameRule(name)};
    }

    public ${name} to${name}() {
        ${name} ${nameRule(name)} = new ${name}();
        return to${name}(this, ${nameRule(name)});
    }

    /**
     * 将${name}${config.criteriaSuffix} 的有效值(不为空),赋值给 ${name}
     *
     * @return ${name}
     */
    public ${name} effective(${name} ${nameRule(name)}) {
        <#if id.javaNullable>
        if (get${id.name?cap_first}() != null) {
            ${nameRule(name)}.set${id.name?cap_first}(get${id.name?cap_first}());
        }
        <#else>
        ${nameRule(name)}.set${id.name?cap_first}(get${id.name?cap_first}());
        </#if>
<#list modelFieldMap?values as field>
    <#if field.javaNullable>
    <#if field.criteriaShow>
        if (<#if field.clazzType=="boolean">is<#else>get</#if>${field.name?cap_first}() != null) {
            ${nameRule(name)}.set${field.name?cap_first}(<#if field.clazzType=="boolean">is<#else>get</#if>${field.name?cap_first}());
        <#if field.longDate??>
            ${nameRule(name)}.set${field.longDate.name?cap_first}(get${field.name?cap_first}().getTime());
        </#if>
        }
    </#if>
    <#else>
        <#if field.criteriaShow>
        ${nameRule(name)}.set${field.name?cap_first}(<#if field.clazzType=="boolean">is<#else>get</#if>${field.name?cap_first}());
         </#if>
        </#if>
</#list>
    <#if version??>
        <#assign field = version />
    <#if field.javaNullable>
        if (get${field.name?cap_first}() != null) {
            ${nameRule(name)}.set${field.name?cap_first}(get${field.name?cap_first}());
        }
    <#else>
        ${nameRule(name)}.set${field.name?cap_first}(get${field.name?cap_first}());
    </#if>
</#if>
        return ${nameRule(name)};
    }
<#if hasRange>

    @Override
    protected void rangeStr(StringBuilder sb) {
    <#list modelFieldMap?values as field>
         <#if field.criteriaShow&&field.hasCriteriaRange>
         <#if field.clazzType="Date">
        if (${config.startRangePrefix}${field.name?cap_first} != null) {
            sb.append("${config.startRangePrefix}${field.name?cap_first}=").append(DateFormatUtil.getDateFormat(datePattern).format(${config.startRangePrefix}${field.name?cap_first})).append(",");
        }
        if (${config.endRangePrefix}${field.name?cap_first} != null) {
            sb.append("${config.endRangePrefix}${field.name?cap_first}=").append(DateFormatUtil.getDateFormat(datePattern).format(${config.endRangePrefix}${field.name?cap_first})).append(",");
        }
         <#else >
        if (${config.startRangePrefix}${field.name?cap_first} != null) {
            sb.append("${config.startRangePrefix}${field.name?cap_first}=").append(${config.startRangePrefix}${field.name?cap_first}).append(",");
        }
        if (${config.endRangePrefix}${field.name?cap_first} != null) {
            sb.append("${config.endRangePrefix}${field.name?cap_first}=").append(${config.endRangePrefix}${field.name?cap_first}).append(",");
        }
         </#if>
         </#if>
     </#list>
    }
</#if>

    @Override
    protected void beforeStr(StringBuilder sb) {
        sb.append("${name}${config.criteriaSuffix}{");
    <#if id.javaNullable>
        if (${id.name} != null) {
            sb.append("${id.name}=").append(${id.name}).append(",");
        }
<#else >
        sb.append("${id.name}=").append(${id.name}).append(",");
</#if>
<#if version??>
    <#if version.javaNullable>
        if (${version.name} != null) {
            sb.append("${version.name}=").append(${version.name}).append(",");
        }
    <#else >
        sb.append("${version.name}=").append(${version.name}).append(",");
    </#if>
</#if>
<#list modelFieldMap?values as field>
    <#if field.javaNullable>
    <#if field.criteriaShow>
        if (${field.name} != null) {
            sb.append("${field.name}=").append(${field.name}).append(",");
        }
    </#if>
    <#else >
        <#if field.criteriaShow>
        sb.append("${field.name}=").append(${field.name}).append(",");
        </#if>
    </#if>
</#list>
    }

<#assign field = id />
<#assign name >${name}${config.criteriaSuffix}</#assign>
<#include "getset.ftl">
<#list modelFieldMap?values as field>
    <#if field.criteriaShow>
        <#include "getsetStringNull.ftl">
    </#if>
</#list>
<#if version??>
    <#assign field = version />
    <#include "getset.ftl">
</#if>
<#if table??>
    <#assign field = table />
    <#include "getset.ftl">
</#if>
}