/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.output;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import com.ewcms.publication.output.event.OutputEventable;

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
        
        resource = new OutputResource("/tmp/index.html","index.html",false);
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
    public void testFileWrite()throws IOException{
        String source = OutputResourceTest.class.getResource("provider/write.jpg").getPath();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        OutputResource resource = new OutputResource(source,"write.jpg",false);
        resource.write(out);
        Assert.assertEquals(335961, out.size());
    }
    
    @Test
    public void tesContentWrite()throws IOException{
        byte[] content = new byte[100];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        OutputResource resource = new OutputResource(content,"write.jpg");
        resource.write(out);
        Assert.assertEquals(100, out.size());
    }
    
    @Test
    public void testContentClose()throws IOException{
        byte[] content = new byte[100];
        OutputResource resource = new OutputResource(content,"write.jpg");
        resource.close();
        
        Assert.assertNull(resource.content);
        Assert.assertTrue(resource.getSize()==0L);
    }
    
    @Test
    public void testTempFileClose()throws IOException{
        String source = OutputResourceTest.class.getResource("provider/write.jpg").getPath();
        String target = System.getProperty("java.io.tmpdir","/tmp") + "/test_temp_close.jpg";
        FileUtils.copyFile(new File(source), new File(target));
        
        OutputResource resource = new OutputResource(target,"write.jpg",true);
        resource.close();
        Assert.assertNull(resource.path);
    }
    
    @Test
    public void testFileClose()throws IOException{
        String source = OutputResourceTest.class.getResource("provider/write.jpg").getPath();
        String target = System.getProperty("java.io.tmpdir","/tmp") + "/test_file_close.jpg";
        FileUtils.copyFile(new File(source), new File(target));
        
        OutputResource resource = new OutputResource(target,"write.jpg",false);
        resource.close();
        Assert.assertNotNull(resource.path);
        Assert.assertTrue((new File(target)).exists());
    }
}
