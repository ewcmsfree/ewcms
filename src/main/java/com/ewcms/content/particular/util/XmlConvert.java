/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public final class XmlConvert {
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> importXML(File xmlFile){
		if (xmlFile != null) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			try {
				List<InputStream> inputStreams = new ArrayList<InputStream>();
				FileType fileType = FileTypeJudge.getType(xmlFile);
				if (fileType == FileType.ZIP || fileType == FileType.RAR){
					inputStreams = parseXmlZIPFile(xmlFile);
				}else if (fileType == FileType.XML){
					InputStream in = new FileInputStream(xmlFile);
					inputStreams.add(in);
				}else{
					return null;
				}
				if (!inputStreams.isEmpty()){
					SAXReader reader = new SAXReader();
					Document doc = null;
					for (InputStream inputStream : inputStreams){
						try {
							doc = reader.read(inputStream);
							if (doc != null){
								Element root = doc.getRootElement();
								Element metaViewData;
								Element projecties;
								for (Iterator<?> metaViewDataIterator = root.elementIterator("MetaViewData"); metaViewDataIterator.hasNext();) {
									metaViewData = (Element) metaViewDataIterator.next();
									for (Iterator<?> projectiesIterator = metaViewData.elementIterator("PROPERTIES"); projectiesIterator.hasNext();){
										projecties = (Element) projectiesIterator.next();
										List<Element> elements = projecties.elements();
										if (!elements.isEmpty()){
											Map<String, Object> map = new HashMap<String, Object>();
											for (Element element : elements){
												map.put(element.getName(), element.getData());
											}
											list.add(map);
										}
									}
								}
							}
						} catch (DocumentException e) {
						} finally{
							if (doc != null){
								doc.clearContent();
								doc = null;
							}
						}
					}
				}
			} catch (IOException e) {
				return null;
			}
			return list;
		}
		return null;
	}
	
	private static List<InputStream> parseXmlZIPFile(File zipFile) {
		List<InputStream> inputStreams = new ArrayList<InputStream>();
		try {
			ZipFile zfile = new ZipFile(zipFile);
			Enumeration<? extends ZipEntry> zipEntries = zfile.entries();
			ZipEntry zipEntry = null;
			while (zipEntries.hasMoreElements()) {
				try {
					zipEntry = (ZipEntry) zipEntries.nextElement();
					inputStreams.add(zfile.getInputStream(zipEntry));
				} catch (Exception e) {
				} finally{
				}
			}
		} catch (Exception e) {
		} finally{
		}
		return inputStreams;
	}

}
