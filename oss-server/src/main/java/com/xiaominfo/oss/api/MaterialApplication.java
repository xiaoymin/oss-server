/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.xiaominfo.oss.api;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.xiaominfo.oss.common.annotation.NotLogin;
import com.xiaominfo.oss.domain.FileBinaryRequest;
import com.xiaominfo.oss.domain.FileBinaryResponse;
import com.xiaominfo.oss.common.pojo.RestfulMessage;
import com.xiaominfo.oss.exception.AssemblerException;
import com.xiaominfo.oss.exception.ErrorCable;
import com.xiaominfo.oss.exception.ErrorConstant;
import com.xiaominfo.oss.module.model.OSSAppInfo;
import com.xiaominfo.oss.module.model.OSSDeveloper;
import com.xiaominfo.oss.module.model.OSSInformation;
import com.xiaominfo.oss.service.MaterialService;
import com.xiaominfo.oss.service.OSSAppInfoService;
import com.xiaominfo.oss.service.OSSDeveloperService;
import com.xiaominfo.oss.service.OSSInformationService;
import org.apache.commons.codec.net.URLCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.List;

/***
 *
 * @since:oss-server 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/05/30 10:47
 */

@RestController
@RequestMapping("/oss/material")
public class MaterialApplication extends RootApis{

    private Log log= LogFactory.get();

    @Autowired
    MaterialService materialService;

    @Autowired
    OSSDeveloperService ossDeveloperService;

    @Autowired
    OSSAppInfoService ossAppInfoService;

    @Autowired
    OSSInformationService ossInformationService;


    @PostMapping(value = "/createDir")
    public RestfulMessage createDirectory(@RequestParam(value = "dir",required = false) String dir,
                                          @RequestParam(value = "pro") String pro){
        RestfulMessage restfulMessage=new RestfulMessage();
        try{
            String root=ossInformationService.queryOne().getRoot();
            StringBuffer dirStr=new StringBuffer();
            dirStr.append(root).append(dir);
            validateProjectName(pro);
            dirStr.append("/").append(pro);
            File file=new File(dirStr.toString());
            createDirectoryQuietly(file);
            successResultCode(restfulMessage);
        }catch (Exception e){
            restfulMessage=wrapperException(e);
        }
        return restfulMessage;
    }


