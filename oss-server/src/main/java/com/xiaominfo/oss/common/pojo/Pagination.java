/*
 * Copyright (C) 2018 xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.xiaominfo.oss.common.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/***
 *
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a>
 * 2018/06/16 22:33
 */
public class Pagination<T> implements Serializable {
    private int count=0;
    private int page_size=10;
    private int current_page=1;
    private List<T> data;
    private int total_page;
    /***
     * add by xiaoymin@foxmail.com 2016-8-5 12:33:48
     */
    private Integer code;
    private String message;
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public int getPage_size() {
        return page_size;
    }
    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }
    public int getCurrent_page() {
        return current_page;
    }
    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }
    public List<T> getData() {
        return data;
    }
    public void setData(List<T> data) {
        this.data = data;
    }
    public int getTotal_page() {
        return total_page;
    }
    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Pagination(int page_size, int current_page) {
        super();
        this.page_size = page_size;
        this.current_page = current_page;
        this.data=new ArrayList<>();
    }
    public Pagination() {
        super();
        //空数组
        //2016-8-10 15:41:59
        this.data=new ArrayList<>();
    }
}
