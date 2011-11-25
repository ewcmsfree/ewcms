package com.ewcms.plugin.report.generate.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalysisUtilTest {

	private static final Logger logger = LoggerFactory.getLogger(AnalysisUtilTest.class);
	
	@Test
	public void testAnalysis() throws Exception {
		String au = "1-3,a=b,c=d,d=4,3424834790-dfsfdsfsf,hhfhh";
		Map<String, String> auMap = AnalysisUtil.analysis(au);
		Iterator<Entry<String, String>> i = auMap.entrySet().iterator();
		while (i.hasNext()){
			Map.Entry<String, String> e = (Map.Entry<String, String>)i.next();
			logger.info(e.getKey() + "=" + e.getValue());
		}
	}
}
