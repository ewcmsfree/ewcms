/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.output.provider;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.VFS;
import org.junit.Test;
import org.springframework.util.Assert;

import com.ewcms.core.site.model.SiteServer;
import com.ewcms.publication.output.OutputResource;

/**
 * LocalOutput单元测试
 * 
 * @author wangwei
 */
public class LocalOutputTest {
    
    @Test
    public void testGetTargtRoot()throws Exception{
        LocalOutput out = new LocalOutput();
        FileObject target = out.getTargetRoot(null,initServer(), VFS.getManager());
        Assert.notNull(target);
        target.close();
    }
    
    @Test
    public void testOut()throws Exception{
        LocalOutput out = new LocalOutput();
        SiteServer server = initServer();
        List<OutputResource> resources = initResources();
        
        out.out(server, resources);
    }
    
    private SiteServer initServer(){
        SiteServer  server = new SiteServer();
        
        String rootPath = System.getProperty("java.io.tmpdir","/tmp");
        server.setPath(rootPath);
        
        return server;
    }
    
    private List<OutputResource> initResources(){
        List<OutputResource> list = new ArrayList<OutputResource>();
        String source = OutputBaseTest.class.getResource("write.jpg").getPath();
        OutputResource resource = new OutputResource(source,"/home/wangwei/test/ftp/write.jpg"){
            @Override
            public void close(){
                //Don't remove source file
            }
        };
        list.add(resource);
        
        return list;
    }
}
