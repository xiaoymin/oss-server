/*
 * Copyright (C) 2017 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.xiaominfo.oss.extend.mybatis;

import cn.hutool.log.LogFactory;
import org.apache.ibatis.logging.Log;

/***
 *
 * @since:province_cdc_III 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/03/27 15:04
 */
public class MybatisStdOutImpl implements Log {

    private final cn.hutool.log.Log logger= LogFactory.get();

    public MybatisStdOutImpl(String clazz) {
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public void error(String s, Throwable e) {
        logger.error(s);
        logger.error(e);
    }

    @Override
    public void error(String s) {
        logger.error(s);
    }

    @Override
    public void debug(String s) {
        logger.debug(s);
    }

    @Override
    public void trace(String s) {
        logger.trace(s);
    }

    @Override
    public void warn(String s) {
        logger.warn(s);
    }
}
