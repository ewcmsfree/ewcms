/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.generator;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.publication.freemarker.GlobalVariable;

import freemarker.template.Configuration;

/**
 * ListGeneratorHtml单元测试
 * 
 * @author wangwei
 */
public class ListGeneratorTest {

    @Test
    public void testConstructParameters(){
        Site site = new Site();
        Channel channel = new Channel();
        ListGenerator generator = new ListGenerator(new Configuration(),site,channel,5,200);
        Map<String,Object> parameters  = generator.constructParameters(site, channel);
        
        Assert.assertEquals(5, parameters.size());
        Assert.assertNotNull(parameters.get(GlobalVariable.SITE.toString()));
        Assert.assertNotNull(parameters.get(GlobalVariable.CHANNEL.toString()));
        Assert.assertNotNull(parameters.get(GlobalVariable.DEBUG.toString()));
        Assert.assertEquals(200,parameters.get(GlobalVariable.PAGE_COUNT.toString()));
        Assert.assertEquals(5,parameters.get(GlobalVariable.PAGE_NUMBER.toString()));        
    }
  
}
