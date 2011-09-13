/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.uri;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.publication.PublishException;

/**
 * 资源uri生成规则
 * 
 * @author wangwei
 */
public class ResourceUriRule extends UriRule{

    private static final Logger logger = LoggerFactory.getLogger(ResourceUriRuleTest.class);
    private static final UriRuleable RESOURCE_URI_RULE = createUriRule();
    
    private UriRuleable uriRule = RESOURCE_URI_RULE;
    private String context;
    
    public ResourceUriRule(String c){
        c = StringUtils.removeStart(c, "/");
        c = StringUtils.removeEnd(c, "/");
        context = c;
    }
    
    @Override
    public String getUri()throws PublishException {
        uriRule.putParameter("n",RandomStringUtils.randomNumeric(32));
        String uri = "/" + context + uriRule.getUri();
        logger.debug("resource new uri = {}",uri);
        return uri;
    }
    
    protected void setUriRule(UriRuleable uriRule){
        this.uriRule = uriRule;
    }
    
    private static UriRuleable createUriRule(){
       
        try {
            UriRule rule = new UriRule();
            String patter = "/${now}/${n}";
            rule.parse(patter);
            
            return rule;
        } catch (PublishException e) {
            logger.error("Patter parse error:{}",e.toString());
            throw new IllegalStateException("ResourceUriRule patter parse error");
        }
    }
}
