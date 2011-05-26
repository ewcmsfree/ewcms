/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.security.manage.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * Test {@link AbstractService}
 * 
 * @author wangwei
 */
public class AbstractServiceTest extends TestCase{

    @Test
    public void testSyncCollection(){
        Set<String> sources = new HashSet<String>();
        sources.add("1");
        sources.add("2");
        sources.add("4");
        
        Set<String> targets = new HashSet<String>();
        targets.add("1");
        targets.add("2");
        targets.add("3");
        
        SimpleService service = new SimpleService();
        Collection<String> defference = service.syncCollection(targets, sources);
        assertTrue(defference.size() == 2);
        assertTrue(targets.size() == 3);
        assertTrue(targets.contains("4"));
    }
    
    class SimpleService extends AbstractService{
        //实例化抽象类测试
    }
}
