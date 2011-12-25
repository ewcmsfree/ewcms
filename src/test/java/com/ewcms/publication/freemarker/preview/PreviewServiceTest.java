/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.preview;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.ewcms.content.document.model.Article;
import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
import com.ewcms.core.site.model.TemplateEntity;
import com.ewcms.core.site.model.TemplateType;
import com.ewcms.publication.PublishException;
import com.ewcms.publication.freemarker.FreemarkerTest;
import com.ewcms.publication.service.ArticlePublishServiceable;
import com.ewcms.publication.service.ChannelPublishServiceable;
import com.ewcms.publication.service.TemplatePublishServiceable;

import freemarker.template.Configuration;

/**
 * PreviewService单元测试
 * 
 * @author wangwe
 */
public class PreviewServiceTest extends FreemarkerTest {

    private PreviewService newPreviewServiceMock() {
        ChannelPublishServiceable channelService = mock(ChannelPublishServiceable.class);
        TemplatePublishServiceable templateService = mock(TemplatePublishServiceable.class);
        when(templateService.getTemplateByUniquePath(any(String.class)))
                .thenAnswer(new Answer<Template>() {
                    @Override
                    public Template answer(InvocationOnMock invocation)
                            throws Throwable {
                        TemplateEntity entity = new TemplateEntity();
                        entity.setTplEntity("mock home template".getBytes());
                        Template template = new Template();
                        template.setTemplateEntity(entity);
                        return template;
                    }
                });
        ArticlePublishServiceable articleService = mock(ArticlePublishServiceable.class);

        return new PreviewService(cfg, articleService, channelService,
                templateService);
    }

    private PreviewService newPreviewService() {

        ChannelPublishServiceable channelService = mock(ChannelPublishServiceable.class);
        TemplatePublishServiceable templateService = mock(TemplatePublishServiceable.class);
        ArticlePublishServiceable articleService = mock(ArticlePublishServiceable.class);
        return new PreviewService(cfg, articleService, channelService,
                templateService);
    }

    @Test
    public void testGetConfigurationIsMock() throws PublishException {
        PreviewService service = newPreviewServiceMock();
        ArticlePublishServiceable articleService = mock(ArticlePublishServiceable.class);
        boolean mock = true;
        Configuration newCfg = service.getConfiguration(articleService, mock);

        Assert.assertNotSame(cfg, newCfg);
    }

    @Test
    public void testGetConfiguration() throws PublishException {
        PreviewService service = newPreviewService();
        ArticlePublishServiceable articleService = mock(ArticlePublishServiceable.class);
        boolean mock = false;
        Configuration newCfg = service.getConfiguration(articleService, mock);

        Assert.assertSame(cfg, newCfg);
    }

    @Test
    public void testViewHomeTemplateIsMock() throws PublishException,
            IOException {
        PreviewService service = newPreviewServiceMock();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        boolean mock = true;
        service.viewHomeTemplate(out, new Site(), new Channel(),
                initTemplate("index.html"), mock);
        Assert.assertTrue(out.size() > 0);
        out.close();
    }

    @Test
    public void testViewHomeTemplate() throws PublishException,
            IOException {
        PreviewService service = newPreviewService();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        boolean mock = false;
        service.viewHomeTemplate(out, new Site(), new Channel(),
                initTemplate("index.html"), mock);
        Assert.assertTrue(out.size() > 0);
        out.close();
    }

    @Test
    public void testViewListTemplateIsMock() throws PublishException, IOException {
        PreviewService service = newPreviewServiceMock();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        boolean mock = true;
        service.viewListTemplate(out, new Site(), new Channel(),
                initTemplate("index.html"), mock);
        Assert.assertTrue(out.size() > 0);
        out.close();
    }

    @Test
    public void testViewListTemplate() throws PublishException, IOException {
        PreviewService service = newPreviewService();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        boolean mock = false;
        service.viewListTemplate(out, new Site(), new Channel(),
                initTemplate("index.html"), mock);
        Assert.assertTrue(out.size() > 0);
        out.close();
    }
    
    @Test
    public void testViewDetailTemplateIsMock() throws PublishException, IOException {
        PreviewService service = newPreviewServiceMock();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        service.viewDetailTemplate(out, new Site(), new Channel(), initTemplate("index.html"));
        Assert.assertTrue(out.size() > 0);
        out.close();
    }
    
    @Test
    public void testViewDetailTemplate()throws PublishException,IOException{
        //Detail is only mock data
    }
    
    @Test
    public void testViewArticleDetailTemplateIsNull()throws PublishException{
        ChannelPublishServiceable channelService = mock(ChannelPublishServiceable.class);
        Channel channel = new Channel();
        Site site = new Site();
        channel.setSite(site);
        when(channelService.getChannel(any(Integer.class))).thenReturn(channel);
        TemplatePublishServiceable templateService = mock(TemplatePublishServiceable.class);
        List<Template> templates = new ArrayList<Template>();
        Template template = initTemplate("index.html");
        template.setType(TemplateType.HOME);
        templates.add(template);
        template = initTemplate("index.html");
        template.setType(TemplateType.LIST);
        templates.add(template);
        when(templateService.getTemplatesInChannel(any(Integer.class))).thenReturn(templates);
        ArticlePublishServiceable articleService = mock(ArticlePublishServiceable.class);
        when(articleService.getArticle(any(Long.class))).thenReturn(new Article());
        
        PreviewService service = new PreviewService(
                cfg, articleService, channelService,templateService);
        
        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            service.viewArticle(out, Integer.MAX_VALUE, Long.MAX_VALUE, 1);
            Assert.fail();
        }catch(PublishException e){
            
        }
    }
    
    @Test
    public void testViewArticleDetail()throws PublishException{
        ChannelPublishServiceable channelService = mock(ChannelPublishServiceable.class);
        Channel channel = new Channel();
        Site site = new Site();
        channel.setSite(site);
        when(channelService.getChannel(any(Integer.class))).thenReturn(channel);
        TemplatePublishServiceable templateService = mock(TemplatePublishServiceable.class);
        List<Template> templates = new ArrayList<Template>();
        Template template = initTemplate("index.html");
        template.setType(TemplateType.DETAIL);
        templates.add(template);
        when(templateService.getTemplatesInChannel(any(Integer.class))).thenReturn(templates);
        ArticlePublishServiceable articleService = mock(ArticlePublishServiceable.class);
        when(articleService.getArticle(any(Long.class))).thenReturn(new Article());
        
        PreviewService service = new PreviewService(
                cfg, articleService, channelService,templateService);
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        service.viewArticle(out, Integer.MAX_VALUE, Long.MAX_VALUE, 1);
        Assert.assertTrue(out.size()>0);
    }
    
    private String getTemplatePath(String name) {
        return String.format("generator/%s", name);
    }

    private Template initTemplate(String name) {
        Template template = new Template();
        template.setUniquePath(getTemplatePath(name));
        return template;
    }

    @Override
    protected void currentConfiguration(Configuration cfg) {

    }
}
