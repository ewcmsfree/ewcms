/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.core.site.util;

import java.text.DecimalFormat;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 中文转换成拼音
 * 
 * @author wuzhijun
 *
 */
public class ConvertUtil {
	public static String pinYin(String chinese){
		String pinYin = "";
		
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		
		char[] charArray = chinese.toCharArray();
		try {
			for (int i = 0; i < charArray.length; i++) {
				if (charArray[i] > 128) {
					pinYin += PinyinHelper.toHanyuPinyinStringArray(charArray[i], defaultFormat)[0];
				} else {
					pinYin += charArray[i];
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return pinYin;
	}
	
	public static String kb(long size) {
		DecimalFormat dfom = new DecimalFormat("####.0");
		if (size <= 0)
			return "0 KB";
		return String.valueOf(dfom.format(size / 1000)) + " KB";
	}

}
