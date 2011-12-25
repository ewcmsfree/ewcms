/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.freemarker.generator;

import java.util.HashMap;
import java.util.Map;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.publication.freemarker.GlobalVariable;
import com.ewcms.publication.uri.UriRuleable;
import com.ewcms.publication.uri.UriRules;

import freemarker.template.Configuration;

/**
 * 生成home页面
 * 
 * @author wangwei
 */
public class HomeGenerator extends GeneratorBase {
    
    public HomeGenerator(Configuration cfg, Site site, Channel channel) {
        this(cfg, site, channel,UriRules.newHome());
    }

    public HomeGenerator(Configuration cfg, Site site, Channel channel,UriRuleable uriRule) {
        super(cfg, site, channel,uriRule);
    }
    
    @Override
    protected Map<String,Object> constructParameters(Site site,Channel channel) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put(GlobalVariable.SITE.toString(), site);
        params.put(GlobalVariable.CHANNEL.toString(), channel);
        params.put(GlobalVariable.DEBUG.toString(), debug);
        
        return params;
    }
}
