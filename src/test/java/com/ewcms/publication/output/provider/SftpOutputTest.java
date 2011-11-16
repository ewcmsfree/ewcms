/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.output.provider;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.VFS;
import org.junit.Test;
import org.junit.Assert;

import com.ewcms.core.site.model.SiteServer;
import com.ewcms.publication.PublishException;
import com.ewcms.publication.output.OutputResource;

/**
 * SftpOutput单元测试
 * 
 * @author wangwei
 *
 */
public class SftpOutputTest {

    @Test
    public void testGetTargtRoot()throws Exception{
        SftpOutput out = new SftpOutput();
        FileSystemOptions opts = new FileSystemOptions();
        out.setUserAuthenticator(opts, "wangwei", "hhywangwei");
        FileObject target = out.getTargetRoot(opts,initServer(), VFS.getManager());
        Assert.assertNotNull(target);
        target.close();
    }
    
    @Test
    public void testOut()throws Exception{
        SftpOutput out = new SftpOutput();
        SiteServer server = initServer();
        List<OutputResource> resources = initResources();
        
        out.out(server, resources);
    }
    @Test
    public void testServerAddressError()throws Exception{
        try{
            SftpOutput out = new SftpOutput();
            SiteServer server = initServer();
            server.setHostName("192.168.18.45");
            out.test(server);
        }catch(Exception e){
            Assert.assertTrue(StringUtils.contains(e.getMessage(), "error.output.notconnect"));
        }
    }
    
    @Test
    public void testServerPortError()throws Exception{
        try{
            SftpOutput out = new SftpOutput();
            SiteServer server = initServer();
            server.setPort("111");
            out.test(server);
        }catch(Exception e){
            Assert.assertTrue(StringUtils.contains(e.getMessage(), "error.output.notconnect"));
        }
    }
    
    @Test
    public void testUserAuthenticatorError()throws Exception{
        try{
            SftpOutput out = new SftpOutput();
            SiteServer server = initServer();
            server.setUserName("wangwei1");
            out.test(server);
        }catch(Exception e){
            Assert.assertTrue(StringUtils.contains(e.getMessage(), "error.output.notconnect"));
        }
    }
    
    @Test
    public void testTestNoDir()throws Exception{
        try{
            SftpOutput out = new SftpOutput();
            SiteServer server = initServer();
            server.setPath("/dddd");
            out.test(server);
        }catch(PublishException e){
           Assert.assertEquals("error.output.nodir", e.getMessage());
        }
    }
    
    @Test
    public void testTestNotWrite()throws Exception{
        try{
            SftpOutput out = new SftpOutput();
            SiteServer server = initServer();
            server.setPath("/usr");
            out.test(server);
        }catch(PublishException e){
           Assert.assertEquals("error.output.nodir", e.getMessage());
        }
    }
    
    private SiteServer initServer(){
        SiteServer  server = new SiteServer();
        
        server.setHostName("127.0.0.1");
        server.setPort("22");
        server.setUserName("wangwei");
        server.setPassword("hhywangwei");
        String rootPath = System.getProperty("java.io.tmpdir","/tmp");
        server.setPath(rootPath);
        
        return server;
    }
    
    private List<OutputResource> initResources(){
        List<OutputResource> list = new ArrayList<OutputResource>();
        String source = OutputBaseTest.class.getResource("write.jpg").getPath();
        OutputResource resource = new OutputResource(source,"test/sftp/write.jpg",false);
        list.add(resource);
        
        return list;
    }
}
