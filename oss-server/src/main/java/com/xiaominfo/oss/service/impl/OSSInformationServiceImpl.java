/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.xiaominfo.oss.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xiaominfo.oss.module.dao.OSSInformationMapper;
import com.xiaominfo.oss.module.model.OSSInformation;
import com.xiaominfo.oss.service.OSSInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;


/***
 *
 * @since:oss-server 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/06/17 10:12
 */
@org.springframework.stereotype.Service
public class OSSInformationServiceImpl extends ServiceImpl<OSSInformationMapper,OSSInformation> implements OSSInformationService {

    @Value(value = "${material.root}")
    private String root;

    @Value(value = "${material.invokingRoot}")
    private String invokingRoot;


    @Autowired
    OSSInformationMapper ossInformationMapper;

    @Override
    public OSSInformation queryOne() {
        List<OSSInformation> list=ossInformationMapper.selectList(new EntityWrapper<>());
        if (list!=null&&list.size()>0){
            return list.get(0);
        }else{
            OSSInformation ossInformation=new OSSInformation();
            ossInformation.setRoot(root);
            ossInformation.setInvokingRoot(invokingRoot);
            return ossInformation;
        }
    }
}
