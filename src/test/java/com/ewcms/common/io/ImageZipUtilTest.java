/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.common.io;

import java.io.File;
import java.io.FilenameFilter;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author 吴智俊
 */
public class ImageZipUtilTest {

    private static String outDir  ;
    
    @BeforeClass
    public static void beforeClass(){
        outDir = ImageZipUtilTest.class.getResource(".").getPath();
    }
    
    @AfterClass
    public static void afterClass(){
        File dir = new File(outDir);
        File[] files = dir.listFiles(new FilenameFilter(){
            public boolean accept(File dir, String name) {
                return name.indexOf("_zip") > 0;
            }
        });
        for(File file : files){
            file.delete();
        }
    }
    
    @Test
    public void testBmpZip(){
        String source = ImageZipUtilTest.class.getResource("1.bmp").getPath();
        String target = outDir + "1_zip.bmp";
        Boolean isZip = ImageZipUtil.compression(source, target, 64, 64);
        Assert.assertEquals(Boolean.TRUE,isZip);
    }
    
    @Test
    public void testPngZip(){
        String source = ImageZipUtilTest.class.getResource("1.png").getPath();
        String target = outDir + "2_zip.png";
        Boolean isZip = ImageZipUtil.compression(source, target, 64, 64);
        Assert.assertTrue(isZip);
    }
    
    @Test
    public void testJpg1Zip(){
        String source = ImageZipUtilTest.class.getResource("1_0.jpg").getPath();
        String target = outDir + "1_0_zip.jpg";
        Boolean isZip = ImageZipUtil.compression(source, target, 64, 64);
        Assert.assertTrue(isZip);
    }
    
    @Test
    public void testJpg1_1Zip(){
        String source = ImageZipUtilTest.class.getResource("1_1.jpg").getPath();
        String target = outDir + "1_1_zip.jpg";
        Boolean isZip = ImageZipUtil.compression(source, target, 64, 64);
        Assert.assertFalse(isZip);
    }
    
    @Test
    public void testGifZip(){
        String source = ImageZipUtilTest.class.getResource("1.gif").getPath();
        String target = outDir + "1_zip.gif";
        Boolean isZip = ImageZipUtil.compression(source, target, 64, 64);
        Assert.assertTrue(isZip);
    }
}
