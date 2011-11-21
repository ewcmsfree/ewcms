/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.report.manager.util;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 图形报表工具集
 * 
 * @author 吴智俊
 */
public class ChartUtil {
	public static Map<String, String> getFontNameMap() {
		Map<String, String> fontNameMap = new TreeMap<String, String>();

		fontNameMap.put("Monospaced", "Monospaced");
		fontNameMap.put("SansSerif", "SansSerif");
		fontNameMap.put("Serif", "Serif");
		fontNameMap.put("宋体", "宋体");

		return fontNameMap;
	}

	public static Map<Integer, String> getFontStyleMap() {
		Map<Integer, String> fontStyleMap = new HashMap<Integer, String>();

		fontStyleMap.put(java.awt.Font.BOLD, "BOLD");
		fontStyleMap.put(java.awt.Font.ITALIC, "ITALIC");
		fontStyleMap.put(java.awt.Font.PLAIN, "PLAIN");

		return fontStyleMap;
	}

	public static Map<Integer, Integer> getFontSizeMap() {
		Map<Integer, Integer> fontSizeMap = new TreeMap<Integer, Integer>();

		for (int i = 5; i < 13; i++) {
			fontSizeMap.put(i, i);
		}
		for (int i = 14; i < 30; i+=2){
			fontSizeMap.put(i,i);
		}
		fontSizeMap.put(36, 36);
		fontSizeMap.put(48, 48);
		fontSizeMap.put(72, 72);
		
		return fontSizeMap;
	}

	public static Map<Integer, String> getRotateMap() {
		Map<Integer, String> rotateMap = new TreeMap<Integer, String>();

		rotateMap.put(0, "0°");
		rotateMap.put(30, "30°");
		rotateMap.put(60, "60°");
		rotateMap.put(90, "90°");

		return rotateMap;
	}

	public static Map<Integer, String> getPositionMap() {
		Map<Integer, String> positionMap = new TreeMap<Integer, String>();

		positionMap.put(0, "LEFT");
		positionMap.put(1, "TOP");
		positionMap.put(2, "RIGHT");
		positionMap.put(3, "BOTTOM");

		return positionMap;
	}

	public static Map<Integer, String> getAlignmentMap() {
		Map<Integer, String> alignmentMap = new HashMap<Integer, String>();

		alignmentMap.put(2, "RIGHT");
		alignmentMap.put(3, "LEFT");
		alignmentMap.put(4, "CENTER");

		return alignmentMap;
	}

}
