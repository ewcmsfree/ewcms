/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.uri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.publication.PublishException;

/**
 * 默认列表页 uri生成规则
 * 
 * @author wangwei
 */
public class DefaultListUriRule extends UriRule {
    
    private static final Logger logger = LoggerFactory.getLogger(DefaultListUriRule.class);
    
    public DefaultListUriRule(){
        String patter = "${c.absUrl}/${p}.html";
        try {
            super.parse(patter);
        } catch (PublishException e) {
            logger.error("Patter parse error:{}",e.toString());
            throw new IllegalStateException("DefaultListUriRule patter parse error");
        }
    }
    
    @Override
    public void parse(String patter)throws PublishException{
        throw new PublishException("This method Can't call use");
    }
}
