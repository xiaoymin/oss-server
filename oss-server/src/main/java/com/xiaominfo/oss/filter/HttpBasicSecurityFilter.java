/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.xiaominfo.oss.filter;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

/***
 * 基础basic验证
 * @since:oss-server 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/06/11 11:43
 */
@Deprecated
public class HttpBasicSecurityFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        String uri=request.getRequestURI();
        String regex=".*?/oss/list.*";
        if (ReUtil.isMatch(Pattern.compile(regex,Pattern.CASE_INSENSITIVE),uri)){
            //判断登录
            Object obj=request.getSession().getAttribute("USER");
            if (obj==null){
                response.sendRedirect("/login");
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
