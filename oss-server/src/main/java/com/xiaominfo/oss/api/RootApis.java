/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.xiaominfo.oss.api;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.xiaominfo.oss.common.pojo.RestfulMessage;
import com.xiaominfo.oss.exception.AssemblerException;
import com.xiaominfo.oss.exception.ErrorCable;
import com.xiaominfo.oss.exception.ErrorConstant;
import org.joda.time.DateTime;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.StringReader;

/***
 *
 * @since:oss-server 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/05/30 11:30
 */
public class RootApis {

    private static final Log log = LogFactory.get();


    protected void assertNotEmpty(String str,String msg){
        if (StrUtil.isEmpty(str)){
            throw new AssemblerException(new ErrorCable(ErrorConstant.REQUEST_PARAMS_NOT_VALID,"request params not valid..."));
        }
    }

    protected void assertArgumentNotEmpty(String arguments,String msg){
        if (StrUtil.isEmpty(arguments)){
            throw new AssemblerException(new ErrorCable(ErrorConstant.REQUEST_PARAMS_NOT_VALID,msg));
        }
    }
    protected void assertArgumentNotEmpty(Object arguments,String msg){
        if (arguments==null){
            throw new AssemblerException(new ErrorCable(ErrorConstant.REQUEST_PARAMS_NOT_VALID,msg));
        }
    }
    protected void assertArgFalse(boolean aBoolean, String aMessage) {
        if (aBoolean) {
            throw new AssemblerException(new ErrorCable(ErrorConstant.REQUEST_PARAMS_NOT_VALID, aMessage));
        }
    }

    protected void isTrue(boolean expression, String msg) {
        if (false == expression) {
            throw new AssemblerException(new ErrorCable(ErrorConstant.REQUEST_PARAMS_NOT_VALID, msg));
        }
    }

    protected void assertJsonNotEmpty(JsonElement jsonElement, String msg){
        if (jsonElement==null||jsonElement.isJsonNull()){
            throw new AssemblerException(new ErrorCable(ErrorConstant.REQUEST_PARAMS_NOT_VALID,msg));
        }
    }

    protected RestfulMessage wrapperException(Exception e){
        log.error(e);
        RestfulMessage restfulMessage=new RestfulMessage();
        if (e!=null){
            String msg=e.getMessage();
            if (StrUtil.isEmpty(msg)){
                restfulMessage.setCode(ErrorConstant.INTERNAL_SERVER_ERROR);
                restfulMessage.setMessage(msg);
            }else if(msg.contains("|")){
                String[] em= StrUtil.split(msg,"|");
                restfulMessage.setCode(Integer.parseInt(em[0]));
                restfulMessage.setMessage(em[1]);
            }else {
                restfulMessage.setCode(ErrorConstant.INTERNAL_SERVER_ERROR);
                restfulMessage.setMessage(e.getMessage());
            }
        }else{
            restfulMessage.setCode(ErrorConstant.INTERNAL_SERVER_ERROR);
            restfulMessage.setMessage("未知错误");
        }
        return restfulMessage;
    }

    /***
     * 验证文件夹名称
     * @param projectName
     */
    protected void validateProjectName(String projectName){
        if (StrUtil.isBlank(projectName)){
            throw new AssemblerException(new ErrorCable(ErrorConstant.REQUEST_PARAMS_NOT_VALID,"project name can't be empty!"));
        }
        String regex="^.*?(\\\\|\\/|\\:|\\*|\\?|\\？|\\\"|\\“|\\”|\\>|\\<|\\|).*";
        if (ReUtil.isMatch(regex,projectName)){
            throw new AssemblerException(new ErrorCable(ErrorConstant.REQUEST_PARAMS_NOT_VALID,"The name of the file can not contain any of the following characters: / /: *?<>|"));
        }
        //不能包含\s字符
        //不能包含中文
        regex=".*?[\\u4e00-\\u9fa5\\s].*";
        if (ReUtil.isMatch(regex,projectName)){
            throw new AssemblerException(new ErrorCable(ErrorConstant.REQUEST_PARAMS_NOT_VALID,"The name of the file does not contain Chinese and space"));
        }


    }

    /***
     * trace异常信息
     * @param e
     * @return
     */
    protected String traceException(Exception e){
        StackTraceElement[] stackTraceElements=e.getStackTrace();
        StringBuffer errs=new StringBuffer();
        for (StackTraceElement stackTraceElement:stackTraceElements){
            errs.append(stackTraceElement.toString());
        }
        return errs.toString();
    }

    protected void successResultCode(RestfulMessage restfulMessage){
        restfulMessage.setCode(ErrorConstant.SUCCESS);
        restfulMessage.setMessage("Success");
    }

    protected JsonObject paramJson(HttpEntity<String> requestEntity){
        String decodeBodyStr = acceptHttpRequest(requestEntity);
        StringReader strReader = new StringReader(decodeBodyStr);
        JsonReader reader = new JsonReader(strReader);
        reader.setLenient(true);
        return new JsonParser().parse(reader).getAsJsonObject();
    }
    protected JsonElement paramJsonElement(HttpEntity<String> requestEntity){
        String decodeBodyStr = acceptHttpRequest(requestEntity);
        StringReader strReader = new StringReader(decodeBodyStr);
        JsonReader reader = new JsonReader(strReader);
        reader.setLenient(true);
        return new JsonParser().parse(reader);
    }

    /***
     * 创建文件夹
     * @param file
     */
    protected void createDirectoryQuietly(File file){
        if (file!=null){
            if (!file.exists()){
                if (!file.mkdirs()){
                    throw new RuntimeException(file.getName()+" is invalid,can't be create directory");
                }
            }
        }
    }

    /**
     * 接收HttpRequest内容
     *
     * @param httpEntity
     * @return
     */
    protected String acceptHttpRequest(HttpEntity<String> httpEntity) {
        String body = httpEntity.getBody();
        postParamHandler(body);
        HttpHeaders httpHeaders = httpEntity.getHeaders();
        MediaType mt = httpHeaders.getContentType();
        assertArgumentNotEmpty(mt, "Not found Content-Type peoperties ...");
        String mediaType = mt.getType() + "/" + mt.getSubtype();
        String bodyStr = body;
        postParamHandler(bodyStr);
        String decodeBodyStr = null;
        if ("application/json".equals(mediaType)) {
            decodeBodyStr = bodyStr;
        } else {
            assertArgFalse(true, "Only support \"application/json\" mediatype, so you must setting Content-Type -> mediatype : application/json in POST header ...");
        }
        return decodeBodyStr;
    }
    protected void postParamHandler(String bodyStr) {
        assertArgumentNotEmpty(bodyStr, "Request params is empty ...");
    }


    /**
     * 产生日志
     * @param msg
     * @return
     */
    protected String log(String msg){
        return DateTime.now().toString("yyyy-MM-dd HH:mm:ss")+">>"+msg+"\r\n";
    }

}
