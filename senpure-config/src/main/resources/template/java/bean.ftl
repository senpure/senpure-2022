package ${javaPack};

<#if hasList>
import java.util.ArrayList;
import java.util.List;
</#if>

/**<#if hasExplain>
 * ${explain}
 * </#if>
 * @author senpure-generator
 * @version ${.now?datetime}
 */
<#assign name>${javaName}</#assign>
public class ${name}  {

<#list fields as field>
    <#if field.hasExplain>
    //${field.explain}
    </#if>
    <#if field.list>
    private List<${field.javaListType}> ${field.name} = new ArrayList(16);
    <#else >
    private ${field.javaType} ${field.name};
    </#if>
</#list>

<#list fields as field>
<#if field.list>
    <#if field.hasExplain&&field.explain?length gt 2>
    /**
     * get ${field.explain}
     * @return
     */
    </#if>
    public  List<${field.javaListType}> get${field.name?cap_first}() {
        return ${field.name};
    }

    <#if field.hasExplain&&field.explain?length gt 2>
    /**
    * set ${field.explain}
    */
    </#if>
    public ${name} set${field.name?cap_first}(List<${field.javaListType}> ${field.name}) {
        this.${field.name}=${field.name};
        return this;
    }
<#else>
    <#if field.hasExplain&&field.explain?length gt 2>
    /**
     * <#if field.javaType="boolean"> is<#else>get</#if> ${field.explain}
     * @return
     */
    </#if>
    public  ${field.javaType} <#if field.javaType="boolean"> is<#else>get</#if>${field.name?cap_first}() {
        return ${field.name};
    }

    <#if field.hasExplain&&field.explain?length gt 2>
    /**
     * set ${field.explain}
     */
    </#if>
    public ${name} set${field.name?cap_first}(${field.javaType} ${field.name}) {
        this.${field.name}=${field.name};
    return this;
    }
</#if>
</#list>
}