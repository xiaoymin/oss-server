/*
 * Copyright (C) 2017 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.xiaominfo.oss.common.pojo;

import javax.servlet.http.HttpServletRequest;

/***
 */
public class ThreadLocalHolder {
	
	//appid
	private static final ThreadLocal<HttpServletRequest> th_request=new ThreadLocal<HttpServletRequest>();


	
	public static HttpServletRequest getRequest(){
		return th_request.get();
	}
	
	public static void setRequest(HttpServletRequest request){
		th_request.set(request);
	}
	


}
