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
import com.xiaominfo.oss.module.dao.OSSDeveloperMapper;
import com.xiaominfo.oss.module.model.OSSDeveloper;
import com.xiaominfo.oss.service.OSSDeveloperService;
import com.xiaominfo.oss.utils.PaginationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 *
 * @since:oss-server 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/06/17 12:13
 */
@Service
public class OSSDeveloperServiceImpl extends ServiceImpl<OSSDeveloperMapper,OSSDeveloper> implements OSSDeveloperService {

    @Autowired
    OSSDeveloperMapper ossDeveloperMapper;

    @Override
    public OSSDeveloper queryByAppid(String appid, String appsecret) {
        Wrapper<OSSDeveloper> wrapper=new EntityWrapper<>();
        wrapper.eq("appid",appid);
        wrapper.eq("appsecret",appsecret);
        List<OSSDeveloper> list=ossDeveloperMapper.selectList(wrapper);
        if (list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public Pagination<OSSDeveloper> queryByPage(OSSDeveloper ossDeveloper, Integer current_page, Integer page_size) {
        Wrapper<OSSDeveloper> wrapper=new EntityWrapper<>();
        Integer count=ossDeveloperMapper.selectCount(wrapper);
        List<OSSDeveloper> list=ossDeveloperMapper.selectPage(PaginationUtils.getBounds(current_page,page_size),wrapper);
        Pagination<OSSDeveloper> pagination=PaginationUtils.transfor(list,count,current_page,page_size);
        return pagination;
    }

    @Override
    public List<OSSDeveloper> queryAllDevs() {
        return ossDeveloperMapper.selectList(new EntityWrapper<>());
    }
}
