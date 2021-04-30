package com.senpure.base.controller;


import com.senpure.base.PermissionConstant;
import com.senpure.base.ResultHelper;
import com.senpure.base.ResultMap;
import com.senpure.base.interceptor.MultipleInterceptor;
import com.senpure.base.struct.LoginedAccount;
import com.senpure.base.util.DateFormatUtil;
import com.senpure.base.util.Http;
import com.senpure.base.util.JSON;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@ControllerAdvice
public class ErrorController extends BaseController {

    @Resource
    private MultipleInterceptor multipleInterceptor;

    public static boolean innerIP(String ip) {

        Pattern reg = Pattern.compile("^(0:0:0:0:0:0:0:1)|(127\\.0\\.0\\.1)|(localhost)|(10\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})|(172\\.((1[6-9])|(2\\d)|(3[01]))\\.\\d{1,3}\\.\\d{1,3})|(192\\.168\\.\\d{1,3}\\.\\d{1,3})$");
        Matcher match = reg.matcher(ip);

        return match.find();
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView error(HttpServletRequest request, HttpServletResponse response, Exception e) {
        logger.error(request.getMethod() + ": " + request.getRequestURI() + " 服务器未知错误", e);
        String ip = Http.getIP(request, true);
        boolean innerIP = innerIP(ip);
        ResultMap result = ResultMap.dim();
        ResultHelper.wrapMessage(result, getLocaleResolver().resolveLocale(request));
        StringBuilder error = new StringBuilder();
        if (Http.isAjaxRequest(request)) {
            if (innerIP) {
                error.append(result.getMessage());
                error.append("[");
                error.append(e.toString());
                error.append("]");
                result.put(ResultMap.MESSAGE_KEY, error.toString());
            }
            Http.returnJson(response, JSON.toJSONString(result));
            return null;
        }
        ModelAndView modelAndView = new ModelAndView("/error");
        LoginedAccount account = Http.getSubject(request, LoginedAccount.class);
        String menuJosn;
        if (account != null) {
            account.getNormalValueMap().forEach((key, normalValue) -> modelAndView.addObject(normalValue.getKey(), normalValue.getValue()));
            menuJosn = JSON.toJSONString(account.getViewMenus());
        } else {
            multipleInterceptor.getAccountValues().forEach(KeyValue -> modelAndView.addObject(KeyValue.getKey(), KeyValue.getValue()));
            menuJosn = JSON.toJSONString(multipleInterceptor.getMenus());
        }
        modelAndView.addObject("menuJson", menuJosn);
        Locale locale = localeResolver.resolveLocale(request);
        String viewLocale;
        if (locale.getCountry().length() > 0) {
            viewLocale = locale.getLanguage() + "-" + locale.getCountry();
        } else {
            viewLocale = locale.getLanguage();
        }
        modelAndView.addObject("viewLocale", viewLocale);

        String format = (String) modelAndView.getModelMap().get(PermissionConstant.DATETIME_FORMAT_KEY);
        error.append("<h4>TIME:").append(DateFormatUtil.getDateFormat(format).format(new Date())).append("</h4>");
        error.append("<h5>");
        error.append(request.getMethod()).append(": ").append(request.getRequestURI()).append("<br>");
        error.append("</h5>");
        error.append(e.toString().replace("\n", "<br>"));
        error.append("<br>");
        if (innerIP) {
            for (StackTraceElement s : e.getStackTrace()) {
                error.append(s.toString()).append("<br>");
            }
        }
        result.put(ResultMap.MESSAGE_KEY, error.toString());
        logger.debug("accountValues {}", multipleInterceptor.getAccountValues().toString());
        logger.debug("menuJson {}", menuJosn);
        logger.debug("view:" + modelAndView.getViewName());
        logger.debug("{} {} > {}", request.getMethod(), request.getRequestURI(), modelAndView.getViewName());
        modelAndView.addAllObjects(result);
        return modelAndView;
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value\nvalue\nvalue");
        System.out.println(JSON.toJSONString(map));
    }
}
