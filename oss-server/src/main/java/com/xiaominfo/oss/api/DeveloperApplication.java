/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.xiaominfo.oss.api;

import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.xiaominfo.oss.common.pojo.Pagination;
import com.xiaominfo.oss.common.pojo.RestfulMessage;
import com.xiaominfo.oss.module.model.OSSDeveloper;
import com.xiaominfo.oss.service.OSSDeveloperService;
import com.xiaominfo.oss.utils.RandomUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/***
 *
 * @since:oss-server 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/06/17 12:01
 */
@RestController
@RequestMapping("/oss/developer")
public class DeveloperApplication extends RootApis{

    @Autowired
    OSSDeveloperService ossDeveloperService;


    @GetMapping("/queryByPage")
    public Pagination<OSSDeveloper> queryByPage(OSSDeveloper ossDeveloper,
                                                @RequestParam(value = "page",defaultValue = "1") Integer current_page,
                                                @RequestParam(value = "rows",defaultValue = "10") Integer page_size){
        return ossDeveloperService.queryByPage(ossDeveloper,current_page,page_size);

    }

    @PostMapping("/queryById")
    public RestfulMessage queryById(String id){
        RestfulMessage restfulMessage=new RestfulMessage();
        try{
            //验证邮箱
            assertArgumentNotEmpty(id,"id不能为空");
            restfulMessage.setData(ossDeveloperService.selectById(id));
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
            assertArgumentNotEmpty(id,"id不能为空");
            String[] ids=StrUtil.split(id,",");
            ossDeveloperService.deleteBatchIds(Lists.newArrayList(ids));
            successResultCode(restfulMessage);
        }catch (Exception e){
            restfulMessage=wrapperException(e);
        }
        return restfulMessage;
    }


    @PostMapping("/merge")
    public RestfulMessage merge(OSSDeveloper ossDeveloper){
        RestfulMessage restfulMessage=new RestfulMessage();
        try{
            //验证邮箱
            assertArgumentNotEmpty(ossDeveloper.getName(),"开发者名称不能为空");
            assertArgumentNotEmpty(ossDeveloper.getTel(),"手机号不能为空");
            assertArgumentNotEmpty(ossDeveloper.getEmail(),"邮箱不能为空");
            isTrue(ReUtil.isMatch(PatternPool.EMAIL,ossDeveloper.getEmail()),"邮箱格式非法");
            if (StrUtil.isEmpty(ossDeveloper.getId())){
                //insert
                ossDeveloper.setAppid("oss"+ RandomUtils.random(6));
                ossDeveloper.setAppsecret(RandomUtils.random(8));
                ossDeveloper.setStatus("0");
                ossDeveloper.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
                ossDeveloper.setUseSpace(0L);
                ossDeveloper.setUseSpaceStr("0kb");
                ossDeveloperService.insert(ossDeveloper);
            }else{
                ossDeveloperService.updateById(ossDeveloper);
            }
            successResultCode(restfulMessage);
        }catch (Exception e){
            restfulMessage=wrapperException(e);
        }
        return restfulMessage;
    }
}
