/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.generate.job.channel.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.ewcms.core.site.model.Channel;
import com.ewcms.scheduling.model.JobInfo;

/**
 * 频道定时任务
 * 
 * <ul>
 * <li>channel:频道对象</li>
 * <li>subChannel:是否应用于子频道</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "job_channel")
@PrimaryKeyJoinColumn(name = "info_id")
public class EwcmsJobChannel extends JobInfo {

	private static final long serialVersionUID = -4373031603153928098L;
	
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "channel_id")
	private Channel channel;
    @Column(name = "sub_channel", nullable = false)
    private Boolean subChannel = true;

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public Boolean getSubChannel() {
		return subChannel;
	}

	public void setSubChannel(Boolean subChannel) {
		this.subChannel = subChannel;
	}
}
