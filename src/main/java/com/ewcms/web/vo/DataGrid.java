/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.web.vo;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author wangwei
 */
public class DataGrid {

    private String cacheKey;
    private Integer total;
    private List<?> rows;
    private List<?> footer;
    private List<String> errors;

    public DataGrid(Integer total,List<?> rows){
        this(null,total,rows);
    }
    
    public DataGrid(String cacheKey,Integer total,List<?> rows){
        this(cacheKey,total,rows,null);
    }
    
    public DataGrid(String cacheKey,Integer total,List<?> rows,List<?> footer){
        this.cacheKey = cacheKey;
        this.total = total;
        this.rows = rows;
        this.footer = footer;
    }

    public DataGrid(List<String> errors){
        this.total =0;
        rows =Collections.emptyList();
        this.errors = errors;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public List<?> getFooter() {
        return footer;
    }

    public void setFooter(List<?> footer) {
        this.footer = footer;
    }    
}
