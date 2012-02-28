/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.publication.task.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ewcms.publication.task.TaskException;
import com.ewcms.publication.task.Taskable;
import com.ewcms.publication.task.impl.process.TaskProcessable;

/**
 * 空的任务
 * 
 * @author wangwei
 */
public class NoneTask implements Taskable{

    @Override
    public String getDescription() {
        return "None task";
    }

    @Override
    public String getUsername() {
        return DEFAULT_USERNAME;
    }

    @Override
    public List<Taskable> getDependenceTasks() {
        return Collections.unmodifiableList(new ArrayList<Taskable>(0));
    }

    @Override
    public String getId() {
        return String.valueOf(Long.MIN_VALUE);
    }

    @Override
    public int getProgress() {
        return 100;
    }

    @Override
    public boolean isCompleted() {
        return true;
    }

    @Override
    public List<TaskProcessable> toTaskProcess() throws TaskException {
        return new ArrayList<TaskProcessable>(0);
    }
}
