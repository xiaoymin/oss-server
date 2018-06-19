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
 * 2018/06/17 19:48
 */
@TableName(value = "oss_app_info")
public class OSSAppInfo extends Model<OSSAppInfo> {

    @TableId(value = "id",type = IdType.UUID)
    private String id;
    private String name;
    private String code;
    private String intro;
    @TableField(value = "dev_id")
    private String devId;

    @TableField(value = "create_time")
    private String createTime;

    @TableField(value = "use_space")
    private long useSpace;

    @TableField(value = "use_space_str")
    private String useSpaceStr;

    public long getUseSpace() {
        return useSpace;
    }

    public void setUseSpace(long useSpace) {
        this.useSpace = useSpace;
    }

    public String getUseSpaceStr() {
        return useSpaceStr;
    }

    public void setUseSpaceStr(String useSpaceStr) {
        this.useSpaceStr = useSpaceStr;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public OSSAppInfo() {
    }

    public OSSAppInfo(String id, long useSpace) {
        this.id = id;
        this.useSpace = useSpace;
    }

    public OSSAppInfo(String id, long useSpace, String useSpaceStr) {
        this.id = id;
        this.useSpace = useSpace;
        this.useSpaceStr = useSpaceStr;
    }
}
