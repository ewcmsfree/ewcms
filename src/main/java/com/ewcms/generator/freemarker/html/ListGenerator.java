/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
import com.ewcms.generator.ReleaseException;
import com.ewcms.generator.freemarker.GlobalVariable;
import com.ewcms.generator.output.OutputResource;
import com.ewcms.generator.service.ArticleLoaderServiceable;
import com.ewcms.generator.uri.DefaultListUriRule;
import com.ewcms.generator.uri.UriRuleable;

import freemarker.template.Configuration;

/**
 * 生成频道文章列表页
 * 
 * @author wangwei
 */
public class ListGenerator extends GeneratorHtmlBase {
    
    private static final Logger logger = LoggerFactory.getLogger(ListGenerator.class);
    
    private Configuration cfg;
    private Site site;
    private Channel channel;
    private ArticleLoaderServiceable service;
    UriRuleable uriRule = new DefaultListUriRule();
    
    public ListGenerator(Configuration cfg,Site site,
            Channel channel,ArticleLoaderServiceable service){
        
        this.cfg = cfg;
        this.site = site;
        this.channel = channel;
        this.service = service;
    }
    
    @Override
    public List<OutputResource> process(Template template)throws ReleaseException {
        List<OutputResource> resources = new ArrayList<OutputResource>();
        freemarker.template.Template t = getFreemarkerTemplate(cfg,template.getUniquePath());
        Integer pageCount = getPageCount();
        logger.debug("Page count is {}",pageCount);
        for(int i = 0 ; i < pageCount; ++i){
            Map<String,Object> parameters = constructParameters(i,pageCount);
            OutputResource resource = generator(t,parameters,uriRule);
            resources.add(resource);
        }
        
        return resources;
    }
    
    private Integer getPageCount(){
        Integer count = service.getArticleCount(channel.getId());
        logger.debug("Article count is {}",count);
        Integer row = channel.getListSize();
        
        return (count + row -1)/row;
    }
    
    private Map<String,Object> constructParameters(Integer pageNumber,Integer pageCount) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put(GlobalVariable.SITE.toString(), site);
        params.put(GlobalVariable.CHANNEL.toString(), channel);
        params.put(GlobalVariable.PAGE_NUMBER.toString(), pageNumber);
        params.put(GlobalVariable.PAGE_COUNT.toString(), pageCount);
        return params;
    }
}
