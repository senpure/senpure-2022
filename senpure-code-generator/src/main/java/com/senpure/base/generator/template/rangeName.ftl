<#if field.useSimpleDate>
    <#assign startJavaNullable>true</#assign>
    <#assign endJavaNullable>true</#assign>
    <#assign startName>startDate</#assign>
    <#assign endName>endDate</#assign>
<#else>
    <#assign startJavaNullable>${field.javaNullable?c}</#assign>
    <#assign endJavaNullable >${field.javaNullable?c}</#assign>
    <#assign startName>${config.startRangePrefix}${field.name?cap_first}</#assign>
    <#assign endName>${config.endRangePrefix}${field.name?cap_first}</#assign>
</#if>