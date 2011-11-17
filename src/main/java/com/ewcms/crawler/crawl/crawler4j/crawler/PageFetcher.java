/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.crawler.crawl.crawler4j.crawler;

import java.io.IOException;
import java.util.Date;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParamBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.crawler.crawl.crawler4j.frontier.DocIDServer;
import com.ewcms.crawler.crawl.crawler4j.url.URLCanonicalizer;
import com.ewcms.crawler.crawl.crawler4j.url.WebURL;


/**
 * @author Yasser Ganjisaffar <yganjisa at uci dot edu>
 * @author wuzhijun
 */

public class PageFetcher {

	private static final Logger logger = LoggerFactory.getLogger(PageFetcher.class.getName());

	private ThreadSafeClientConnManager connectionManager;

	private DefaultHttpClient httpclient;

	private Object mutex = PageFetcher.class.toString() + "_MUTEX";

	private int processedCount = 0;
	private long startOfPeriod = 0;
	private long lastFetchTime = 0;

	private long politenessDelay = Configurations.getIntProperty("fetcher.default_politeness_delay", 200);

	public static final int MAX_DOWNLOAD_SIZE = Configurations.getIntProperty("fetcher.max_download_size", 1048576);

	private static final boolean show404Pages = Configurations.getBooleanProperty("logging.show_404_pages", true);

	private IdleConnectionMonitorThread connectionMonitorThread = null;

	public long getPolitenessDelay() {
		return politenessDelay;
	}

	public void setPolitenessDelay(long politenessDelay) {
		this.politenessDelay = politenessDelay;
	}

	static {
//		HttpParams params = new BasicHttpParams();
//		HttpProtocolParamBean paramsBean = new HttpProtocolParamBean(params);
//		paramsBean.setVersion(HttpVersion.HTTP_1_1);
//		paramsBean.setContentCharset("UTF-8");
//		paramsBean.setUseExpectContinue(false);
//
//		params.setParameter("http.useragent", Configurations.getStringProperty("fetcher.user_agent",
//				"crawler4j (http://code.google.com/p/crawler4j/)"));
//
//		params.setIntParameter("http.socket.timeout", Configurations.getIntProperty("fetcher.socket_timeout", 20000));
//
//		params.setIntParameter("http.connection.timeout",
//				Configurations.getIntProperty("fetcher.connection_timeout", 30000));
//
//		params.setBooleanParameter("http.protocol.handle-redirects", false);
//
//		ConnPerRouteBean connPerRouteBean = new ConnPerRouteBean();
//		connPerRouteBean.setDefaultMaxPerRoute(Configurations.getIntProperty("fetcher.max_connections_per_host", 100));
//		ConnManagerParams.setMaxConnectionsPerRoute(params, connPerRouteBean);
//		ConnManagerParams.setMaxTotalConnections(params,
//				Configurations.getIntProperty("fetcher.max_total_connections", 100));
//
//		SchemeRegistry schemeRegistry = new SchemeRegistry();
//		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
//
//		if (Configurations.getBooleanProperty("fetcher.crawl_https", false)) {
//			schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
//		}
//
//		connectionManager = new ThreadSafeClientConnManager(params, schemeRegistry);
//		//logger.setLevel(Level.INFO);
//		httpclient = new DefaultHttpClient(connectionManager, params);
	}
	
