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

import java.io.File;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.crawler.crawl.crawler4j.frontier.DocIDServer;
import com.ewcms.crawler.crawl.crawler4j.frontier.Frontier;
import com.ewcms.crawler.crawl.crawler4j.robotstxt.RobotstxtServer;
import com.ewcms.crawler.crawl.crawler4j.url.URLCanonicalizer;
import com.ewcms.crawler.crawl.crawler4j.url.WebURL;
import com.ewcms.crawler.crawl.crawler4j.util.IO;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;


/**
 * @author Yasser Ganjisaffar <yganjisa at uci dot edu>
 * @author wuzhijun
 */
public class CrawlController {

	private static final Logger logger = LoggerFactory.getLogger(CrawlController.class.getName());

	private Environment env;
	private PageFetcher pageFetcher;
	private List<Object> crawlersLocalData = new ArrayList<Object>();

	public List<Object> getCrawlersLocalData() {
		return crawlersLocalData;
	}

	public PageFetcher getPageFetcher(){
		return pageFetcher;
	}
	
	List<Thread> threads;

	public CrawlController(String storageFolder,PageFetcher pageFetcher) throws Exception {
		this(storageFolder, Configurations.getBooleanProperty("crawler.enable_resume", true), pageFetcher);
	}
	
	public CrawlController(String storageFolder, boolean resumable, PageFetcher pageFetcher) throws Exception {
		this.pageFetcher = pageFetcher;
		
		File folder = new File(storageFolder);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setAllowCreate(true);
		envConfig.setTransactional(resumable);
		envConfig.setLocking(resumable);

		File envHome = new File(storageFolder + "/frontier");
		if (!envHome.exists()) {
			envHome.mkdir();
		}
		if (!resumable) {
			IO.deleteFolderContents(envHome);
		}

		env = new Environment(envHome, envConfig);
		Frontier.init(env, resumable);
		DocIDServer.init(env, resumable);

		pageFetcher.startConnectionMonitorThread();
	}

	public <T extends WebCrawler> void start(T _c, int numberOfCrawlers) {
		try {
			crawlersLocalData.clear();
			threads = new ArrayList<Thread>();
			List<T> crawlers = new ArrayList<T>();
			int numberofCrawlers = numberOfCrawlers;
			for (int i = 1; i <= numberofCrawlers; i++) {
				T crawler = _c;
				Thread thread = new Thread(crawler, "Crawler " + i);
				crawler.setThread(thread);
				crawler.setMyId(i);
				crawler.setMyController(this);
				thread.start();
				crawlers.add(crawler);
				threads.add(thread);
				logger.info("Crawler " + i + " started.");
			}
			while (true) {
				sleep(10);
				boolean someoneIsWorking = false;
				for (int i = 0; i < threads.size(); i++) {
					Thread thread = threads.get(i);
					if (!thread.isAlive()) {
						logger.info("Thread " + i + " was dead, I'll recreate it.");
						T crawler = _c;
						thread = new Thread(crawler, "Crawler " + (i + 1));
						threads.remove(i);
						threads.add(i, thread);
						crawler.setThread(thread);
						crawler.setMyId(i + 1);
						crawler.setMyController(this);
						thread.start();
						crawlers.remove(i);
						crawlers.add(i, crawler);
					} else if (thread.getState() == State.RUNNABLE) {
						someoneIsWorking = true;
					}
				}
				if (!someoneIsWorking) {
					// Make sure again that none of the threads are alive.
					logger.info("It looks like no thread is working, waiting for 40 seconds to make sure...");
					sleep(40);

					if (!isAnyThreadWorking()) {
						long queueLength = Frontier.getQueueLength();
						if (queueLength > 0) {
							continue;
						}
						logger.info("No thread is working and no more URLs are in queue waiting for another 60 seconds to make sure...");
						sleep(60);
						queueLength = Frontier.getQueueLength();
						if (queueLength > 0) {
							continue;
						}
						logger.info("All of the crawlers are stopped. Finishing the process...");
						for (T crawler : crawlers) {
							crawler.onBeforeExit();
							crawlersLocalData.add(crawler.getMyLocalData());
						}

						// At this step, frontier notifies the threads that were waiting for new URLs and they should stop
						// We will wait a few seconds for them and then return.
						Frontier.finish();
						logger.info("Waiting for 10 seconds before final clean up...");
						sleep(10);

						Frontier.close();
						pageFetcher.stopConnectionMonitorThread();
						return;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
		}
	}
	
	public void stop(){
	}

	private void sleep(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (Exception e) {
		}
	}

	private boolean isAnyThreadWorking() {
		boolean someoneIsWorking = false;
		for (int i = 0; i < threads.size(); i++) {
			Thread thread = threads.get(i);
			if (thread.isAlive() && thread.getState() == State.RUNNABLE) {
				someoneIsWorking = true;
			}
		}
		return someoneIsWorking;
	}

	public void addSeed(String pageUrl) {
		String canonicalUrl = URLCanonicalizer.getCanonicalURL(pageUrl);
		if (canonicalUrl == null) {
			logger.error("Invalid seed URL: " + pageUrl);
			return;
		}
		int docid = DocIDServer.getDocID(canonicalUrl);
		if (docid > 0) {
			// This URL is already seen.
			return;
		}

		WebURL webUrl = new WebURL();
		webUrl.setURL(canonicalUrl);
		docid = DocIDServer.getNewDocID(canonicalUrl);
		webUrl.setDocid(docid);
		webUrl.setDepth((short) 0);
		if (!RobotstxtServer.allows(webUrl, pageFetcher)) {
			logger.info("Robots.txt does not allow this seed: " + pageUrl);
		} else {
			Frontier.schedule(webUrl);
		}
	}

	public void setPolitenessDelay(int milliseconds) {
		if (milliseconds < 0) {
			return;
		}
		if (milliseconds > 10000) {
			milliseconds = 10000;
		}
		PageFetcher.setPolitenessDelay(milliseconds);
	}

	public void setMaximumCrawlDepth(int depth) throws Exception {
		if (depth < -1) {
			throw new Exception("Maximum crawl depth should be either a positive number or -1 for unlimited depth.");
		}
		if (depth > Short.MAX_VALUE) {
			throw new Exception("Maximum value for crawl depth is " + Short.MAX_VALUE);
		}
		WebCrawler.setMaximumCrawlDepth((short) depth);
	}

	public void setMaximumPagesToFetch(int max) {
		Frontier.setMaximumPagesToFetch(max);
	}

	public void setProxy(String proxyHost, int proxyPort) {
		pageFetcher.setProxy(proxyHost, proxyPort);
	}

	public void setProxy(String proxyHost, int proxyPort, String username, String password) {
		pageFetcher.setProxy(proxyHost, proxyPort, username, password);
	}
}
