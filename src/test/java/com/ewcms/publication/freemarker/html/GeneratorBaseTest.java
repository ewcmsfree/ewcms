/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.html;

import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.Assert;

import com.ewcms.core.site.model.Channel;
import com.ewcms.core.site.model.Site;
import com.ewcms.core.site.model.Template;
import com.ewcms.publication.PublishException;
import com.ewcms.publication.freemarker.GlobalVariable;
import com.ewcms.publication.output.OutputResource;
import com.ewcms.publication.uri.UriRule;

public class GeneratorBaseTest {

    @Test
    public void testCreateTempFile()throws Exception{
        GeneratorHtml html = new GeneratorHtml();
        File file = html.createTempFile();
        Assert.assertNotNull(file);
        Assert.assertTrue(file.getName().length()>32);
    }
    
    @Test
    public void testCompleteParameters()throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("o", "test");
        
        UriRule rule = new UriRule();
        rule.parse("${o}");
        
        GeneratorHtml html = new GeneratorHtml();
        html.completeParameters(map,rule);
        
        Assert.assertEquals(2, map.size());
        Assert.assertTrue(map.containsKey(GlobalVariable.URI_RULE.toString()));
        Assert.assertEquals("test", rule.getUri());
    }
    
    static class GeneratorHtml extends GeneratorBase{

        @Override
        public List<OutputResource> process(Site site, Channel channel,Template template) throws PublishException {
            return null;
        }

        @Override
        public void previewProcess(OutputStream stream, Site site,
                Channel channel, Template template) throws PublishException {
            // TODO Auto-generated method stub
            
        }
    }
}
