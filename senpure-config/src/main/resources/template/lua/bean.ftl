<#list fields as field>
--${field.name}<#if field.hasExplain>(${field.explain})</#if>
</#list>
${name} ={}
<#list records as record>
<#list record.values as value>
    <#if value_index==0 >
${name}[${value.inJavaCodeValues[0]}] = {</#if></#list><#list record.values as value>${value.field.name}=<#if value.field.list>{<#list value.inJavaCodeValues as v>${v}<#if v_has_next>,</#if></#list>}<#else >${value.inJavaCodeValues[0]}</#if><#if value_has_next>,</#if></#list>};
</#list>
