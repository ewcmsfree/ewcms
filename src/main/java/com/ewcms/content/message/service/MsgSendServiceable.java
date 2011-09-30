/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.message.service;

import java.util.List;

import com.ewcms.content.message.model.MsgSend;

public interface MsgSendServiceable {
	public Long addMsgSend(MsgSend msgSend, String content, List<String> userNames);

	public Long updMsgSend(MsgSend msgSend);

	public void delMsgSend(Long msgSendId);

	public MsgSend findMsgSend(Long msgSendId);
	
	public List<MsgSend> findMsgSendByUserName();
	
	public Long addSubscription(Long msgSendId, String title, String detail);
	
	public void delSubscription(Long msgContentId);
	
	public List<MsgSend> findMsgSendByGeneral();
	
	public List<MsgSend> findMsgSendByNotice();
	
	public List<MsgSend> findMsgSendBySubscription();
	
	public String subscribeMsg(Long msgSendId);
}
