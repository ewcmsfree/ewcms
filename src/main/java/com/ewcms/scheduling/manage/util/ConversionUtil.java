/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.manage.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.ewcms.plugin.report.generate.vo.PageShowParam;
import com.ewcms.plugin.report.model.Parameter;
import com.ewcms.scheduling.generate.job.report.model.EwcmsJobParameter;
import com.ewcms.scheduling.manage.vo.PageDisplayVO;
import com.ewcms.scheduling.model.JobCalendarTrigger;
import com.ewcms.scheduling.model.JobInfo;
import com.ewcms.scheduling.model.JobSimpleTrigger;
import com.ewcms.scheduling.model.JobTrigger;

/**
 * @author 吴智俊
 */
public class ConversionUtil {
	/**
	 * 字符串转换成整型数组
	 * 
	 * @param value 字符串
	 * @return 整型数组
	 */
	public static Integer[] stringToArray(String value) {
		if (value != null && value.length() > 0) {
			StringTokenizer tokenizer = new StringTokenizer(value, ",", false);
			Integer[] values = new Integer[tokenizer.countTokens()];
			for (int i = 0; tokenizer.hasMoreElements(); i++) {
				values[i] = Integer.valueOf(tokenizer.nextToken());
			}
			return values;
		} else {
			return new Integer[0];
		}
	}

	/**
	 * 整型数组转换成字符串
	 * 
	 * @param values 整型数组
	 * @return 字符串
	 */
	public static String arrayToString(Integer[] values) {
		String value = "";
		if (values != null && values.length > 0) {
			for (Integer i : values) {
				value += i + ",";
			}
			if (value.length() > 0) {
				value = value.substring(0, value.length() - 1);
			}
		}
		return value;
	}
	
