/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.crawler.generate.fetcher;

import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.*;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.plugin.crawler.generate.crawler.Configurable;
import com.ewcms.plugin.crawler.generate.crawler.CrawlConfig;
import com.ewcms.plugin.crawler.generate.url.URLCanonicalizer;
import com.ewcms.plugin.crawler.generate.url.WebURL;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.zip.GZIPInputStream;

/**
 * @author Yasser Ganjisaffar <lastname at gmail dot com>
 */

public class PageFetcher extends Configurable {

	private static final Logger logger = LoggerFactory.getLogger(PageFetcher.class);

	protected ThreadSafeClientConnManager connectionManager;

	protected DefaultHttpClient httpClient;

	protected final Object mutex = new Object();

	protected long lastFetchTime = 0;

	protected IdleConnectionMonitorThread connectionMonitorThread = null;

	public PageFetcher(CrawlConfig config) {
		super(config);

		HttpParams params = new BasicHttpParams();
		HttpProtocolParamBean paramsBean = new HttpProtocolParamBean(params);
		paramsBean.setVersion(HttpVersion.HTTP_1_1);
		paramsBean.setContentCharset("UTF-8");
		paramsBean.setUseExpectContinue(false);

		params.setParameter(CoreProtocolPNames.USER_AGENT, config.getUserAgentString());
		params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, config.getSocketTimeout());
		params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, config.getConnectionTimeout());

		params.setBooleanParameter("http.protocol.handle-redirects", false);

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));

		if (config.isIncludeHttpsPages()) {
			schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
		}

		connectionManager = new ThreadSafeClientConnManager(schemeRegistry);
		connectionManager.setMaxTotal(config.getMaxTotalConnections());
		connectionManager.setDefaultMaxPerRoute(config.getMaxConnectionsPerHost());
		httpClient = new DefaultHttpClient(connectionManager, params);

		if (config.getProxyHost() != null) {

			if (config.getProxyUsername() != null) {
				httpClient.getCredentialsProvider().setCredentials(
						new AuthScope(config.getProxyHost(), config.getProxyPort()),
						new UsernamePasswordCredentials(config.getProxyUsername(), config.getProxyPassword()));
			}

			HttpHost proxy = new HttpHost(config.getProxyHost(), config.getProxyPort());
			httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        }

        httpClient.addResponseInterceptor(new HttpResponseInterceptor() {

            @Override
            public void process(final HttpResponse response, final HttpContext context) throws HttpException,
                    IOException {
                HttpEntity entity = response.getEntity();
                Header contentEncoding = entity.getContentEncoding();
                if (contentEncoding != null) {
                    HeaderElement[] codecs = contentEncoding.getElements();
                    for (HeaderElement codec : codecs) {
                        if (codec.getName().equalsIgnoreCase("gzip")) {
                            response.setEntity(new GzipDecompressingEntity(response.getEntity()));
                            return;
                        }
                    }
                }
            }

        });

		if (connectionMonitorThread == null) {
			connectionMonitorThread = new IdleConnectionMonitorThread(connectionManager);
		}
		connectionMonitorThread.start();

	}

	public PageFetchResult fetchHeader(WebURL webUrl) {
		PageFetchResult fetchResult = new PageFetchResult();
		String toFetchURL = webUrl.getURL();
		HttpGet get = null;
		try {
			get = new HttpGet(toFetchURL);
			synchronized (mutex) {
				long now = (new Date()).getTime();
				if (now - lastFetchTime < config.getPolitenessDelay()) {
					Thread.sleep(config.getPolitenessDelay() - (now - lastFetchTime));
				}
				lastFetchTime = (new Date()).getTime();
			}
			get.addHeader("Accept-Encoding", "gzip");
			HttpResponse response = httpClient.execute(get);
			fetchResult.setEntity(response.getEntity());

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				if (statusCode != HttpStatus.SC_NOT_FOUND) {
					if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
						Header header = response.getFirstHeader("Location");
						if (header != null) {
							String movedToUrl = header.getValue();
							movedToUrl = URLCanonicalizer.getCanonicalURL(movedToUrl, toFetchURL).toExternalForm();
							webUrl.setURL(movedToUrl);
						} else {
							webUrl.setURL(null);
						}
						fetchResult.setStatusCode(PageFetchStatus.Moved);
						return fetchResult;
					}
					logger.info("Failed: " + response.getStatusLine().toString() + ", while fetching " + toFetchURL);
				} else if (config.isShow404PagesInLogs()) {
					logger.info("Not Found: " + toFetchURL + " (Link found in doc#: " + webUrl.getParentDocid() + ")");
				}
				fetchResult.setStatusCode(response.getStatusLine().getStatusCode());
				return fetchResult;
			}

			fetchResult.setFetchedUrl(toFetchURL);
			String uri = get.getURI().toString();
			if (!uri.equals(toFetchURL)) {
				if (!URLCanonicalizer.getCanonicalURL(uri).equals(toFetchURL)) {
					fetchResult.setFetchedUrl(uri);
				}
			}

			if (fetchResult.getEntity() != null) {
				long size = fetchResult.getEntity().getContentLength();
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
				if (size > config.getMaxDownloadSize()) {
					fetchResult.setStatusCode(PageFetchStatus.PageTooBig);
					return fetchResult;
				}

				fetchResult.setStatusCode(PageFetchStatus.OK);
				return fetchResult;

			} else {
				get.abort();
			}
		} catch (IOException e) {
			logger.error("Fatal transport error: " + e.getMessage() + " while fetching " + toFetchURL
					+ " (link found in doc #" + webUrl.getParentDocid() + ")");
			fetchResult.setStatusCode(PageFetchStatus.FatalTransportError);
			return fetchResult;
		} catch (IllegalStateException e) {
			// ignoring exceptions that occur because of not registering https
			// and other schemes
		} catch (Exception e) {
			if (e.getMessage() == null) {
				logger.error("Error while fetching " + webUrl.getURL());
			} else {
				logger.error(e.getMessage() + " while fetching " + webUrl.getURL());
			}
		} finally {
			try {
				if (fetchResult.getEntity() == null && get != null) {
					get.abort();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		fetchResult.setStatusCode(PageFetchStatus.UnknownError);
		return fetchResult;
	}

	public synchronized void shutDown() {
		if (connectionMonitorThread != null) {
			connectionManager.shutdown();
			connectionMonitorThread.shutdown();
		}
	}

	private static class GzipDecompressingEntity extends HttpEntityWrapper {

		public GzipDecompressingEntity(final HttpEntity entity) {
			super(entity);
		}

		@Override
		public InputStream getContent() throws IOException, IllegalStateException {

			// the wrapped entity's getContent() decides about repeatability
			InputStream wrappedin = wrappedEntity.getContent();

			return new GZIPInputStream(wrappedin);
		}

		@Override
		public long getContentLength() {
			// length of ungzipped content is not known
			return -1;
		}

	}
}
