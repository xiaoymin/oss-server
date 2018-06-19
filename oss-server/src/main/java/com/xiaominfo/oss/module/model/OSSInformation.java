/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.xiaominfo.oss.module.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/***
 *
 * @since:oss-server 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/06/17 9:56
 */
@TableName("oss_information")
public class OSSInformation extends Model<OSSInformation> {

    @TableId(value = "id",type = IdType.UUID)
    private String id;

    private String root;

    @TableField(value = "invoking_root")
    private String invokingRoot;

    @TableField(value = "nginx_log_path")
    private String nginxLogPath;

    @TableField(value = "create_time")
    private String createTime;

    @TableField(value = "modified_time")
    private String modifiedTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getInvokingRoot() {
        return invokingRoot;
    }

    public void setInvokingRoot(String invokingRoot) {
        this.invokingRoot = invokingRoot;
    }

    public String getNginxLogPath() {
        return nginxLogPath;
    }

    public void setNginxLogPath(String nginxLogPath) {
        this.nginxLogPath = nginxLogPath;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
