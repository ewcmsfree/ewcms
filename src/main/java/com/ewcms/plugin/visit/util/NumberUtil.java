package com.ewcms.plugin.visit.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 
 * @author wu_zhijun
 *
 */
public class NumberUtil {
	/**
	 * 百分比
	 * 
	 * @param number 除数
	 * @param beNumber 被除数
	 * @return String
	 */
	public static String percentage(Long number, Long beNumber){
		String rate = "0.00%";
		try{
			if (beNumber != 0) {
				DecimalFormat df = new DecimalFormat("#0.00%");
				rate = df.format((number*1.0)/(beNumber*1.0));
			}
		} catch (Exception e){
		}
		return rate;
	}
	
	public static double round (double v, int scale) {
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, 4).doubleValue();
	}
}
