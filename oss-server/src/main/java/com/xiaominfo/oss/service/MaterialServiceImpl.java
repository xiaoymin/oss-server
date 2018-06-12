/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.xiaominfo.oss.service;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.google.common.collect.Lists;
import com.xiaominfo.oss.api.RootApis;
import com.xiaominfo.oss.domain.FileBinaryRequest;
import com.xiaominfo.oss.domain.FileBinaryResponse;
import com.xiaominfo.oss.domain.FileStream;
import com.xiaominfo.oss.domain.RemoteResult;
import com.xiaominfo.oss.utils.IdGen;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

/***
 *
 * @since:oss-server 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/05/30 11:00
 */
@Service
public class MaterialServiceImpl extends RootApis implements MaterialService {

    @Autowired
    MaterialConfig materialConfig;

    private Log log= LogFactory.get();

    @Value(value = "${material.root}")
    private String root;


    @Override
    public RemoteResult saveFile(FileStream paramFileStream) throws  IOException {
        
        return null;
    }

    @Override
    public List<FileBinaryResponse> saveAndStore(File projectDirectory, List<FileBinaryRequest> fileBinaryRequests) throws IOException {
        File rootFile=new File(root);
        int start=rootFile.getAbsolutePath().length();
        List<FileBinaryResponse> fileBinaryResponses= Lists.newArrayList();
        String currentTimeString= DateTime.now().toString("yyyyMMddHHmmss");
        //按月目录
        final String monthPath = currentTimeString.substring(0, 6);
        //按日目录,如W020151111
        final String dayPath = currentTimeString.substring(6, 8);
        final String fileDirectory=projectDirectory.getAbsolutePath()+"/"+monthPath+"/"+dayPath;
        log.info("directory:{}",fileDirectory);
        File saveFileDirectory=new File(fileDirectory);
        createDirectoryQuietly(saveFileDirectory);
        for (FileBinaryRequest fileBinaryRequest:fileBinaryRequests){
            byte[] bs = Base64.decodeBase64(fileBinaryRequest.getFile());
            //文件id
            String uuid=IdGen.getUUID();
            //生成uuid文件名称
            String fileName= uuid+"."+fileBinaryRequest.getMediaType();
            //静态资源存储路径
            StringBuffer storePathBuffer=new StringBuffer();
            String pre=projectDirectory.getAbsolutePath().substring(start);
            pre= com.xiaominfo.oss.utils.FileUtils.transforSysSpec(pre);
            if (!pre.startsWith("/")){
                storePathBuffer.append("/");
            }
            storePathBuffer.append(pre).append("/")
                    .append(monthPath).append("/").append(dayPath).append("/").append(fileName);
            //判断最后一位是否是/
            String invokePath=materialConfig.getInvokePath();
            if (materialConfig.getInvokePath().endsWith("/")){
                invokePath= materialConfig.getInvokePath().substring(0,invokePath.lastIndexOf("/"));
            }
            String url=invokePath+storePathBuffer.toString();
            File targetFile=new File(saveFileDirectory.getAbsolutePath()+"/"+fileName);
            //输出文件
            FileUtils.writeByteArrayToFile(targetFile,bs);
            //不可执行,防止恶意脚本攻击system
            targetFile.setExecutable(false);
            FileBinaryResponse fileBinaryResponse=new FileBinaryResponse(uuid,url,storePathBuffer.toString());
            fileBinaryResponses.add(fileBinaryResponse);
        }
        return fileBinaryResponses;
    }

