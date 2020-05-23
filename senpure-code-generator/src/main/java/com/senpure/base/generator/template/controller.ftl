package ${controllerPackage};

import com.senpure.base.controller.BaseController;
<#if useCriteriaStr>
import ${criteriaPackage}.${name}${config.criteriaStrSuffix};
</#if>
import ${criteriaPackage}.${name}${config.criteriaSuffix};
import ${servicePackage}.${name}${config.serviceSuffix};
import ${modelPackage}.${name};
import com.senpure.base.ActionResult;
import com.senpure.base.ResultMap;
import ${resultPackage}.${name}${config.resultPageSuffix};
import ${resultPackage}.${name}${config.resultRecordSuffix};
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
<#if generatePermission>
import com.senpure.base.annotation.PermissionVerify;
</#if>
<#if generateMenu>
import com.senpure.base.menu.MenuGenerator;
</#if>

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
${sovereignty}
 * @version ${.now?datetime}
 */
@Controller
@RequestMapping("/${module}")
<#if generateMenu>
@MenuGenerator(id = ${menuId?c}, text = "${name}")
</#if>
public class ${name}Controller extends BaseController {

    @Resource
    private ${name}${config.serviceSuffix} ${nameRule(name)}${config.serviceSuffix};
    // Field can be converted to a local variable 警告，不用管，方便以后修改
    private String view = "/${module}/${nameRule(name)}";

    //Cannot resolve @PathVariable 'page' 警告，不用管
    @RequestMapping(value = {"/${pluralize(nameRule(name))}", "/${pluralize(nameRule(name))}/{page}"}, method = {RequestMethod.GET, RequestMethod.POST})
    <#if generatePermission>
    @PermissionVerify(name = "/${module}/${nameRule(name)}_read", value = "${pluralize(nameRule(name))}_read")
    </#if>
<#if generateMenu>
    @MenuGenerator(id = ${(menuId+1)?c}, text = "${name} Detail")
</#if>
<#if useCriteriaStr>
    <#assign criteriaClazz>${name}${config.criteriaStrSuffix}</#assign>
    <#assign criteriaName>criteriaStr</#assign>
<#else >
    <#assign criteriaClazz>${name}${config.criteriaSuffix}</#assign>
    <#assign criteriaName>criteria</#assign>
</#if>
    @ApiImplicitParams(@ApiImplicitParam(name = "page", value = "页数", dataType = "int", paramType = "path", example = "1"))
    @ApiResponses(@ApiResponse(code = 200, message = "OK", response = ${modelPackage}.${name}.class))
    public ModelAndView read${pluralize(nameRule(name))?cap_first}(HttpServletRequest request, @ModelAttribute("criteria") @Valid ${criteriaClazz} ${criteriaName}, BindingResult result) {
        if (result.hasErrors()) {
            logger.warn("客户端输入不正确{}", result);
            return incorrect(request, result, view);
        }
<#if useCriteriaStr>
        ${name}${config.criteriaSuffix} criteria = ${criteriaName}.to${name}${config.criteriaSuffix}();
</#if>
        logger.debug("查询条件:{}", criteria);
        ${name}${config.resultPageSuffix} pageResult = ${nameRule(name)}${config.serviceSuffix}.findPage(criteria);
        ResultMap resultMap = ResultMap.result(pageResult.getCode());
        resultMap.putTotal(pageResult.getTotal());
        resultMap.put("${pluralize(nameRule(name))}",pageResult.get${pluralize(nameRule(name))?cap_first}());
        return result(request, view, resultMap);
    }

