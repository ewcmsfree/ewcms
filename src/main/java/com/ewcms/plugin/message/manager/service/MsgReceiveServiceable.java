/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.plugin.message.manager.service;

import java.util.List;

import com.ewcms.plugin.message.model.MsgReceive;

public interface MsgReceiveServiceable {
	public void delMsgReceive(Long msgReceiveId);

	public MsgReceive findMsgReceive(Long msgReceiveId);
	
	public List<MsgReceive> findMsgReceiveByUserName();
	
	public void markReadMsgReceive(Long msgReceiveId, Boolean read);
	
	public List<MsgReceive> findMsgReceiveByUnRead(String userName);
	
	public Long findUnReadMessageCountByUserName(String userName);
	
	public void readMsgReceive(Long msgReceiveId);
}
