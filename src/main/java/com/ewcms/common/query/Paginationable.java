/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.query;

public interface Paginationable {

   static final int DEFAULT_ROW = 20;
   
   static final int DEFAULT_PAGE = 0;
    
    /**
     * 设置一次查询记录数
     * 
     * @param row 记录数
     */
   Paginationable setRow(int row);
    
    /**
     * 设置查询第几页
     * 
     * @param page 页数
     */
   Paginationable setPage(int page);
}
