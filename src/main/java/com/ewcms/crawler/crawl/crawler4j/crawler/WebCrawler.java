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

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.crawler.crawl.crawler4j.frontier.DocIDServer;
import com.ewcms.crawler.crawl.crawler4j.frontier.Frontier;
import com.ewcms.crawler.crawl.crawler4j.robotstxt.RobotstxtServer;
import com.ewcms.crawler.crawl.crawler4j.url.URLCanonicalizer;
import com.ewcms.crawler.crawl.crawler4j.url.WebURL;


/**
 * @author Yasser Ganjisaffar <yganjisa at uci dot edu>
 * @author wuzhijun
 */

public class WebCrawler implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(WebCrawler.class.getName());

	private Thread myThread;

	private final static int PROCESS_OK = -12;

	private HTMLParser htmlParser;

	int myid;

	private CrawlController myController;

	private short maxCrawlDepth = Configurations.getShortProperty("crawler.max_depth", (short) -1);
	private boolean includeBinaryContent = !Configurations.getBooleanProperty("crawler.include_binary_content", false);
	private final boolean followRedirects = Configurations.getBooleanProperty("fetcher.follow_redirects", true);

	public CrawlController getMyController() {
		return myController;
	}

	public void setMyController(CrawlController myController) {
		this.myController = myController;
	}

	public short getMaxCrawlDepth() {
		return maxCrawlDepth;
	}

	private void setMaxCrawlDepth(short maxCrawlDepth) {
		this.maxCrawlDepth = maxCrawlDepth;
	}

	public void setMaximumCrawlDepth(short depth) throws Exception {
		if (depth < -1) {
			throw new Exception("Maximum crawl depth should be either a positive number or -1 for unlimited depth.");
		}
		if (depth > Short.MAX_VALUE) {
			throw new Exception("Maximum value for crawl depth is " + Short.MAX_VALUE);
		}
		setMaxCrawlDepth(depth);
	}
	
	public boolean isIncludeBinaryContent() {
		return includeBinaryContent;
	}

	public void setIncludeBinaryContent(boolean includeBinaryContent) {
		this.includeBinaryContent = includeBinaryContent;
	}

	public boolean isFollowRedirects() {
		return followRedirects;
	}

	public WebCrawler() {
		htmlParser = new HTMLParser();
	}

	public WebCrawler(int myid) {
		this.myid = myid;
	}

	public void setMyId(int myid) {
		this.myid = myid;
	}

	public int getMyId() {
		return myid;
	}

	public void onStart() {

	}

	public void onBeforeExit() {

	}

	public Object getMyLocalData() {
		return null;
	}

	public void run() {
		onStart();
		while (true) {
			Frontier frontier = myController.getFrontier();
			List<WebURL> assignedURLs = new ArrayList<WebURL>(50);
			frontier.getNextURLs(50, assignedURLs);
			if (assignedURLs.size() == 0) {
				if (frontier.isFinished()) {
					return;
				}
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				for (WebURL curURL : assignedURLs) {
					if (curURL != null) {
						if (curURL.getDocid() == 14) {
							System.out.println();
						}
						processPage(curURL);
						frontier.setProcessed(curURL);
					}
				}
			}
		}
	}

	public boolean shouldVisit(WebURL url) {
		return true;
	}

	public void visit(Page page) {
		// Should be implemented in sub classes
	}

	private int processPage(WebURL curURL) {
		if (curURL == null) {
			return -1;
		}
		Page page = new Page(curURL);
		PageFetcher pageFetcher = myController.getPageFetcher();
		Frontier frontier = myController.getFrontier();
		DocIDServer docIDServer = myController.getDocIDServer();
		int statusCode = pageFetcher.fetch(page, includeBinaryContent, docIDServer);
		// The page might have been redirected. So we have to refresh curURL
		curURL = page.getWebURL();
		int docid = curURL.getDocid();
		if (statusCode != PageFetchStatus.OK) {
			if (statusCode == PageFetchStatus.Moved) {
				if (followRedirects) {
					String movedToUrl = curURL.getURL();
					if (movedToUrl == null) {
						return PageFetchStatus.MovedToUnknownLocation;
					}
					movedToUrl = URLCanonicalizer.getCanonicalURL(movedToUrl);
					int newdocid = docIDServer.getDocID(movedToUrl);
					if (newdocid > 0) {
						return PageFetchStatus.RedirectedPageIsSeen;
					} else {
						WebURL webURL = new WebURL();
						webURL.setURL(movedToUrl);
						webURL.setParentDocid(curURL.getParentDocid());
						webURL.setDepth((short) (curURL.getDepth()));
						webURL.setDocid(-1);
						if (shouldVisit(webURL) && RobotstxtServer.allows(webURL, pageFetcher, docIDServer)) {
							webURL.setDocid(docIDServer.getNewDocID(movedToUrl));	
							frontier.schedule(webURL);
						}
					}
				}
				return PageFetchStatus.Moved;
			} else if (statusCode == PageFetchStatus.PageTooBig) {
				logger.error("Page was bigger than max allowed size: " + curURL.getURL());
			}
			return statusCode;
		}

		try {
			if (!page.isBinary()) {
				htmlParser.parse(page.getHTML(), curURL.getURL());
				page.setText(htmlParser.getText());
				page.setTitle(htmlParser.getTitle());

				if (page.getText() == null) {
					return PageFetchStatus.NotInTextFormat;
				}

				Iterator<String> it = htmlParser.getLinks().iterator();
				List<WebURL> toSchedule = new ArrayList<WebURL>();
				List<WebURL> toList = new ArrayList<WebURL>();
				while (it.hasNext()) {
					String url = it.next();
					if (url != null) {
						int newdocid = docIDServer.getDocID(url);
						if (newdocid > 0) {
							if (newdocid != docid) {
								WebURL webURL = new WebURL();
								webURL.setURL(url);
								webURL.setDocid(newdocid);
								toList.add(webURL);
							}
						} else {
							WebURL webURL = new WebURL();
							webURL.setURL(url);
							webURL.setDocid(-1);
							webURL.setParentDocid(docid);
							webURL.setDepth((short) (curURL.getDepth() + 1));							
							if (shouldVisit(webURL) && RobotstxtServer.allows(webURL, pageFetcher, docIDServer)) {
								if (maxCrawlDepth == -1 || curURL.getDepth() < maxCrawlDepth) {
									webURL.setDocid(docIDServer.getNewDocID(url));
									toSchedule.add(webURL);
									toList.add(webURL);
								}
							}
						}
					}
				}
				frontier.scheduleAll(toSchedule);
				page.setURLs(toList);
			}
			visit(page);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage() + ", while processing: " + curURL.getURL());
		}
		return PROCESS_OK;
	}

	public Thread getThread() {
		return myThread;
	}

	public void setThread(Thread myThread) {
		this.myThread = myThread;
	}

}
