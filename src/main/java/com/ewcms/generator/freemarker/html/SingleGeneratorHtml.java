/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.html;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.util.Assert;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
import com.ewcms.core.site.model.TemplateType;
import com.ewcms.generator.ReleaseException;
import com.ewcms.generator.ResourceInfo;
import com.ewcms.generator.freemarker.GlobalVariable;
import com.ewcms.generator.uri.DefaultHomeUriRule;
import com.ewcms.generator.uri.UriRule;
import com.ewcms.generator.uri.UriRuleable;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/**
 * 生成单页面
 * <br>
 * 如index.html,不需要外部数据辅助页面生成
 * 
 * @author wangwei
 */
public class SingleGeneratorHtml implements GeneratorHtmlable {

    private Site site;
    private Channel channel;
    private Configuration cfg;
    
    public SingleGeneratorHtml(Configuration cfg,Site site,Channel channel){
        Assert.notNull(cfg);
        Assert.notNull(site);
        Assert.notNull(channel);
        
        this.cfg = cfg;
        this.channel = channel;
        this.site = site;
    }
    
    @Override
    public List<ResourceInfo> process(Template template)throws ReleaseException {
        Map<String,Object> parameters = constructParameters();
        UriRuleable rule;
        if(getType(template) == TemplateType.HOME){
            rule = new DefaultHomeUriRule();
        }else{
            rule = new UriRule(template.getUniquePath());
        }
        
       
        return null;
    }
    
    private ResourceInfo writer(String templatePath,UriRuleable rule,Map<String,Object> parameters)throws IOException, TemplateException, ReleaseException{
        String tempFileName = RandomStringUtils.random(32);
        File temp = File.createTempFile(tempFileName, ".html");
        
        Writer out =new FileWriter(temp);
        freemarker.template.Template template= cfg.getTemplate(templatePath);
        template.process(parameters, out);
        out.flush();
        out.close();
        String uri = rule.getUri(parameters);
        ResourceInfo info = new ResourceInfo(temp.getPath(),uri);
        return info;
    }
    
    
    
    private TemplateType getType(Template template){
        return TemplateType.HOME;
    }
    
    private Map<String,Object> constructParameters() {
        
        Map<String,Object> params = new HashMap<String,Object>();
        params.put(GlobalVariable.SITE.toString(), site);
        params.put(GlobalVariable.CHANNEL.toString(), channel);
        return params;
    }
    
}
