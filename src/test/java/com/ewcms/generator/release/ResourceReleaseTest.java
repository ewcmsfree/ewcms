/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ewcms.generator.release;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author wangwei
 */
public class ResourceReleaseTest {

    @Test
    public void  testGetDir(){
        ResourceRelease release = new ResourceRelease();
        String fileName = "/home/wangwei/html/1.jpg";
        String dir = release.getDir(fileName);
        Assert.assertEquals(dir, "/home/wangwei/html");
    }
}
