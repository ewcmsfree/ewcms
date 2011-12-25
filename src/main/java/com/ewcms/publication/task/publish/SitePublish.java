/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.task.publish;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.core.site.model.SiteServer;
import com.ewcms.publication.output.DeployOperatorable;
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
    
    protected final SiteServer server;
    protected final DeployOperatorable operator;

    public SitePublish(SiteServer server) {
        this.server=server; 
        this.operator = 
            server.getOutputType().deployOperator(server);
    }

    /**
     * 得到需要发布任务过程
     * 
     *TODO 按照依赖任务递归发布，如果一次查询出所有任务过程，当任务交多时可能出现memory out错误。
     * 
     * @param processes 任务过程集合
     * @param task 任务
     * @throws TaskException
     */
    protected void getTaskProcesses(List<TaskProcessable> processes,Taskable task)throws TaskException{
        for (Taskable dependency : task.getDependences()) {
            getTaskProcesses(processes,dependency);
        }
        processes.addAll(task.execute());
    }
    
    
    @Override
    public void publish(final Taskable task) throws TaskException {
        List<TaskProcessable> processes = new ArrayList<TaskProcessable>();
        getTaskProcesses(processes,task);
        if (processes.isEmpty()) {
            logger.debug("\"{}\" publish is empty",task.getDescription());
            return;
        }
        for(TaskProcessable process: processes){
            Boolean success =process.execute(operator);
            logger.debug("publish success is {}",success);
        }
    }
}