    @RequestMapping(value = "/${nameRule(name)}/{${id.name}}", method = RequestMethod.GET)
    <#if generatePermission>
    @PermissionVerify(name = "/${module}/${nameRule(name)}_read", value = "${nameRule(name)}_read")
    </#if>
    @ResponseBody
    @ApiImplicitParams(@ApiImplicitParam(name = "${id.name}", value = "主键", required = true, example = "888888", <#if id.clazzType="String"><#elseif id.clazzType="Integer">dataType = "int", <#else>dataType = "${id.clazzType?uncap_first}", </#if>paramType = "path"))
    @ApiResponses(@ApiResponse(code = 200, message = "OK", response = ${name}${config.resultRecordSuffix}.class))
    public ResultMap read${name}(HttpServletRequest request, @PathVariable String ${id.name}) {
        <#if id.clazzType !="String">
        ${id.clazzType} number${id.name?cap_first};
        try {
            number${id.name?cap_first} = ${id.clazzType}.valueOf(${id.name});
        } catch (NumberFormatException e) {
            logger.error("输入转换失败", e);
            return wrapMessage(request, ResultMap.notExist(), id);
        }
    </#if>
        logger.debug("查询${name}:{}", ${id.name});
        ${name} ${nameRule(name)} = ${nameRule(name)}${config.serviceSuffix}.find(<#if id.clazzType !="String">number${id.name?cap_first}<#else>${id.name}</#if>);
        if (${nameRule(name)} != null) {
            return wrapMessage(request, ResultMap.success()).put("${nameRule(name)}",${nameRule(name)});
        } else {
            return wrapMessage(request, ResultMap.notExist(), id);
        }
    }


    @RequestMapping(value = "/${nameRule(name)}", method = RequestMethod.POST)
    <#if generatePermission>
    @PermissionVerify(value = "${nameRule(name)}_create")
    </#if>
    @ResponseBody
     @ApiResponses(@ApiResponse(code = 200, message = "OK", response = ${name}${config.resultRecordSuffix}.class))
    public ResultMap create${name}(HttpServletRequest request, @ModelAttribute("criteria") @Valid ${criteriaClazz} ${criteriaName}, BindingResult result) {
        if (result.hasErrors()) {
            logger.warn("客户端输入不正确{}", result);
            return incorrect(request, result);
        }
<#if useCriteriaStr>
        ${name}${config.criteriaSuffix} criteria = ${criteriaName}.to${name}${config.criteriaSuffix}();
</#if>
        logger.debug("创建${name}:{}", criteria);
        if (${nameRule(name)}${config.serviceSuffix}.save(criteria)) {
            return wrapMessage(request, ResultMap.success()).put("${id.name}", criteria.get${id.name?cap_first}());
        } else {
            return wrapMessage(request, ResultMap.dim());
        }
    }

    @RequestMapping(value = "/${nameRule(name)}/{${id.name}}", method = RequestMethod.PUT)
    <#if generatePermission>
    @PermissionVerify(value = "${nameRule(name)}_update")
    </#if>
    @ResponseBody
    @ApiImplicitParams(@ApiImplicitParam(name = "${id.name}", value = "主键", required = true, example = "888888", <#if id.clazzType="String"><#elseif id.clazzType="Integer">dataType = "int", <#else>dataType = "${id.clazzType?uncap_first}", </#if>paramType = "path"))
    @ApiResponses(@ApiResponse(code = 200, message = "OK", response = ActionResult.class))
    public ResultMap update${name}(HttpServletRequest request, @PathVariable String ${id.name}, @ModelAttribute("criteria") @Valid ${criteriaClazz} ${criteriaName}, BindingResult result) {
        if (result.hasErrors()) {
            logger.warn("客户端输入不正确{}", result);
            return incorrect(request, result);
        }
    <#if id.clazzType !="String">
        ${id.clazzType} number${id.name?cap_first};
        try {
            number${id.name?cap_first} = ${id.clazzType}.valueOf(${id.name});
        } catch (NumberFormatException e) {
            logger.error("输入转换失败", e);
            return wrapMessage(request, ResultMap.notExist(), id);
        }
    </#if>
        <#if useCriteriaStr>
        ${name}${config.criteriaSuffix} criteria = ${criteriaName}.to${name}${config.criteriaSuffix}();
        </#if>
        criteria.set${id.name?cap_first}(<#if id.clazzType !="String">number${id.name?cap_first}<#else>${id.name}</#if>);
        logger.debug("修改${name}:{}", criteria);
    <#if version??>
       <#if version.javaNullable>
        if (criteria.get${version.name?cap_first}() == null) {
        <#else>
        if (criteria.get${version.name?cap_first}() == 0) {
        </#if>
            ${name} ${nameRule(name)} = ${nameRule(name)}${config.serviceSuffix}.find(criteria.get${id.name?cap_first}());
            if (${nameRule(name)} == null) {
                return wrapMessage(request, ResultMap.notExist(), id);
            }
            criteria.effective(${nameRule(name)});
            if (${nameRule(name)}${config.serviceSuffix}.update(${nameRule(name)})) {
                return wrapMessage(request, ResultMap.success());
            } else {
                return wrapMessage(request, ResultMap.dim());
            }
        }
    <#else >
    </#if>
        if (${nameRule(name)}${config.serviceSuffix}.update(criteria.to${name}())) {
            return wrapMessage(request, ResultMap.success());
        } else {
            return wrapMessage(request, ResultMap.dim());
        }
    }

    @RequestMapping(value = "/${nameRule(name)}/{${id.name}}", method = RequestMethod.DELETE)
<#if generatePermission>
    @PermissionVerify(value = "${nameRule(name)}_delete")
</#if>
    @ResponseBody
    @ApiImplicitParams(@ApiImplicitParam(name = "${id.name}", value = "主键", required = true, example = "888888", <#if id.clazzType="String"><#elseif id.clazzType="Integer">dataType = "int", <#else>dataType = "${id.clazzType?uncap_first}", </#if>paramType = "path"))
    @ApiResponses(@ApiResponse(code = 200, message = "OK", response = ActionResult.class))
    public ResultMap delete${name}(HttpServletRequest request, @PathVariable String ${id.name}) {
        <#if id.clazzType !="String">
        ${id.clazzType} number${id.name?cap_first};
        try {
            number${id.name?cap_first} = ${id.clazzType}.valueOf(${id.name});
        } catch (NumberFormatException e) {
            logger.error("输入转换失败", e);
            return wrapMessage(request, ResultMap.notExist(), id);
        }
        </#if>
        logger.debug("删除${name}:{}", ${id.name});
        if (${nameRule(name)}${config.serviceSuffix}.delete(<#if id.clazzType !="String">number${id.name?cap_first}<#else>${id.name}</#if>)) {
            return wrapMessage(request, ResultMap.success());
        } else {
            return wrapMessage(request, ResultMap.dim());
        }
    }
}
