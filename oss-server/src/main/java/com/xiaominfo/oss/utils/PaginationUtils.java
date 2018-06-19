/*
 * Copyright (C) 2017 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.xiaominfo.oss.utils;

import com.xiaominfo.oss.common.pojo.Pagination;
import com.xiaominfo.oss.exception.ErrorConstant;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/***
 *
 * @since:oss-server 1.0
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a> 
 * 2018/06/17 9:10
 */
public class PaginationUtils {


    /***
     * 获取分页对象
     * @param data
     * @param count
     * @param current_page
     * @param page_size
     * @param <T>
     * @return
     */
    public static <T> Pagination<T> transfor(List<T> data, Integer count, Integer current_page, Integer page_size){
        Pagination<T> pagination=new Pagination<>();
        pagination.setCurrent_page(current_page);
        pagination.setCount(count);
        pagination.setData(data);
        pagination.setPage_size(page_size);
        //计算分页
        int totalPageNum = (count  +  page_size  - 1) / page_size;
        pagination.setTotal_page(totalPageNum);
        pagination.setCode(ErrorConstant.SUCCESS);
        return pagination;
    }

    /***
     * 获取mybatis分页对象
     * @param current_page
     * @param page_size
     * @return
     */
    public static RowBounds getBounds(Integer current_page,Integer page_size){
        int offset=(current_page-1)*page_size;
        RowBounds rowBounds=new RowBounds(offset,page_size);
        return rowBounds;
    }
}
