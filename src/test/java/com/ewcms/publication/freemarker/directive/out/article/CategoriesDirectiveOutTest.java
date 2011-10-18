/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.directive.out.article;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.ewcms.content.document.model.Category;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateScalarModel;

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
        String outValue = out.constructOut(new ArrayList<Category>(), null, null);
        Assert.assertNull(outValue);
    }
    
    @Test
    public void testConstructOut()throws Exception{
        CategoriesDirectiveOut out = new CategoriesDirectiveOut();
        String outValue = out.constructOut(initCategories(), null, null);
        String expected = "<ul><li>test</li><li>test1</li></ul>";
        Assert.assertEquals(expected, outValue);
    }
    
    @Test
    public void testClassAndStyleOfConstructOut()throws Exception{
        CategoriesDirectiveOut out = new CategoriesDirectiveOut();
        
        Map<String,TemplateScalarModel> params = new HashMap<String,TemplateScalarModel>();
        params.put("class", new SimpleScalar("test_class"));
        params.put("style", new SimpleScalar("test_style"));
        
        String outValue = out.constructOut(initCategories(), null, params);
        String expected = "<ul class=\"test_class\" style=\"test_style\"><li>test</li><li>test1</li></ul>";
        Assert.assertEquals(expected, outValue);
    }
    
    private List<Category> initCategories(){
        List<Category> list = new ArrayList<Category>();
        
        Category category = new Category();
        category.setCategoryName("test");
        list.add(category);
        category = new Category();
        category.setCategoryName("test1");
        list.add(category);
        
        return list;
    }
}
