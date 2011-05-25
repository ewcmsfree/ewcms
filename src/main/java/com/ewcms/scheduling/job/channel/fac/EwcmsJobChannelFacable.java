/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.job.channel.fac;

import java.util.List;

import com.ewcms.core.site.model.Channel;
import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.job.channel.model.EwcmsJobChannel;
import com.ewcms.scheduling.manage.vo.PageDisplayVO;

/**
 * 频道定时任务Fac接口
 * 
 * @author 吴智俊
 */
public interface EwcmsJobChannelFacable {
	/**
	 * 通过任务编号查询频道定时任务对象
	 * 
	 * @param jobId 任务编号
	 * @return 频道定时任务对象
	 */
	public EwcmsJobChannel getScheduledJobChannel(Integer jobId);
	
	/**
	 * 通过频道编号查询频道定期任务对象
	 * 
	 * @param channelId 频道编号
	 * @return 频道定时任务对象
	 */
	public EwcmsJobChannel findJobChannelByChannelId(Integer channelId);
	
	/**
	 * 通过频道父节点编号查询频道子节点对象集合
	 * 
	 * @param parentChannelId 频道父节点编号
	 * @return 频道子节点对象集合
	 */
	public List<Channel> findChildenChannelByParentChannelId(Integer parentChannelId);
	
	/**
	 * 新增或修改频道定时任务
	 * 
	 * @param channelId 频道编号
	 * @param vo 页面设置的对象值
	 * @param subChannel 是否应用于子频道
	 * @return 频道定时任务编号
	 * @throws BaseException
	 */
	public Integer saveOrUpdateJobChannel(Integer channelId, PageDisplayVO vo, Boolean isAppChildenChannel) throws BaseException;
}
