/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.html;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
import com.ewcms.publication.PublishException;
import com.ewcms.publication.freemarker.GlobalVariable;
import com.ewcms.publication.output.OutputResource;
import com.ewcms.publication.uri.DefaultHomeUriRule;
import com.ewcms.publication.uri.NullUriRule;
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

    private static final UriRuleable DEFAULT_URI_RULE = new DefaultHomeUriRule();
    
    private Configuration cfg;
    
    public HomeGenerator(Configuration cfg){
        Assert.notNull(cfg);
        this.cfg = cfg;
    }
    
    /**
     * 构造生成页面参数集合
     * 
     * @param site 
     *          站点对象
     * @param channel 
     *          频道对象
     * @param debug
     *          是否调试
     * @return
     */
    private Map<String,Object> constructParameters(Site site,Channel channel,Boolean debug) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put(GlobalVariable.SITE.toString(), site);
        params.put(GlobalVariable.CHANNEL.toString(), channel);
        params.put(GlobalVariable.DEBUG.toString(), debug);
        
        return params;
    }
        
    @Override
    public List<OutputResource> process(Site site,Channel channel,Template template)throws PublishException {
        Map<String,Object> parameters = constructParameters(site,channel,Boolean.FALSE);
        freemarker.template.Template t = getFreemarkerTemplate(cfg,template.getUniquePath());
        UriRuleable rule = getUriRule(template.getUriPattern(),DEFAULT_URI_RULE);
        return Arrays.asList(generator(t,parameters,rule));
    }

    @Override
    public void previewProcess(OutputStream stream, Site site, Channel channel,Template template) throws PublishException {
        Map<String,Object> parameters = constructParameters(site,channel,Boolean.TRUE);
        freemarker.template.Template t = getFreemarkerTemplate(cfg,template.getUniquePath());
        UriRuleable rule =new NullUriRule();
        Writer writer = new OutputStreamWriter(stream);
        write(t , parameters, rule, writer);
    }
}
