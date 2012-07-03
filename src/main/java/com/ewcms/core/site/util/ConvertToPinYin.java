package com.ewcms.core.site.util;

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
public class ConvertToPinYin {
	public static String covert(String chinese){
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
}
