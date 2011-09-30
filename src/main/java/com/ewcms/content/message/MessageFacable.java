/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.message;

import java.util.List;

import com.ewcms.content.message.model.MsgReceive;
import com.ewcms.content.message.model.MsgSend;

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
	
	public List<MsgSend> findMsgSendByGeneral();
	
	public List<MsgSend> findMsgSendByNotice();
	
	public List<MsgSend> findMsgSendBySubscription();
	
	public List<MsgReceive> findMsgReceiveByUnRead();
	
	public void readMsgReceive(Long msgReceiveId);
}
