/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.core.site.service;

import java.util.List;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.ewcms.core.site.dao.ChannelDAO;
import com.ewcms.core.site.model.Channel;

/**
 * @author wuzhijun
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"}, inheritLocations = true)
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class ChannelSortInitTest{
	
	@Autowired
	private ChannelDAO channelDAO;
	
	@Test
	public void updateChannelSort() throws Exception{
		List<Integer> parentIds = channelDAO.findChannelParent();
		for (Integer parentId : parentIds){
			List<Channel> channels = channelDAO.getChannelChildren(parentId);
			Long sort = 1L;
			for (Channel channel : channels){
				channel.setSort(sort);
				channelDAO.merge(channel);
		    	sort++;
			}
		}
	}
}
