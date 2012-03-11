/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.scheduling.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author 吴智俊
 */
@Entity
@Table(name = "job_class")
@SequenceGenerator(name = "seq_job_class", sequenceName = "seq_job_class_id", allocationSize = 1)
public class JobClass implements Serializable {

	private static final long serialVersionUID = -4428638031352721701L;

	@Id
    @GeneratedValue(generator = "seq_job_class",strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	@Column(name = "classname", nullable = false)
	private String className;
	@Column(name = "classentity", nullable = false, unique = true)
	private String classEntity;
	@Column(name = "description", columnDefinition = "text")
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClassEntity() {
		return classEntity;
	}

	public void setClassEntity(String classEntity) {
		this.classEntity = classEntity;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final JobClass other = (JobClass) obj;
		if (this.id != other.id
				&& (this.id == null || !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 41 * hash + (this.id != null ? this.id.hashCode() : 0);
		return hash;
	}
}
