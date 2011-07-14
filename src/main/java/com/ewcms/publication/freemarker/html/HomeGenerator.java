/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.html;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
import com.ewcms.publication.PublishException;
import com.ewcms.publication.freemarker.GlobalVariable;
import com.ewcms.publication.output.OutputResource;
import com.ewcms.publication.uri.DefaultHomeUriRule;
import com.ewcms.publication.uri.UriRuleable;

import freemarker.template.Configuration;

/**
 * 生成home页面
 * <br>
 * 如index.html,不需要外部数据辅助页面生成
 * 
 * @author wangwei
 */
public class HomeGenerator extends GeneratorBase {

    static final Logger logger = LoggerFactory.getLogger(HomeGenerator.class);
    
    private Configuration cfg;
    private UriRuleable rule = new DefaultHomeUriRule();
    
    public HomeGenerator(Configuration cfg){
        Assert.notNull(cfg);
        
        this.cfg = cfg;
    }
    
    @Override
    public List<OutputResource> process(Template template,Site site,Channel channel)throws PublishException {
       return process(template,rule,site,channel);
    }
    
    protected List<OutputResource> process(Template template,UriRuleable rule,Site site,Channel channel)throws PublishException {
        Map<String,Object> parameters = constructParameters(site,channel);
        freemarker.template.Template t = getFreemarkerTemplate(cfg,template.getUniquePath());
        return Arrays.asList(generator(t,parameters,rule));
    }
    
    private Map<String,Object> constructParameters(Site site,Channel channel) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put(GlobalVariable.SITE.toString(), site);
        params.put(GlobalVariable.CHANNEL.toString(), channel);
        
        return params;
    }
}
