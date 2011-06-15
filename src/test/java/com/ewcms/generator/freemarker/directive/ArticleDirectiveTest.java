/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive;

import org.junit.Assert;
import org.junit.Test;

import com.ewcms.generator.freemarker.directive.out.DateDirectiveOut;
import com.ewcms.generator.freemarker.directive.out.DefaultDirectiveOut;

/**
 * ArticleDirective单元测试
 * 
 * @author wangwei
 */
public class ArticleDirectiveTest {

    @Test
    public void testNewPutDirective()throws Exception{
        ArticleDirective directive = new ArticleDirective();
        directive.putDirective("测试", "test", new DefaultDirectiveOut());
        
        String name = directive.getPropertyName("测试");
        Assert.assertEquals("test", name);
        name = directive.getPropertyName("test");
        Assert.assertEquals("test", name);
        
        DirectiveOutable out = directive.getDirectiveOut("test");
        Assert.assertNotNull(out);
    }
    
    @Test
    public void testUpdatePutDirective()throws Exception{
        ArticleDirective directive = new ArticleDirective();
        directive.putDirective("文章标题","title", new DateDirectiveOut());
        
        String name = directive.getPropertyName("文章标题");
        Assert.assertEquals("title", name);
        name = directive.getPropertyName("标题");
        Assert.assertEquals("title", name);
        name = directive.getPropertyName("title");
        Assert.assertEquals("title", name);
        
        DirectiveOutable out = directive.getDirectiveOut("title");
        Assert.assertEquals(DateDirectiveOut.class, out.getClass());
    }
    
    @Test
    public void testAlreadyHasAlias()throws Exception{
        String[] aliases = initAliases();
        
        ArticleDirective directive = new ArticleDirective();
        
        for(String alias : aliases){
            String name = directive.getPropertyName(alias);
            Assert.assertNotNull(name);
        }
    }
    
    @Test
    public void testAlreadyHasDirectiveOut()throws Exception{
        String[] aliases = initAliases();
        
        ArticleDirective directive = new ArticleDirective();
        
        for(String alias : aliases){
            String name = directive.getPropertyName(alias);
            DirectiveOutable out = directive.getDirectiveOut(name);
            Assert.assertNotNull(out);
        }
    }
    
    private String[] initAliases(){
        return new String[]{
                "编号","标题","短标题","副标题","作者","编辑",
                "引导图片","摘要","来源","关键字","标签","分类",
                "链接地址","关联文章","正文","创建时间","修改时间",
                "发布时间"
        };
    }
}