    /***
     * byte 字节码上传文件
     * 文件夹名称：只能是数组+字母+下划线组成
     * 传输文件格式：
     * {
     *     "project":"",
     *     "appid":"",
     *     "appsecret":"",
     *     "files":[
     *     {
     *         "original_name":"test.png",
     *         "file":"F://test.png",
     *         "media_type":"png"
     *     }
     *     ]
     * }
     * @param entity
     * @return
     */
    @NotLogin
    @PostMapping(value = "/uploadByBinary",produces = "application/json;charset=UTF-8")
    public RestfulMessage upload(HttpEntity<String> entity){
        RestfulMessage restfulMessage=new RestfulMessage();
        try{
            log.info("/uploadByBinary...");
            String bodyStr=entity.getBody();
            String decodeBodyStr = new String(URLCodec.decodeUrl(bodyStr.getBytes("UTF-8")), "UTF-8");
            StringReader strReader = new StringReader(decodeBodyStr);
            JsonReader reader = new JsonReader(strReader);
            reader.setLenient(true);
            JsonElement je = new JsonParser().parse(reader);
            if (je==null || je.isJsonNull() || !je.isJsonObject() ||("null".equals(je.toString())) || (je.toString() == null)) {
                throw new AssemblerException(new ErrorCable(ErrorConstant.REQUEST_PARAMS_NOT_VALID,"not any data"));
            }
            //获取project
            JsonObject req=je.getAsJsonObject();

            //获取appid
            JsonElement appIdEle=req.get("appid");
            JsonElement appSecretEle=req.get("appsecret");
            assertJsonNotEmpty(appIdEle,"appid can't be empty ");
            assertJsonNotEmpty(appSecretEle,"appsecret can't be empty ");
            JsonElement projectEle=req.get("project");
            JsonElement files=req.get("files");
            assertJsonNotEmpty(projectEle,"project name can't be empty!!!");
            assertJsonNotEmpty(files,"files can't be empty!!!");
            if (!files.isJsonArray()){
                throw new AssemblerException(new ErrorCable(ErrorConstant.REQUEST_PARAMS_NOT_VALID,"files must be array"));
            }
            OSSDeveloper ossDeveloper=ossDeveloperService.queryByAppid(appIdEle.getAsString(),appSecretEle.getAsString());
            assertArgumentNotEmpty(ossDeveloper,"appid or appsecret is invalid");
            String projectFilePathName=projectEle.getAsString();
            validateProjectName(projectFilePathName);
            //判断文件夹code是否相等
            List<OSSAppInfo> ossAppInfos=ossAppInfoService.queryByDevIds(ossDeveloper.getId());
            if (ossAppInfos==null||ossAppInfos.size()==0){
                throw new AssemblerException(new ErrorCable(ErrorConstant.REQUEST_PARAMS_NOT_VALID,"You do not have permission to upload files"));
            }
            OSSAppInfo ossApp=null;
            boolean flag=false;
            for (OSSAppInfo ossAppInfo:ossAppInfos){
                if (StrUtil.equalsIgnoreCase(ossAppInfo.getCode(),projectFilePathName)){
                    flag=true;
                    ossApp=ossAppInfo;
                    break;
                }
            }
            if (!flag){
                throw new AssemblerException(new ErrorCable(ErrorConstant.REQUEST_PARAMS_NOT_VALID,"You do not have permission to upload files"));
            }
            OSSInformation ossInformation=ossInformationService.queryOne();
            String root=ossInformation.getRoot();
            //验证文件夹规则,不能包含特殊字符
            File file=new File(root);
            createDirectoryQuietly(file);
            File projectFile=new File(file.getAbsolutePath()+File.separator+projectFilePathName);
            if (!projectFile.exists()){
                throw new AssemblerException(new ErrorCable(ErrorConstant.AUTHENTICATION_FAILED,"You do not have operating authority for this directory "+projectFilePathName+", or the directory was not created"));
            }
            createDirectoryQuietly(projectFile);
            Type typeOf = new TypeToken<List<FileBinaryRequest>>() {}.getType();
            List<FileBinaryRequest> materials = new Gson().fromJson(files, typeOf);
            for (FileBinaryRequest fileBinaryRequest:materials){
                assertArgumentNotEmpty(fileBinaryRequest.getMediaType(),"media_type is requried ...");
                assertArgumentNotEmpty(fileBinaryRequest.getFile(),"Not found file bytes, but it is requried ...");
                //fileBinaryRequest.setFileBytes(material.getFileBytes().replaceAll(" ", "+"));
                fileBinaryRequest.setFile(fileBinaryRequest.getFile().replaceAll(" ","+"));
            }
            List<FileBinaryResponse> fileBinaryResponseList=materialService.saveAndStore(ossInformation,ossDeveloper,ossApp,projectFile,materials);
            restfulMessage.setData(fileBinaryResponseList);
            successResultCode(restfulMessage);
        }catch (Exception e){
            restfulMessage=wrapperException(e);
        }
        return restfulMessage;
    }


