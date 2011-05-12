/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.core.document.search.util;

import java.io.StringReader;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.html.dom.HTMLDocumentImpl;
import org.cyberneko.html.parsers.DOMFragmentParser;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.html.HTMLDocument;
import org.xml.sax.InputSource;

/**
 * 字符操作
 * 
 * @author 吴智俊
 */
public class StringUtil {
	public static Pattern patternHtmlTag = Pattern.compile("<[^<>]+>", 32);

	public static String clearHtmlTag(String html) {
		String text = patternHtmlTag.matcher(html).replaceAll("");
		if (isEmpty(text)) {
			return "";
		} else {
			text = htmlDecode(html);
			return text.replaceAll("[\\s\u3000]{2,}", " ");
		}
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static String htmlDecode(String txt) {
		txt = replaceEx(txt, "&#8226;", "\267");
		return StringEscapeUtils.unescapeHtml(txt);
	}

	public static String replaceEx(String str, String subStr, String reStr) {
		if (str == null)
			return null;
		if (subStr == null || subStr.equals("")
				|| subStr.length() > str.length() || reStr == null)
			return str;
		StringBuffer sb = new StringBuffer();
		int lastIndex = 0;
		do {
			int index = str.indexOf(subStr, lastIndex);
			if (index >= 0) {
				sb.append(str.substring(lastIndex, index));
				sb.append(reStr);
				lastIndex = index + subStr.length();
			} else {
				sb.append(str.substring(lastIndex));
				return sb.toString();
			}
		} while (true);
	}

	public static String getPureText(String html) {
		try {
			DOMFragmentParser parser;
			org.w3c.dom.DocumentFragment fragment;
			parser = new DOMFragmentParser();
			HTMLDocument document = new HTMLDocumentImpl();
			fragment = document.createDocumentFragment();
			String txt;
			parser.parse(new InputSource(new StringReader(html)), fragment);
			txt = getPureText(((Node) (fragment)));
			return htmlDecode(txt);
		} catch (Exception e) {
			System.out.println("XML中存在非法字符!");
			return null;
		}
	}

	public static String getPureText(Node node) {
		if (!node.hasChildNodes() && isTextNode(node))
			return node.getNodeValue();
		if (isFiltered(node))
			return "";
		if (node.hasAttributes()) {
			Node a = node.getAttributes().getNamedItem("style");
			if (a != null) {
				String style = a.getNodeValue();
				Pattern p = Pattern.compile("display\\s*\\:\\s*none", 2);
				if (p.matcher(style).find())
					return "";
			}
		}
		StringBuffer sb = new StringBuffer();
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			Node child = list.item(i);
			String name = child.getNodeName();
			sb.append(getPureText(child));
			sb.append(" ");
			if (name.equals("TR") || name.equals("P") || name.equals("DIV"))
				sb.append("\n");
		}

		return sb.toString();
	}

	public static boolean isTextNode(Node node) {
		if (node == null)
			return false;
		short nodeType = node.getNodeType();
		return nodeType == 4 || nodeType == 3;
	}

	private static boolean isFiltered(Node node) {
		short type = node.getNodeType();
		String name = node.getNodeName();
		if (type == 8)
			return true;
		if (name.equals("SCRIPT"))
			return true;
		if (name.equals("LINK"))
			return true;
		if (name.equals("STYLE"))
			return true;
		return name.equals("OBJECT");
	}

	public static String hexEncode(byte bs[]) {
		return new String((new Hex()).encode(bs));
	}

	public static String join(Object arr[]) {
		return join(arr, ",");
	}

	public static String join(Object arr[][]) {
		return join(arr, "\n", ",");
	}

	public static String join(Object arr[], String spliter) {
		if (arr == null)
			return null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (i != 0)
				sb.append(spliter);
			sb.append(arr[i]);
		}

		return sb.toString();
	}

	public static String join(Object arr[][], String spliter1, String spliter2) {
		if (arr == null)
			return null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (i != 0)
				sb.append(spliter2);
			sb.append(join(arr[i], spliter2));
		}

		return sb.toString();
	}

	public static String join(List<String> list) {
		return join(list, ",");
	}

	public static String join(List<String> list, String spliter) {
		if (list == null)
			return null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			if (i != 0)
				sb.append(spliter);
			sb.append(list.get(i));
		}

		return sb.toString();
	}

}
