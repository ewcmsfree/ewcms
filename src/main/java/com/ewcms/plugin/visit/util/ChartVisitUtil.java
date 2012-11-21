/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.visit.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * @author wu_zhijun
 * 
 */
public class ChartVisitUtil {
	public static String getLine2DChart(List<String> categories,
			Map<String, Map<String, Long>> dataSet, int labelCount) {
		StringBuffer xml = new StringBuffer();
		String[] Colors = { "1D8BD1", "F1683C", "2AD62A", "FF0000", "006F00",
				"CCCC00", "0D8ECF", "04D215", "B0DE09", "F8FF01", "FF9E01",
				"FF6600", "814EE6", "F234B0", "FF9966", "0099FF", "993300",
				"333300", "003300", "003366", "000080", "333399", "333333",
				"800000", "FF6600", "808000", "808080", "008080", "0000FF",
				"666699", "808080", "FF9900", "99CC00", "339966", "33CCCC",
				"3366FF", "800080", "999999", "FF00FF", "FFCC00", "FFFF00",
				"00FF00", "00FFFF", "00CCFF", "993366", "C0C0C0", "FF99CC",
				"FFCC99", "FFFF99", "CCFFCC", "CCFFFF", "99CCFF", "CC99FF",
				"FFFFFF" };
		xml.append("<graph lineThickness='0' canvasBorderThickness='0' alternateHGridAlpha='5' canvasBorderColor='666666' divLineColor='ff5904' divLineAlpha='20' showAlternateHGridColor='1' AlternateHGridColor='ff5904' hovercapbg='FFECAA' hovercapborder='F47E00' formatNumberScale='0' decimalPrecision='0' showvalues='0' numdivlines='4' numVdivlines='0' rotateNames='0' labelDisplay='NONE'>");
		xml.append("<categories>");

		int space = new Double(
				Math.ceil(categories.size() * 0.95D / labelCount)).intValue();
		int count = 0;
		for (int i = 0; i < categories.size(); i++) {
			String key = categories.get(i);
			if (i % space == 0)
				count++;
			xml.append("<category name='"
					+ key
					+ "' "
					+ ((i % space == 0) && (count <= labelCount) ? ""
							: "showName='0'") + "/>");
		}
		xml.append("</categories>");

		Iterator<Entry<String, Map<String, Long>>> itDataSet = dataSet
				.entrySet().iterator();
		int j = 0;
		while (itDataSet.hasNext()) {
			Map.Entry<String, Map<String, Long>> entry = itDataSet.next();
			String seriesName = entry.getKey();
			Map<String, Long> setMap = entry.getValue();
			String color = Colors[((j) % Colors.length)];
			xml.append("<dataset seriesName='" + seriesName + "' color='"
					+ color + "' anchorBorderColor='" + color
					+ "' anchorBgColor='" + color + "'>");
			Iterator<Entry<String, Long>> valueMap = setMap.entrySet()
					.iterator();
			while (valueMap.hasNext()) {
				Map.Entry<String, Long> m = valueMap.next();
				Long total = m.getValue();
				xml.append("<set value='" + total + "'/>");
			}
			xml.append("</dataset>");
			j++;
		}
		xml.append("</graph>");

		return xml.toString();
	}

