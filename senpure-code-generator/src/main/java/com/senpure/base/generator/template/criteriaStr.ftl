package ${criteriaPackage};

import com.senpure.base.criterion.CriteriaStr;
<#if hasDate>
import com.senpure.base.struct.PatternDate;
import com.senpure.base.validator.DynamicDate;
</#if>
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

<#global "int"="Integer"/>
/**<#if hasExplain>
 * ${explain}
 *</#if>
${sovereignty}
 * @version ${.now?datetime}
 */
public class ${name}${config.criteriaStrSuffix} extends CriteriaStr implements Serializable {
    private static final long serialVersionUID = ${serial(modelFieldMap)}L;

<#if id.hasExplain>
    //${id.explain}
</#if>
    ${apiModelProperty(name,id)}
    ${id.accessType} String ${id.name};
<#if version??>
    <#if version.hasExplain>
    //${version.explain}
    </#if>
    @ApiModelProperty(hidden = true )
    ${version.accessType} String ${version.name};
</#if>
<#list modelFieldMap?values as field>
    <#if field.criteriaShow>
        <#if field.hasExplain>
    //${field.explain}
        </#if>
    ${apiModelProperty(name,field)}
    ${field.accessType} String ${field.name};
     <#if field.date>
    //${field.name} 时间格式
    ${apiModelProperty(name,field,"pattern")}
    ${field.accessType} String ${field.name}Pattern ;
    @DynamicDate
    ${field.accessType} final PatternDate ${field.name}Valid = new PatternDate();
        </#if><#--时间类型-->
    </#if>
</#list>
<#list modelFieldMap?values as field>
    <#if field.criteriaShow>
        <#if field.hasCriteriaRange>
    ${apiModelProperty(name,field,"start")}
    ${field.accessType} String ${config.startRangePrefix}${field.name?cap_first};
    ${apiModelProperty(name,field,"end")}
    ${field.accessType} String ${config.endRangePrefix}${field.name?cap_first};
            <#if field.date>
    @DynamicDate
    ${field.accessType} final PatternDate ${config.startRangePrefix}${field.name?cap_first}Valid = new PatternDate();
    @DynamicDate
    ${field.accessType} final PatternDate ${config.endRangePrefix}${field.name?cap_first}Valid = new PatternDate();
            </#if>
        </#if><#-- 范围判断-->
    </#if>
</#list>
<#list modelFieldMap?values as field>
    <#if field.criteriaShow>
        <#if field.criteriaOrder>
    //table [${tableName}][column = <#if field.longDate??>${field.longDate.column}<#else >${field.column}</#if>] criteriaOrder
    ${apiModelProperty(name,field,"order")}
    ${field.accessType} String ${field.name}Order;
        </#if>
    </#if>
</#list>

    public ${name}${config.criteriaSuffix} to${name}${config.criteriaSuffix}() {
        ${name}${config.criteriaSuffix} criteria = new ${name}${config.criteriaSuffix}();
        criteria.setPage(Integer.parseInt(getPage()));
        criteria.setPageSize(Integer.parseInt(getPageSize()));
<#assign field = id/>
<#include "strFieldTo.ftl">
<#if version??>
<#assign field = version/>
<#include "strFieldTo.ftl">
</#if>
<#list modelFieldMap?values as field>
<#include "strFieldTo.ftl">
</#list>
        return criteria;
    }
<#if hasRange>

    @Override
    protected void rangeStr(StringBuilder sb) {
    <#list modelFieldMap?values as field>
        <#if field.criteriaShow&&field.hasCriteriaRange>
        if (${config.startRangePrefix}${field.name?cap_first} != null) {
            sb.append("${config.startRangePrefix}${field.name?cap_first}=").append(${config.startRangePrefix}${field.name?cap_first}).append(",");
        }
        if (${config.endRangePrefix}${field.name?cap_first} != null) {
            sb.append("${config.endRangePrefix}${field.name?cap_first}=").append(${config.endRangePrefix}${field.name?cap_first}).append(",");
        }
        </#if>
    </#list>
    }
</#if>

    @Override
    protected void beforeStr(StringBuilder sb) {
        sb.append("${name}${config.criteriaStrSuffix}{");
        if (${id.name} != null) {
            sb.append("${id.name}=").append(${id.name}).append(",");
        }
<#if version??>
        if (${version.name} != null) {
            sb.append("${version.name}=").append(${version.name}).append(",");
        }
</#if>
<#list modelFieldMap?values as field>
    <#if field.criteriaShow>
        if (${field.name} != null) {
            sb.append("${field.name}=").append(${field.name}).append(",");
        }
    </#if>
</#list>
    }

    @Override
    protected void afterStr(StringBuilder sb) {
<#list modelFieldMap?values as field>
        <#if field.criteriaOrder>
        if (${field.name}Order != null) {
            sb.append("${field.name}Order=").append(${field.name}Order).append(",");
        }
        </#if>
</#list>
        super.afterStr(sb);
    }

<#assign field = id />
<#assign name >${name}${config.criteriaStrSuffix}</#assign>
<#include "getsetStringNullStr.ftl">
<#if version??>
    <#assign field = version />
    <#include "getsetStringNullStr.ftl">
</#if>
<#list modelFieldMap?values as field>
    <#include "getsetStringNullStr.ftl">
</#list>
}