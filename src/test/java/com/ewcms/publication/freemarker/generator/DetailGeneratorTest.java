/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.generator;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.ewcms.content.document.model.Article;
import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.publication.freemarker.GlobalVariable;

import freemarker.template.Configuration;

public class DetailGeneratorTest {

    @Test
    public void testConstructParameters(){
        Site site = new Site();
        Channel channel = new Channel();
        Article article = new Article();
        article.setContentTotal(5);
        DetailGenerator generator = new DetailGenerator(new Configuration(),site,channel,article,1);
        Map<String,Object> parameters  = generator.constructParameters(site, channel);
        
        Assert.assertEquals(6, parameters.size());
        Assert.assertNotNull(parameters.get(GlobalVariable.SITE.toString()));
        Assert.assertNotNull(parameters.get(GlobalVariable.CHANNEL.toString()));
        Assert.assertNotNull(parameters.get(GlobalVariable.ARTICLE.toString()));
        Assert.assertNotNull(parameters.get(GlobalVariable.DEBUG.toString()));
        Assert.assertEquals(5,parameters.get(GlobalVariable.PAGE_COUNT.toString()));
        Assert.assertEquals(1,parameters.get(GlobalVariable.PAGE_NUMBER.toString()));        
    }
}
