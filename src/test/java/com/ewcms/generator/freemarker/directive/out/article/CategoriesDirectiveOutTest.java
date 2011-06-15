/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive.out.article;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ewcms.content.document.model.ArticleCategory;
import com.ewcms.generator.freemarker.directive.out.article.CategoriesDirectiveOut;

/**
 * CategoriesDirectiveOutss单元测试
 * 
 * @author wangwei
 */
public class CategoriesDirectiveOutTest {

    @Test
    public void testLoopValue()throws Exception{
        CategoriesDirectiveOut out = new CategoriesDirectiveOut();
        Object value = new Object();
        Object loopValue = out.loopValue(value, null, null);
        Assert.assertEquals(value, loopValue);
    }
    
    @Test
    public void testValueIsEmptyOfConstructOut()throws Exception{
        CategoriesDirectiveOut out = new CategoriesDirectiveOut();
        String outValue = out.constructOut(null, null, null);
        Assert.assertNull(outValue);
        
        outValue = out.constructOut(new ArrayList<ArticleCategory>(), null, null);
        Assert.assertNull(outValue);
    }
    
    @Test
    public void testConstructOut()throws Exception{
        CategoriesDirectiveOut out = new CategoriesDirectiveOut();
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
