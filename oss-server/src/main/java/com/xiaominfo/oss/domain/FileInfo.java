/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.xiaominfo.oss.domain;

/***
 *
 * @since:oss-server 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/06/11 13:00
 */
public class FileInfo {

    private String url;

    private String name;

    private String type;

    private String lastModifiedTime;

    private String size;

    /***
     * 媒体类型
     */
    private String mediaType;

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FileInfo(String url, String name, String lastModifiedTime, String size) {
        this.url = url;
        this.name = name;
        this.lastModifiedTime = lastModifiedTime;
        this.size = size;
    }

    public FileInfo(String name, String lastModifiedTime, String size) {
        this.name = name;
        this.lastModifiedTime = lastModifiedTime;
        this.size = size;
    }
}
