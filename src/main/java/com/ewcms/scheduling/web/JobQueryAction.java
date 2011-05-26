/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
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

import com.ewcms.common.query.Resultable;
import com.ewcms.common.query.jpa.QueryFactory;
import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.manage.fac.SchedulingFacable;
import com.ewcms.scheduling.model.JobInfo;
import com.ewcms.scheduling.quartz.JobsQuartzSchedulerable;
import com.ewcms.web.QueryBaseAction;
import com.ewcms.web.util.JSONUtil;
import com.ewcms.web.util.Struts2Util;
import com.ewcms.web.vo.DataGrid;

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
	private JobsQuartzSchedulerable jobsQuartzScheduler;
	@Autowired
	private SchedulingFacable schedulingFac;

    @Override
    protected Resultable queryResult(QueryFactory queryFactory, String cacheKey, int rows, int page, Order order) {
        return null;
    }

    @Override
    protected Resultable querySelectionsResult(QueryFactory queryFactory, int rows, int page, String[] selections, Order order) {
        return null;
    }
    
	@Override
	public String query() {
		List<JobInfo> alqcJobs = new ArrayList<JobInfo>();
		try{
			alqcJobs = schedulingFac.getScheduledJobs();
			alqcJobs = jobsQuartzScheduler.getJobsRuntimeInformation(alqcJobs);
		}catch(BaseException e){
			log.error(e.toString());
		}
		DataGrid data = new DataGrid(alqcJobs.size(), alqcJobs);
		Struts2Util.renderJson(JSONUtil.toJSON(data, DATE_FORMAT));
		
		return NONE;
	}

}
