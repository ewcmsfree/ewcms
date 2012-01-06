/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.task.impl.process;

import com.ewcms.publication.PublishException;
import com.ewcms.publication.deploy.DeployOperatorable;
import com.ewcms.publication.task.impl.event.NoneEvent;
import com.ewcms.publication.task.impl.event.TaskEventable;

/**
 * 实现任务过程
 * 
 * @author wangwei
 */
public abstract class TaskProcessBase implements TaskProcessable{
    private TaskEventable event =new NoneEvent();
    
    @Override
    public Boolean execute(DeployOperatorable operator){
        try{
            String uri = process(operator);
            event.success(uri);
            return Boolean.TRUE;
        }catch(Exception e){
            event.error(e);
            return Boolean.FALSE;
        }
    }

    /**
     * 执行发布过程
     * 
     * @param operator 发布操作对象
     * @return 发布资源路径
     * @throws PublishException
     */
    protected abstract String process(DeployOperatorable operator)throws PublishException;
    
    @Override
    public void registerEvent(TaskEventable event) {
        this.event = event;
    }
}
