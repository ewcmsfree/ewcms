/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.out;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ewcms.content.document.model.ArticleCategory;

/**
 * ArticleCategoriesDirectiveOutss单元测试
 * 
 * @author wangwei
 */
public class ArticleCategoriesDirectiveOutTest {

    @Test
    public void testLoopValue()throws Exception{
        ArticleCategoriesDirectiveOut out = new ArticleCategoriesDirectiveOut();
        Object value = new Object();
        Object loopValue = out.loopValue(value, null, null);
        Assert.assertEquals(value, loopValue);
    }
    
    @Test
    public void testValueIsEmptyOfConstructOut()throws Exception{
        ArticleCategoriesDirectiveOut out = new ArticleCategoriesDirectiveOut();
        String outValue = out.constructOut(null, null, null);
        Assert.assertNull(outValue);
        
        outValue = out.constructOut(new ArrayList<ArticleCategory>(), null, null);
        Assert.assertNull(outValue);
    }
    
    @Test
    public void testConstructOut()throws Exception{
        ArticleCategoriesDirectiveOut out = new ArticleCategoriesDirectiveOut();
        String outValue = out.constructOut(initCategories(), null, null);
        String expected = "<ul><li>test</li><li>test1</li></ul>";
        Assert.assertEquals(expected, outValue);
    }
    
    private List<ArticleCategory> initCategories(){
        List<ArticleCategory> list = new ArrayList<ArticleCategory>();
        
        ArticleCategory category = new ArticleCategory();
        category.setCategoryName("test");
        list.add(category);
        category = new ArticleCategory();
        category.setCategoryName("test1");
        list.add(category);
        
        return list;
    }
}
