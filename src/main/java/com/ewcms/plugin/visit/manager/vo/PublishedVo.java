/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.visit.manager.vo;

import java.io.Serializable;

/**
 * 发布统计
 * 
 * @author wu_zhijun
 * 
 */
public class PublishedVo implements Serializable {

	private static final long serialVersionUID = 8593307266126719253L;

	private String organName;
	private String userName;
	private String name;
	private Long draftCount = 0L;
	private Long reeditCount = 0L;
	private Long reviewCount = 0L;
	private Long releaseCount = 0L;
	
	/**
	 * 人员发布统计构造函数
	 * 
	 * @param organName
	 * @param userName
	 * @param name
	 * @param draftCount
	 * @param reeditCount
	 * @param reviewCount
	 * @param releaseCount
	 */
	public PublishedVo(String organName, String userName, String name, Long draftCount, Long reeditCount, Long reviewCount, Long releaseCount) {
		super();
		this.organName = organName;
		this.userName = userName;
		this.name = name;
		this.draftCount = draftCount;
		this.reeditCount = reeditCount;
		this.reviewCount = reviewCount;
		this.releaseCount = releaseCount;
	}
	
	/**
	 * 栏目发布统计/组织机构发布统计构造函数
	 * 
	 * @param name
	 * @param draftCount
	 * @param reeditCount
	 * @param reviewCount
	 * @param releaseCount
	 */
	public PublishedVo(String userName, Long draftCount, Long reeditCount, Long reviewCount, Long releaseCount) {
		super();
		this.userName = userName;
		this.draftCount = draftCount;
		this.reeditCount = reeditCount;
		this.reviewCount = reviewCount;
		this.releaseCount = releaseCount;
	}
	
	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getDraftCount() {
		return draftCount;
	}

	public void setDraftCount(Long draftCount) {
		this.draftCount = draftCount;
	}

	public Long getReeditCount() {
		return reeditCount;
	}

	public void setReeditCount(Long reeditCount) {
		this.reeditCount = reeditCount;
	}

	public Long getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(Long reviewCount) {
		this.reviewCount = reviewCount;
	}

	public Long getReleaseCount() {
		return releaseCount;
	}

	public void setReleaseCount(Long releaseCount) {
		this.releaseCount = releaseCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		PublishedVo other = (PublishedVo) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
}
