package com.ewcms.scheduling.generate.job.report;

import java.util.List;
import java.util.Set;

import com.ewcms.scheduling.BaseException;
import com.ewcms.scheduling.generate.job.report.model.EwcmsJobParameter;
import com.ewcms.scheduling.generate.job.report.model.EwcmsJobReport;
import com.ewcms.scheduling.manage.vo.PageDisplayVO;

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
	 * @return 采集器定时任务对象
	 */
	public EwcmsJobReport getScheduledJobReport(Integer jobId);
	
	public EwcmsJobReport getSchedulingByReportId(Long reportId, String reportType);
	
	public List<EwcmsJobParameter> findByJobReportParameterById(Integer jobReportId);
}