    @Override
    public List<FileBinaryResponse> saveAndStore(File projectDirectory, MultipartFile[] multipartFiles) throws IOException {
        File rootFile=new File(root);
        int start=rootFile.getAbsolutePath().length();
        List<FileBinaryResponse> fileBinaryResponses= Lists.newArrayList();
        String currentTimeString= DateTime.now().toString("yyyyMMddHHmmss");
        //按月目录
        final String monthPath = currentTimeString.substring(0, 6);
        //按日目录,如W020151111
        final String dayPath = currentTimeString.substring(6, 8);
        final String fileDirectory=projectDirectory.getAbsolutePath()+"/"+monthPath+"/"+dayPath;
        log.info("directory:{}",fileDirectory);
        File saveFileDirectory=new File(fileDirectory);
        createDirectoryQuietly(saveFileDirectory);
        for (MultipartFile multpFile:multipartFiles){
            //文件id
            String uuid=IdGen.getUUID();
            String fileName=uuid;
            String mediaType = "";
            if (multpFile.getOriginalFilename().lastIndexOf(".")>0){
                mediaType=multpFile.getOriginalFilename().substring(multpFile.getOriginalFilename().lastIndexOf(".")+1);
                fileName=fileName+"."+mediaType;
            }
            //静态资源存储路径
            StringBuffer storePathBuffer=new StringBuffer();
            String pre=projectDirectory.getAbsolutePath().substring(start);
            pre= com.xiaominfo.oss.utils.FileUtils.transforSysSpec(pre);
            if (!pre.startsWith("/")){
                storePathBuffer.append("/");
            }
            storePathBuffer.append(pre).append("/")
                    .append(monthPath).append("/").append(dayPath).append("/").append(fileName);
            //判断最后一位是否是/
            String invokePath=materialConfig.getInvokePath();
            if (materialConfig.getInvokePath().endsWith("/")){
                invokePath= materialConfig.getInvokePath().substring(0,invokePath.lastIndexOf("/"));
            }
            String url=invokePath+storePathBuffer.toString();
            File targetFile=new File(saveFileDirectory.getAbsolutePath()+"/"+fileName);
            FileOutputStream fos=new FileOutputStream(targetFile);
            InputStream ins=multpFile.getInputStream();
            byte[] byts=new byte[1024*1024];
            int len=-1;
            while ((len=ins.read(byts))!=-1){
                fos.write(byts,0,len);
            }
            //关闭输入输出流
            IOUtils.closeQuietly(fos);
            IOUtils.closeQuietly(ins);
            //不可执行,防止恶意脚本攻击system
            targetFile.setExecutable(false);
            FileBinaryResponse fileBinaryResponse=new FileBinaryResponse(uuid,url,storePathBuffer.toString());
            fileBinaryResponses.add(fileBinaryResponse);
        }
        return fileBinaryResponses;
    }

    @Override
    public List<FileBinaryResponse> saveAndStoreBySys(File projectDirectory, MultipartFile[] multipartFiles) throws IOException {
        File rootFile=new File(root);
        int start=rootFile.getAbsolutePath().length();
        List<FileBinaryResponse> fileBinaryResponses= Lists.newArrayList();
        //按日目录,如W020151111
        final String fileDirectory=projectDirectory.getAbsolutePath();
        log.info("directory:{}",fileDirectory);
        File saveFileDirectory=new File(fileDirectory);
        createDirectoryQuietly(saveFileDirectory);
        for (MultipartFile multpFile:multipartFiles){
            //文件id
            String uuid=IdGen.getUUID();
            String fileName=uuid;
            String mediaType = "";
            if (multpFile.getOriginalFilename().lastIndexOf(".")>0){
                mediaType=multpFile.getOriginalFilename().substring(multpFile.getOriginalFilename().lastIndexOf(".")+1);
                fileName=fileName+"."+mediaType;
            }
            //静态资源存储路径
            StringBuffer storePathBuffer=new StringBuffer();
            String pre=projectDirectory.getAbsolutePath().substring(start);
            pre= com.xiaominfo.oss.utils.FileUtils.transforSysSpec(pre);
            if (!pre.startsWith("/")){
                storePathBuffer.append("/");
            }
            storePathBuffer.append(pre).append("/").append(fileName);
            //判断最后一位是否是/
            String invokePath=materialConfig.getInvokePath();
            if (materialConfig.getInvokePath().endsWith("/")){
                invokePath= materialConfig.getInvokePath().substring(0,invokePath.lastIndexOf("/"));
            }
            String url=invokePath+storePathBuffer.toString();
            File targetFile=new File(saveFileDirectory.getAbsolutePath()+"/"+fileName);
            FileOutputStream fos=new FileOutputStream(targetFile);
            InputStream ins=multpFile.getInputStream();
            byte[] byts=new byte[1024*1024];
            int len=-1;
            while ((len=ins.read(byts))!=-1){
                fos.write(byts,0,len);
            }
            //关闭输入输出流
            IOUtils.closeQuietly(fos);
            IOUtils.closeQuietly(ins);
            //不可执行,防止恶意脚本攻击system
            targetFile.setExecutable(false);
            FileBinaryResponse fileBinaryResponse=new FileBinaryResponse(uuid,url,storePathBuffer.toString());
            fileBinaryResponses.add(fileBinaryResponse);
        }
        return fileBinaryResponses;
    }
}