    /***
     * 表单提交,上传
     * @param project
     * @param files
     * @return
     */
    @NotLogin
    @PostMapping("{project}/uploadMaterial")
    public RestfulMessage uploadMaterial(@PathVariable(value = "project") String project,
                                         @RequestParam(value = "module",required = false) String module,
                                         @RequestParam(value = "appid",required = false) String appid,
                                         @RequestParam(value = "appsecret",required = false) String appsecret,@RequestParam(value="file") MultipartFile[] files){
        RestfulMessage restfulMessage=new RestfulMessage();
        try{
            assertArgumentNotEmpty(project,"project can't be empty!!!");
            assertArgumentNotEmpty(appid,"appid can't be empty ");
            assertArgumentNotEmpty(appsecret,"appsecret can't be empty ");
            OSSDeveloper ossDeveloper=ossDeveloperService.queryByAppid(appid,appsecret);
            assertArgumentNotEmpty(ossDeveloper,"appid or appsecret is invalid");
            //判断文件夹code是否相等
            List<OSSAppInfo> ossAppInfos=ossAppInfoService.queryByDevIds(ossDeveloper.getId());
            if (ossAppInfos==null||ossAppInfos.size()==0){
                throw new AssemblerException(new ErrorCable(ErrorConstant.REQUEST_PARAMS_NOT_VALID,"You do not have permission to upload files"));
            }
            boolean flag=false;
            OSSAppInfo ossApp=null;
            for (OSSAppInfo ossAppInfo:ossAppInfos){
                if (StrUtil.equalsIgnoreCase(ossAppInfo.getCode(),project)){
                    flag=true;
                    ossApp=ossAppInfo;
                    break;
                }
            }
            if (!flag){
                throw new AssemblerException(new ErrorCable(ErrorConstant.REQUEST_PARAMS_NOT_VALID,"You do not have permission to upload files"));
            }
            validateProjectName(project);
            OSSInformation ossInformation=ossInformationService.queryOne();
            String root=ossInformation.getRoot();
            //验证文件夹规则,不能包含特殊字符
            File file=new File(root);
            createDirectoryQuietly(file);
            StringBuffer path=new StringBuffer();
            path.append(file.getAbsolutePath());
            path.append(File.separator);
            path.append(project);
            if (StrUtil.isNotEmpty(module)){
                if (!module.startsWith("/")){
                    path.append("/");
                }
                path.append(module);
            }
            log.info("path:{}",path);
            File projectFile=new File(path.toString());
            /*if (!projectFile.exists()){
                throw new AssemblerException(new ErrorCable(ErrorConstant.AUTHENTICATION_FAILED,"You do not have operating authority for this directory "+project+", or the directory was not created"));
            }*/
            createDirectoryQuietly(projectFile);
            List<FileBinaryResponse> fileBinaryResponseList=materialService.saveAndStore(ossInformation,ossDeveloper,ossApp,projectFile,files);
            restfulMessage.setData(fileBinaryResponseList);
            successResultCode(restfulMessage);
        }catch (Exception e){
            restfulMessage=wrapperException(e);
        }
        return restfulMessage;
    }


    @PostMapping("/uploadBySys")
    public RestfulMessage uploadSys(@RequestParam(value = "dir",required = false) String dir,@RequestParam(value="file") MultipartFile[] files){
        RestfulMessage restfulMessage=new RestfulMessage();
        try{
            OSSInformation ossInformation=ossInformationService.queryOne();
            String root=ossInformation.getRoot();
            //验证文件夹规则,不能包含特殊字符
            File file=new File(root);
            createDirectoryQuietly(file);
            StringBuffer path=new StringBuffer();
            path.append(file.getAbsolutePath());
            if (StrUtil.isNotEmpty(dir)){
                if (!dir.startsWith("/")){
                    path.append(File.separator);
                }
                path.append(dir);
            }
            log.info("path:{}",path);
            File projectFile=new File(path.toString());
            createDirectoryQuietly(projectFile);
            List<FileBinaryResponse> fileBinaryResponseList=materialService.saveAndStoreBySys(ossInformation,projectFile,files);
            restfulMessage.setData(fileBinaryResponseList);
            successResultCode(restfulMessage);
        }catch (Exception e){
            restfulMessage=wrapperException(e);
        }
        return restfulMessage;
    }
}
