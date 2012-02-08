/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.freemarker.generator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.publication.freemarker.FreemarkerTest;
import com.ewcms.publication.freemarker.GlobalVariable;
import com.ewcms.publication.uri.NullUriRule;
import com.ewcms.publication.uri.UriRuleable;
import com.ewcms.publication.uri.UriRules;

import freemarker.template.Configuration;

/**
 * GeneratorBase 单元测试
 * 
 * @author wangwei
 */
public class GeneratorBaseTest extends FreemarkerTest {

    @Test
    public void testGetUri()throws Exception{
        UriRuleable rule = mock(UriRuleable.class);
        GeneratorBaseImpl html = new GeneratorBaseImpl(rule);
        when(html.getPublishUri()).thenReturn("/index.html");
        String uri = html.getPublishUri();
        
        Assert.assertEquals("/index.html", uri);
    }
    
    @Test
    public void testGetFreemarkerTemplate()throws Exception{
        GeneratorBaseImpl html = new GeneratorBaseImpl();
        freemarker.template.Template template = html.getFreemarkerTemplate(cfg, getTemplatePath("index.html"));
        Assert.assertNotNull(template);
    }
    
    @Test
    public void testCreateTempFile()throws Exception{
        GeneratorBaseImpl html = new GeneratorBaseImpl();
        File file = html.createTempFile();
        Assert.assertNotNull(file);
        Assert.assertTrue(file.getName().length()>32);
    }
    
    @Test
    public void testSetUriRuleParameters()throws Exception{
        Map<String,Object> parameters = new HashMap<String,Object>();
        parameters.put("o", "test");
        
        UriRuleable rule =UriRules.newUriRuleBy("${o}");
        
        GeneratorBaseImpl html = new GeneratorBaseImpl();
        html.setUriRuleParameters(rule,parameters);
        
        Assert.assertEquals("test", rule.getUri());
    }
    
    @Test
    public void testAddUriRuleToProcessParameters()throws Exception{
        Map<String,Object> parameters = new HashMap<String,Object>();
        
        UriRuleable rule =UriRules.newUriRuleBy("");
        
        GeneratorBaseImpl html = new GeneratorBaseImpl();
        html.addUriRuleToProcessParameters(rule, parameters);
        
        Assert.assertEquals(1, parameters.size());
        Assert.assertNotNull(parameters.get(GlobalVariable.URI_RULE.toString()));
    }
    
    @Test
    public void testWrite()throws Exception{
        Map<String,Object> parameters = new HashMap<String,Object>();
        UriRuleable rule =UriRules.newUriRuleBy("");
        
        GeneratorBaseImpl html = new GeneratorBaseImpl();
        
        freemarker.template.Template template = 
            html.getFreemarkerTemplate(cfg, getTemplatePath("index.html"));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(out);
        html.write(template, parameters, rule, writer);
        
        String content = getContent(out.toByteArray());
        content = StringUtils.deleteWhitespace(content);
        Assert.assertEquals("homepage", content);
        
        out.close();
    }
    
    @Test
    public void testProcessOutputStream()throws Exception{
        GeneratorBaseImpl html = new GeneratorBaseImpl(cfg,initSite(),initChannel());
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        html.process(out,getTemplatePath("index.html"));
        
        String content = getContent(out.toByteArray());
        content = StringUtils.deleteWhitespace(content);
        Assert.assertEquals("homepage", content);
        
        out.close();
    }
    
    @Test
    public void testProcessFile()throws Exception{
        GeneratorBaseImpl html = new GeneratorBaseImpl(cfg,initSite(),initChannel());
        
        File file = html.process(getTemplatePath("index.html"));
        Assert.assertNotNull(file);
    }
    
    @Override
    protected void currentConfiguration(Configuration cfg) {
        //不需要
    }
    
    private String getContent(byte[] content)throws Exception{
        ByteArrayInputStream in = new ByteArrayInputStream(content);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in,"utf-8"));
        StringBuilder builder = new StringBuilder();
        String line;
        while((line = reader.readLine())!= null){
            builder.append(line);
        }
        reader.close();
        in.close();
        return builder.toString();
    }
    
    private Site initSite(){
        Site  site = new Site();
        return site;
    }
    
    private Channel initChannel(){
        Channel channel = new Channel();
        
        channel.setId(1);
        channel.setListSize(10);
        channel.setAbsUrl("/news/cn");
        
        return channel;
    }
    
    private String getTemplatePath(String name) {
        return String.format("generator/%s", name);
    }
    
    private class GeneratorBaseImpl extends GeneratorBase{

        public GeneratorBaseImpl(){
            this(null,null,null);
        }
        
        public GeneratorBaseImpl(Configuration cfg, Site site, Channel channel) {
            super(cfg, site, channel,new NullUriRule());
        }

        public GeneratorBaseImpl(UriRuleable rule) {
            super(null,null,null,rule);
        }
        
        @Override
        protected Map<String, Object> constructParameters(Site site, Channel channel) {
            Map<String,Object> map = new HashMap<String,Object>();
            
            return map;
        }
    }
}
