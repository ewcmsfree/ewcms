/*
 * Copyright (c)2010 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.core.document.search;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

import com.ewcms.analyzer.IKSegmentation;
import com.ewcms.analyzer.Lexeme;
import com.ewcms.analyzer.lucene.IKAnalyzer;
import com.ewcms.analyzer.lucene.IKQueryParser;
import com.ewcms.core.document.search.util.FileUtil;
import com.ewcms.core.document.search.util.NumberUtil;
import com.ewcms.core.document.search.util.StringUtil;

/**
 * 
 * @author 吴智俊
 */
public class ExtractKeywordAndSummary {
	private static String filterWords;
	private static String filterChars;
	
	public static String getTextFromHtml(String html) {
		String text = StringUtil.getPureText(html);
		if (StringUtil.isEmpty(text))
			text = StringUtil.clearHtmlTag(html);
		return text.replaceAll("[\\s\\u0020\u3000]{2,}", " ");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String[] getKeyword(String content) {
		content = getTextFromHtml(content);
		IKSegmentation seg = new IKSegmentation(new StringReader(content));
		LinkedHashMap<String,Integer> map = new LinkedHashMap<String,Integer>();
		ArrayList<Object> list = new ArrayList<Object>();
		try {
			for (Lexeme word = seg.next(); word != null; word = seg.next()) {
				String k = word.getLexemeText();
				if (k != null && k.length() != 1)
					if (map.containsKey(k))
						map.put(k, new Integer(((Integer) map.get(word
								.getLexemeText())).intValue() + 1));
					else
						map.put(k, new Integer(1));
			}
			

			Object ks[] = keyArray(map);
			Object vs[] = valueArray(map);
			ArrayList<Object> arr = new ArrayList<Object>();
			for (int i = 0; i < ks.length; i++) {
				String k = ks[i].toString();
				if (filter(k)) {
					int count = ((Integer) vs[i]).intValue();
					for (int j = 0; j < ks.length; j++)
						if (j != i && ks[j].toString().indexOf(k) >= 0) {
							int otherCount = ((Integer) vs[j]).intValue();
							count -= otherCount;
						}

					arr
							.add(((Object) (new Object[] { k,
									new Integer(count) })));
				}
			}

			Collections.sort(arr, new Comparator() {
				public int compare(Object o1, Object o2) {
					Object arr1[] = (Object[]) o1;
					Object arr2[] = (Object[]) o2;
					Integer i1 = (Integer) arr1[1];
					Integer i2 = (Integer) arr2[1];
					return i2.intValue() - i1.intValue();
				}
			});

			for (int i = 0; i < arr.size(); i++) {
				Object wordArr[] = (Object[]) arr.get(i);
				String k = wordArr[0].toString();
				int count = ((Integer) wordArr[1]).intValue();
				if (count == 1 || list.contains(k))
					continue;
				if (list.size() < 3) {
					list.add(k);
					continue;
				}
				if (list.size() == 3) {
					if (count > 15)
						list.add(k);
					continue;
				}
				if (list.size() != 4)
					break;
				if (count > 20)
					list.add(k);
			}

			if (list.size() > 0 && list.size() <= 3 && arr.size() > list.size()) {
				int lastCount = ((Integer) ((Object[]) arr.get(list.size() - 1))[1])
						.intValue();
				for (int i = list.size(); i < 5 && i < arr.size(); i++) {
					Object wordArr[] = (Object[]) arr.get(i);
					int count = ((Integer) wordArr[1]).intValue();
					if (count >= lastCount - 1 && !list.contains(wordArr[0]))
						list.add(wordArr[0]);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		String arr[] = new String[list.size()];
		for (int i = 0; i < list.size(); i++)
			arr[i] = list.get(i).toString();

		return arr;
	}
	
    @SuppressWarnings("rawtypes")
	private static Object[] keyArray(LinkedHashMap<String,Integer> map)
    {
        if(map.size() == 0)
            return new Object[0];
        Object arr[] = new Object[map.size()];
        int i = 0;
        for(Iterator iter = map.keySet().iterator(); iter.hasNext();)
            arr[i++] = iter.next();

        return arr;
    }

    @SuppressWarnings("rawtypes")
	private static Object[] valueArray(LinkedHashMap<String,Integer> map)
    {
        if(map.size() == 0)
            return new Object[0];
        Object arr[] = new Object[map.size()];
        int i = 0;
        for(Iterator iter = map.values().iterator(); iter.hasNext();)
            arr[i++] = iter.next();

        return arr;
    }
    
	private static boolean filter(String word) {
		if (filterWords == null || filterChars == null)
			try {
				filterWords = FileUtil.readText(ExtractKeywordAndSummary.class.getResource("wordfilter.dic").openStream(), "UTF-8");
				filterChars = FileUtil.readText(ExtractKeywordAndSummary.class.getResource("charfilter.dic").openStream(), "UTF-8");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		if (NumberUtil.isNumber(word))
			return false;
		if (word == null || word.length() < 2)
			return false;
		if (filterWords.indexOf(word) >= 0)
			return false;
		String s = word.substring(0, 1);
		String e = word.substring(word.length() - 1);
		return filterChars.indexOf(s) < 0 && filterChars.indexOf(e) < 0;
	}

	public static String getTextAbstract(String title, String content) {
		try {
			content = getTextFromHtml(content);
			org.apache.lucene.search.Query q = IKQueryParser.parse("CONTENT",
					title);
			SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("", "");
			Highlighter highlighter = new Highlighter(formatter,
					new QueryScorer(q));
			highlighter.setTextFragmenter(new SimpleFragmenter(200));
			org.apache.lucene.analysis.TokenStream tokenStream = (new IKAnalyzer())
					.tokenStream("CONTENT", new StringReader(content));
			String tmp = highlighter.getBestFragment(tokenStream, content);
			if (StringUtil.isNotEmpty(tmp))
				content = tmp.trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int start = 0;
		int end = 0;
		boolean startFlag = true;
		for (int i = 0; i < content.length(); i++) {
			char c = content.charAt(i);
			if (startFlag) {
				if (Character.isWhitespace(c) || Character.isISOControl(c)
						|| c == ',' || c == '，' || c == '”'
						|| c == '’' || c == '.' || c == '。'
						|| c == '>' || c == '?' || c == '？' || c == ' '
						|| c == '　' || c == ' ' || c == '!'
						|| c == '！' || c == ';' || c == '；'
						|| c == ':' || c == '：' || c == ']'
						|| c == '］')
					continue;
				start = i;
				startFlag = false;
			}
			if (!startFlag)
				if (c == '.' || c == '。' || c == '?' || c == '？'
						|| c == '!' || c == '！' || c == ' '
						|| c == '　' || c == ' ') {
					if (i < 8)
						start = i + 1;
					end = i;
					if (i != content.length() - 1
							&& (content.charAt(i + 1) == '”' || content
									.charAt(i + 1) == '’'))
						end = i + 1;
				} else {
					if ((c == ',' || c == '，' || c == '>' || c == '》' || c == '、')
							&& i < 2)
						start = i + 1;
					if (c == '’' || c == '”')
						if (i != content.length() - 1) {
							char next = content.charAt(i + 1);
							if (next != ',' && next == '，'
									&& next == '、' && next == ';'
									&& next == '；')
								end = i + 1;
						} else {
							end = i;
						}
				}
		}

		if (end != 0 && end > start) {
			content = content.substring(start, end + 1).trim();
			start = 0;
			for (int i = 0; i < content.length(); i++) {
				char c = content.charAt(i);
				if ((c == '.' || c == '。' || c == '?' || c == '？'
						|| c == '!' || c == '！' || c == ' '
						|| c == '　' || c == ' ')
						&& i < 8)
					start = i + 1;
			}

			if (start != 0)
				content = content.substring(start);
			end = 0;
			if (StringUtil.isNotEmpty(content)) {
				char c = content.charAt(content.length() - 1);
				if (c != '.' && c != '。' && c != '?' && c != '？'
						&& c != '!' && c != '！') {
					for (int i = content.length() - 2; i > 0; i--) {
						c = content.charAt(i);
						if (c != ';' && c != '；' && c != ','
								&& c != '，' && c != '>' && c != '》')
							continue;
						end = i;
						break;
					}

				}
			}
			if (end != 0)
				content = content.substring(0, end);
		}
		return content;
	}
}
