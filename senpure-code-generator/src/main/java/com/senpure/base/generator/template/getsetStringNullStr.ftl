<#if field.criteriaShow>
    <#if field.hasExplain>
    /**
     * get ${field.explain}
     *
     * @return
     */
    </#if>
    public String get${field.name?cap_first}() {
        return ${field.name};
    }

    <#if field.hasExplain>
    /**
     * set ${field.explain}
     *
     * @return
     */</#if>
    public ${name} set${field.name?cap_first}(String ${field.name}) {
        if (${field.name} != null && ${field.name}.trim().length() == 0) {
            return this;
        }
        this.${field.name} = ${field.name};
       <#if field.date>
        this.${field.name}Valid.setDateStr(${field.name});
       </#if>
        return this;
    }

    <#if field.date>
    public String get${field.name?cap_first}Pattern() {
        return ${field.name}Pattern;
    }

    public ${name} set${field.name?cap_first}Pattern(String ${field.name}Pattern) {
        if (${field.name}Pattern != null && ${field.name}Pattern.trim().length() == 0) {
            return this;
        }
        this.${field.name}Pattern = ${field.name}Pattern;
        this.${field.name}Valid.setPattern(${field.name}Pattern);
        <#if field.hasCriteriaRange>
        this.${config.startRangePrefix}${field.name?cap_first}Valid.setPattern(${field.name}Pattern);
        this.${config.endRangePrefix}${field.name?cap_first}Valid.setPattern(${field.name}Pattern);
        </#if>
        return this;
    }

    </#if><#--时间类型-->
    <#if field.hasCriteriaRange><#--start end get set -->
        <#if field.hasExplain>
    /**
     * get start ${field.explain}
     *
     * @return
     */
        </#if>
    public String get${config.startRangePrefix?cap_first}${field.name?cap_first}() {
        return ${config.startRangePrefix}${field.name?cap_first};
    }

        <#if field.hasExplain>
    /**
     * set start ${field.explain}
     *
     * @return
     */
        </#if>
    public ${name} set${config.startRangePrefix?cap_first}${field.name?cap_first}(String ${config.startRangePrefix}${field.name?cap_first}) {
        if (${config.startRangePrefix}${field.name?cap_first} != null && ${config.startRangePrefix}${field.name?cap_first}.trim().length() == 0) {
            return this;
        }
        this.${config.startRangePrefix}${field.name?cap_first} = ${config.startRangePrefix}${field.name?cap_first};
        <#if field.clazzType=="Date">
        this.${config.startRangePrefix}${field.name?cap_first}Valid.setDateStr(${config.startRangePrefix}${field.name?cap_first});
        </#if>
        return this;
    }

        <#if field.hasExplain>
    /**
     * get end ${field.explain}
     *
     * @return
     */
        </#if>
    public String get${config.endRangePrefix?cap_first}${field.name?cap_first}() {
        return ${config.endRangePrefix}${field.name?cap_first};
    }

        <#if field.hasExplain>
    /**
     * set end ${field.explain}
     *
     * @return
     */
        </#if>
    public ${name} set${config.endRangePrefix?cap_first}${field.name?cap_first}(String ${config.endRangePrefix}${field.name?cap_first}) {
        if (${config.endRangePrefix}${field.name?cap_first} != null && ${config.endRangePrefix}${field.name?cap_first}.trim().length() == 0) {
            return this;
        }
        this.${config.endRangePrefix}${field.name?cap_first} = ${config.endRangePrefix}${field.name?cap_first};
        <#if field.clazzType=="Date">
        this.${config.endRangePrefix}${field.name?cap_first}Valid.setDateStr(${config.endRangePrefix}${field.name?cap_first});
        </#if>
        return this;
    }

    </#if><#--start end get set  结束-->
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
        if (${field.name}Order != null && ${field.name}Order.trim().length() == 0) {
            this.${field.name}Order = null;
            return this;
        }
        this.${field.name}Order = ${field.name}Order;
        return this;
    }

    </#if>
</#if>