/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.generator;

import java.util.HashMap;
import java.util.Map;

import com.ewcms.content.document.model.Article;
import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.publication.freemarker.GlobalVariable;
import com.ewcms.publication.uri.UriRuleable;
import com.ewcms.publication.uri.UriRules;

import freemarker.template.Configuration;

/**
 * 生成文章页
 *
 * @author wangwei
 */
public class DetailGenerator extends GeneratorBase {

    private final Article article;
    private final int pageNumber;
    
    /**
     * 生成文章页面构造函数
     * 
     * @param cfg freemarker 配置对象
     * @param site 站点
     * @param channel 频道
     * @param Article article 文章对象
     * @param pageNumber 页数
     */
    public DetailGenerator(Configuration cfg, Site site, Channel channel,Article article,int pageNumber) {
        this(cfg, site, channel,UriRules.newDetail(),article,pageNumber);
    }
    
    /**
     * 生成文章页面构造函数
     * 
     * @param cfg freemarker 配置对象
     * @param site 站点
     * @param channel 频道
     * @param rule uri生成规则
     * @param Article article 文章对象
     * @param pageNumber 页数
     */
    public DetailGenerator(Configuration cfg, Site site, Channel channel,UriRuleable rule,Article article,int pageNumber) {
        super(cfg, site, channel,rule);
        this.article = article;
        this.pageNumber = pageNumber;
    }

    @Override
    protected Map<String, Object> constructParameters(Site site,Channel channel) {
        
        Map<String,Object> params = new HashMap<String,Object>();
        params.put(GlobalVariable.SITE.toString(), site);
        params.put(GlobalVariable.CHANNEL.toString(), channel);
        params.put(GlobalVariable.ARTICLE.toString(), article);
        params.put(GlobalVariable.PAGE_NUMBER.toString(), pageNumber);
        params.put(GlobalVariable.PAGE_COUNT.toString(), article.getContentTotal());
        params.put(GlobalVariable.DEBUG.toString(), debug);
        
        return params;
    }
}
