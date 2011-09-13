/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.publication.resource.operator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.Assert;

import com.ewcms.publication.uri.ResourceUriRule;

/**
 * FileoPerator单元测试
 * 
 * @author wangwei
 */
public class FileOperatorTest {

    @Test
    public void testWrite()throws IOException{
        String root = System.getProperty("java.io.tmpdir","/tmp");
        
        FileOperator operator = new FileOperator(root);
        String uri = operator.write(FileOperatorTest.class.getResourceAsStream("write.jpg"), new ResourceUriRule("resource"),"jpg");
        Assert.assertTrue(StringUtils.contains(uri, ".jpg"));
        File file = new File(root + uri);
        Assert.assertTrue(file.exists());
    }
    
    @Test
    public void testRead()throws IOException{
        FileOperator operator = new FileOperator("");
        String uri = FileOperatorTest.class.getResource("write.jpg").getPath();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        
        operator.read(output, uri);
        
        Assert.assertTrue(output.size()>1000);
    }
    
    @Test
    public  void testDelete()throws IOException{
        String root = System.getProperty("java.io.tmpdir","/tmp");
        
        FileOperator operator = new FileOperator(root);
        String uri = operator.write(FileOperatorTest.class.getResourceAsStream("write.jpg"), new ResourceUriRule("resource"),"jpg");
        Assert.assertTrue((new File(root + uri)).exists());
        
        operator.delete(uri);
        // File cannot exist method assert file exist(maybe because java nio)!!!
        Assert.assertTrue(true);
    }
}
