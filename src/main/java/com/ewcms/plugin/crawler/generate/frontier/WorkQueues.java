/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.crawler.generate.frontier;

import com.ewcms.plugin.crawler.generate.url.WebURL;
import com.ewcms.plugin.crawler.generate.util.Util;
import com.sleepycat.je.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yasser Ganjisaffar <lastname at gmail dot com>
 */
public class WorkQueues {

	protected Database urlsDB = null;
	protected Environment env;

	protected boolean resumable;

	protected WebURLTupleBinding webURLBinding;

	protected final Object mutex = new Object();

	public WorkQueues(Environment env, String dbName, boolean resumable) throws DatabaseException {
		this.env = env;
		this.resumable = resumable;
		DatabaseConfig dbConfig = new DatabaseConfig();
		dbConfig.setAllowCreate(true);
		dbConfig.setTransactional(resumable);
		dbConfig.setDeferredWrite(!resumable);
		urlsDB = env.openDatabase(null, dbName, dbConfig);
		webURLBinding = new WebURLTupleBinding();
	}

	public List<WebURL> get(int max) throws DatabaseException {
		synchronized (mutex) {
			int matches = 0;
			List<WebURL> results = new ArrayList<WebURL>(max);

			Cursor cursor = null;
			OperationStatus result;
			DatabaseEntry key = new DatabaseEntry();
			DatabaseEntry value = new DatabaseEntry();
			Transaction txn;
			if (resumable) {
				txn = env.beginTransaction(null, null);
			} else {
				txn = null;
			}
			try {
				cursor = urlsDB.openCursor(txn, null);
				result = cursor.getFirst(key, value, null);

				while (matches < max && result == OperationStatus.SUCCESS) {
					if (value.getData().length > 0) {
						results.add(webURLBinding.entryToObject(value));
						matches++;
					}
					result = cursor.getNext(key, value, null);
				}
			} catch (DatabaseException e) {
				if (txn != null) {
					txn.abort();
					txn = null;
				}
				throw e;
			} finally {
				if (cursor != null) {
					cursor.close();
				}
				if (txn != null) {
					txn.commit();
				}
			}
			return results;
		}
	}

	public void delete(int count) throws DatabaseException {
		synchronized (mutex) {
			int matches = 0;

			Cursor cursor = null;
			OperationStatus result;
			DatabaseEntry key = new DatabaseEntry();
			DatabaseEntry value = new DatabaseEntry();
			Transaction txn;
			if (resumable) {
				txn = env.beginTransaction(null, null);
			} else {
				txn = null;
			}
			try {
				cursor = urlsDB.openCursor(txn, null);
				result = cursor.getFirst(key, value, null);

				while (matches < count && result == OperationStatus.SUCCESS) {
					cursor.delete();
					matches++;
					result = cursor.getNext(key, value, null);
				}
			} catch (DatabaseException e) {
				if (txn != null) {
					txn.abort();
					txn = null;
				}
				throw e;
			} finally {
				if (cursor != null) {
					cursor.close();
				}
				if (txn != null) {
					txn.commit();
				}
			}
		}
	}

	public void put(WebURL url) throws DatabaseException {
		
		/*
		 * The key that is used for storing URLs determines the order
		 * they are crawled. Lower key values results in earlier crawling.
		 * Here our keys are 6 bytes. The first byte comes from the URL priority.
		 * The second byte comes from depth of crawl at which this URL is first found.
		 * The rest of the 4 bytes come from the docid of the URL. As a result,
		 * URLs with lower priority numbers will be crawled earlier. If priority
		 * numbers are the same, those found at lower depths will be crawled earlier.
		 * If depth is also equal, those found earlier (therefore, smaller docid) will
		 * be crawled earlier.
		 */
		byte[] keyData = new byte[6];
		keyData[0] = url.getPriority();
		keyData[1] = (url.getDepth() > Byte.MAX_VALUE ? Byte.MAX_VALUE : (byte) url.getDepth());
		Util.putIntInByteArray(url.getDocid(), keyData, 2);

		DatabaseEntry value = new DatabaseEntry();
		webURLBinding.objectToEntry(url, value);
		Transaction txn;
		if (resumable) {
			txn = env.beginTransaction(null, null);
		} else {
			txn = null;
		}
		urlsDB.put(txn, new DatabaseEntry(keyData), value);
		if (resumable) {
			if (txn != null) {
				txn.commit();
			}
		}
	}

	public long getLength() {
		try {
			return urlsDB.count();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public void sync() {
		if (resumable) {
			return;
		}
		if (urlsDB == null) {
			return;
		}
		try {
			urlsDB.sync();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			urlsDB.close();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}
}
