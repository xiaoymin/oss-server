/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.xiaominfo.oss.domain;

import java.util.Map;

/***
 *
 * @since:oss-server 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/05/30 10:53
 */
public class RemoteResult {

    private String url;
    private Map<String, String> thumb;

    RemoteResult()
    {
    }

    public Map<String, String> getThumb()
    {
        return this.thumb;
    }

    public void setThumb(Map<String, String> thumb) {
        this.thumb = thumb;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
