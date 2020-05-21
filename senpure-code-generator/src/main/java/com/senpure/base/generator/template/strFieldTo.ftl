<#if field.criteriaShow>
    <#if field.hasExplain>
        //${field.explain}
    </#if>
        if (${field.name} != null) {
    <#if field.javaNullable><#--可以为空用valueOf 不能用parse-->
        <#if field.clazzType="String">
            criteria.set${field.name?cap_first}(${field.name});
        <#elseif field.date>
            criteria.set${field.name?cap_first}(${field.name}Valid.getDate());
        <#else >
            criteria.set${field.name?cap_first}(${field.clazzType}.valueOf(${field.name}));
        </#if>
    <#else><#--不用考虑太多，以后再说-->
        criteria.set${field.name?cap_first}(${.globals[field.clazzType]!field.clazzType?cap_first}.parse${field.clazzType?cap_first}(${field.name}));
    </#if>
        }
    <#if field.hasCriteriaRange>
        <#if field.date>
            <#if field.clazzType="Date">
        if (${config.startRangePrefix}${field.name?cap_first} != null) {
            criteria.set${config.startRangePrefix?cap_first}${field.name?cap_first}(${config.startRangePrefix}${field.name?cap_first}Valid.getDate());
        }
        if (${config.endRangePrefix}${field.name?cap_first} != null) {
            criteria.set${config.endRangePrefix?cap_first}${field.name?cap_first}(${config.endRangePrefix}${field.name?cap_first}Valid.getDate());
        }
            <#else>
        if (${config.startRangePrefix}${field.name?cap_first} != null) {
            criteria.set${config.startRangePrefix?cap_first}${field.name?cap_first}(${config.startRangePrefix}${field.name?cap_first}Valid.getDate().getTime());
        }
        if (${config.endRangePrefix}${field.name?cap_first} != null) {
            criteria.set${config.endRangePrefix?cap_first}${field.name?cap_first}(${config.endRangePrefix}${field.name?cap_first}Valid.getDate().getTime());
        }
            </#if>
        <#else><#--不是时间格式-->
        if (${config.startRangePrefix}${field.name?cap_first} != null) {
            criteria.set${config.startRangePrefix?cap_first}${field.name?cap_first}(${field.clazzType}.valueOf(${config.startRangePrefix}${field.name?cap_first}));
        }
        if (${config.endRangePrefix}${field.name?cap_first} != null) {
            criteria.set${config.endRangePrefix?cap_first}${field.name?cap_first}(${field.clazzType}.valueOf(${config.endRangePrefix}${field.name?cap_first}));
        }
        </#if>
</#if><#--hasCriteriaRange-->
</#if><#--strshow-->
<#if field.criteriaOrder>
        //table [${tableName}][column = ${field.column}] criteriaOrder
        if (${field.name}Order != null) {
            criteria.set${field.name?cap_first}Order(${field.name}Order);
        }
</#if>
