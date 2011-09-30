/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.message.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ewcms.content.message.dao.MsgReceiveDAO;
import com.ewcms.content.message.model.MsgReceive;
import com.ewcms.web.util.EwcmsContextUtil;

/**
 * 
 * @author wu_zhijun
 *
 */
@Service
public class MsgReceiveService implements MsgReceiveServiceable {
	
	@Autowired
	private MsgReceiveDAO msgReceiveDAO;

	@Override
	public void delMsgReceive(Long msgReceiveId) {
		msgReceiveDAO.removeByPK(msgReceiveId);
	}

	@Override
	public MsgReceive findMsgReceive(Long msgReceiveId) {
		return msgReceiveDAO.findMsgReceiveByUserNameAndId(EwcmsContextUtil.getUserDetails().getUsername(), msgReceiveId);
	}

	@Override
	public List<MsgReceive> findMsgReceiveByUserName() {
		return msgReceiveDAO.findMsgReceiveByUserName(EwcmsContextUtil.getUserDetails().getUsername());
	}

	@Override
	public void markReadMsgReceive(Long msgReceiveId, Boolean read) {
		MsgReceive msgReceive = findMsgReceive(msgReceiveId);
		Assert.notNull(msgReceive);
		if (msgReceive.getRead() != read){
			msgReceive.setRead(read);
			if (read){
				msgReceive.setReadTime(new Date(Calendar.getInstance().getTime().getTime()));
			}else{
				msgReceive.setReadTime(null);
			}
			msgReceiveDAO.merge(msgReceive);
		}
	}

	@Override
	public List<MsgReceive> findMsgReceiveByUnRead() {
		return msgReceiveDAO.findMsgReceiveByUserNameAndUnRead(EwcmsContextUtil.getUserDetails().getUsername());
	}
}