	public static String getPie3DChart(Map<String, Long> dataSet) {
		StringBuffer xml = new StringBuffer();
		String[] Colors = { "FF0000", "006F00", "CCCC00", "0D8ECF", "04D215",
				"B0DE09", "F8FF01", "FF9E01", "FF6600", "814EE6", "F234B0",
				"FF9966", "0099FF", "993300", "333300", "003300", "003366",
				"000080", "333399", "333333", "800000", "FF6600", "808000",
				"808080", "008080", "0000FF", "666699", "808080", "FF9900",
				"99CC00", "339966", "33CCCC", "3366FF", "800080", "999999",
				"FF00FF", "FFCC00", "FFFF00", "00FF00", "00FFFF", "00CCFF",
				"993366", "C0C0C0", "FF99CC", "FFCC99", "FFFF99", "CCFFCC",
				"CCFFFF", "99CCFF", "CC99FF", "FFFFFF", "1D8BD1", "F1683C",
				"2AD62A" };
		xml.append("<graph baseFontSize=\"12\" showNames=\"1\" hoverCapSepChar=\":\" animation=\"1\" nameTBDistance=\"20\" showPercentageInLabel=\"1\">");
		Iterator<Entry<String, Long>> it = dataSet.entrySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			Map.Entry<String, Long> e = it.next();
			xml.append("<set value=\"" + e.getValue() + "\" name=\""
					+ e.getKey() + "\" color=\"" + Colors[(i % Colors.length)]
					+ "\"/>");
			i++;
		}
		xml.append("</graph>");

		return xml.toString();
	}

	public static String getMixed2DChart(List<String> categories, Map<String, Map<String, String>> dataSet, String yColumn, int labelCount) {
		StringBuffer xml = new StringBuffer();
		String[] Colors = { "F6BD0F", "8BBA00", "FF0000", "2AD62A", "006F00",
				"CCCC00", "0D8ECF", "04D215", "B0DE09", "F8FF01", "FF9E01",
				"FF6600", "814EE6", "F234B0", "FF9966", "0099FF", "993300",
				"333300", "003300", "003366", "000080", "333399", "333333",
				"800000", "FF6600", "808000", "808080", "008080", "0000FF",
				"666699", "808080", "FF9900", "99CC00", "339966", "33CCCC",
				"3366FF", "800080", "999999", "FF00FF", "FFCC00", "FFFF00",
				"00FF00", "00FFFF", "00CCFF", "993366", "C0C0C0", "FF99CC",
				"FFCC99", "FFFF99", "CCFFCC", "CCFFFF", "99CCFF", "CC99FF",
				"FFFFFF" };
		xml.append("<graph lineThickness='0' canvasBorderThickness='0' alternateHGridAlpha='5' canvasBorderColor='666666' divLineColor='ff5904' divLineAlpha='20' showAlternateHGridColor='1' AlternateHGridColor='ff5904' hovercapbg='FFECAA' hovercapborder='F47E00' formatNumberScale='0' decimalPrecision='0' showvalues='0' numdivlines='4' numVdivlines='0' rotateNames='0'>");
		xml.append("<categories>");
		int space = new Double(
				Math.ceil(categories.size() * 0.95D / labelCount)).intValue();
		int count = 0;
		for (int i = 0; i < categories.size(); i++) {
			String key = categories.get(i);
			if (i % space == 0)
				count++;
			xml.append("<category name='"
					+ key
					+ "' "
					+ ((i % space == 0) && (count <= labelCount) ? ""
							: "showName='0'") + "/>");
		}
		xml.append("</categories>");

		Iterator<Entry<String, Map<String, String>>> itDataSet = dataSet
				.entrySet().iterator();

		int j = 0;
		while (itDataSet.hasNext()) {
			Map.Entry<String, Map<String, String>> entry = itDataSet.next();
			String seriesName = entry.getKey();
			Map<String, String> setMap = entry.getValue();
			String color = Colors[((j) % Colors.length)];
			xml.append("<dataset seriesName='" + seriesName + "' color='"
					+ color + "' anchorBorderColor='" + color
					+ "' anchorBgColor='" + color + "'");

			if (seriesName.equalsIgnoreCase(yColumn)) {
				xml.append(" parentYAxis='S'");
			}
			xml.append(">");
			Iterator<Entry<String, String>> valueMap = setMap.entrySet()
					.iterator();
			while (valueMap.hasNext()) {
				Map.Entry<String, String> m = valueMap.next();
				String total = m.getValue();
				xml.append("<set value='" + total + "'/>");
			}
			xml.append("</dataset>");
			j++;
		}
		xml.append("</graph>");

		return xml.toString();
	}
}