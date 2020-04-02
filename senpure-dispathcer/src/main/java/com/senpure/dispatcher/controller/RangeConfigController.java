package com.senpure.dispatcher.controller;

import com.senpure.base.result.ResultMap;
import com.senpure.base.spring.BaseController;
import com.senpure.dispatcher.criteria.RangeConfigCriteriaStr;
import com.senpure.dispatcher.criteria.RangeConfigCriteria;
import com.senpure.dispatcher.service.RangeConfigService;
import com.senpure.dispatcher.model.RangeConfig;
import com.senpure.base.result.ActionResult;
import com.senpure.dispatcher.result.RangeConfigPageResult;
import com.senpure.dispatcher.result.RangeConfigRecordResult;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author senpure-generator
 * @version 2019-8-1 16:37:55
 */
@Controller
@RequestMapping("/dispatcher")
public class RangeConfigController extends BaseController {

    private RangeConfigService rangeConfigService;
    // Field can be converted to a local variable 警告，不用管，方便以后修改
    private String view = "/dispatcher/rangeConfig";

    @Autowired
    public void setRangeConfigService(RangeConfigService rangeConfigService) {
        this.rangeConfigService = rangeConfigService;
    }

    //Cannot resolve @PathVariable 'page' 警告，不用管
    @RequestMapping(value = {"/rangeConfigs", "/rangeConfigs/{page}"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ApiImplicitParams(@ApiImplicitParam(name = "page", value = "页数", dataType = "int", paramType = "path", example = "1"))
    @ApiResponses(@ApiResponse(code = 200, message = "OK", response = com.senpure.dispatcher.model.RangeConfig.class))
    public ModelAndView readRangeConfigs(HttpServletRequest request, @ModelAttribute("criteria") @Valid RangeConfigCriteriaStr criteriaStr, BindingResult result) {
        if (result.hasErrors()) {
            logger.warn("客户端输入不正确{}", result);
            return incorrect(request, result, view);
        }
        RangeConfigCriteria criteria = criteriaStr.toRangeConfigCriteria();
        logger.debug("查询条件:{}", criteria);
        RangeConfigPageResult pageResult = rangeConfigService.findPage(criteria);
        ResultMap resultMap = ResultMap.result(pageResult.getCode());
        resultMap.putTotal(pageResult.getTotal());
        resultMap.put("rangeConfigs",pageResult.getRangeConfigs());
        return result(request, view, resultMap);
    }

    @RequestMapping(value = "/rangeConfig/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "主键", required = true, example = "888888", dataType = "int", paramType = "path"))
    @ApiResponses(@ApiResponse(code = 200, message = "OK", response = RangeConfigRecordResult.class))
    public ResultMap readRangeConfig(HttpServletRequest request, @PathVariable String id) {
        Integer numberId;
        try {
            numberId = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            logger.error("输入转换失败", e);
            return wrapMessage(request, ResultMap.notExist(), id);
        }
        logger.debug("查询RangeConfig:{}", id);
        RangeConfig rangeConfig = rangeConfigService.find(numberId);
        if (rangeConfig != null) {
            return wrapMessage(request, ResultMap.success()).put("rangeConfig",rangeConfig);
        } else {
            return wrapMessage(request, ResultMap.notExist(), id);
        }
    }


    @RequestMapping(value = "/rangeConfig", method = RequestMethod.POST)
    @ResponseBody
     @ApiResponses(@ApiResponse(code = 200, message = "OK", response = RangeConfigRecordResult.class))
    public ResultMap createRangeConfig(HttpServletRequest request, @ModelAttribute("criteria") @Valid RangeConfigCriteriaStr criteriaStr, BindingResult result) {
        if (result.hasErrors()) {
            logger.warn("客户端输入不正确{}", result);
            return incorrect(request, result);
        }
        RangeConfigCriteria criteria = criteriaStr.toRangeConfigCriteria();
        logger.debug("创建RangeConfig:{}", criteria);
        if (rangeConfigService.save(criteria)) {
            return wrapMessage(request, ResultMap.success()).put("id", criteria.getId());
        } else {
            return wrapMessage(request, ResultMap.dim());
        }
    }

    @RequestMapping(value = "/rangeConfig/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "主键", required = true, example = "888888", dataType = "int", paramType = "path"))
    @ApiResponses(@ApiResponse(code = 200, message = "OK", response = ActionResult.class))
    public ResultMap updateRangeConfig(HttpServletRequest request, @PathVariable String id, @ModelAttribute("criteria") @Valid RangeConfigCriteriaStr criteriaStr, BindingResult result) {
        if (result.hasErrors()) {
            logger.warn("客户端输入不正确{}", result);
            return incorrect(request, result);
        }
        Integer numberId;
        try {
            numberId = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            logger.error("输入转换失败", e);
            return wrapMessage(request, ResultMap.notExist(), id);
        }
        RangeConfigCriteria criteria = criteriaStr.toRangeConfigCriteria();
        criteria.setId(numberId);
        logger.debug("修改RangeConfig:{}", criteria);
        if (criteria.getVersion() == null) {
            RangeConfig rangeConfig = rangeConfigService.find(criteria.getId());
            if (rangeConfig == null) {
                return wrapMessage(request, ResultMap.notExist(), id);
            }
            criteria.effective(rangeConfig);
            if (rangeConfigService.update(rangeConfig)) {
                return wrapMessage(request, ResultMap.success());
            } else {
                return wrapMessage(request, ResultMap.dim());
            }
        }
        if (rangeConfigService.update(criteria.toRangeConfig())) {
            return wrapMessage(request, ResultMap.success());
        } else {
            return wrapMessage(request, ResultMap.dim());
        }
    }

    @RequestMapping(value = "/rangeConfig/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "主键", required = true, example = "888888", dataType = "int", paramType = "path"))
    @ApiResponses(@ApiResponse(code = 200, message = "OK", response = ActionResult.class))
    public ResultMap deleteRangeConfig(HttpServletRequest request, @PathVariable String id) {
        Integer numberId;
        try {
            numberId = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            logger.error("输入转换失败", e);
            return wrapMessage(request, ResultMap.notExist(), id);
        }
        return wrapMessage(request, ResultMap.dim());
//        logger.debug("删除RangeConfig:{}", id);
//        if (rangeConfigService.delete(numberId)) {
//            return wrapMessage(request, ResultMap.success());
//        } else {
//            return wrapMessage(request, ResultMap.dim());
//        }
    }
}
