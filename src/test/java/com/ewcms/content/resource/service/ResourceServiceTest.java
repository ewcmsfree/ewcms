/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.resource.service;

import org.junit.Test;
import org.junit.Assert;

/**
 * ResourceSevice单元测试
 * 
 * @author wangwei
 */
public class ResourceServiceTest {

    @Test
    public void testGetFilename(){
        
        ResourceService service = new ResourceService();
        String path = "/home/wangwei/test.html";
        String name = service.getFilename(path);
        Assert.assertEquals("test.html", name);
        
        path = "test.html";
        name= service.getFilename(path);
        Assert.assertEquals("test.html", name);
    }
    
    @Test
    public void testGetThumbUri(){
        ResourceService service = new ResourceService();
        String uri = "china/image.jpg";
        String thumbUri = service.getThumbUri(uri);
        Assert.assertEquals("china/image_thumb.jpg", thumbUri);
        
        uri = "china./image.jpg";
        thumbUri = service.getThumbUri(uri);
        Assert.assertEquals("china./image_thumb.jpg", thumbUri);
        
        uri = ".jpg";
        thumbUri = service.getThumbUri(uri);
        Assert.assertEquals("_thumb.jpg", thumbUri);
        
        uri = "image";
        thumbUri = service.getThumbUri(uri);
        Assert.assertEquals("image_thumb", thumbUri);
    }
    
    @Test
    public void testGetSuffix(){
        ResourceService service = new ResourceService();
        String filename = "test.html";
        String suffix = service.getSuffix(filename);
        Assert.assertEquals("html", suffix);
        
        filename="test";
        suffix = service.getSuffix(filename);
        Assert.assertEquals("", suffix);
    }
}
