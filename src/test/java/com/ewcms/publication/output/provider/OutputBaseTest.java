/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.output.provider;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.vfs2.FileContent;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.UserAuthenticator;
import org.apache.commons.vfs2.impl.DefaultFileSystemConfigBuilder;
import org.junit.Assert;
import org.junit.Test;

import com.ewcms.core.site.model.SiteServer;
import com.ewcms.publication.output.OutputResource;
import com.ewcms.publication.output.event.OutputEventable;

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
    public void testOutResources()throws Exception{
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        OutputBaseImpl output = new OutputBaseImpl(){
            
            int count = 0;
            @Override
            protected FileObject getTargetFileObject(FileObject root,String path)throws FileSystemException{
                assertTargetPath(++count,path);   
                FileObject fileObject = mock(FileObject.class);
                FileContent content = mock(FileContent.class);
                content.setAttribute("path",path);
                when(content.getOutputStream()).thenReturn(out);
                when(fileObject.getContent()).thenReturn(content);
                return fileObject;
            }
            
            private void assertTargetPath(Integer count,String path){
                Assert.assertEquals("/test/docuemnt/"+count.toString()+"/index.html", path);
            }
            
        };
        
        List<OutputResource> resources = initOutResources();
        output.outResources(null, "test", resources);
        Assert.assertEquals(335961 * 2, out.size());
    }
    
    @Test
    public void testOutResourcesException()throws Exception{
        OutputBaseImpl output = new OutputBaseImpl();
        List<OutputResource> resources = initOutResources();
                
        OutputEventable rootEvent = mock(OutputEventable.class);
        OutputResource r = resources.get(0);
        r.registerEvent(rootEvent);
        
        OutputEventable child1Event = mock(OutputEventable.class);
        r.getChildren().get(0).registerEvent(child1Event);
        
        r.getChildren().remove(1);
        NullCloseOutputResource newChild = new NullCloseOutputResource("/noFile/noExist","/docuemnt/2/index.html");
        OutputEventable child2Event = mock(OutputEventable.class);
        newChild.registerEvent(child2Event);
        r.getChildren().add(newChild);
        
        output.outResources(null, "test", resources);
        
        verify(rootEvent,never()).success();
        verify(rootEvent).error(eq("子资源发布错误:/docuemnt/2/index.html"), any(Throwable.class));
        
        verify(child1Event).success();
        verify(child1Event,never()).error(any(String.class), any(Throwable.class));
        
        verify(child2Event,never()).success();
        verify(child2Event).error(eq("发布错误:/docuemnt/2/index.html"), any(Throwable.class));
    }
    
    private List<OutputResource> initOutResources(){
        
        List<OutputResource> list = new ArrayList<OutputResource>();
        NullCloseOutputResource root = new NullCloseOutputResource();
        list.add(root);
        
        List<OutputResource> children = new ArrayList<OutputResource>();
        String source = OutputBaseTest.class.getResource("write.jpg").getPath();
        children.add(new NullCloseOutputResource(source,"/docuemnt/1/index.html"));
        children.add(new NullCloseOutputResource(source,"/docuemnt/2/index.html"));
        root.setChildren(children);
        
        return list;
    }
    
    static class OutputBaseImpl extends  OutputBase{

        @Override
        protected FileObject getTargetFileObject(FileObject root,String path)throws FileSystemException{
            FileObject fileObject = mock(FileObject.class);
            FileContent content = mock(FileContent.class);
            content.setAttribute("path",path);
            when(content.getOutputStream()).thenReturn(new ByteArrayOutputStream());
            when(fileObject.getContent()).thenReturn(content);
            return fileObject;
        }
        
        @Override
        protected FileObject getTargetRoot(FileSystemOptions opts,SiteServer server,FileSystemManager manager) throws FileSystemException {
            return null;
        }
    }
    
    /**
     * 把OutputResource Close方法清空
     * 
     * @author wangwei
     */
    static class NullCloseOutputResource extends OutputResource{
        
        public NullCloseOutputResource() {
            super();
        }

        public NullCloseOutputResource(byte[] content, String uri) {
            super(content, uri);
        }

        public NullCloseOutputResource(String path, String uri, long size) {
            super(path, uri, false,size);
        }

        public NullCloseOutputResource(String path, String uri) {
            super(path, uri,false);
        }

        @Override
        public void close(){
           // none
        }
    }
}
