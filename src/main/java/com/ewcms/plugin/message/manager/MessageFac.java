/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.message.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.plugin.message.manager.service.MsgReceiveServiceable;
import com.ewcms.plugin.message.manager.service.MsgSendServiceable;
import com.ewcms.plugin.message.model.MsgReceive;
import com.ewcms.plugin.message.model.MsgSend;

@Service
public class MessageFac implements MessageFacable {

	@Autowired
	private MsgSendServiceable msgSendService;
	@Autowired
	private MsgReceiveServiceable msgReceiveService;
	
	@Override
	public List<MsgSend> findMsgSendByUserName() {
		return msgSendService.findMsgSendByUserName();
	}

	@Override
	public List<MsgReceive> findMsgReceiveByUserName() {
		return msgReceiveService.findMsgReceiveByUserName();
	}

	@Override
	public Long addMsgSend(MsgSend msgSend, String content, List<String> userNames) {
		return msgSendService.addMsgSend(msgSend, content, userNames);
	}

	@Override
	public Long updMsgSend(MsgSend msgSend) {
		return null;
	}

	@Override
	public void delMsgSend(Long msgSendId) {
		msgSendService.delMsgSend(msgSendId);
	}

	@Override
	public MsgSend findMsgSend(Long msgSendId) {
		return msgSendService.findMsgSend(msgSendId);
	}

	@Override
	public void delMsgReceive(Long msgReceiveId) {
		msgReceiveService.delMsgReceive(msgReceiveId);
	}

	@Override
	public MsgReceive findMsgReceive(Long msgReceiveId) {
		return msgReceiveService.findMsgReceive(msgReceiveId);
	}

	@Override
	public void markReadMsgReceive(Long msgReceiveId, Boolean read) {
		msgReceiveService.markReadMsgReceive(msgReceiveId, read);
	}

	@Override
	public Long addSubscription(Long msgSendId, String title, String detail) {
		return msgSendService.addSubscription(msgSendId, title, detail);
	}

	@Override
	public void delSubscription(Long msgContentId) {
		msgSendService.delSubscription(msgContentId);
	}

//	@Override
//	public List<MsgSend> findMsgSendByGeneral(Integer row) {
//		return msgSendService.findMsgSendByGeneral(row);
//	}

	@Override
	public List<MsgSend> findMsgSendByNotice(Integer row) {
		return msgSendService.findMsgSendByNotice(row);
	}

	@Override
	public List<MsgSend> findMsgSendBySubscription(Integer row) {
		return msgSendService.findMsgSendBySubscription(row);
	}

	@Override
	public List<MsgReceive> findMsgReceiveByUnRead(String userName) {
		return msgReceiveService.findMsgReceiveByUnRead(userName);
	}

	@Override
	public void readMsgReceive(Long msgReceiveId) {
		msgReceiveService.readMsgReceive(msgReceiveId);
	}

	@Override
	public String subscribeMsg(Long msgSendId) {
		return msgSendService.subscribeMsg(msgSendId);
	}

	@Override
	public Long findUnReadMessageCountByUserName(String userName) {
		return msgReceiveService.findUnReadMessageCountByUserName(userName);
	}
}
