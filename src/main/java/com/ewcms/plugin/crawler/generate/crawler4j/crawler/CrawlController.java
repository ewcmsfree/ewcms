/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.crawler.generate.crawler4j.crawler;

import java.io.File;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.plugin.crawler.generate.crawler4j.frontier.DocIDServer;
import com.ewcms.plugin.crawler.generate.crawler4j.frontier.Frontier;
import com.ewcms.plugin.crawler.generate.crawler4j.robotstxt.RobotstxtServer;
import com.ewcms.plugin.crawler.generate.crawler4j.url.URLCanonicalizer;
import com.ewcms.plugin.crawler.generate.crawler4j.url.WebURL;
import com.ewcms.plugin.crawler.generate.crawler4j.util.IO;
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
	private DocIDServer docIDServer;
	private Frontier frontier;
	private List<Object> crawlersLocalData = new ArrayList<Object>();

	public List<Object> getCrawlersLocalData() {
		return crawlersLocalData;
	}

	public PageFetcher getPageFetcher(){
		return pageFetcher;
	}
	
	public DocIDServer getDocIDServer(){
		return docIDServer;
	}
	
	public Frontier getFrontier(){
		return frontier;
	}
	
	List<Thread> threads;

	public CrawlController(String storageFolder,PageFetcher pageFetcher, DocIDServer docIDServer, Frontier frontier) throws Exception {
		this(storageFolder, Configurations.getBooleanProperty("crawler.enable_resume", true), pageFetcher, docIDServer, frontier);
	}
	
	public CrawlController(String storageFolder, boolean resumable, PageFetcher pageFetcher, DocIDServer docIDServer, Frontier frontier) throws Exception {
		this.pageFetcher = pageFetcher;
		this.frontier = frontier;
		this.docIDServer = docIDServer;
		
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
		
		frontier.init(env, resumable);
		docIDServer.init(env, resumable);
		frontier.setDocIDServer(docIDServer);

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
						long queueLength = frontier.getQueueLength();
						if (queueLength > 0) {
							continue;
						}
						logger.info("No thread is working and no more URLs are in queue waiting for another 60 seconds to make sure...");
						sleep(60);
						queueLength = frontier.getQueueLength();
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
						frontier.finish();
						logger.info("Waiting for 10 seconds before final clean up...");
						sleep(10);

						frontier.close();
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
		int docid = docIDServer.getDocID(canonicalUrl);
		if (docid > 0) {
			// This URL is already seen.
			return;
		}

		WebURL webUrl = new WebURL();
		webUrl.setURL(canonicalUrl);
		docid = docIDServer.getNewDocID(canonicalUrl);
		webUrl.setDocid(docid);
		webUrl.setDepth((short) 0);
		if (!RobotstxtServer.allows(webUrl, pageFetcher, docIDServer)) {
			logger.info("Robots.txt does not allow this seed: " + pageUrl);
		} else {
			frontier.schedule(webUrl);
		}
	}

	public void setPolitenessDelay(int milliseconds) {
		if (milliseconds < 0) {
			return;
		}
		if (milliseconds > 10000) {
			milliseconds = 10000;
		}
		pageFetcher.setPolitenessDelay(milliseconds);
	}

//	public void setMaximumCrawlDepth(int depth) throws Exception {
//		if (depth < -1) {
//			throw new Exception("Maximum crawl depth should be either a positive number or -1 for unlimited depth.");
//		}
//		if (depth > Short.MAX_VALUE) {
//			throw new Exception("Maximum value for crawl depth is " + Short.MAX_VALUE);
//		}
//		webCrawler.setMaximumCrawlDepth((short) depth);
//	}

	public void setMaximumPagesToFetch(int max) {
		frontier.setMaximumPagesToFetch(max);
	}

	public void setProxy(String proxyHost, int proxyPort) {
		pageFetcher.setProxy(proxyHost, proxyPort);
	}

	public void setProxy(String proxyHost, int proxyPort, String username, String password) {
		pageFetcher.setProxy(proxyHost, proxyPort, username, password);
	}
}
