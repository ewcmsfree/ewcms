/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JTCT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  http://www.teachernet.com
 */

package com.ewcms.web.vo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wangwei
 */
public class DataGrid {

    private Integer total;
    private Object rows;
    private List<String> errors;

    public DataGrid(Integer total,Object rows){
        this.total = total;
        this.rows = rows;
    }

    public DataGrid(List<String> errors){
        this.total =0;
        rows =new ArrayList();
        this.errors = errors;
    }

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
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
}
