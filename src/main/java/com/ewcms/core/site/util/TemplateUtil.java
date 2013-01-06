package com.ewcms.core.site.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateUtil {

	private final static String REGEX_HTML_TAG = "<\\s*@\\s*([^>]*)\\s*>";
	private final static String REGEX_CHANNEL_ATTRIB = "((channel)\\s*=\\s*(\"([^\"]+)\"))|((channel)\\s*=\\s*(\\d+))|((channel)\\s*=\\s*(\\[([^\\]]+)\\]))|((channel)\\s*=\\s*(\'([^\']+)\'))";
	private final static String REGEX_CHILD_ATTRIB = "(child)\\s*=\\s*(true)";

	private final static Pattern pattern_tag = Pattern.compile(REGEX_HTML_TAG);
	private final static Pattern pattern_channel_attrib = Pattern.compile(REGEX_CHANNEL_ATTRIB);
	private final static Pattern pattern_child_attrib = Pattern.compile(REGEX_CHILD_ATTRIB);

	public final static String CHILD_ATTRIB = ":true";

	public static List<String> associate(String content) {
		List<String> result = new ArrayList<String>();

		Matcher matcher_tag = pattern_tag.matcher(content);
		while (matcher_tag.find()) {
			String tag = matcher_tag.group();
			Matcher matcher_channel_attrib = pattern_channel_attrib.matcher(tag);

			String channel_attrib = "";
			String child_attrib = "";
			if (matcher_channel_attrib.find()) {
				channel_attrib = matcher_channel_attrib.group().trim().replace("channel=", "");
			}

			Matcher matcher_child_attrib = pattern_child_attrib.matcher(tag);
			if (matcher_child_attrib.find()) {
				child_attrib = matcher_child_attrib.group().trim();
			}

			if (channel_attrib == null || channel_attrib.isEmpty()) continue;
			if (child_attrib != null && !child_attrib.isEmpty()) child_attrib = CHILD_ATTRIB;

			if (!isNumeric(channel_attrib)){
				channel_attrib = channel_attrib.replace("\"", "").replace("\'", "");
			}
			
			result.add(channel_attrib + child_attrib);
		}

		return result;
	}
	
	public static boolean verify(String content){
		return true;
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
}
