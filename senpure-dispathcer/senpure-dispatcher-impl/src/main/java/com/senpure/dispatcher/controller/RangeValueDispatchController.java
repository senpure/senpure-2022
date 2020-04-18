package com.senpure.dispatcher.controller;

import com.senpure.base.result.ResultMap;
import com.senpure.base.spring.BaseController;
import com.senpure.dispatcher.criteria.RangeValueCriteria;
import com.senpure.dispatcher.criteria.RangeValueCriteriaStr;
import com.senpure.dispatcher.model.RangeValue;
import com.senpure.dispatcher.result.RangeValueRecordResult;
import com.senpure.dispatcher.service.RangeValueService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * RangeValueDispatchController
 *
 * @author senpure
 * @time 2019-08-01 16:53:06
 */
@Controller
public class RangeValueDispatchController  extends BaseController {

    @Autowired
    private RangeValueService rangeValueService;

    @GetMapping("range/dispatch")
    @ResponseBody
    @ApiResponses(@ApiResponse(code = 200, message = "OK", response = RangeValueRecordResult.class))
    public ResultMap dispatch(HttpServletRequest request, @Valid @ModelAttribute("criteria") RangeValueCriteriaStr criteria, BindingResult validResult) {
        if (validResult.hasErrors()) {
            return incorrect(request, validResult);
        }
        RangeValueCriteria valueCriteria = criteria.toRangeValueCriteria();
       RangeValue rangeValue  = rangeValueService.dispatch(valueCriteria.getConfigId(),criteria.getServerName(), criteria.getServerKey());
        if (rangeValue  == null) {
            return wrapMessage(request, ResultMap.dim());
        }
        return ResultMap.success().put("rangeValue",rangeValue );
    }
}
