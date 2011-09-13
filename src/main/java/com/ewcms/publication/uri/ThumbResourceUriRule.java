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
 * 资源引导图uri生成规则
 * 
 * @author wangwei
 */
public class ThumbResourceUriRule extends ResourceUriRule {
    private static final Logger logger = LoggerFactory.getLogger(ThumbResourceUriRule.class);
    private static final UriRuleable RESOURCE_URI_RULE = createUriRule();
    
    private UriRuleable uriRule = RESOURCE_URI_RULE;
    
    public ThumbResourceUriRule(String context){
        super(context);
    }
    
    @Override
    public String getUri()throws PublishException {
        super.setUriRule(uriRule);
        return super.getUri();
    }
    
    private static UriRuleable createUriRule(){
        
        try {
            UriRule rule = new UriRule();
            String patter = "/${now}/${n}_thumb";
            rule.parse(patter);
            
            return rule;
        } catch (PublishException e) {
            logger.error("Patter parse error:{}",e.toString());
            throw new IllegalStateException("ResourceUriRule patter parse error");
        }
    }
}
