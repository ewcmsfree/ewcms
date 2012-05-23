package com.ewcms.content.particular.util;

import java.io.File;

import org.junit.Test;

public class XmlConvertTest {
//	@Test
//	public void testImportXML(){
//		String filePath = "E:/work/workspace/ewcms/src/test/resources/xmjbxx.xml";
//        File file = new File(filePath);
//        XmlConvert.importXML(file);
//	}
	
	@Test
	public void testImportZip(){
		String filePath = "E:/work/workspace/ewcms/src/test/resources/xmjbxx.zip";
        File file = new File(filePath);
        XmlConvert.importXML(file);
	}
}
