/**
 * 创建日期 2011-3-21
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.scheduling.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.core.site.SiteFac;
import com.ewcms.core.site.model.Channel;
import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.job.channel.fac.EwcmsJobChannelFacable;
import com.ewcms.scheduling.job.channel.model.EwcmsJobChannel;
import com.ewcms.scheduling.manage.util.ConversionUtil;
import com.ewcms.scheduling.manage.vo.PageDisplayVO;
import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author 吴智俊
 */
@Controller("scheduling.jobchannel.index")
public class JobChannelAction extends ActionSupport {

	private static final long serialVersionUID = -7180641001521655948L;
	
	@Autowired
	private EwcmsJobChannelFacable ewcmsJobChannelFac;
	@Autowired
	private SiteFac siteFac;
	
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
			Integer jobId = ewcmsJobChannelFac.saveOrUpdateJobChannel(getChannelId(), getPageDisplayVo(), getSubChannel());
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
