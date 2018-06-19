/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.xiaominfo.oss.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xiaominfo.oss.common.pojo.Pagination;
import com.xiaominfo.oss.module.dao.OSSAppInfoMapper;
import com.xiaominfo.oss.module.model.OSSAppInfo;
import com.xiaominfo.oss.service.OSSAppInfoService;
import com.xiaominfo.oss.utils.PaginationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 *
 * @since:oss-server 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/06/17 19:53
 */
@Service
public class OSSAppInfoServiceImpl extends ServiceImpl<OSSAppInfoMapper,OSSAppInfo> implements OSSAppInfoService {

    @Autowired
    OSSAppInfoMapper ossAppInfoMapper;

    @Override
    public Pagination<OSSAppInfo> queryByPage(OSSAppInfo ossAppInfo, Integer current_page, Integer page_size) {
        Wrapper<OSSAppInfo> wrapper=new EntityWrapper<>();
        Integer count=ossAppInfoMapper.selectCount(wrapper);
        List<OSSAppInfo> list=ossAppInfoMapper.selectPage(PaginationUtils.getBounds(current_page,page_size),wrapper);
        return PaginationUtils.transfor(list,count,current_page,page_size);
    }

    @Override
    public Integer queryByCode(String code) {
        Wrapper<OSSAppInfo> wrapper=new EntityWrapper<>();
        wrapper.eq("code",code);
        Integer list=ossAppInfoMapper.selectCount(wrapper);
        return list;
    }

    @Override
    public List<OSSAppInfo> queryByDevIds(String devId) {
        Wrapper<OSSAppInfo> wrapper=new EntityWrapper<>();
        wrapper.eq("dev_id",devId);
        return ossAppInfoMapper.selectList(wrapper);
    }
}
