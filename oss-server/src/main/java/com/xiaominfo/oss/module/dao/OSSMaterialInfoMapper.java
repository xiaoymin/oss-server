/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.xiaominfo.oss.module.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xiaominfo.oss.module.model.OSSMaterialInfo;

/***
 *
 * @since:oss-server 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/06/18 8:53
 */
public interface OSSMaterialInfoMapper extends BaseMapper<OSSMaterialInfo> {

    /***
     * 查询使用space
     * @return
     */
    Integer selectMaterialUseSpace();
}
