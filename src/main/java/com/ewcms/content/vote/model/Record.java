/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.vote.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 用户填写问卷调查结果 
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>subjectName:主题名
 * <li>subjectValue:主题值</li>
 * <li>inputValue:填写内容</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "vote_record")
@SequenceGenerator(name = "seq_vote_record", sequenceName = "seq_vote_record_id", allocationSize = 1)
public class Record implements Serializable {

	private static final long serialVersionUID = 1681610497803424816L;

	@Id
	@GeneratedValue(generator = "seq_vote_record", strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	@Column(name = "subject_name")
	private String subjectName;
	@Column(name = "subject_value", columnDefinition = "text")
	private String subjectValue;
	@Column(name = "input_value", columnDefinition = "text")
	private String inputValue;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSubjectValue() {
		return subjectValue;
	}

	public void setSubjectValue(String subjectValue) {
		this.subjectValue = subjectValue;
	}

	public String getInputValue() {
		return inputValue;
	}

	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Record other = (Record) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
