/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.output.provider;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;

import org.apache.commons.vfs2.FileContent;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.UserAuthenticator;
import org.apache.commons.vfs2.impl.DefaultFileSystemConfigBuilder;
import org.junit.Assert;
import org.junit.Test;

import com.ewcms.publication.PublishException;
import com.ewcms.publication.output.DeployOperatorable;

/**
 * DeployOperatorBase单元测试
 * 
 * @author wangwei
 */
public class DeployOperatorBaseTest {

    
   @Test
    public void testSetAuthenticator()throws Exception{
        DeployOperatorable operator = new DeployOperatorBaseImpl.Builder().build();
        
        FileSystemOptions opts = new FileSystemOptions();
        DeployOperatorBase operatorBase = (DeployOperatorBase)operator;
        operatorBase.setAuthenticator(opts, "user", "password");
        UserAuthenticator auth = DefaultFileSystemConfigBuilder.getInstance().getUserAuthenticator(opts);
        Assert.assertNotNull(auth);
    }
   
   @Test
   public void testUsernameIsNullSetAuthenticator()throws Exception{
       DeployOperatorable operator = new DeployOperatorBaseImpl.Builder().build();
       
       FileSystemOptions opts = new FileSystemOptions();
       DeployOperatorBase operatorBase = (DeployOperatorBase)operator;
       operatorBase.setAuthenticator(opts, null, null);
       UserAuthenticator auth = DefaultFileSystemConfigBuilder.getInstance().getUserAuthenticator(opts);
       Assert.assertNull(auth);
   }
    
    @Test
    public void testGetFullTargetPath(){
        DeployOperatorable operator = new DeployOperatorBaseImpl.Builder().build();
        DeployOperatorBase operatorBase = (DeployOperatorBase)operator;
        String root = "root";
        String path = "test/index.html";
        
        String target = operatorBase.targetFullPath(root, path);
        String excepted = "/root/test/index.html";
        Assert.assertEquals(excepted, target);
        
        root = "root/";
        path = "test//index.html";
        target = operatorBase.targetFullPath(root, path);
        Assert.assertEquals(excepted, target);
        
        root = "/root/";
        path = "/test/index.html";
        target = operatorBase.targetFullPath(root, path);
        Assert.assertEquals(excepted, target);
    }
    
    @Test
    public void testCopyFilePath()throws Exception{
        
        String rootPath = System.getProperty("java.io.tmpdir","/tmp");
        DeployOperatorable operator = 
            new DeployOperatorBaseImpl.Builder().setPath(rootPath).build();
        DeployOperatorBaseImpl operatorImpl = (DeployOperatorBaseImpl)operator;
        
        String source = DeployOperatorBaseTest.class.getResource("write.jpg").getPath();
        operatorImpl.copy(source, "/docuemnt/1/write.jpg");
        FileObject fileObject = operatorImpl.getFileObject();
        
        ByteArrayOutputStream content = (ByteArrayOutputStream)fileObject.getContent().getOutputStream();
        Assert.assertEquals(335961 , content.size());
    }
    
    @Test
    public void testCopyFileBytes()throws Exception{
        
        String rootPath = System.getProperty("java.io.tmpdir","/tmp");
        DeployOperatorable operator = 
            new DeployOperatorBaseImpl.Builder().setPath(rootPath).build();
        DeployOperatorBaseImpl operatorImpl = (DeployOperatorBaseImpl)operator;
        
        String content = "test-byte";
        operatorImpl.copy(content.getBytes(), "/docuemnt/2/write.jpg");
        FileObject fileObject = operatorImpl.getFileObject();
        
        ByteArrayOutputStream stream = (ByteArrayOutputStream)fileObject.getContent().getOutputStream();
        Assert.assertEquals(content.getBytes().length , stream.size());
    }
    
    @Test
    public void testFileObjectExistDelete()throws Exception{
        String rootPath = System.getProperty("java.io.tmpdir","/tmp");
        DeployOperatorable operator = 
            new DeployOperatorBaseImpl.Builder().setPath(rootPath).build();
        DeployOperatorBaseImpl operatorImpl = (DeployOperatorBaseImpl)operator;
        
        FileObject fileObject = operatorImpl.getFileObject();
        when(fileObject.exists()).thenReturn(true);
        
        operatorImpl.delete("/document/1/test.html");
        
        verify(fileObject,atLeastOnce()).delete();
    }
    
