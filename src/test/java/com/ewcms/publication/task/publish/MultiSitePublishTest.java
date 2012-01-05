/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.task.publish;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.ewcms.core.site.model.SiteServer;
import com.ewcms.publication.task.Taskable;

/**
 * MultiSitePublish单元测试
 * </p>
 * 该测试需要多线程支持
 * 
 * @author wangwei
 */
public class MultiSitePublishTest extends SitePublishTest {
    
    @Test
    public void testPublish()throws Exception{
        SiteServer server = initSiteServer();
        SiteMultiPublish publish = new SiteMultiPublish(server);
        Taskable task = mock(Taskable.class);
        when(task.execute()).thenReturn(initTaskProcesses("multi"));
        publish.publish(task);
    }
}
