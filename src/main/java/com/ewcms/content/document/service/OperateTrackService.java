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
import com.ewcms.security.manage.service.UserServiceable;
import com.ewcms.web.util.EwcmsContextUtil;

/**
 * 
 * @author wu_zhijun
 *
 */
@Service
public class OperateTrackService implements OperateTrackServiceable {

	@Autowired
	private OperateTrackDAO operateTrackDAO;
	@Autowired
	private UserServiceable userService;
	
	@Override
	public void addOperateTrack(Long articleMainId, String statusDesc, String description, String reason, String userName, String userRealName) {
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
	public void addOperateTrack(Long articleMainId, String statusDesc, String description, String reason){
		String userName = EwcmsContextUtil.getUserDetails().getUsername();
		String userRealName = userService.getUserRealName();
		addOperateTrack(articleMainId, statusDesc, description, reason, userName, userRealName);
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
