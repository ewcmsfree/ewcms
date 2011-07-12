/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.generator.output.provider;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.vfs.FileContent;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.UserAuthenticator;
import org.apache.commons.vfs.impl.DefaultFileSystemConfigBuilder;
import org.junit.Assert;
import org.junit.Test;

import com.ewcms.core.site.model.SiteServer;
import com.ewcms.generator.output.OutputResource;

/**
 * OutputBase单元测试
 * 
 * @author wangwei
 */
public class OutputBaseTest {

    
    @Test
    public void testSetUserAuthenticator()throws Exception{
        OutputBaseImpl output = new OutputBaseImpl();
        FileSystemOptions opts = new FileSystemOptions();
        output.setUserAuthenticator(opts, "username", "password");
        UserAuthenticator auth = DefaultFileSystemConfigBuilder.getInstance().getUserAuthenticator(opts);
        Assert.assertNotNull(auth);
    }
    
    @Test
    public void testGetTargetPath(){
        OutputBaseImpl output = new OutputBaseImpl();
        String root = "root";
        String path = "test/index.html";
        String target = output.getTargetPath(root, path);
        String excepted = "/root/test/index.html";
        Assert.assertEquals(excepted, target);
        
        root = "root/";
        path = "test//index.html";
        target = output.getTargetPath(root, path);
        Assert.assertEquals(excepted, target);
        
        root = "/root/";
        path = "/test/index.html";
        target = output.getTargetPath(root, path);
        Assert.assertEquals(excepted, target);
    }
    
    @Test
    public void testWriteStream()throws Exception{
        InputStream in = OutputBaseTest.class.getResourceAsStream("write.jpg");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        OutputBaseImpl output = new OutputBaseImpl();
        output.writeStream(out,in);
                
        Assert.assertEquals(335961,out.size());
    }
    
    @Test
    public void testOutResources()throws Exception{
        
        OutputBaseImpl output = new OutputBaseImpl(){
            
            int count = 0;
            @Override
            protected FileObject getTargetFileObject(FileObject root,String path)throws FileSystemException{
                assertTargetPath(++count,path);   
                FileObject fileObject = mock(FileObject.class);
                FileContent content = mock(FileContent.class);
                content.setAttribute("path",path);
                when(content.getOutputStream()).thenReturn(new ByteArrayOutputStream());
                when(fileObject.getContent()).thenReturn(content);
                return fileObject;
            }
            
            private void assertTargetPath(Integer count,String path){
                Assert.assertEquals("/test/docuemnt/"+count.toString()+"/index.html", path);
            }
            
            @Override
            protected void writeStream(OutputStream out, InputStream in) throws IOException {
                super.writeStream(out, in);
                int size = ((ByteArrayOutputStream)out).size();
                Assert.assertEquals(335961, size);
            }
        };
        
        List<OutputResource> resources = initOutResources();
        output.outResources(null, "test", resources);
    }
    
    private List<OutputResource> initOutResources(){
        
        List<OutputResource> list = new ArrayList<OutputResource>();
        OutputResource root = new OutputResource();
        list.add(root);
        
        List<OutputResource> children = new ArrayList<OutputResource>();
        String source = OutputBaseTest.class.getResource("write.jpg").getPath();
        children.add(new OutputResource(source,"/docuemnt/1/index.html"));
        children.add(new OutputResource(source,"/docuemnt/2/index.html"));
        root.setChildren(children);
        
        return list;
    }
    
    class OutputBaseImpl extends  OutputBase{

        @Override
        protected FileObject getTargetRoot(FileSystemOptions opts,SiteServer server,FileSystemManager manager) throws FileSystemException {
            return null;
        }
    }
}
