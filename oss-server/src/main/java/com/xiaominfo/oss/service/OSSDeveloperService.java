/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */
package com.xiaominfo.oss.service;

import com.baomidou.mybatisplus.service.IService;
import com.xiaominfo.oss.common.pojo.Pagination;
import com.xiaominfo.oss.module.model.OSSDeveloper;

import java.util.List;

/***
 *
 * @since:cloud-ims 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/06/17 12:12
 */
public interface OSSDeveloperService extends IService<OSSDeveloper> {

    /***
     * 根据appid、appsecret查询开发者
     * @param appid
     * @param appsecret
     * @return
     */
    OSSDeveloper queryByAppid(String appid,String appsecret);

    /***
     * 分页查询
     * @param ossDeveloper
     * @param current_page
     * @param page_size
     * @return
     */
    Pagination<OSSDeveloper> queryByPage(OSSDeveloper ossDeveloper,Integer current_page,Integer page_size);

    /***
     * 查询所有开发者
     * @return
     */
    List<OSSDeveloper> queryAllDevs();
}
