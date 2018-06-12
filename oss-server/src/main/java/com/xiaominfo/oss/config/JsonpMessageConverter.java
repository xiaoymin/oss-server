/*
 * Copyright (C) 2016 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */
package com.xiaominfo.oss.config;

import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

/***
 * GsonJsonpHttpMessageConverter
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a>
 * 2018-5-31 12:48:07
 */
public class JsonpMessageConverter extends GsonHttpMessageConverter {
	private String jsonPrefix;
	private String callbackParam="callback";
	public JsonpMessageConverter() {
		super();
	}
	@Override
	protected Object readInternal(Type resolvedType, Reader reader) throws Exception {
		return super.readInternal(resolvedType, reader);
	}
	@Override
	protected void writeInternal(Object o, @Nullable Type type, Writer writer) throws Exception {
		String callback=null;
		RequestAttributes reqAttrs = RequestContextHolder.currentRequestAttributes();
		//Gson gson=new Gson();
		//解决-->gson实体转json时当字段值为空时，json串中就不存在该属于，
		Gson gson=new GsonBuilder().serializeNulls().create();
		try {
			if(reqAttrs instanceof ServletRequestAttributes){
				callback=((ServletRequestAttributes)reqAttrs).getRequest().getParameter(callbackParam);
			}
			if (StrUtil.isNotBlank(callback)) {
				writer.append(callback+"(");
			}
			if (this.jsonPrefix != null) {
				writer.append(this.jsonPrefix);
			}
			if (type != null) {
				if (type.getTypeName().contains("String")){
					//如果是string，不序列化
					writer.append(o.toString());
				}else{
					gson.toJson(o, type, writer);
				}
			}
			else {
				gson.toJson(o, writer);
			}
			if (StrUtil.isNotBlank(callback)) {
				writer.append(")");
			}
			//can't close writer..
			//writer.close();
		}
		catch (JsonIOException ex) {
			throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
		}
	}

	public String getCallbackParam() {
		return callbackParam;
	}

	public void setCallbackParam(String callbackParam) {
		this.callbackParam = callbackParam;
	}

 

}
