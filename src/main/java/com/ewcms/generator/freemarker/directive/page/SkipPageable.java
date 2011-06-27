/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.page;

/**
 * 跳转页面
 * <br>
 * 
 * 根据参数得到跳转页面对象
 *  
 * @author wangwei
 *
 * @param <T> 跳转页面类型
 */
interface SkipPageable<T> {

    /**
     * 跳转页面对象
     * 
     * @param count  总页数
     * @param number 页数
     * @param label  显示标签
     * @param url    链接地址
     * @return
     */
    T skip(Integer count,Integer number,String label,String url);
}
