/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.html;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
import com.ewcms.core.site.model.TemplateType;
import com.ewcms.generator.freemarker.FreemarkerTest;
import com.ewcms.generator.output.OutputResource;

import freemarker.template.Configuration;

/**
 * SingleCeneratorHtml单元测试
 * 
 * @author wangwei
 */
public class HomeGeneratorTest extends FreemarkerTest {

    @Override
    protected void currentConfiguration(Configuration cfg) {
        //不需要
    }
    
    @Test
    public void testHomeTemplate()throws Exception{
        GeneratorHtmlable generator = new HomeGenerator(cfg,initSite(),initChannel());
        List<OutputResource> list = generator.process(initTemplate(getTemplatePath("index.html"),TemplateType.HOME));
        Assert.assertFalse(list.isEmpty());
        
        OutputResource info = list.get(0);
        String content = getContent(info.getPath());
        content = StringUtils.deleteWhitespace(content);
        Assert.assertEquals("homepage", content);
        Assert.assertEquals("news/cn/index.html", info.getReleasePath());
    }
    
    private String getContent(String path)throws Exception{
        BufferedReader reader = new BufferedReader(new FileReader(path));
        StringBuilder builder = new StringBuilder();
        String line;
        while((line = reader.readLine())!= null){
            builder.append(line);
        }
        reader.close();
        return builder.toString();
    }
    
    private Site initSite(){
        Site  site = new Site();
        
        return site;
    }
    
    private Channel initChannel(){
        Channel channel = new Channel();
        
        channel.setAbsUrl("/news/cn");
        
        return channel;
    }
    
    private String getTemplatePath(String name) {
        return String.format("html/%s", name);
    }
    
    private Template initTemplate(String path,TemplateType type){
        Template template = new Template();
        
        template.setUniquePath(path);
        template.setType(type);
        
        return template;
    }
    
}
