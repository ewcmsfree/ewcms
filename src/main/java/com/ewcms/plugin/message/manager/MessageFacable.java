/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.message.manager;

import java.util.List;

import com.ewcms.plugin.message.model.MsgReceive;
import com.ewcms.plugin.message.model.MsgSend;

public interface MessageFacable {
	public Long addMsgSend(MsgSend msgSend, String content, List<String> userNames);

	public Long updMsgSend(MsgSend msgSend);

	public void delMsgSend(Long msgSendId);

	public MsgSend findMsgSend(Long msgSendId);
	
	public List<MsgSend> findMsgSendByUserName();

	public void delMsgReceive(Long msgReceiveId);

	public MsgReceive findMsgReceive(Long msgReceiveId);
	
	public List<MsgReceive> findMsgReceiveByUserName();
	
	public void markReadMsgReceive(Long msgReceiveId, Boolean read);
	
	public Long addSubscription(Long msgSendId, String title, String detail);
	
	public void delSubscription(Long msgContentId);
	
//	public List<MsgSend> findMsgSendByGeneral(Integer row);
	
	public List<MsgSend> findMsgSendByNotice(Integer row);
	
	public List<MsgSend> findMsgSendBySubscription(Integer row);
	
	public List<MsgReceive> findMsgReceiveByUnRead(String userName);
	
	public Long findUnReadMessageCountByUserName(String userName);
	
	public void readMsgReceive(Long msgReceiveId);
	
	public String subscribeMsg(Long msgSendId);
}
