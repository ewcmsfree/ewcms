/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.task.publish;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.core.site.model.OutputType;
import com.ewcms.core.site.model.SiteServer;
import com.ewcms.publication.deploy.DeployOperatorable;
import com.ewcms.publication.task.Taskable;
import com.ewcms.publication.task.impl.event.TaskEventable;
import com.ewcms.publication.task.impl.process.TaskProcessable;

/**
 * 单元测试
 * 
 * @author wangwei
 */
public class SitePublishTest {
 private static final Logger logger = LoggerFactory.getLogger(SitePublishTest.class);
    
    @Test
    public void testPublish()throws Exception{
        SiteServer server = initSiteServer();
        SitePublish publish = new SitePublish(server);
        Taskable task = mock(Taskable.class);
        when(task.toTaskProcess()).thenReturn(initTaskProcesses("single"));
        publish.publish(task);
    }
    
    protected List<TaskProcessable> initTaskProcesses(String dir)throws IOException{
        List<TaskProcessable> processes = new ArrayList<TaskProcessable>();
        for(int i = 0 ; i < 1000 ; i++){
            byte[] content = getContent();
            String path = String.format("%s/%d.jpg",dir, i);
            TaskProcess process = new TaskProcess(i,content,path);
            processes.add(process);
        }
        return processes;
    }
    
    private byte[] getContent()throws IOException{
        File file = new File(MultiSitePublishTest.class.getResource("write.jpg").getPath());
        return FileUtils.readFileToByteArray(file);
    }
    
    protected SiteServer initSiteServer(){
        SiteServer server = new SiteServer();
        server.setId(Integer.MAX_VALUE);
        server.setHostName("127.0.0.1");
        server.setPath("/home/wangwei/test");
        server.setUserName("wangwei");
        server.setPassword("hhywangwei");
        server.setOutputType(OutputType.SFTP);
        
        return server;
    }
    
    protected class TaskProcess implements TaskProcessable{
        private final Integer id;
        private final byte[] content;
        private final String path;
        
        TaskProcess(Integer id,byte[] content,String path){
            this.id = id;
            this.content = content;
            this.path = path;
        }
        
        @Override
        public Boolean execute(DeployOperatorable operator) {
            logger.debug("id={} start",id);
            Boolean success = Boolean.TRUE;
//            try{
//                operator.copy(content, path);
//            }catch(PublishException e){
//                success = Boolean.FALSE;
//            }
            logger.debug("id={} end",id);
            return success;
        }

        @Override
        public void registerEvent(TaskEventable event) {
            // do not instance
        }
    }
}
