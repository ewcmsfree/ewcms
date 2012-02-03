/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.task.publish;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.core.site.model.SiteServer;
import com.ewcms.publication.deploy.DeployOperatorable;
import com.ewcms.publication.task.TaskException;
import com.ewcms.publication.task.Taskable;
import com.ewcms.publication.task.impl.process.TaskProcessable;

/**
 * 实现单线程站点任务发布
 * 
 * @author wangwei
 */
public class SitePublish implements SitePublishable{
    private static final Logger logger = LoggerFactory.getLogger(SitePublish.class);
    
    protected final DeployOperatorable operator;
    private volatile boolean cancelled = false;

    public SitePublish(SiteServer server) {
        this.operator = server.getOutputType().deployOperator(server);
    }

    /**
     * 执行发布任务
     * 
     * @param task 任务对象
     * @throws TaskException
     */
    protected void execute(Taskable task)throws TaskException{
        List<TaskProcessable> processes = task.toTaskProcess();
        for(TaskProcessable process: processes){
            if(cancelled){
                logger.info("Task was closed");
                break;
            }
            Boolean success =process.execute(operator);
            logger.debug("publish success is {}",success);
        }
    }
    
    @Override
    public void publish(Taskable task) throws TaskException {
        cancelled = false;
        List<Taskable> dependences = task.getDependences();
        for(Taskable dependence :  dependences) {
            logger.debug("\"{}\" publish", task.getDescription());
            publish(dependence);
        }
        if(!cancelled){
            execute(task);
        }
    }

    @Override
    public void cancelPublish() {
        cancelled = true;
    }
}
