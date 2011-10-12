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

package com.ewcms.crawler.crawl.crawler4j.robotstxt;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.ewcms.crawler.crawl.crawler4j.crawler.Configurations;
import com.ewcms.crawler.crawl.crawler4j.crawler.Page;
import com.ewcms.crawler.crawl.crawler4j.crawler.PageFetchStatus;
import com.ewcms.crawler.crawl.crawler4j.crawler.PageFetcher;
import com.ewcms.crawler.crawl.crawler4j.frontier.DocIDServer;
import com.ewcms.crawler.crawl.crawler4j.url.WebURL;


/**
 * @author Yasser Ganjisaffar <yganjisa at uci dot edu>
 * @author wuzhijun
 */


public class RobotstxtServer {
	
	private static Map<String, HostDirectives> host2directives = new HashMap<String, HostDirectives>();
	
	private static final String USER_AGENT_NAME = Configurations.getStringProperty("fetcher.user_agent_name", "crawler4j");
	private static final int MAX_MAP_SIZE = Configurations.getIntProperty("crawler.robotstxt.max_map_size", 100);
	private static boolean active = Configurations.getBooleanProperty("crawler.obey_robotstxt", false);
	private static final Object mutex = RobotstxtServer.class.toString() + "_MUTEX"; 
	
	public static boolean allows(WebURL webURL, PageFetcher pageFetcher, DocIDServer docIDServer) {
		if (!active) {
			return true;
		}
		try {
			URL url = new URL(webURL.getURL());
			String host = url.getHost().toLowerCase();
			String path = url.getPath();
			
			HostDirectives directives = host2directives.get(host);
			if (directives == null) {
				directives = fetchDirectives(host, pageFetcher, docIDServer);
			} 
			return directives.allows(path);			
		} catch (MalformedURLException e) {			
			e.printStackTrace();
		}
		return true;
	}
	
	public static void setActive(boolean active) {
		RobotstxtServer.active = active;
	}
	
	private static HostDirectives fetchDirectives(String host, PageFetcher pageFetcher, DocIDServer docIDServer) {
		WebURL robotsTxt = new WebURL();
		robotsTxt.setURL("http://" + host + "/robots.txt");
		Page page = new Page(robotsTxt);
		int statusCode = pageFetcher.fetch(page, true, docIDServer);
		HostDirectives directives = null;
		if (statusCode == PageFetchStatus.OK) {
			directives = RobotstxtParser.parse(page.getHTML(), USER_AGENT_NAME);			
		}
		if (directives == null) {
			// We still need to have this object to keep track of the time we fetched it
			directives = new HostDirectives();
		}
		synchronized (mutex) {
			if (host2directives.size() == MAX_MAP_SIZE) {
				String minHost = null;
				long minAccessTime = Long.MAX_VALUE;
				for (Entry<String, HostDirectives> entry : host2directives.entrySet()) {
					if (entry.getValue().getLastAccessTime() < minAccessTime) {
						minAccessTime = entry.getValue().getLastAccessTime();
						minHost = entry.getKey();
					}					
				}
				host2directives.remove(minHost);
			}
			host2directives.put(host, directives);
		}
		return directives;
	}

}
