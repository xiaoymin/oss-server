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
 * 2018/06/17 12:10
 */
@TableName("oss_developer")
public class OSSDeveloper extends Model<OSSDeveloper> {
    @TableId(value = "id",type = IdType.UUID)
    private String id;

    private String name;

    private String appid;

    private String appsecret;

    private String email;
    private String tel;

    private String intro;
    private String status;

    @TableField(value = "use_space")
    private long useSpace;


    @TableField(value = "use_space_str")
    private String useSpaceStr;

    @TableField(value = "create_time")
    private String createTime;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getUseSpace() {
        return useSpace;
    }

    public void setUseSpace(long useSpace) {
        this.useSpace = useSpace;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    public OSSDeveloper(String id, long useSpace) {
        this.id = id;
        this.useSpace = useSpace;
    }

    public OSSDeveloper(String id, long useSpace, String useSpaceStr) {
        this.id = id;
        this.useSpace = useSpace;
        this.useSpaceStr = useSpaceStr;
    }

    public OSSDeveloper() {
    }
}
