/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.xiaominfo.oss.api;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.xiaominfo.oss.common.pojo.Pagination;
import com.xiaominfo.oss.common.pojo.RestfulMessage;
import com.xiaominfo.oss.exception.AssemblerException;
import com.xiaominfo.oss.exception.ErrorCable;
import com.xiaominfo.oss.exception.ErrorConstant;
import com.xiaominfo.oss.module.model.OSSAppInfo;
import com.xiaominfo.oss.service.OSSAppInfoService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/***
 *
 * @since:oss-server 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/06/17 19:53
 */
@RestController
@RequestMapping("/oss/app")
public class AppInfoApplication extends RootApis{

    @Autowired
    OSSAppInfoService ossAppInfoService;

    @GetMapping("/queryByPage")
    public Pagination<OSSAppInfo> queryByPage(OSSAppInfo ossAppInfo,
                                                @RequestParam(value = "page",defaultValue = "1") Integer current_page,
                                                @RequestParam(value = "rows",defaultValue = "10") Integer page_size){
        return ossAppInfoService.queryByPage(ossAppInfo,current_page,page_size);

    }

    @PostMapping("/queryById")
    public RestfulMessage queryById(String id){
        RestfulMessage restfulMessage=new RestfulMessage();
        try{
            assertArgumentNotEmpty(id,"项目id不能为空");
            restfulMessage.setData(ossAppInfoService.selectById(id));
            successResultCode(restfulMessage);
        }catch (Exception e){
            restfulMessage=wrapperException(e);
        }
        return restfulMessage;
    }

    @PostMapping("/delete")
    public RestfulMessage delete(String id){
        RestfulMessage restfulMessage=new RestfulMessage();
        try{
            //验证邮箱
            assertArgumentNotEmpty(id,"项目id不能为空");
            String[] ids= StrUtil.split(id,",");
            ossAppInfoService.deleteBatchIds(Lists.newArrayList(ids));
            successResultCode(restfulMessage);
        }catch (Exception e){
            restfulMessage=wrapperException(e);
        }
        return restfulMessage;
    }


    @PostMapping("/merge")
    public RestfulMessage merge(OSSAppInfo ossAppInfo){
        RestfulMessage restfulMessage=new RestfulMessage();
        try{
            //验证邮箱
            assertArgumentNotEmpty(ossAppInfo.getName(),"项目名称不能为空");
            assertArgumentNotEmpty(ossAppInfo.getCode(),"项目code不能为空");
            assertArgumentNotEmpty(ossAppInfo.getDevId(),"开发者不能为空");
            String regex="^.*?(\\\\|\\/|\\:|\\*|\\?|\\？|\\\"|\\“|\\”|\\>|\\<|\\|).*";
            if (ReUtil.isMatch(regex,ossAppInfo.getCode())){
                throw new AssemblerException(new ErrorCable(ErrorConstant.REQUEST_PARAMS_NOT_VALID,"项目code不能包含以下字符: / /: *?<>|"));
            }
            //不能包含\s字符
            //不能包含中文
            regex=".*?[\\u4e00-\\u9fa5\\s].*";
            if (ReUtil.isMatch(regex,ossAppInfo.getCode())){
                throw new AssemblerException(new ErrorCable(ErrorConstant.REQUEST_PARAMS_NOT_VALID,"项目code不能包含中文"));
            }
            //判断是否存在
            if (ossAppInfoService.queryByCode(ossAppInfo.getCode())>0){
                throw new AssemblerException(new ErrorCable(ErrorConstant.REQUEST_PARAMS_NOT_VALID,"项目code已经存在"));
            }
            if (StrUtil.isEmpty(ossAppInfo.getId())){
                //insert
                ossAppInfo.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
                ossAppInfo.setUseSpace(0L);
                ossAppInfo.setUseSpaceStr("0kb");
                ossAppInfoService.insert(ossAppInfo);
            }else{
                ossAppInfoService.updateById(ossAppInfo);
            }
            successResultCode(restfulMessage);
        }catch (Exception e){
            restfulMessage=wrapperException(e);
        }
        return restfulMessage;
    }

}
