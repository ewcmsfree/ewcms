/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.crawler.crawl.crawler4j.frontier;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.crawler.crawl.crawler4j.crawler.Configurations;
import com.ewcms.crawler.crawl.crawler4j.url.WebURL;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;


/**
 * @author Yasser Ganjisaffar <yganjisa at uci dot edu>
 */

public class Frontier {

	private static final Logger logger = LoggerFactory.getLogger(Frontier.class.getName());

	private WorkQueues workQueues;
	private InProcessPagesDB inprocessPages;
	private DocIDServer docIDServer;

	private Object mutex = Frontier.class.toString() + "_Mutex";

	private Object waitingList = Frontier.class.toString() + "_WaitingList";

	private boolean isFinished = false;

	private int maxPagesToFetch = Configurations.getIntProperty("crawler.max_pages_to_fetch", -1);

	private int scheduledPages;

	public DocIDServer getDocIDServer() {
		return docIDServer;
	}

	public void setDocIDServer(DocIDServer docIDServer) {
		this.docIDServer = docIDServer;
	}

	public void init(Environment env, boolean resumable) {
		try {
			workQueues = new WorkQueues(env, "PendingURLsDB", resumable);
			if (resumable) {
				inprocessPages = new InProcessPagesDB(env);
				long docCount = inprocessPages.getLength();
				if (docCount > 0) {
					logger.info("Rescheduling " + docCount + " URLs from previous crawl.");
					while (true) {
						List<WebURL> urls = inprocessPages.get(100);
						if (urls.size() == 0) {
							break;
						}
						inprocessPages.delete(urls.size());
						scheduleAll(urls);
					}
				}
			} else {
				inprocessPages = null;
				scheduledPages = 0;
			}			
		} catch (DatabaseException e) {
			logger.error("Error while initializing the Frontier: " + e.getMessage());
			workQueues = null;
		}
	}

	public void scheduleAll(List<WebURL> urls) {
		synchronized (mutex) {
			Iterator<WebURL> it = urls.iterator();
			while (it.hasNext()) {
				WebURL url = it.next();
				if (maxPagesToFetch < 0 || scheduledPages < maxPagesToFetch) {					
					try {
						workQueues.put(url);
						scheduledPages++;
					} catch (DatabaseException e) {
						logger.error("Error while puting the url in the work queue.");
					}
				}
			}
			synchronized (waitingList) {
				waitingList.notifyAll();
			}
		}
	}

	public void schedule(WebURL url) {
		synchronized (mutex) {
			try {
				if (maxPagesToFetch < 0 || scheduledPages < maxPagesToFetch) {
					workQueues.put(url);
					scheduledPages++;
				}
			} catch (DatabaseException e) {
				logger.error("Error while puting the url in the work queue.");
			}
		}
	}

	public void getNextURLs(int max, List<WebURL> result) {
		while (true) {
			synchronized (mutex) {
				try {
					List<WebURL> curResults = workQueues.get(max);
					workQueues.delete(curResults.size());
					if (inprocessPages != null) {
						for (WebURL curPage : curResults) {
							inprocessPages.put(curPage);
						}
					}
					result.addAll(curResults);					
				} catch (DatabaseException e) {
					logger.error("Error while getting next urls: " + e.getMessage());
					e.printStackTrace();
				}
				if (result.size() > 0) {
					return;
				}
			}
			try {
				synchronized (waitingList) {
					waitingList.wait();
				}
			} catch (InterruptedException e) {
			}
			if (isFinished) {
				return;
			}
		}
	}
	
	public void setProcessed(WebURL webURL) {
		if (inprocessPages != null) {
			if (!inprocessPages.removeURL(webURL)) {
				logger.warn("Could not remove: " + webURL.getURL() + " from list of processed pages.");
			}
		}
	}

	public long getQueueLength() {
		return workQueues.getLength();
	}

	public long getNumberOfAssignedPages() {
		return inprocessPages.getLength();
	}
	
	public void sync() {
		workQueues.sync();
		docIDServer.sync();
	}

	public boolean isFinished() {
		return isFinished;
	}
	
	public void setMaximumPagesToFetch(int max) {
		maxPagesToFetch = max;
	}

	public void close() {
		sync();
		workQueues.close();
		docIDServer.close();
	}

	public void finish() {
		isFinished = true;
		synchronized (waitingList) {
			waitingList.notifyAll();
		}
	}
}
