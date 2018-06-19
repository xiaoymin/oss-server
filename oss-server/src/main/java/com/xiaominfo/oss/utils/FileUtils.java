/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.xiaominfo.oss.utils;


import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;

import java.io.File;
import java.math.BigDecimal;
import java.util.regex.Pattern;

/***
 *
 * @since:oss-server 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/06/11 13:38
 */
public class FileUtils {

    public static final int KB_SIZE = 1024;
    public static final int MB_SIZE = 1024 * KB_SIZE;
    public static final int GB_SIZE = 1024 * MB_SIZE;



    /***
     * 获取文件大小
     * @param file
     * @return
     */
    public static long getFileSize(File file){
        long size=0L;
        if (file.exists()){
            if (!file.isDirectory()){
                size=file.length();
            }else{
                size+=getDirSize(file);
            }
        }
        return size;
    }


    public static long getDirSize(File file){
        long dirsize=0l;
        if (file!=null){
            if (file.exists()){
                if (file.isDirectory()){
                    File[] files=file.listFiles();
                    for (File fl:files){
                        if (fl.isDirectory()){
                            dirsize+=getDirSize(fl);
                        }else{
                            dirsize+=fl.length();
                        }
                    }
                }
            }
        }
        return dirsize;
    }

    /***
     * byte字节转换为字符串
     * @param fileBytes
     * @return
     */
    public static String byteToString(long fileBytes){
        StringBuffer byteStr=new StringBuffer();
        BigDecimal fullSize=new BigDecimal(fileBytes);
        //mb
        BigDecimal mbSize=new BigDecimal(MB_SIZE);
        float gbsize=fullSize.divide(new BigDecimal(GB_SIZE),2,BigDecimal.ROUND_HALF_UP).floatValue();
        if (gbsize>1){
            byteStr.append(gbsize).append("GB");
        }else{
            float dvsize=fullSize.divide(mbSize,2,BigDecimal.ROUND_HALF_UP).floatValue();
            if (dvsize>1){
                byteStr.append(dvsize).append("MB");
            }else{
                //kb显示
                BigDecimal kbSize=new BigDecimal(KB_SIZE);
                byteStr.append(fullSize.divide(kbSize,2,BigDecimal.ROUND_HALF_UP).floatValue()).append("KB");
            }
        }
        return byteStr.toString();
    }

    /**
     * 获取文件类型
     * @param file
     * @return
     */
    public static String getFileType(File file){
        String type="文件";
        if (file.isDirectory()){
            type="文件夹";
        }else{
            String fileName=file.getName();
            //判断是否有后缀
            if (StrUtil.isNotBlank(fileName)){
                if (fileName.contains(".")){
                    String suffix=fileName.substring(fileName.lastIndexOf(".")+1);
                    type=suffix.toUpperCase()+" 文件";
                }
            }
        }
        return type;
    }

    public static String getMediaType(File file){
        String mediaType="text";
        if (file.isDirectory()){
            mediaType="dir";
        }else{
            String fileName=file.getName();
            //判断是否有后缀
            if (StrUtil.isNotBlank(fileName)){
                if (fileName.contains(".")){
                    String suffix=fileName.substring(fileName.lastIndexOf(".")+1);
                    mediaType=suffix.toLowerCase();
                }
            }
        }
        return mediaType;
    }


    public static String transforSysSpec(String path){
        //获取操作系统
        String regex=".*?window.*";
        String nPath="";
        if (ReUtil.isMatch(Pattern.compile(regex,Pattern.CASE_INSENSITIVE),System.getProperty("os.name"))){
            //如果是windows
            nPath=path.replaceAll("\\\\","/");
        }else{
            nPath=path;
        }
        return nPath;
    }




    public static void main(String[] args) {
        File file=new File("D:\\无标题.xls");
        System.out.println(getFileType(file));
        System.out.println(getFileSize(file));
        System.out.println(byteToString(getDirSize(file)));
        System.out.println(System.getProperty("os.name"));
    }



}
