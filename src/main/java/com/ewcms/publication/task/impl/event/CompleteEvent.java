/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.task.impl.event;

import org.springframework.util.Assert;

import com.ewcms.publication.task.Taskable;

/**
 * 实现完成事件
 * 
 * @author wangwei
 */
public class CompleteEvent implements TaskEventable{

    protected final Taskable task;
    
    public CompleteEvent(Taskable task){
        Assert.notNull(task,"Task is null");
        this.task = task;
    }
    
    @Override
    public void success(String uri) {
        task.completeProcess();
        successAfter(uri);
    }

    /**
     * 成功后处理过程
     */
    protected void successAfter(String uri){
        
    }
    
    @Override
    public void error(Throwable e) {
        task.completeProcess();
        errorAfter(e);
    }
    
    /**
     * 错误后处理过程
     * 
     * @param e 错误对象
     */
    protected void errorAfter(Throwable e){
        
    }
}
