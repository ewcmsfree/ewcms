/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.uri;

import java.util.Map;

import com.ewcms.generator.ReleaseException;

/**
 * 资源发布地址规则接口
 * 
 * @author wangwei
 */
public interface UriRuleable {
        
    /**
     * 得到通一资源地址
     * 
     * @param parameters 生成地址参数集合
     * @return
     * @throws ReleaseException
     */
    public String getUri(Map<String,Object> parameters)throws ReleaseException;
    
}
