/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.scheduling.generate.job.report;

import java.util.Calendar;
import java.util.LinkedHashMap;

import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ewcms.plugin.report.generate.factory.ChartFactoryable;
import com.ewcms.plugin.report.generate.factory.TextFactoryable;
import com.ewcms.plugin.report.manager.ReportFacable;
import com.ewcms.plugin.report.model.ChartReport;
import com.ewcms.plugin.report.model.Repository;
import com.ewcms.plugin.report.model.TextReport;
import com.ewcms.plugin.report.model.TextType;
import com.ewcms.scheduling.generate.job.BaseEwcmsExecutionJob;
import com.ewcms.scheduling.generate.job.report.model.EwcmsJobParameter;
import com.ewcms.scheduling.generate.job.report.model.EwcmsJobReport;

/**
 * 执行报表Job
 * 
 * @author 吴智俊
 */
public class EwcmsExecutionReportJob extends BaseEwcmsExecutionJob {

	private static final Logger logger = LoggerFactory.getLogger(EwcmsExecutionReportJob.class);
    
    private static final String JOB_REPORT_FACTORY = "ewcmsJobReportFac";
    private static final String REPORT_FACTORY = "reportFac";
    private static final String CHART_FACTORY = "chartFactory";
    private static final String TEXT_FACTORY = "textFactory";

    protected EwcmsJobReport jobDetails;

    protected void jobExecute() throws Exception {
    	logger.info("生成报表开始...");
    	
        jobDetails = getJobDetails();

        String outputFormat = jobDetails.getOutputFormat();
        String label = jobDetails.getLabel();
        String description = jobDetails.getDescription();
        
        if (outputFormat != null && outputFormat.length() > 0) {
            StringTokenizer tokenizerOutput = new StringTokenizer(outputFormat, ",", false);
            while (tokenizerOutput.hasMoreElements()) {
                Integer iOutput = Integer.valueOf(tokenizerOutput.nextToken());
                attachOutput(iOutput, label, description);
            }
        }else{
        	attachOutput(null, label, description);
        }
        logger.info("生成报表结束.");
    }

    protected void attachOutput(Integer iOutput, String label, String description) throws Exception {
        Set<EwcmsJobParameter> jobParams = jobDetails.getEwcmsJobParameters();
        Map<String, String> parameters = new LinkedHashMap<String, String>();
        for (EwcmsJobParameter jobParameter : jobParams) {
            String enName = jobParameter.getParameter().getEnName();
            String value = jobParameter.getParameterValue();
            parameters.put(enName, value);
        }

        TextReport textReport = jobDetails.getTextReport();
        ChartReport chartReport = jobDetails.getChartReport();

        String fileType = "";
        byte[] bytes = null;
        if (textReport != null) {
            TextFactoryable reportEngine = getTextFactory();
            TextType textEnum = conversionToEnum(iOutput);
            bytes = reportEngine.export(parameters, textReport, textEnum, null, null);
            fileType = textEnum.getDescription();
        }

        if (chartReport != null) {
            fileType = "PNG";
            ChartFactoryable chartEngine = getChartFactory();
            bytes = chartEngine.export(chartReport, parameters);
        }

        ReportFacable reportFac = getReportFac();
        Repository repository = new Repository();
        
        repository.setEntity(bytes);
        repository.setType(fileType);
        repository.setName(label);
        repository.setDescription(description);
        repository.setUpdateDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        reportFac.addRepository(repository);
    }


    protected void alqcJobClear() {
        jobDetails = null;
    }

    protected EwcmsJobReport getJobDetails() {
    	EwcmsJobReportFacable ewcmsJobReportFac = getEwcmsJobReportFac();
        JobDataMap jobDataMap = jobContext.getTrigger().getJobDataMap();

        int jobId = jobDataMap.getInt(JOB_DATA_KEY_DETAILS_ID);
        EwcmsJobReport ewcmsJobReport = ewcmsJobReportFac.getScheduledJobReport(jobId);
        return ewcmsJobReport;
    }

    protected EwcmsJobReportFacable getEwcmsJobReportFac(){
    	return (EwcmsJobReportFacable) applicationContext.getBean(JOB_REPORT_FACTORY);
    }
    
    protected ReportFacable getReportFac(){
    	return (ReportFacable) applicationContext.getBean(REPORT_FACTORY);
    }
    
    protected ChartFactoryable getChartFactory() {
        return (ChartFactoryable) applicationContext.getBean(CHART_FACTORY);
    }

    protected TextFactoryable getTextFactory() {
        return (TextFactoryable) applicationContext.getBean(TEXT_FACTORY);
    }
    
    protected TextType conversionToEnum(Integer iOutput) {
        if (iOutput.intValue() == TextType.HTML.ordinal()) {
            return TextType.HTML;
        } else if (iOutput.intValue() == TextType.PDF.ordinal()) {
            return TextType.PDF;
        } else if (iOutput.intValue() == TextType.XLS.ordinal()) {
            return TextType.XLS;
        } else if (iOutput.intValue() == TextType.RTF.ordinal()) {
            return TextType.RTF;
        } else if (iOutput.intValue() == TextType.XML.ordinal()) {
            return TextType.XML;
        } else {
            return TextType.PDF;
        }
    }

	@Override
	protected void jobClear() {
		jobDetails = null;		
	}
}
