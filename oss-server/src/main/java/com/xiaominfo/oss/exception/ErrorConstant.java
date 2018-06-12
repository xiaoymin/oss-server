/*
 * Copyright (C) 2017 Zhejiang LISHI Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.lishicloud.com.
 */
package com.xiaominfo.oss.exception;

/**
 * Created by yuanwai (<a href="mailto:lxdhzzj@gmail.com">lxdhzzj@gmail.com</a>)
 * Create Time: 2015/7/8 18:06
 * Description:
 */
public interface ErrorConstant {

    Integer INTERNAL_SERVER_ERROR=8500;//服务器内部错误。
    Integer METHOD_NOT_ALLOWED=8401;//不允许的操作（指定了错误的HTTP方法或API）。
    Integer REQUEST_PARAMS_NOT_VALID=8402;//请求参数非法。
    Integer AUTHENTICATION_FAILED=8403;//权限校验错误 。
    Integer NOT_FOUND_RESOURCE=8404;//没有找到资源
    Integer DATA_REQUIRED_NOT_FOUNT=8405;//请求数据不存在。
    Integer REQUEST_TIME_EXPIRES_TIMEOUT=8406;//请求已超时。
    Integer DUPLICATION_OPERATION=8410;//重复操作。
    Integer CAN_NOT_DELETE=8407;//不允许删除
    Integer SUCCESS=8200;//成功
    Integer NOT_LOGIN=8510;//未登录
}