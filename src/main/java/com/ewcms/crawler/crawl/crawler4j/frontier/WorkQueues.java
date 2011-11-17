/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.crawler.crawl.crawler4j.frontier;

import java.util.ArrayList;
import java.util.List;

import com.ewcms.crawler.crawl.crawler4j.url.WebURL;
import com.ewcms.crawler.crawl.crawler4j.util.Util;
import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.Transaction;


/**
 * @author Yasser Ganjisaffar <yganjisa at uci dot edu>
 */

public class WorkQueues {

	protected Database urlsDB = null;
	protected Environment env;

	private boolean resumable;

	private WebURLTupleBinding webURLBinding;

	protected Object mutex = "WorkQueues_Mutex";

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
			OperationStatus result = null;
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
						WebURL curi = (WebURL) webURLBinding.entryToObject(value);
						results.add(curi);
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
			OperationStatus result = null;
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

	public void put(WebURL curi) throws DatabaseException {
		byte[] keyData = Util.int2ByteArray(curi.getDocid());
		DatabaseEntry value = new DatabaseEntry();
		webURLBinding.objectToEntry(curi, value);
		Transaction txn;
		if (resumable) {
			txn = env.beginTransaction(null, null);
		} else {
			txn = null;
		}
		urlsDB.put(txn, new DatabaseEntry(keyData), value);
		if (resumable) {
			txn.commit();
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
