/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.content.document.dao.OperateTrackDAO;
import com.ewcms.content.document.model.OperateTrack;

/**
 * 
 * @author wu_zhijun
 *
 */
@Service
public class OperateTrackService implements OperateTrackServiceable {

	@Autowired
	private OperateTrackDAO operateTrackDAO;
	
	@Override
	public void addOperateTrack(Long articleMainId, String statusDesc,	String description, String reason, String userName,	String userRealName) {
		OperateTrack operateTrack = new OperateTrack();
		
		operateTrack.setArticleMainId(articleMainId);
		operateTrack.setStatusDesc(statusDesc);
		operateTrack.setDescription(description);
		operateTrack.setReason(reason);
		operateTrack.setUserName(userName);
		operateTrack.setUserRealName(userRealName);
		
		operateTrackDAO.persist(operateTrack);
	}

	@Override
	public List<OperateTrack> findOperateTrackByArticleMainId(Long articleMainId) {
		return operateTrackDAO.findOperateTrackByArticleMainId(articleMainId);
	}

	@Override
	public OperateTrack findOperateTrackById(Long operateTrackId) {
		return operateTrackDAO.get(operateTrackId);
	}

}
