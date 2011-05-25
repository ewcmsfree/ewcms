/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.query;

import com.ewcms.common.query.cache.CacheResultable;
import com.ewcms.common.query.cache.ResultCacheable;

/**
 *
 * @author wangwei
 */
public interface Queryable extends Paginationable {
    
    Queryable setCache(ResultCacheable cache);
    
    /**
     * 设置一次查询记录数
     * 
     * @param row 记录数
     */
    @Override
    Queryable setRow(int row);
    
    /**
     * 设置查询第几页
     * 
     * @param page 页数
     */
    @Override
    Queryable setPage(int page);
   
    /**
     * 查询
     * 
     * @return
     */
    Result queryResult();
    
    /**
     * 缓存查询
     * 
     * @param cacheKey
     * @return
     */
    CacheResultable queryCacheResult(String cacheKey);
}
