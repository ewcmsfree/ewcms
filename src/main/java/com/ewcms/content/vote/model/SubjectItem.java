/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.vote.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 问卷调查主题明细
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>title:选项标题</li>
 * <li>subjectItemStatus:问卷调查主题明细状态</li>
 * <li>sort:排序</li>
 * <li>content:内容</li>
 * <li>voteNumber:票数</li>
 * </ul>
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "vote_subject_item")
@SequenceGenerator(name = "seq_vote_subject_item", sequenceName = "seq_vote_subject_item_id", allocationSize = 1)
public class SubjectItem implements Serializable {

	private static final long serialVersionUID = 8605463440202838775L;

	@Id
	@GeneratedValue(generator = "seq_vote_subject_item", strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	@Column(name = "title", length = 100)
	private String title;
	@Enumerated(EnumType.STRING)
	@Column(name = "itemstatus")
	private SubjectItemStatus subjectItemStatus;
	@Column(name = "sort")
	private Long sort;
	@Column(name = "content", columnDefinition = "text")
	private String content;
	@Column(name = "votenumber")
	private Long voteNumber;
	
	public SubjectItem(){
		this.subjectItemStatus = SubjectItemStatus.CHOOSE;
		voteNumber = 0L;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public SubjectItemStatus getSubjectItemStatus() {
		return subjectItemStatus;
	}

	public void setSubjectItemStatus(SubjectItemStatus subjectItemStatus) {
		this.subjectItemStatus = subjectItemStatus;
	}

	public String getSubjectItemStatusDescription(){
		if (subjectItemStatus != null){
			return subjectItemStatus.getDescription();
		}else{
			return SubjectItemStatus.CHOOSE.getDescription();
		}
	}
	
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getVoteNumber() {
		return voteNumber;
	}

	public void setVoteNumber(Long voteNumber) {
		this.voteNumber = voteNumber;
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
		SubjectItem other = (SubjectItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
