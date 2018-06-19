/*
 * Copyright (C) 2017 Zhejiang BYCDAO Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.bycdao.com.
 * Developer Web Site: http://open.bycdao.com.
 */

package com.xiaominfo.oss.config;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.xiaominfo.oss.api.RootApis;
import com.xiaominfo.oss.common.pojo.RestfulMessage;
import com.xiaominfo.oss.exception.ErrorConstant;
import com.xiaominfo.oss.exception.UserNotLoginException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 *
 * @since:oss-server 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/06/15 13:39
 */
@ControllerAdvice
public class GlobalExceptionHandler extends RootApis{

    Log log= LogFactory.get();


    @ExceptionHandler(value=UserNotLoginException.class)
    @ResponseBody
    public RestfulMessage handlerUserNotLoginException(HttpServletRequest request, HttpServletResponse response, Exception ex){
        //用户未登陆
        //判断请求头,是否ajax请求
        String header=request.getHeader("X-Requested-With");
        RestfulMessage r=new RestfulMessage();
        try {
            if (StrUtil.isNotBlank(header)||StrUtil.equalsIgnoreCase(header, "XMLHttpRequest")) {
                r.setCode(ErrorConstant.NOT_LOGIN);
                r.setMessage(ex.getMessage());
            }else{
                response.sendRedirect("/login");
            }
        } catch (Exception e) {
            log.error(e);
        }
        return r;
    }
}
