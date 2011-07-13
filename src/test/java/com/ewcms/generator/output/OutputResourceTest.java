/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.output;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.ewcms.generator.output.event.OutputEventable;

/**
 * OutputResource单元测试
 * 
 * @author wangwei
 */
public class OutputResourceTest {

    @Test
    public void testAddChild(){
        OutputResource resource = new OutputResource();
        resource.addChild(new OutputResource());
        
        Assert.assertEquals(1, resource.getChildren().size());
    }
    
    @Test
    public void testIsOutput(){
        OutputResource resource = new OutputResource();
        Assert.assertFalse(resource.isOutput());
        
        resource = new OutputResource("/tmp/index.html","index.html");
        Assert.assertTrue(resource.isOutput());
        
        resource = new OutputResource(new byte[10],"index.html");
        Assert.assertTrue(resource.isOutput());
    }
    
    @Test
    public void testOutputSuccess(){
        OutputEventable event = mock(OutputEventable.class);
        OutputResource resource = new OutputResource();
        resource.registerEvent(event);
        resource.outputSuccess();
        
        verify(event).success();
    }
    
    @Test
    public void testOutputError(){
        OutputEventable event = mock(OutputEventable.class);
        OutputResource resource = new OutputResource();
        resource.registerEvent(event);
        resource.outputError(null,null);
        
        verify(event).error(null,null);
    }
    
    @Test
    public void testFileOfWrite()throws IOException{
        String source = OutputResourceTest.class.getResource("provider/write.jpg").getPath();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        OutputResource resource = new OutputResource(source,"write.jpg");
        resource.write(out);
        Assert.assertEquals(335961, out.size());
    }
    
    @Test
    public void testFileOfWriter()throws IOException{
        byte[] content = new byte[100];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        OutputResource resource = new OutputResource(content,"write.jpg");
        resource.write(out);
        Assert.assertEquals(100, out.size());
    }
    
}
