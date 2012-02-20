/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.query.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.util.Assert;

import com.ewcms.common.query.Paginationable;

public class CacheResult implements CacheResultable,Paginationable,Serializable{
	
	private static final long serialVersionUID = -8968737366176823246L;
	
	private int row = DEFAULT_ROW;
    private int page = DEFAULT_PAGE;
    
    private String cacheKey;
    private int count ;
    private List<Object> resultList = new ArrayList<Object>();
    private List<Object> extList;
    private boolean modified ;
    
    public CacheResult(String cacheKey,int count,List<Object> extList){
        this.cacheKey = cacheKey;
        this.count = count;
        this.extList = extList;
        this.modified = true;
    }
    
    public CacheResult(CacheResult result){
        cacheKey = result.cacheKey;
        count = result.count;
        resultList = result.resultList;
        extList = result.extList;
        row = result.row;
        page = result.page;
        modified = false;
    }
    
    public CacheResult(String cacheKey,CacheResult result){
        this.cacheKey = cacheKey;
        count = result.count;
        resultList = result.resultList;
        extList = result.extList;
        row = result.row;
        page = result.page;
        modified = true;
    }
    
    @Override
    public CacheResult setRow(int row) {
        Assert.isTrue(row > 0,"row is not <= 0");
        this.row = row;
        return this;
    }

    @Override
    public CacheResult setPage(int page) {
        Assert.isTrue( page >= 0 ,"page is not < 0");
        this.page = page;
        return this;
    }

    @Override
    public String getCacheKey() {
        return cacheKey;
    }
    
    @Override
    public int getCount() {
        return count;
    }
    
    @Override
    public int getPageCount() {
        return (count + row - 1) / row;
    }
    
    @Override
    public List<Object> getResultList() {
        
        int fromIndex = getFromIndex();
        if(fromIndex >= count){
            return Collections.emptyList();
        }
        Assert.isTrue(isLoaded(),"load data incomplete");
        int toIndex = getToIndex();
        return resultList.subList(fromIndex, toIndex);
    }
    
    private int getFromIndex(){
        return row * page;
    }
    
    private int getToIndex(){
        int toIndex = row * (page + 1);
        toIndex = toIndex > count ? count : toIndex;
        return toIndex;
    }
    
    public boolean isLoaded(){
        
        int fromIndex = getFromIndex();
        if(fromIndex >= count){
            return true;
        }
               
        int toIndex = getToIndex();
        
        return (fromIndex < resultList.size()) && (toIndex <= resultList.size());
    }
    
    public int getStartPosition(){
        return resultList.size();
    }

    @Override
    public List<Object> getExtList() {
        return extList;
    }

    public void appendResultList(List<Object> list) {
        int space = count - resultList.size();
        if(space <= 0){
            return ;
        }
        
        modified = true;
        
        if(space > list.size()){
            resultList.addAll(list);
        }else{
            resultList.addAll(list.subList(0, space));
        }
    }

    @Override
    public boolean isModified() {
        return modified;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((cacheKey == null) ? 0 : cacheKey.hashCode());
        result = prime * result + count;
        result = prime * result + ((extList == null) ? 0 : extList.hashCode());
        result = prime * result
                + ((resultList == null) ? 0 : resultList.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CacheResult other = (CacheResult) obj;
        if (cacheKey == null) {
            if (other.cacheKey != null)
                return false;
        } else if (!cacheKey.equals(other.cacheKey))
            return false;
        if (count != other.count)
            return false;
        if (extList == null) {
            if (other.extList != null)
                return false;
        } else if (!extList.equals(other.extList))
            return false;
        if (resultList == null) {
            if (other.resultList != null)
                return false;
        } else {
            if(other.resultList == null)
                return false;
            if (!resultList.equals(other.resultList))
                return false;
        }
            
        return true;
    }
}
