/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.scheduling.generate.job.report;

import java.util.List;
import java.util.Set;

import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.generate.job.report.model.EwcmsJobParameter;
import com.ewcms.scheduling.generate.job.report.model.EwcmsJobReport;
import com.ewcms.scheduling.manager.vo.PageDisplayVO;

/**
 * 
 * @author wu_zhijun
 *
 */
public interface EwcmsJobReportFacable {
	/**
	 * 新增和修改报表定时工作任务
	 * 
	 * @param reportId 采集器编号
	 * @param vo 定时设置对象
	 * @return 定时工作任务编号
	 */
	public Integer saveOrUpdateJobReport(Long reportId, PageDisplayVO vo, String reportType, Set<EwcmsJobParameter> ewcmsJobParameters) throws BaseException;
	
	/**
	 * 通过任务编号查询报表定时任务对象
	 * 
	 * @param jobId 任务编号
	 * @return EwcmsJobReport 报表定时任务对象
	 */
	public EwcmsJobReport getScheduledJobReport(Integer jobId);
	
	/**
	 * 查询报表定时任务对象
	 * 
	 * @param reportId 报表编号(包括文字和图型报表)
	 * @param reportType 报表类型(文字:text,图型:chart)
	 * @return EwcmsJobReport 报表定时任务对象
	 */
	public EwcmsJobReport getSchedulingByReportId(Long reportId, String reportType);
	
	/**
	 * 查询报表定时任务参数对象
	 * 
	 * @param jobReportId 任务编号
	 * @return List 定时任务参数集合
	 */
	public List<EwcmsJobParameter> findByJobReportParameterById(Integer jobReportId);
}