	public static PageDisplayVO constructPageVo(JobInfo alqcJob) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		PageDisplayVO pageDisplayVo = new PageDisplayVO();
		if (alqcJob == null) return pageDisplayVo;
		// 任务(Job)信息 //
		pageDisplayVo.setJobId(alqcJob.getId());
		pageDisplayVo.setJobVersion(alqcJob.getVersion());
		pageDisplayVo.setLabel(alqcJob.getLabel());
		pageDisplayVo.setUserName(alqcJob.getUserName());
		pageDisplayVo.setDescription(alqcJob.getDescription());
		if (alqcJob.getJobClass() != null){
			pageDisplayVo.setJobClassId(alqcJob.getJobClass().getId());
		}
		// 开始状态 //
		if (alqcJob.getTrigger().getStartType().intValue() == 1) {// 立刻执行 //
			pageDisplayVo.setStart(1);
		} else {// 开始时间 //
			pageDisplayVo.setStart(2);
			Date startDate = alqcJob.getTrigger().getStartDate();
			if (startDate != null){
				pageDisplayVo.setStartDate(bartDateFormat.format(startDate));
			}
		}
		// 调度计划 //
		JobTrigger trigger = alqcJob.getTrigger();
		if (trigger != null) {
			pageDisplayVo.setTriggerId(trigger.getId());
			pageDisplayVo.setTriggerVersion(trigger.getVersion());
			if (trigger instanceof JobSimpleTrigger) {
				Integer recurrenceInterval = ((JobSimpleTrigger) trigger).getRecurrenceInterval();
				Integer recurrenceIntervalUnit = ((JobSimpleTrigger) trigger).getRecurrenceIntervalUnit();
				Integer occurenceCount = ((JobSimpleTrigger) trigger).getOccurrenceCount();
				if (recurrenceInterval == null && recurrenceIntervalUnit == null && occurenceCount == 1) {
					pageDisplayVo.setMode(0);
					pageDisplayVo.setOccurrenceCount(occurenceCount);
				} else {// 简单 //
					pageDisplayVo.setMode(1);

					pageDisplayVo.setRecurrenceInterval(((JobSimpleTrigger) trigger).getRecurrenceInterval());
					pageDisplayVo.setRecurrenceIntervalUnit(((JobSimpleTrigger) trigger).getRecurrenceIntervalUnit());

					Date endDate = ((JobSimpleTrigger) trigger).getEndDate();

					if (endDate == null && occurenceCount == -1) {// 无限期 //
						pageDisplayVo.setOccur(1);
						pageDisplayVo.setEndDateSimple(null);
						pageDisplayVo.setOccurrenceCount(null);
					} else if (endDate != null && occurenceCount == -1) {// 结束日期 //
						pageDisplayVo.setOccur(2);
						pageDisplayVo.setEndDateSimple(bartDateFormat.format(endDate));
						pageDisplayVo.setOccurrenceCount(null);
					} else if (endDate == null && occurenceCount > 0) {// 次数 //
						pageDisplayVo.setOccur(3);
						pageDisplayVo.setEndDateSimple(null);
						pageDisplayVo.setOccurrenceCount(occurenceCount);
					}
				}
			} else if (trigger instanceof JobCalendarTrigger) {
				pageDisplayVo.setMode(2);// 复杂 //
				pageDisplayVo.setMinutes(((JobCalendarTrigger) trigger).getMinutes());// 分钟 //
				pageDisplayVo.setHours(((JobCalendarTrigger) trigger).getHours());// 小时 //
				pageDisplayVo.setMonths(ConversionUtil.stringToArray(((JobCalendarTrigger) trigger).getMonths()));// 月份 //

				Date endDate = ((JobCalendarTrigger) trigger).getEndDate();
				if (endDate != null){
					pageDisplayVo.setEndDateCalendar(bartDateFormat.format(endDate));
				}
				
				String weekDays = ((JobCalendarTrigger) trigger).getWeekDays();
				String monthDays = ((JobCalendarTrigger) trigger).getMonthDays();
				if (weekDays != null && weekDays.length() > 0) {// 一周之内 //
					pageDisplayVo.setDays(2);
					pageDisplayVo.setWeekDays(ConversionUtil.stringToArray(weekDays));
					pageDisplayVo.setMonthDays(null);
				} else if (monthDays != null && monthDays.length() > 0) {// 一月之内 //
					pageDisplayVo.setDays(3);
					pageDisplayVo.setWeekDays(null);
					pageDisplayVo.setMonthDays(ConversionUtil.stringToArray(monthDays));
				} else {// 每一天 //
					pageDisplayVo.setDays(1);
					pageDisplayVo.setWeekDays(null);
					pageDisplayVo.setMonthDays(null);
				}

			} else {// 无 //
				pageDisplayVo.setMode(0);
				pageDisplayVo.setOccurrenceCount(1);
			}
		}
		return pageDisplayVo;
	}
	
	public static JobInfo constructAlqcJobVo(JobInfo jobInfo, PageDisplayVO pageDisplayVo) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			jobInfo.setVersion(pageDisplayVo.getJobVersion());
			jobInfo.setLabel(pageDisplayVo.getLabel());
			jobInfo.setUserName(pageDisplayVo.getUserName());
			jobInfo.setDescription(pageDisplayVo.getDescription());

			if (pageDisplayVo.getMode() == 0) {// 无 //
				JobSimpleTrigger t = new JobSimpleTrigger();
				t.setId(pageDisplayVo.getTriggerId());
				t.setVersion(pageDisplayVo.getTriggerVersion());

				if (pageDisplayVo.getStart() == 1) {// 立刻执行 //
					t.setStartType(1);
					t.setStartDate(null);
				} else {// 开始时间 //
					t.setStartType(2);
					if (pageDisplayVo.getStartDate() != null && !pageDisplayVo.getStartDate().equals("")) {
						t.setStartDate(bartDateFormat.parse(pageDisplayVo.getStartDate()));
					}
				}
				t.setOccurrenceCount(1);

				jobInfo.setTrigger(t);
			} else if (pageDisplayVo.getMode() == 1) {// SimpleTrigger //
				JobSimpleTrigger st = new JobSimpleTrigger();

				if (pageDisplayVo.getStart() == 1) {// 立刻执行 //
					st.setStartType(1);
					st.setStartDate(null);
				} else {// 开始时间 //
					st.setStartType(2);
					if (pageDisplayVo.getStartDate() != null && !pageDisplayVo.getStartDate().equals("")) {
						st.setStartDate(bartDateFormat.parse(pageDisplayVo.getStartDate()));
					}
				}
				st.setId(pageDisplayVo.getTriggerId());
				st.setVersion(pageDisplayVo.getTriggerVersion());
				st.setRecurrenceInterval(pageDisplayVo.getRecurrenceInterval());
				st.setRecurrenceIntervalUnit(pageDisplayVo.getRecurrenceIntervalUnit());

				if (pageDisplayVo.getOccur() == 1) {// 无限期 //
					st.setOccurrenceCount(-1);
					st.setEndDate(null);
				} else if (pageDisplayVo.getOccur() == 2) {// 结束日期 //
					st.setOccurrenceCount(-1);
					if (pageDisplayVo.getEndDateSimple() != null && !pageDisplayVo.getEndDateSimple().equals("")) {
						st.setEndDate(bartDateFormat.parse(pageDisplayVo.getEndDateSimple()));
					}
				} else if (pageDisplayVo.getOccur() == 3) {// 次数 //
					st.setOccurrenceCount(pageDisplayVo.getOccurrenceCount());
					st.setEndDate(null);
				}
				jobInfo.setTrigger(st);
			} else if (pageDisplayVo.getMode() == 2) {// CalendarTrigger
				JobCalendarTrigger ct = new JobCalendarTrigger();
				if (pageDisplayVo.getStart() == 1) {// 立刻执行 //
					ct.setStartType(1);
					ct.setStartDate(null);
				} else {// 开始时间 //
					ct.setStartType(2);
					if (pageDisplayVo.getStartDate() != null && !pageDisplayVo.getStartDate().equals("")) {
						ct.setStartDate(bartDateFormat.parse(pageDisplayVo.getStartDate()));
					}
				}
				ct.setId(pageDisplayVo.getTriggerId());
				ct.setVersion(pageDisplayVo.getTriggerVersion());
				if (pageDisplayVo.getEndDateCalendar() != null && !pageDisplayVo.getEndDateCalendar().equals("")) {
					ct.setEndDate(bartDateFormat.parse(pageDisplayVo.getEndDateCalendar()));
				}
				ct.setMinutes(pageDisplayVo.getMinutes());
				ct.setHours(pageDisplayVo.getHours());
				ct.setDaysType(pageDisplayVo.getDays());
				ct.setMonths(ConversionUtil.arrayToString(pageDisplayVo.getMonths()));

				if (pageDisplayVo.getDays() == 1) {// 每天 //
					ct.setWeekDays(null);
					ct.setMonthDays(null);
				} else if (pageDisplayVo.getDays() == 2) {// 一周内 //
					ct.setWeekDays(ConversionUtil.arrayToString(pageDisplayVo.getWeekDays()));
					ct.setMonthDays(null);
				} else if (pageDisplayVo.getDays() == 3) {// 一个月内 //
					ct.setWeekDays(null);
					ct.setMonthDays(ConversionUtil.arrayToString(pageDisplayVo.getMonthDays()));
				}
				jobInfo.setTrigger(ct);
			}
			return jobInfo;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new JobInfo();
	}
	
	/**
	 * 如果调度器中有参数值与原参数的默认值不相等,则使用调度器中的参数值
	 * 
	 * @param alqcJobParameters
	 *            调度器参数集合
	 * @param pageShowParams
	 *            页面原参数集合
	 * @return 页面原参数集合
	 */
	public static List<PageShowParam> coversionParameterFromPage(List<EwcmsJobParameter> alqcJobParameters, List<PageShowParam> pageShowParams) {
		for (PageShowParam pageShowParam : pageShowParams) {
			Long paramId = pageShowParam.getId();
			String enName = pageShowParam.getEnName();
			String pageValue = pageShowParam.getDefaultValue();
			for (EwcmsJobParameter alqcJobParameter : alqcJobParameters) {
				if (paramId.intValue() == alqcJobParameter.getParameter().getId().intValue() && enName.equals(alqcJobParameter.getParameter().getEnName())) {
					String value = alqcJobParameter.getParameterValue();
					if (value != null && !value.equals(pageValue)) {
						pageShowParam.setDefaultValue(value);
						break;
					}
				}
			}
		}
		return pageShowParams;
	}
	
	/**
	 * 
	 * @param ewcmsJobParameters
	 * @param parameters
	 * @return
	 */
	public static Set<EwcmsJobParameter> pageToJob(Set<EwcmsJobParameter> ewcmsJobParameters,Set<Parameter> parameters,HttpServletRequest request){
		EwcmsJobParameter jobParameter = null;
		if (!parameters.isEmpty()) {
			if (ewcmsJobParameters.isEmpty()){
				ewcmsJobParameters = new LinkedHashSet<EwcmsJobParameter>();
				for (Parameter parameter : parameters) {
					String value = (String) request.getParameter(parameter.getEnName());
	
					jobParameter = new EwcmsJobParameter();
	
					jobParameter.setParameter(parameter);
					jobParameter.setParameterValue(value);
	
					ewcmsJobParameters.add(jobParameter);
				}
			}else{
				for (Parameter parameter : parameters){
					String enName = parameter.getEnName();
					String value = (String) request.getParameter(parameter.getEnName());
					Boolean mate = false;
					for (EwcmsJobParameter alqcJobParameter : ewcmsJobParameters){
						String jobEnName = alqcJobParameter.getParameter().getEnName();
						if (enName.equals(jobEnName)){
							alqcJobParameter.setParameterValue(value);
							mate = true;
							break;
						}
					}
					if (!mate){
						jobParameter = new EwcmsJobParameter();
						
						jobParameter.setParameter(parameter);
						jobParameter.setParameterValue(value);
		
						ewcmsJobParameters.add(jobParameter);
					}
				}
			}
		}else{
			ewcmsJobParameters = new LinkedHashSet<EwcmsJobParameter>();
		}
		return ewcmsJobParameters;
	}

}
