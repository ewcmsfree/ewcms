/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.crawler.manager.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.plugin.crawler.manager.CrawlerFacable;
import com.ewcms.plugin.crawler.model.Storage;
import com.ewcms.web.CrudBaseAction;

/**
 * 
 * @author wuzhijun
 *
 */
public class StorageAction extends CrudBaseAction<Storage, Long> {
	
	private static final long serialVersionUID = -3015680995222096823L;

	@Autowired
	private CrawlerFacable crawlerFac;
	
	public List<Long> getSelections() {
        return super.getOperatorPK();
    }
	
	public void setSelections(List<Long> selections) {
        super.setOperatorPK(selections);
    }
	
	@Override
	protected Long getPK(Storage vo) {
		return null;
	}

	@Override
	protected Storage getOperator(Long pk) {
		return null;
	}

	@Override
	protected void deleteOperator(Long pk) {
		crawlerFac.delStorage(pk);
	}

	@Override
	protected Long saveOperator(Storage vo, boolean isUpdate){
			return null;
	}

	@Override
	protected Storage createEmptyVo() {
		return null;
	}
}