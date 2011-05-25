/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.generator.freemarker.directive.page;

/**
 *
 * @author wangwei
 */
public class PageParam {

    private int page;
    private int count;
    private int row = -1;
    private String urlPattern;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PageParam other = (PageParam) obj;
        if (this.page != other.page) {
            return false;
        }
        if (this.count != other.count) {
            return false;
        }
        if (this.row != other.row) {
            return false;
        }
        if ((this.urlPattern == null) ? (other.urlPattern != null) : !this.urlPattern.equals(other.urlPattern)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.page;
        hash = 23 * hash + this.count;
        hash = 23 * hash + this.row;
        hash = 23 * hash + (this.urlPattern != null ? this.urlPattern.hashCode() : 0);
        return hash;
    }
}
