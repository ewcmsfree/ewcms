/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.html;

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
import com.ewcms.generator.ReleaseException;
import com.ewcms.generator.freemarker.GlobalVariable;
import com.ewcms.generator.output.OutputResource;
import com.ewcms.generator.uri.DefaultHomeUriRule;
import com.ewcms.generator.uri.UriRuleable;

import freemarker.template.Configuration;

/**
 * 生成单页面
 * <br>
 * 如index.html,不需要外部数据辅助页面生成
 * 
 * @author wangwei
 */
public class HomeGenerator extends GeneratorHtmlBase {

    static final Logger logger = LoggerFactory.getLogger(HomeGenerator.class);
    
    private Site site;
    private Channel channel;
    Configuration cfg;
    UriRuleable rule = new DefaultHomeUriRule();
    
    public HomeGenerator(Configuration cfg,Site site,Channel channel){
        Assert.notNull(cfg);
        Assert.notNull(site);
        Assert.notNull(channel);
        
        this.cfg = cfg;
        this.channel = channel;
        this.site = site;
    }
    
    @Override
    public List<OutputResource> process(Template template)throws ReleaseException {
        Map<String,Object> parameters = constructParameters();
        freemarker.template.Template t = getFreemarkerTemplate(cfg,template.getUniquePath());
        return Arrays.asList(generator(t,parameters,rule));
    }
    
    private Map<String,Object> constructParameters() {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put(GlobalVariable.SITE.toString(), site);
        params.put(GlobalVariable.CHANNEL.toString(), channel);
        
        return params;
    }
}
