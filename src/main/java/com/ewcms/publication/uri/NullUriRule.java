/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.uri;

import java.util.Map;

import com.ewcms.publication.PublishException;

/**
 * 空的生成规则
 * 
 * @author wangwei
 */
public class NullUriRule implements UriRuleable {

    @Override
    public void setParameters(Map<String, Object> parameters) {
        
    }

    @Override
    public void putParameter(String parameter, Object value) {
        
    }

    @Override
    public String getUri() throws PublishException {
        return "";
    }
}
