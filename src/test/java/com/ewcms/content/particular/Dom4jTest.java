/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

/**
 * 
 * @author wuzhijun
 * 
 */
public class Dom4jTest {
	@Test
	public void testParser() throws DocumentException {
		String filePath = "../ewcms/src/test/resources/xmjbxx.xml";
        File file = new File(filePath);
        
		SAXReader reader = new SAXReader();
		Document doc = reader.read(file);
		Element root = doc.getRootElement();
		Element metaViewData;
		Element projecties;
		for (Iterator<?> metaViewDataIterator = root.elementIterator("MetaViewData"); metaViewDataIterator.hasNext();) {
			metaViewData = (Element) metaViewDataIterator.next();
			for (Iterator<?> projectiesIterator = metaViewData.elementIterator("PROPERTIES"); projectiesIterator.hasNext();){
				projecties = (Element) projectiesIterator.next();
				List<Element> list = projecties.elements();
				for (Element a : list){
					System.out.println(a.getName() + " : " + a.getText()); 
				}
			}
		}
	}
}
