/**
 * 创建日期 2011-3-21
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.scheduling.job.channel.fac;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ewcms.core.site.model.Channel;
import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.job.channel.model.EwcmsJobChannel;
import com.ewcms.scheduling.job.channel.service.EwcmsJobChannelServiceable;
import com.ewcms.scheduling.manage.vo.PageDisplayVO;

/**
 * 频道定时任务Fac
 * 
 * @author 吴智俊
 */
@Service()
public class EwcmsJobChannelFac implements EwcmsJobChannelFacable {
	@Autowired
	private EwcmsJobChannelServiceable ewcmsJobChannelService;

	@Override
	public EwcmsJobChannel getScheduledJobChannel(Integer jobId) {
		return ewcmsJobChannelService.getScheduledJobChannel(jobId);
	}

	@Override
	public EwcmsJobChannel findJobChannelByChannelId(Integer channelId) {
		return ewcmsJobChannelService.findJobChannelByChannelId(channelId);
	}

	@Override
	public Integer saveOrUpdateJobChannel(Integer channelId, PageDisplayVO vo, Boolean isAppChildenChannel) throws BaseException {
		return ewcmsJobChannelService.saveOrUpdateJobChannel(channelId, vo, isAppChildenChannel);
	}
	
	@Override
	public List<Channel> findChildenChannelByParentChannelId(Integer parentChannelId){
		return ewcmsJobChannelService.findChildenChannelByParentChannelId(parentChannelId);
	}
}
