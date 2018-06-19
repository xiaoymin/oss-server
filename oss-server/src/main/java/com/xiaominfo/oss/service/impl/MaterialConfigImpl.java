/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.xiaominfo.oss.service.impl;

import com.xiaominfo.oss.service.MaterialConfig;

/***
 *
 * @since:oss-server 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/05/30 10:48
 */
public class MaterialConfigImpl implements MaterialConfig {

    private String rootPath;
    private String invokingRoot;
    private String pathStyle;

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getInvokingRoot() {
        return invokingRoot;
    }

    public void setInvokingRoot(String invokingRoot) {
        this.invokingRoot = invokingRoot;
    }

    public String getPathStyle() {
        return pathStyle;
    }

    public void setPathStyle(String pathStyle) {
        this.pathStyle = pathStyle;
    }



    @Override
    public String getRoot() {
        return getRootPath();
    }

    @Override
    public String getInvokePath() {
        return getInvokingRoot();
    }

    @Override
    public String getPathType() {
        return getPathStyle();
    }
}
