/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.scheduling.generate.job.report.model;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.ewcms.plugin.report.model.ChartReport;
import com.ewcms.plugin.report.model.TextReport;
import com.ewcms.scheduling.model.JobInfo;

/**
 * 报表调度器任务
 * 
 * <ul>
 * <li>mail:AlqcJobMail对象</li>
 * <li>outputFormat:输出格式</li>
 * <li>ewcmsJobParameters:EwcmsJobParameter集合</li>
 * <li>report:Report对象</li>
 * <li>chart:Chart对象</li>
 * </ul>
 *
 * @author wu_zhijun
 */
@Entity
@Table(name = "job_report")
@PrimaryKeyJoinColumn(name = "info_id")
public class EwcmsJobReport extends JobInfo {

	private static final long serialVersionUID = -6943912221507320883L;
	//输出格式
    public static final Integer OUTPUT_FORMAT_HTML = 0;
    public static final Integer OUTPUT_FORMAT_PDF = 1;
    public static final Integer OUTPUT_FORMAT_XLS = 2;
    public static final Integer OUTPUT_FORMAT_RTF = 3;
    public static final Integer OUTPUT_FORMAT_XML = 4;
    
    @Column(name = "outputformat", length = 20)
    private String outputFormat;
    @OneToMany(cascade = {CascadeType.ALL}, targetEntity = EwcmsJobParameter.class, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "job_report_id")
    private Set<EwcmsJobParameter> ewcmsJobParameters;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "report_id")
    private TextReport textReport;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "chart_id")
    private ChartReport chartReport;

    public EwcmsJobReport() {
        ewcmsJobParameters = new LinkedHashSet<EwcmsJobParameter>();
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    @JsonIgnore
    public Set<EwcmsJobParameter> getEwcmsJobParameters() {
        return ewcmsJobParameters;
    }

    public void setEwcmsJobParameters(Set<EwcmsJobParameter> ewcmsJobParameters) {
        this.ewcmsJobParameters = ewcmsJobParameters;
    }

    @JsonIgnore
    public TextReport getTextReport() {
        return textReport;
    }

    public void setTextReport(TextReport textReport) {
        this.textReport = textReport;
    }

    @JsonIgnore
    public ChartReport getChartReport() {
        return chartReport;
    }

    public void setChartReport(ChartReport chartReport) {
        this.chartReport = chartReport;
    }
}
