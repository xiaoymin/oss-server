/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */
package com.xiaominfo.oss.service;

import com.baomidou.mybatisplus.service.IService;
import com.xiaominfo.oss.module.model.OSSInformation;

/***
 *
 * @since:cloud-ims 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/06/17 10:11
 */
public interface OSSInformationService extends IService<OSSInformation> {

    /***
     * 获取一条
     * @return
     */
    OSSInformation queryOne();
}
