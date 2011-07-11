package com.ewcms.generator.output.provider;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.vfs.FileObject;
import org.junit.Test;
import org.springframework.util.Assert;

import com.ewcms.core.site.model.SiteServer;
import com.ewcms.generator.output.OutputResource;

public class LocalOutputTest {
    
    @Test
    public void testGetTargtRoot()throws Exception{
        LocalOutput out = new LocalOutput();
        FileObject target = out.getTargetRoot(initServer(), SftpOutput.DEFAULT_FILE_SYSTEM_MANAGER);
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
        OutputResource resource = new OutputResource(source,"vfs/local/write.jpg");
        list.add(resource);
        
        return list;
    }
}
