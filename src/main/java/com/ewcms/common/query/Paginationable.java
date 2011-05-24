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
