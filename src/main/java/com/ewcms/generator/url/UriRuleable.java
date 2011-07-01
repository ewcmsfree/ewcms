/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.url;

import java.util.Map;

/**
 * 资源发布地址规则接口
 * 
 * @author wangwei
 */
public interface UriRuleable {
        
    /**
     * 得到通一资源地址
     * 
     * @param patter 生成地址模板
     * @param parameters 生成地址参数集合
     * @return
     */
    public String getUri(String patter,Map<String,Object> parameters);
    
}
