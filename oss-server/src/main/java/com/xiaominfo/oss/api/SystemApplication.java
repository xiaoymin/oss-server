/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.xiaominfo.oss.api;

import cn.hutool.core.util.StrUtil;
import com.xiaominfo.oss.common.pojo.RestfulMessage;
import com.xiaominfo.oss.module.model.OSSInformation;
import com.xiaominfo.oss.service.OSSInformationService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 *
 * @since:oss-server 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/06/17 10:30
 */
@RestController
@RequestMapping("/oss/sys")
public class SystemApplication extends RootApis{

    @Autowired
    OSSInformationService ossInformationService;

    @PostMapping("/mergeInformation")
    public RestfulMessage mergeInformation(OSSInformation ossInformation){
        RestfulMessage restfulMessage=new RestfulMessage();
        try{
            assertArgumentNotEmpty(ossInformation.getRoot(),"存储路径不能为空");
            assertArgumentNotEmpty(ossInformation.getInvokingRoot(),"素材下载根目录不能为空");
            isTrue(ossInformation.getRoot().endsWith("/"),"存储路径必须以/结尾");
            if (StrUtil.isEmpty(ossInformation.getId())){
                ossInformation.setCreateTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
                ossInformationService.insert(ossInformation);
            }else{
                ossInformation.setModifiedTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
                ossInformationService.updateById(ossInformation);
            }
            restfulMessage.setData(ossInformation.getId());
            successResultCode(restfulMessage);
        }catch (Exception e){
            restfulMessage=wrapperException(e);
        }
        return restfulMessage;
    }
}
