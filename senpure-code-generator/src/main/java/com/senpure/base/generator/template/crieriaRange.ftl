<#list criteriaFieldMap?values as field>
    <#if field.criteriaShow&&field.hasCriteriaRange>
<#include "rangeName.ftl">
        <#if startJavaNullable?boolean>
            <if test="${startName} != null">
        </#if>
        <#if field.redundancy>
            <#--冗余并且只显示一个-->
            <#if field.redundancyField??&&field.redundancyConfig.showOnlyOne>
            and ${field.redundancyField.column} >= ${r'#{'}${startName}.${field.redundancyConfig.transformMethod}}
            <#else>
            and ${field.column} >= ${r'#{'}${startName}}
            </#if>
        <#else>
            and ${field.column} >= ${r'#{'}${startName}}
        </#if>
        <#if startJavaNullable?boolean>
            </if>
        </#if>
        <#if endJavaNullable?boolean>
            <if test="${endName} != null">
        </#if>
        <#if field.redundancy>
            <#if field.redundancyField??&&field.redundancyConfig.showOnlyOne>
                and ${field.redundancyField.column} &lt;= ${r'#{'}${endName}.${field.redundancyConfig.transformMethod}}
            <#else >
                and ${field.column} &lt;= ${r'#{'}${endName}}
            </#if>
        <#else>
            and ${field.column} &lt;= ${r'#{'}${endName}}
        </#if>
        <#if endJavaNullable?boolean>
            </if>
        </#if>
    </#if>
</#list>