/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.scheduling.generate.job.report.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ewcms.plugin.report.model.Parameter;

/**
 * 报表调度器所使用的参数
 * 
 * <ul>
 * <li>id:参数编号</li>
 * <li>value:参数值</li>
 * <li>parameter:org.jict.alqc.report.model.Parameter对象</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "job_report_parameter")
@SequenceGenerator(name = "seq_job_report_parameter", sequenceName = "seq_job_report_parameter_id", allocationSize = 1)
public class EwcmsJobParameter implements Serializable {

    private static final long serialVersionUID = 5822868212042814116L;
	@Id
    @GeneratedValue(generator = "seq_job_report_parameter",strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
    private Long id;
    @Column(name = "parametervalue")
    private String parameterValue;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "rep_param_id")
    private Parameter parameter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((parameter == null) ? 0 : parameter.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EwcmsJobParameter other = (EwcmsJobParameter) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (parameter == null) {
			if (other.parameter != null)
				return false;
		} else if (!parameter.equals(other.parameter))
			return false;
		return true;
	}

}
