/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.uri;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.ewcms.publication.ObjectBean;
import com.ewcms.publication.PublishException;
import com.ewcms.publication.freemarker.GlobalVariable;

/**
 * UrlRule单元测试
 * 
 * @author wangwei
 */
public class UriRuleTest {
    
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    @Test
    public void testParameterIsNoneGetVariableValue()throws Exception{
        Map<String,Object> parameters =new HashMap<String,Object>();
        
        try{
            UriRule rule = new UriRule(new RuleParse(""));
            rule.getVariableValue("a.id", parameters);
            Assert.fail();
        }catch(PublishException e){
        }
    }
    
    @Test
    public void testGetVariableValue()throws Exception{
       Map<String,Object> parameters = new HashMap<String,Object>();
       parameters.put("o", initObjectBean()); 
       UriRule rule = new UriRule(new RuleParse(""));
       Object value =rule.getVariableValue("o.title", parameters);        
       Assert.assertEquals("root", value);
       
       value =rule.getVariableValue("o.parent.title", parameters); 
       Assert.assertEquals("child", value);
    }
    
    @Test
    public void testStringTypeFormatValue(){
        
        UriRule rule = new UriRule(new RuleParse(""));
        String value = rule.formatValue("test", null);
        Assert.assertEquals("test",value);
    }
    
    @Test
    public void testDateTypeFormatValue(){
        
        UriRule rule = new UriRule(new RuleParse(""));
        Calendar calendar = Calendar.getInstance();
        calendar.set(2011, 0, 1);
        
        String value = rule.formatValue(new Date(calendar.getTimeInMillis()), null);
        Assert.assertEquals("2011-01-01", value);
        
        value = rule.formatValue(new Date(calendar.getTimeInMillis()), "yyyy年MM月dd日");
        Assert.assertEquals("2011年01月01日", value);
    }
    
    @Test
    public void testNumberTypeFormatValue(){
        
        UriRule rule = new UriRule(new RuleParse(""));
        
        Integer number = Integer.valueOf(1099);
        String value = rule.formatValue(number, null);
        Assert.assertEquals("1099", value);
        
        Float f = 2.55f;
        value = rule.formatValue(f, "#.0");
        Assert.assertEquals("2.5", value);
    }
    
    @Test
    public void testGetUri()throws Exception{
        String patter = "news/cn/${o.id}.html";
        Map<String,Object> parameters = new HashMap<String,Object>();
        parameters.put("o", initObjectBean()); 
        UriRule rule = new UriRule(new RuleParse(patter));
        rule.setParameters(parameters);
        String uri = rule.getUri();
        Assert.assertEquals("news/cn/1.html", uri);
    }
    
    @Test
    public void testInnerVariableGetUri()throws Exception{
        String patter = "news/cn/${now?yyyy-MM-dd}/${c.id}_${p}.html";
        Map<String,Object> parameters = new HashMap<String,Object>();
        parameters.put(GlobalVariable.CHANNEL.toString(), initObjectBean()); 
        parameters.put(GlobalVariable.PAGE_NUMBER.toString(), Integer.valueOf(1));
        
        UriRule rule = new UriRule(new RuleParse(patter));
        rule.setParameters(parameters);
        String uri = rule.getUri();
        String expected ="news/cn/"+ dateFormat.format(new Date()) + "/1_1.html";
        Assert.assertEquals(expected, uri);
    }
    
    private ObjectBean initObjectBean(){
        ObjectBean bean = new ObjectBean();
        bean.setId(1);
        bean.setTitle("root");
        
        ObjectBean child = new ObjectBean();
        child.setTitle("child");
        bean.setParent(child);
        
        return bean;
    }
    
    
}
