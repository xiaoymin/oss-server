/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.xiaominfo.oss.api;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.ImmutableMap;
import com.xiaominfo.oss.common.annotation.NotLogin;
import com.xiaominfo.oss.common.conf.Consts;
import com.xiaominfo.oss.common.pojo.RestfulMessage;
import com.xiaominfo.oss.exception.ErrorConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/***
 *
 * @since:oss-server 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/06/11 14:25
 */
@Controller
public class IndexApplication extends RootApis{

    @Value(value = "${oss.security.userName}")
    private String userN;

    @Value(value = "${oss.security.password}")
    private String passwd;


    @GetMapping(value = {"/","/index"})
    public String index(){
        return "index";
    }


    @NotLogin
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/exit")
    public String exit(HttpServletRequest request){
        request.getSession().removeAttribute(Consts.Admin_User_Session);
        return "login";
    }

    @NotLogin
    @PostMapping("/login")
    @ResponseBody
    public RestfulMessage loginValidate(String username, String password, Model model, HttpServletRequest request){
        RestfulMessage r=new RestfulMessage();
        if (StrUtil.equalsIgnoreCase(username,userN)&&StrUtil.equalsIgnoreCase(password,passwd)){
            request.getSession().setAttribute(Consts.Admin_User_Session, ImmutableMap.of("userName",username));
            successResultCode(r);
        }else{
            r.setCode(ErrorConstant.REQUEST_PARAMS_NOT_VALID);
            r.setMessage("用户名或密码错误");
        }
        return r;
    }
}
