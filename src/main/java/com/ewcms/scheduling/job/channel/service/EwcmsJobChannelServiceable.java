/**
 * 创建日期 2011-3-21
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.scheduling.job.channel.service;

import java.util.List;

import com.ewcms.core.site.model.Channel;
import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.job.channel.model.EwcmsJobChannel;
import com.ewcms.scheduling.manage.vo.PageDisplayVO;

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
	public Integer saveOrUpdateJobChannel(Integer channelId, PageDisplayVO vo, Boolean isAppChildenChannel) throws BaseException;
	
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
	 * 查询频道对象
	 * @param channelId 频道编号
	 * @return
	 */
	public Channel findChannelByChannelId(Integer channelId);
}
