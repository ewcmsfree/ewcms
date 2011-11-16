/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.output.provider;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.VFS;
import org.junit.Test;
import org.springframework.util.Assert;

import com.ewcms.core.site.model.SiteServer;
import com.ewcms.publication.output.OutputResource;

/**
 * FtpOutput单元测试
 * 
 * @author wangwei
 */
public class FtpOutputTest {
    
    @Test
    public void testGetTargtRoot()throws Exception{
        FtpOutput out = new FtpOutput();
        SiteServer server = initServer();
        FileSystemOptions opts =out.initOptions(server);;
        FileObject target = out.getTargetRoot(opts,initServer(), VFS.getManager());
        Assert.notNull(target);
        target.close();
    }
    
    @Test
    public void testOut()throws Exception{
        FtpOutput out = new FtpOutput();
        SiteServer server = initServer();
        List<OutputResource> resources = initResources();
        
        out.out(server, resources);
    }
    
    private SiteServer initServer(){
        SiteServer  server = new SiteServer();
        
        server.setHostName("127.0.0.1");
        server.setPort("21");
        server.setUserName("wangwei");
        server.setPassword("hhywangwei");
        String rootPath = System.getProperty("java.io.tmpdir","/tmp");
        server.setPath(rootPath);
        
        return server;
    }
    
    private List<OutputResource> initResources(){
        List<OutputResource> list = new ArrayList<OutputResource>();
        String source = OutputBaseTest.class.getResource("write.jpg").getPath();
        OutputResource resource = new OutputResource(source,"test/ftp/write.jpg",false);
        list.add(resource);
        
        return list;
    }
}
