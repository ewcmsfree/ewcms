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

package com.ewcms.crawler.crawl.crawler4j.frontier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.crawler.crawl.crawler4j.util.Util;
import com.sleepycat.je.*;


/**
 * @author Yasser Ganjisaffar <yganjisa at uci dot edu>
 */

public class DocIDServer {

	private Database docIDsDB = null;

	private static final Logger logger = LoggerFactory.getLogger(DocIDServer.class.getName());
	private Object mutex = "DocIDServer_Mutex";

	private static int lastDocID;
	private static boolean resumable;

	public void init(Environment env, boolean resumable) throws DatabaseException {
		DocIDServer.resumable = resumable;
		DatabaseConfig dbConfig = new DatabaseConfig();
		dbConfig.setAllowCreate(true);
		dbConfig.setTransactional(resumable);
		dbConfig.setDeferredWrite(!resumable);
		docIDsDB = env.openDatabase(null, "DocIDs", dbConfig);
		if (resumable) {
			int docCount = getDocCount();
			if (docCount > 0) {
				logger.info("Loaded " + docCount + " URLs that had been detected in previous crawl.");
				lastDocID = docCount;
			}
		} else {
			lastDocID = 0;
		}
	}

	/**
	 * Returns the docid of an already seen url.
	 * If url is not seen before, it will return -1
	 */
	public int getDocID(String url) {
		synchronized (mutex) {
			if (docIDsDB == null) {
				return -1;
			}
			OperationStatus result = null;
			DatabaseEntry value = new DatabaseEntry();
			try {
				DatabaseEntry key = new DatabaseEntry(url.getBytes());
				result = docIDsDB.get(null, key, value, null);

				if (result == OperationStatus.SUCCESS && value.getData().length > 0) {
					return Util.byteArray2Int(value.getData());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return -1;
		}
	}

	public int getNewDocID(String url) {
		synchronized (mutex) {
			try {
				// Make sure that we have not already assigned a docid for this URL
				int docid = getDocID(url);
				if (docid > 0) {
					return docid;
				}

				lastDocID++;
				docIDsDB.put(null, new DatabaseEntry(url.getBytes()), new DatabaseEntry(Util.int2ByteArray(lastDocID)));
				return lastDocID;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return -1;
		}
	}

	public int getDocCount() {
		try {
			return (int) docIDsDB.count();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public void sync() {
		if (resumable) {
			return;
		}
		if (docIDsDB == null) {
			return;
		}
		try {
			docIDsDB.sync();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			docIDsDB.close();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}
}
