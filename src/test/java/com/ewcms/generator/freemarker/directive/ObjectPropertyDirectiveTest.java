/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.freemarker.directive;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.ewcms.generator.freemarker.FreemarkerTest;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * ObjectPropertyDirective单元测试
 * 
 * @author wangwei
 *
 */
public class ObjectPropertyDirectiveTest extends FreemarkerTest{

    @Override
    protected void currentConfiguration(Configuration cfg) {
        cfg.setSharedVariable("property", new ObjectPropertyDirective());
    }
    
    @Test
    public void testGetValue()throws Exception{
        ObjectBean objectValue = new ObjectBean();
        objectValue.setId(1);
        objectValue.setReadOnly(true);
        objectValue.value = "public";
        
        ObjectBean parent = new ObjectBean();
        parent.setTitle("test");
        objectValue.setParent(parent);
        
        ObjectPropertyDirective directive = new ObjectPropertyDirective();
        Integer id = (Integer)directive.getValue(objectValue, "id");
        assertEquals(id,new Integer(1));
        Boolean readOnly = (Boolean)directive.getValue(objectValue, "readOnly");
        assertTrue(readOnly);
        String value = (String)directive.getValue(objectValue, "value");
        assertEquals(value,"public");
        
        String title = (String)directive.getValue(objectValue, "parent.title");
        assertEquals(title,"test");
    }
    
    private String getTemplatePath(String name){
        return String.format("directive/objectproperty/%s", name);
    }
    
    @Test
    public void testDefault()throws Exception{
        Template template = cfg.getTemplate(getTemplatePath("default.html"));
        
        ObjectBean objectValue = new ObjectBean();
        objectValue.setTitle("test");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("object", objectValue);
        
        String content = process(template, map);
        content = content.trim(); 
        assertEquals(content,"test");
    }
    
    @Test
    public void testValue()throws Exception{
        Template template = cfg.getTemplate(getTemplatePath("value.html"));
        
        List<ObjectBean> list = new ArrayList<ObjectBean>();
        ObjectBean objectValue = new ObjectBean();
        objectValue.setTitle("test");
        list.add(objectValue);
        objectValue = new ObjectBean();
        objectValue.setTitle("test1");
        list.add(objectValue);
        
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("objects", list);
        
        String content = process(template,map);
        content = content.trim();
        assertEquals(content,"testtest1");
    }
    
    @Test
    public void testLoop()throws Exception{
        Template template = cfg.getTemplate(getTemplatePath("loop.html"));
        
        ObjectBean objectValue = new ObjectBean();
        objectValue.setTitle("test");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("object", objectValue);
        
        String content = process(template, map);
        content = content.trim(); 
        assertEquals(content,"test\ntest");
    }
}
