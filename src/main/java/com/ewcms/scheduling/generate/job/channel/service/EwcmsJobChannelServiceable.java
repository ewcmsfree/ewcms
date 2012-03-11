/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.generate.job.channel.service;

import com.ewcms.core.site.model.Channel;
import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.generate.job.channel.model.EwcmsJobChannel;
import com.ewcms.scheduling.manager.vo.PageDisplayVO;

/**
 * 频道定时任务Service接口
 * 
 * @author 吴智俊
 */
public interface EwcmsJobChannelServiceable {
	
	/**
	 * 新增和修改频道定时工作任务
	 * 
	 * @param channelId 频道编号
	 * @param vo 定时设置对象
	 * @param isAppChildenChannel 是否应用于子频道
	 * @return 定时工作任务编号
	 */
	public Long saveOrUpdateJobChannel(Integer channelId, PageDisplayVO vo, Boolean isAppChildenChannel) throws BaseException;
	
	/**
	 * 通过任务编号查询频道定时任务对象
	 * 
	 * @param jobId 任务编号
	 * @return 频道定时任务对象
	 */
	public EwcmsJobChannel getScheduledJobChannel(Long jobId);
	
	/**
	 * 通过频道编号查询频道定期任务对象
	 * 
	 * @param channelId 频道编号
	 * @return 频道定时任务对象
	 */
	public EwcmsJobChannel findJobChannelByChannelId(Integer channelId);
	
	/**
	 * 查询频道对象
	 * @param channelId 频道编号
	 * @return
	 */
	public Channel findChannelByChannelId(Integer channelId);
}
