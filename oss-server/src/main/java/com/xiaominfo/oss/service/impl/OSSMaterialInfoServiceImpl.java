/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.xiaominfo.oss.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xiaominfo.oss.module.dao.OSSMaterialInfoMapper;
import com.xiaominfo.oss.module.model.OSSMaterialInfo;
import com.xiaominfo.oss.service.OSSMaterialInfoService;
import com.xiaominfo.oss.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/***
 *
 * @since:oss-server 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/06/18 8:54
 */
@Service
public class OSSMaterialInfoServiceImpl extends ServiceImpl<OSSMaterialInfoMapper,OSSMaterialInfo> implements OSSMaterialInfoService {

    @Autowired
    OSSMaterialInfoMapper ossMaterialInfoMapper;

    @Override
    public String queryTotalSpaceByteStr() {
        Integer len=ossMaterialInfoMapper.selectMaterialUseSpace();
        if (len==null){
            return "0kb";
        }
        return FileUtils.byteToString(new BigDecimal(len).longValue());
    }
}