	private synchronized void initConnectionManager(){
		HttpParams params = new BasicHttpParams();
		HttpProtocolParamBean paramsBean = new HttpProtocolParamBean(params);
		paramsBean.setVersion(HttpVersion.HTTP_1_1);
		paramsBean.setContentCharset("UTF-8");
		paramsBean.setUseExpectContinue(false);

		params.setParameter("http.useragent", Configurations.getStringProperty("fetcher.user_agent",
				"crawler4j (http://code.google.com/p/crawler4j/)"));

		params.setIntParameter("http.socket.timeout", Configurations.getIntProperty("fetcher.socket_timeout", 20000));

		params.setIntParameter("http.connection.timeout",
				Configurations.getIntProperty("fetcher.connection_timeout", 30000));

		params.setBooleanParameter("http.protocol.handle-redirects", false);

		ConnPerRouteBean connPerRouteBean = new ConnPerRouteBean();
		connPerRouteBean.setDefaultMaxPerRoute(Configurations.getIntProperty("fetcher.max_connections_per_host", 100));
		ConnManagerParams.setMaxConnectionsPerRoute(params, connPerRouteBean);
		ConnManagerParams.setMaxTotalConnections(params,
				Configurations.getIntProperty("fetcher.max_total_connections", 100));

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));

		if (Configurations.getBooleanProperty("fetcher.crawl_https", false)) {
			schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
		}

		connectionManager = new ThreadSafeClientConnManager(params, schemeRegistry);
		//logger.setLevel(Level.INFO);
		httpclient = new DefaultHttpClient(connectionManager, params);
	}

	public synchronized void startConnectionMonitorThread() {
		if (connectionMonitorThread == null) {
			initConnectionManager();
			connectionMonitorThread = new IdleConnectionMonitorThread(connectionManager);
		}
		connectionMonitorThread.start();
	}

	public synchronized void stopConnectionMonitorThread() {
		try{
			if (connectionMonitorThread != null) {
				connectionManager.shutdown();
				connectionMonitorThread.shutdown();
			}
		}finally{
			connectionManager = null;
			connectionMonitorThread = null;
		}
	}

	public int fetch(Page page, boolean ignoreIfBinary, DocIDServer docIDServer) {
		String toFetchURL = page.getWebURL().getURL();
		HttpGet get = null;
		HttpEntity entity = null;
		try {
			get = new HttpGet(toFetchURL);
			synchronized (mutex) {
				long now = (new Date()).getTime();
				if (now - startOfPeriod > 10000) {
					logger.info("Number of pages fetched per second: " + processedCount
							/ ((now - startOfPeriod) / 1000));
					processedCount = 0;
					startOfPeriod = now;
				}
				processedCount++;

				if (now - lastFetchTime < politenessDelay) {
					Thread.sleep(politenessDelay - (now - lastFetchTime));
				}
				lastFetchTime = (new Date()).getTime();
			}
			HttpResponse response = httpclient.execute(get);
			entity = response.getEntity();

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				if (statusCode != HttpStatus.SC_NOT_FOUND) {
					if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
						Header header = response.getFirstHeader("Location");
						if (header != null) {
							String movedToUrl = header.getValue();
							page.getWebURL().setURL(movedToUrl);
						} else {
							page.getWebURL().setURL(null);
						}
						return PageFetchStatus.Moved;
					}
					logger.info("Failed: " + response.getStatusLine().toString() + ", while fetching " + toFetchURL);
				} else if (show404Pages) {
					logger.info("Not Found: " + toFetchURL + " (Link found in doc#: "
							+ page.getWebURL().getParentDocid() + ")");
				}
				return response.getStatusLine().getStatusCode();
			}

			String uri = get.getURI().toString();
			if (!uri.equals(toFetchURL)) {
				if (!URLCanonicalizer.getCanonicalURL(uri).equals(toFetchURL)) {
					int newdocid = docIDServer.getDocID(uri);
					if (newdocid != -1) {
						if (newdocid > 0) {
							return PageFetchStatus.RedirectedPageIsSeen;
						}
						WebURL webURL = new WebURL();
						webURL.setURL(uri);
						webURL.setDocid(docIDServer.getNewDocID(uri));
						page.setWebURL(webURL);
					}
				}
			}

			if (entity != null) {
				long size = entity.getContentLength();
				if (size == -1) {
					Header length = response.getLastHeader("Content-Length");
					if (length == null) {
						length = response.getLastHeader("Content-length");
					}
					if (length != null) {
						size = Integer.parseInt(length.getValue());
					} else {
						size = -1;
					}
				}
				if (size > MAX_DOWNLOAD_SIZE) {
					entity.consumeContent();
					return PageFetchStatus.PageTooBig;
				}

				boolean isBinary = false;

				Header type = entity.getContentType();
				if (type != null) {
					String typeStr = type.getValue().toLowerCase();
					if (typeStr.contains("image") || typeStr.contains("audio") || typeStr.contains("video")) {
						isBinary = true;
						if (ignoreIfBinary) {
							return PageFetchStatus.PageIsBinary;
						}
					}
				}

				if (page.load(entity.getContent(), (int) size, isBinary)) {
					return PageFetchStatus.OK;
				} else {
					return PageFetchStatus.PageLoadError;
				}
			} else {
				get.abort();
			}
		} catch (IOException e) {
			logger.error("Fatal transport error: " + e.getMessage() + " while fetching " + toFetchURL
					+ " (link found in doc #" + page.getWebURL().getParentDocid() + ")");
			return PageFetchStatus.FatalTransportError;
		} catch (IllegalStateException e) {
			// ignoring exceptions that occur because of not registering https
			// and other schemes
		} catch (Exception e) {
			if (e.getMessage() == null) {
				logger.error("Error while fetching " + page.getWebURL().getURL());
			} else {
				logger.error(e.getMessage() + " while fetching " + page.getWebURL().getURL());
			}
		} finally {
			try {
				if (entity != null) {
					entity.consumeContent();
				} else if (get != null) {
					get.abort();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return PageFetchStatus.UnknownError;
	}

	public void setProxy(String proxyHost, int proxyPort) {
		HttpHost proxy = new HttpHost(proxyHost, proxyPort);
		httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
	}

	public void setProxy(String proxyHost, int proxyPort, String username, String password) {
		httpclient.getCredentialsProvider().setCredentials(new AuthScope(proxyHost, proxyPort),
				new UsernamePasswordCredentials(username, password));
		setProxy(proxyHost, proxyPort);
	}

}
