/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */
package com.xiaominfo.oss.service;

import com.baomidou.mybatisplus.service.IService;
import com.xiaominfo.oss.common.pojo.Pagination;
import com.xiaominfo.oss.module.model.OSSAppInfo;

import java.util.List;

/***
 *
 * @since:cloud-ims 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/06/17 19:52
 */
public interface OSSAppInfoService extends IService<OSSAppInfo> {

    /***
     * 分页查询
     * @param ossAppInfo
     * @param current_page
     * @param page_size
     * @return
     */
    Pagination<OSSAppInfo> queryByPage(OSSAppInfo ossAppInfo,Integer current_page,Integer page_size);

    /***
     * 根据code查询
     * @param code
     * @return
     */
    Integer queryByCode(String code);

    /***
     * 根据devId查询应用列表
     * @param devId
     * @return
     */
    List<OSSAppInfo> queryByDevIds(String devId);
}
