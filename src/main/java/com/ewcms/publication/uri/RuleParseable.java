/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.uri;

import java.util.Map;

/**
 * 解析uri模版生成规则
 * 
 * @author wangwei
 */
public interface RuleParseable {
    
    /**
     * 解析模版得到模版中的变量
     * 
     * @return key:变量名, value:格式化
     */
    public Map<String,String> getVariables();
    
    /**
     * 得到uri规则模版
     * 
     * @return uri规则模版
     */
    public String getPatter();
}
