/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.xiaominfo.oss.api;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xiaominfo.oss.service.OSSAppInfoService;
import com.xiaominfo.oss.service.OSSDeveloperService;
import com.xiaominfo.oss.service.OSSInformationService;
import com.xiaominfo.oss.service.OSSMaterialInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/***
 * 前端url跳转Application
 * @since:oss-server 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/06/16 19:59
 */
@Controller
@RequestMapping("/oss")
public class ModelApplication {

    @Value(value = "${material.root}")
    private String root;

    @Value(value = "${material.invokingRoot}")
    private String invokingRoot;

    @Autowired
    OSSInformationService ossInformationService;

    @Autowired
    OSSDeveloperService ossDeveloperService;

    @Autowired
    OSSAppInfoService ossAppInfoService;

    @Autowired
    OSSMaterialInfoService ossMaterialInfoService;


    /**
     * 主面板
     * @return
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model){
        model.addAttribute("apps",ossAppInfoService.selectCount(new EntityWrapper<>()));
        model.addAttribute("devs",ossDeveloperService.selectCount(new EntityWrapper<>()));
        model.addAttribute("files",ossMaterialInfoService.selectCount(new EntityWrapper<>()));
        model.addAttribute("totalUseSpace",ossMaterialInfoService.queryTotalSpaceByteStr());
        return "dashboard";
    }

    @GetMapping("/information")
    public String information(Model model){
        model.addAttribute("ossInfo",ossInformationService.queryOne());
        return "information";
    }

    @GetMapping("/developer")
    public String developer(){
        return "developer";
    }

    @GetMapping("/appinfo")
    public String appinfo(Model model){
        model.addAttribute("devList",ossDeveloperService.queryAllDevs());
        return "appinfo";
    }

}
