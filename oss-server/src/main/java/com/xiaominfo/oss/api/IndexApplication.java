/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.xiaominfo.oss.api;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/***
 *
 * @since:oss-server 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/06/11 14:25
 */
@Controller
public class IndexApplication {

    @Value(value = "${oss.security.userName}")
    private String userN;

    @Value(value = "${oss.security.password}")
    private String passwd;


    @GetMapping(value = {"/","/index"})
    public String index(){
        return "redirect:/oss/list/";
    }


    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String loginValidate(String username, String password, Model model, HttpServletRequest request){
        if (StrUtil.equalsIgnoreCase(username,userN)&&StrUtil.equalsIgnoreCase(password,passwd)){
            request.getSession().setAttribute("USER", ImmutableMap.of("userName",username));
            return "redirect:/oss/list/";
        }
        model.addAttribute("message","用户名或密码错误");
        return "login";
    }
}
