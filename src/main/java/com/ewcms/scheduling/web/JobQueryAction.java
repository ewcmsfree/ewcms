/**
 * 创建日期 2011-3-21
 * Copyright (c)2008 Jiangxi Institute of Computing Technology(JICT), All rights reserved.
 * JICT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.jict.org
 */
package com.ewcms.scheduling.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ewcms.comm.jpa.query.PageQueryable;
import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.manage.fac.AlqcSchedulingFacable;
import com.ewcms.scheduling.model.AlqcJob;
import com.ewcms.scheduling.quartz.AlqcJobsQuartzSchedulerable;
import com.ewcms.util.DataGrid;
import com.ewcms.util.JSONUtil;
import com.ewcms.util.Struts2Util;
import com.ewcms.web.action.QueryBaseAction;

/**
 *
 * @author 吴智俊
 */
@Controller("scheduling.job.query")
public class JobQueryAction extends QueryBaseAction {
	
	private static final long serialVersionUID = -8882837349113907705L;
	
	protected static final Log log = LogFactory.getLog(JobQueryAction.class);
	
	private DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
	
	@Autowired
	private AlqcJobsQuartzSchedulerable alqcJobsQuartzScheduler;
	@Autowired
	private AlqcSchedulingFacable alqcSchedulingFac;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected PageQueryable constructNewQuery(Order order) {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected PageQueryable constructQuery(Order order) {
		return null;
	}
	
	@Override
	public String query() {
		List<AlqcJob> alqcJobs = new ArrayList<AlqcJob>();
		try{
			alqcJobs = alqcSchedulingFac.getScheduledJobs();
			alqcJobs = alqcJobsQuartzScheduler.getJobsRuntimeInformation(alqcJobs);
		}catch(BaseException e){
			log.error(e.toString());
		}
		DataGrid data = new DataGrid(alqcJobs.size(), alqcJobs);
		Struts2Util.renderJson(JSONUtil.toJSON(data, DATE_FORMAT));
		return NONE;
	}

}
