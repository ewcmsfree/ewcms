/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ewcms.crawler.crawl.crawler4j.crawler;

import it.unimi.dsi.parser.BulletParser;
import it.unimi.dsi.parser.callback.TextExtractor;

import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.ewcms.crawler.crawl.crawler4j.url.URLCanonicalizer;


/**
 * @author Yasser Ganjisaffar <yganjisa at uci dot edu>
 */

public class HTMLParser {

	private String text;
	private String title;

	private BulletParser bulletParser;
	private TextExtractor textExtractor;
	private LinkExtractor linkExtractor;

	private static final int MAX_OUT_LINKS = Configurations.getIntProperty(
			"fetcher.max_outlinks", 5000);

	private Set<String> urls;

	public HTMLParser() {
		bulletParser = new BulletParser();
		textExtractor = new TextExtractor();
		linkExtractor = new LinkExtractor();
		
		linkExtractor.setIncludeImagesSources(Configurations
				.getBooleanProperty("crawler.include_images", false));
	}

	public void parse(String htmlContent, String contextURL) {
		urls = new HashSet<String>();
		char[] chars = htmlContent.toCharArray();

		bulletParser.setCallback(textExtractor);
		bulletParser.parse(chars);
		text = textExtractor.text.toString().trim();
		title = textExtractor.title.toString().trim();

		bulletParser.setCallback(linkExtractor);
		bulletParser.parse(chars);
		Iterator<String> it = linkExtractor.urls.iterator();
		
		String baseURL = linkExtractor.base();
		if (baseURL != null) {
			contextURL = baseURL;
		}

		int urlCount = 0;
		while (it.hasNext()) {
			String href = it.next();
			href = href.trim();
			if (href.length() == 0) {
				continue;
			}
			String hrefWithoutProtocol = href.toLowerCase();
			if (href.startsWith("http://")) {
				hrefWithoutProtocol = href.substring(7);
			}
			if (hrefWithoutProtocol.indexOf("javascript:") < 0
					&& hrefWithoutProtocol.indexOf("@") < 0) {
				URL url = URLCanonicalizer.getCanonicalURL(href, contextURL);
				if (url != null) {
					urls.add(url.toExternalForm());
					urlCount++;
					if (urlCount > MAX_OUT_LINKS) {
						break;
					}	
				}				
			}
		}
	}

	public String getText() {
		return text;
	}

	public String getTitle() {
		return title;
	}

	public Set<String> getLinks() {
		return urls;
	}
}
