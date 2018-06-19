/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.xiaominfo.oss.utils;

import java.util.Random;

/***
 *
 * @since:oss-server 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018-6-17 17:50:38
 */
public class RandomUtils {

    private static String[] ens=new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    private static int[] arr=new int[]{0,1,2,3,4,5,6,7,8,9};


    public static String random(int len){
        StringBuffer str=new StringBuffer();
        for (int i=0;i<len;i++){
            int x=new Random().nextInt(ens.length);
            if (x%2==0){
                //字母
                str.append(ens[x].toLowerCase());
            }else{
                //数字
                x=new Random().nextInt(arr.length);
                str.append(arr[x]);
            }
        }
        return str.toString();
    }
    public static void main(String[] args) {

        System.out.println(random(6));

    }
}
