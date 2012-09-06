/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.manager.web;

import org.springframework.beans.factory.annotation.Autowired;

import com.ewcms.core.site.SiteFacable;
import com.ewcms.core.site.model.Channel;
import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.generate.job.channel.EwcmsJobChannelFacable;
import com.ewcms.scheduling.generate.job.channel.model.EwcmsJobChannel;
import com.ewcms.scheduling.manager.util.ConversionUtil;
import com.ewcms.scheduling.manager.vo.PageDisplayVO;
import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author 吴智俊
 */
public class JobChannelAction extends ActionSupport {

	private static final long serialVersionUID = -7180641001521655948L;
	
	@Autowired
	private EwcmsJobChannelFacable ewcmsJobChannelFac;
	@Autowired
	private SiteFacable siteFac;
	
	private Integer channelId;
	private Boolean subChannel;
	private PageDisplayVO pageDisplayVo;
	
	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}


	public Boolean getSubChannel() {
		return subChannel;
	}

	public void setSubChannel(Boolean subChannel) {
		this.subChannel = subChannel;
	}

	public PageDisplayVO getPageDisplayVo() {
		return pageDisplayVo;
	}

	public void setPageDisplayVo(PageDisplayVO pageDisplayVo) {
		this.pageDisplayVo = pageDisplayVo;
	}

	public String getJobChannel(){
		EwcmsJobChannel jobChannel = ewcmsJobChannelFac.findJobChannelByChannelId(getChannelId());
		PageDisplayVO vo = new PageDisplayVO();
		if (jobChannel != null){
			vo = ConversionUtil.constructPageVo(jobChannel);
			setSubChannel(jobChannel.getSubChannel());
		}else{
			Channel channel = siteFac.getChannel(getChannelId());
			if (channel != null){
				vo.setLabel(channel.getName());
			}
		}
		setPageDisplayVo(vo);
		return INPUT;
	}

	public String saveJobChannel() {
		try{
			Long jobId = ewcmsJobChannelFac.saveOrUpdateJobChannel(getChannelId(), getPageDisplayVo(), getSubChannel());
			if (jobId == null){
				addActionMessage("操作失败");
			}
			EwcmsJobChannel jobChannel = ewcmsJobChannelFac.findJobChannelByChannelId(getChannelId());
			if (jobChannel != null){
				PageDisplayVO vo = ConversionUtil.constructPageVo(jobChannel);
				setSubChannel(jobChannel.getSubChannel());
				setPageDisplayVo(vo);
				addActionMessage("数据保存成功!");
			}
		}catch(BaseException e){
			addActionMessage(e.getPageMessage());
		}
		return SUCCESS;
	}
}
