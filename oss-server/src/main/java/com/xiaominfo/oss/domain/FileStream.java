/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.xiaominfo.oss.domain;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/***
 *
 * @since:oss-server 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/05/30 10:47
 */
public class FileStream {

    @SerializedName("file_stream_hex")
    private String fileStreamHex;

    @SerializedName("file_store_url")
    private String fileStoreUrl;

    @SerializedName("thumbs")
    private Map thumbs;

    public FileStream()
    {
    }

    public FileStream(String fileStreamHex, String fileStoreUrl)
    {
        this.fileStreamHex = fileStreamHex;
        this.fileStoreUrl = fileStoreUrl;
    }

    public FileStream(String fileStreamHex, String fileStoreUrl, Map thumbs) {
        this.fileStreamHex = fileStreamHex;
        this.fileStoreUrl = fileStoreUrl;
        this.thumbs = thumbs;
    }

    public String getFileStreamHex() {
        return this.fileStreamHex;
    }

    public void setFileStreamHex(String fileStreamHex) {
        this.fileStreamHex = fileStreamHex;
    }

    public String getFileStoreUrl() {
        return this.fileStoreUrl;
    }

    public void setFileStoreUrl(String fileStoreUrl) {
        this.fileStoreUrl = fileStoreUrl;
    }

    public Map getThumbs() {
        return this.thumbs;
    }

    public void setThumbs(Map thumbs) {
        this.thumbs = thumbs;
    }

}
