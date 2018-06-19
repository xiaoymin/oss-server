/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.xiaominfo.oss.api;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.google.common.collect.Lists;
import com.xiaominfo.oss.domain.FileInfo;
import com.xiaominfo.oss.common.pojo.RestfulMessage;
import com.xiaominfo.oss.utils.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

/***
 * 列表展现oss的上传文件信息
 * @since:oss-server 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/06/11 11:29
 */
@Controller
@RequestMapping("/oss/list")
public class OSSApplication {

    private Log log= LogFactory.get();

    @Value(value = "${spring.application.name}")
    private String appName;

    @Value(value = "${material.root}")
    private String root;

    @Value(value = "${material.invokingRoot}")
    private String invokeUri;

    @GetMapping(value = "/")
    public String index(Model model, @RequestParam(value = "dir",required = false) String dir, HttpServletRequest request){
        log.info("dir:{}",dir);
        List<FileInfo> fileInfos= Lists.newArrayList();
        File rootFile=new File(root);
        log.info(rootFile.getAbsolutePath());
        if (StrUtil.isBlank(dir)){
            //如果是空的话,直接获取当前根目录
             fileInfos.addAll(getFileInfos(rootFile,rootFile));
        }else{
            //dir不为空
            String dirPath=rootFile.getAbsolutePath()+dir;
            File dirFile=new File(dirPath);
            fileInfos.addAll(getFileInfos(dirFile,rootFile));
        }
        model.addAttribute("list",fileInfos);
        if (StrUtil.isBlank(appName)){
            appName="OSS Server Online Preview System";
        }
        Object view=request.getSession().getAttribute("VIEW");
        if (view==null){
            view="list";
        }
        model.addAttribute("view",view);
        model.addAttribute("appName",appName);
        model.addAttribute("dir",dir);
        return "list";
    }


    @GetMapping("/toogleView")
    @ResponseBody
    public RestfulMessage toggleView(@RequestParam(value = "view",required = false) String view, HttpServletRequest request){
        RestfulMessage restfulMessage=new RestfulMessage();
        request.getSession().setAttribute("VIEW",view);
        return  restfulMessage;
    }
    private String transSysSpec(String uri,String path){
        StringBuffer str=new StringBuffer();
        //获取操作系统
        String regex=".*?window.*";
        String nPath="";
        if (ReUtil.isMatch(Pattern.compile(regex,Pattern.CASE_INSENSITIVE),System.getProperty("os.name"))){
            //如果是windows
            nPath=path.replaceAll("\\\\","/");
        }else{
            nPath=path;
        }
        str.append(uri);
        //判断uri是否是/结尾
        if (uri.endsWith("/")){
            if (nPath.startsWith("/")){
                str.append(nPath.substring(1));
            }else{
                str.append(nPath);
            }
        }else{
            //非/结尾
            if (!nPath.startsWith("/")){
                str.append("/");
            }
            str.append(nPath);
        }
        return str.toString();
    }




    private List<FileInfo> getFileInfos(File dirFile,File root){
        int start=root.getAbsolutePath().length();
        List<FileInfo> fileInfos= Lists.newArrayList();
        File[] files=dirFile.listFiles();
        if (ArrayUtil.isEmpty(files)){
            return fileInfos;
        }
        for (File file:files){
            FileInfo fileInfo=new FileInfo(invokeUri+file.getName(),file.getName()
                    ,DateUtil.date(file.lastModified()).toString(DatePattern.NORM_DATETIME_PATTERN)
                    , FileUtils.byteToString(FileUtils.getFileSize(file)));
            fileInfo.setType(FileUtils.getFileType(file));
            fileInfo.setMediaType(FileUtils.getMediaType(file));
            if (file.isDirectory()){
                String dirPath=dirFile.getAbsolutePath().substring(start);
                dirPath=FileUtils.transforSysSpec(dirPath);
                StringBuffer dir=new StringBuffer();
                if (!dirPath.startsWith("/")){
                    dir.append("/");
                }
                dir.append(dirPath);
                if (StrUtil.isNotEmpty(dirPath)){
                    if (!dirPath.endsWith("/")){
                        dir.append("/");
                    }
                }
                dir.append(file.getName());
                String url="/oss/list/?dir="+dir.toString();
                fileInfo.setUrl(url);
            }else{
                String path=file.getAbsolutePath();
                //截取根路径
                String url=transSysSpec(invokeUri,path.substring(start));
                log.info("url:{}",url);
                fileInfo.setUrl(url);
            }
            fileInfos.add(fileInfo);
        }
        return fileInfos;
    }
}