    @Test
    public void testFileObjectNotExistDelete()throws Exception{
        String rootPath = System.getProperty("java.io.tmpdir","/tmp");
        DeployOperatorable operator = 
            new DeployOperatorBaseImpl.Builder().setPath(rootPath).build();
        DeployOperatorBaseImpl operatorImpl = (DeployOperatorBaseImpl)operator;
        
        FileObject fileObject = operatorImpl.getFileObject();
        when(fileObject.exists()).thenReturn(false);
        
        operatorImpl.delete("/document/1/test.html");
        
        verify(fileObject,never()).delete();
    }
    
    @Test
    public void testTestSuccess()throws Exception{
        String rootPath = System.getProperty("java.io.tmpdir","/tmp");
        DeployOperatorable operator = 
            new DeployOperatorBaseImpl.Builder().setPath(rootPath).build();
        DeployOperatorBaseImpl operatorImpl = (DeployOperatorBaseImpl)operator;
        
        FileObject fileObject = operatorImpl.getRootFileObject();
        when(fileObject.exists()).thenReturn(true);
        when(fileObject.isWriteable()).thenReturn(true);
        
        Assert.assertTrue(operatorImpl.test());
    }
    
    @Test
    public void testDirNotConnectionSuccess()throws Exception{
        String rootPath = System.getProperty("java.io.tmpdir","/tmp");
        DeployOperatorable operator = 
            new DeployOperatorBaseImpl.Builder(true).setPath(rootPath).build();
        DeployOperatorBaseImpl operatorImpl = (DeployOperatorBaseImpl)operator;
        
        try{
            Assert.assertTrue(operatorImpl.test());
            Assert.fail();
        }catch(PublishException e){
            Assert.assertEquals(e.getMessage(), "error.output.notconnect");
        }
    }
    
    @Test
    public void testDirNotExistTest()throws Exception{
        String rootPath = System.getProperty("java.io.tmpdir","/tmp");
        DeployOperatorable operator = 
            new DeployOperatorBaseImpl.Builder().setPath(rootPath).build();
        DeployOperatorBaseImpl operatorImpl = (DeployOperatorBaseImpl)operator;
        
        FileObject fileObject = operatorImpl.getRootFileObject();
        when(fileObject.exists()).thenReturn(false);
        when(fileObject.isWriteable()).thenReturn(true);
        
        try{
            Assert.assertTrue(operatorImpl.test());
            Assert.fail();
        }catch(PublishException e){
            Assert.assertEquals(e.getMessage(), "error.output.nodir");
        }
    }
    
    @Test
    public void testDirNotWriteableTest()throws Exception{
        String rootPath = System.getProperty("java.io.tmpdir","/tmp");
        DeployOperatorable operator = 
            new DeployOperatorBaseImpl.Builder().setPath(rootPath).build();
        DeployOperatorBaseImpl operatorImpl = (DeployOperatorBaseImpl)operator;
        
        FileObject fileObject = operatorImpl.getRootFileObject();
        when(fileObject.exists()).thenReturn(true);
        when(fileObject.isWriteable()).thenReturn(false);
        
        try{
            Assert.assertTrue(operatorImpl.test());
            Assert.fail();
        }catch(PublishException e){
            Assert.assertEquals(e.getMessage(), "error.output.notwrite");
        }
    }
    
   static class DeployOperatorBaseImpl extends  DeployOperatorBase{

       private FileObject fileObject = mock(FileObject.class);
       private FileObject rootFileObject = mock(FileObject.class);
       private static boolean getRootFileObjectIsException;
       
        public static class Builder extends BuilderBase{
            public Builder(){
                this(false);
            }
            
            public Builder(boolean exception){
                getRootFileObjectIsException =exception;
            }
            
            @Override
            public DeployOperatorable build() {
                return new DeployOperatorBaseImpl(this);
            }
        }
        
        public DeployOperatorBaseImpl(BuilderBase builder) {
            super(builder);
        }

        @Override
        protected FileObject getTargetFileObject(FileObject root,String path)throws FileSystemException{
            FileContent content = mock(FileContent.class);
            content.setAttribute("path",path);
            when(content.getOutputStream()).thenReturn(new ByteArrayOutputStream());
            when(fileObject.getContent()).thenReturn(content);
            return fileObject;
        }
        
        @Override
        protected FileObject getRootFileObject(FileSystemOptions opts,BuilderBase builder,FileSystemManager manager) throws FileSystemException {
            if(getRootFileObjectIsException){
                throw new FileSystemException("mock exception");
            }
            return rootFileObject;
        }
        
        public FileObject getFileObject(){
            return fileObject;
        }
        
        public FileObject getRootObject(){
            return rootFileObject;
        }
    }
}
