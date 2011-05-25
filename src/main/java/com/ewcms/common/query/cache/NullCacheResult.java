/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.query.cache;

import java.util.List;

import com.ewcms.common.query.Resultable;

public class NullCacheResult implements CacheResultable{

    private String cacheKey;
    private Resultable result;
    
    public NullCacheResult(String cacheKey,Resultable result){
        this.cacheKey = cacheKey;
        this.result = result;
    }
    
    @Override
    public int getCount() {
        return result.getCount();
    }

    @Override
    public int getPageCount() {
        return result.getPageCount();
    }

    @Override
    public List<Object> getResultList() {
        return result.getResultList();
    }

    @Override
    public List<Object> getExtList() {
        return result.getExtList();
    }
  
    @Override
    public String getCacheKey() {
        return cacheKey;
    }

    @Override
    public boolean isModified() {
        return true;
    }
}
