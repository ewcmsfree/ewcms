/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.uri;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * RuleParse单元测试
 * 
 * @author wangwei
 */
public class RuleParseTest {

    @Test
    public void testNoneVariableParseVariables() throws Exception {
        String patter = "home/index.html";
        RuleParse rule = new RuleParse(patter);
        Map<String, String> variables = rule.getVariables();
        Assert.assertTrue(variables.isEmpty());
    }

    @Test
    public void testLeftNotCompletePatterVariables() {
        String patter = "home/${index.html";
        RuleParse rule = new RuleParse(patter);
        Map<String, String> variables = rule.getVariables();
        Assert.assertTrue(variables.isEmpty());
    }
    
    @Test
    public void testRightNotCompletePatterVariables() {
        String patter = "home/index}.html";
        RuleParse rule = new RuleParse(patter);
        Map<String, String> variables = rule.getVariables();
        Assert.assertTrue(variables.isEmpty());
    }
    
    @Test
    public void testOnlyOneVariables()throws Exception{
        String patter = " ${a}";
        
        RuleParse rule = new RuleParse(patter);
        Map<String, String> variables = rule.getVariables();
        Assert.assertEquals(1, variables.size());
        Assert.assertTrue(variables.containsKey("a"));
        Assert.assertNull(variables.get("a"));
    }
    
    @Test
    public void testParseVariables()throws Exception{
        String patter = "news/cn/${now?yyyy-MM-dd}/${a.id}.html";
        
        RuleParse rule = new RuleParse(patter);
        Map<String, String> variables = rule.getVariables();
        Assert.assertEquals(2, variables.size());
        Assert.assertTrue(variables.containsKey("now"));
        Assert.assertEquals("yyyy-MM-dd", variables.get("now"));
        Assert.assertTrue(variables.containsKey("a.id"));
        Assert.assertNull(variables.get("aid"));
    }
}
