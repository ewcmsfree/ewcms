/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.freemarker.cache;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.Assert;

import com.ewcms.publication.service.TemplatePublishServiceable;

/**
 * DatabaseTemplateLoader单元测试
 * 
 * @author wangwei
 */
public class DatabaseTemplateLoaderTest {

    @Test
    public void testFindTemplateSourceIsNull()throws Exception{
        
        TemplatePublishServiceable service = mock(TemplatePublishServiceable.class);
        when(service.getTemplateByUniquePath(any(String.class))).thenReturn(null);
        DatabaseTemplateLoader loader = new DatabaseTemplateLoader(service);
        
        Object source = loader.findTemplateSource("/test.html");
        Assert.assertNull(source);
    }
}
