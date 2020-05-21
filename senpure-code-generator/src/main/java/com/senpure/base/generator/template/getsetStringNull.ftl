<#if field.hasExplain>
    /**
     * <#if field.clazzType="boolean">is<#else>get</#if> ${field.explain}
     *
     * @return
     */
</#if>
    public ${field.clazzType} <#if field.clazzType="boolean">is<#else>get</#if>${field.name?cap_first}() {
        return ${field.name};
    }

<#if field.hasExplain>
    /**
     * set ${field.explain}
     *
     * @return
     */</#if>
    public ${name} set${field.name?cap_first}(${field.clazzType} ${field.name}) {
<#if field.clazzType=='String'>
        if (${field.name} != null && ${field.name}.trim().length() == 0) {
            this.${field.name} = null;
            return this;
        }
</#if>
        this.${field.name} = ${field.name};
        return this;
    }

<#if field.hasCriteriaRange>
    public ${field.clazzType} get${config.startRangePrefix?cap_first}${field.name?cap_first}() {
        return ${config.startRangePrefix}${field.name?cap_first};
    }

    public ${name} set${config.startRangePrefix?cap_first}${field.name?cap_first}(${field.clazzType} ${config.startRangePrefix}${field.name?cap_first}) {
    <#if field.clazzType=='String'>
        if (${config.startRangePrefix}${field.name?cap_first} != null && ${config.startRangePrefix}${field.name?cap_first}.trim().length() == 0) {
            this.${config.startRangePrefix}${field.name?cap_first} = null;
            return this;
        }
    </#if>
        this.${config.startRangePrefix}${field.name?cap_first} = ${config.startRangePrefix}${field.name?cap_first};
        return this;
    }

    public ${field.clazzType} get${config.endRangePrefix?cap_first}${field.name?cap_first}() {
        return ${config.endRangePrefix}${field.name?cap_first};
    }

    public ${name} set${config.endRangePrefix?cap_first}${field.name?cap_first}(${field.clazzType} ${config.endRangePrefix}${field.name?cap_first}) {
    <#if field.clazzType=='String'>
        if (${config.endRangePrefix}${field.name?cap_first} != null && ${config.endRangePrefix}${field.name?cap_first}.trim().length() == 0) {
            this.${config.endRangePrefix}${field.name?cap_first} = null;
            return this;
        }
    </#if>
        this.${config.endRangePrefix}${field.name?cap_first} = ${config.endRangePrefix}${field.name?cap_first};
        return this;
    }

</#if>
<#if field.criteriaOrder>
    /**
     * get table [${tableName}][column = ${field.column}] criteriaOrder
     *
     * @return
     */
    public String get${field.name?cap_first}Order() {
        return ${field.name}Order;
    }

    /**
     * set table [${tableName}][column = ${field.column}] criteriaOrder DESC||ASC
     *
     * @return
     */
    public ${name} set${field.name?cap_first}Order(String ${field.name}Order) {
        this.${field.name}Order = ${field.name}Order;
    <#if field.longDate??>
        putSort("${field.longDate.column}", ${field.name}Order);
    <#else >
        putSort("${field.column}", ${field.name}Order);
    </#if>
        return this;
    }

</#if>
