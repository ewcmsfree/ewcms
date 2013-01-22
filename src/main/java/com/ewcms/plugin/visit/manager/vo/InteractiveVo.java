/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.plugin.visit.manager.vo;

import java.io.Serializable;

/**
 * 互动服务统计
 * 
 * @author wu_zhijun
 * 
 */
public class InteractiveVo implements Serializable {

	private static final long serialVersionUID = -1997222476378329669L;

	private String organName;
	private Long zxblCount = 0L;
	private Long zxhfCount = 0L;
	private Long zxtgCount = 0L;
	private Long zxwtgCount = 0L;
	private Long tsblCount = 0L;
	private Long tshfCount = 0L;
	private Long tstgCount = 0L;
	private Long tswtgCount = 0L;
	private Long jyblCount = 0L;
	private Long jyhfCount = 0L;
	private Long jytgCount = 0L;
	private Long jywtgCount = 0L;
	
	private Long tgCount = 0L;
	private Long wtgCount = 0L;

	/**
	 * 政民互动
	 * 
	 * @param organName
	 * @param zxblCount
	 * @param zxhfCount
	 * @param zxtgCount
	 * @param zxwtgCount
	 * @param tsblCount
	 * @param tshfCount
	 * @param tstgCount
	 * @param tswtgCount
	 * @param jyblCount
	 * @param jyhfCount
	 * @param jytgCount
	 * @param jywtgCount
	 */
	public InteractiveVo(String organName, Long zxblCount, Long zxhfCount, Long zxtgCount, Long zxwtgCount, Long tsblCount, Long tshfCount, Long tstgCount,
			Long tswtgCount, Long jyblCount, Long jyhfCount, Long jytgCount, Long jywtgCount) {
		super();
		this.organName = organName;
		this.zxblCount = zxblCount;
		this.zxhfCount = zxhfCount;
		this.zxtgCount = zxtgCount;
		this.zxwtgCount = zxwtgCount;
		this.tsblCount = tsblCount;
		this.tshfCount = tshfCount;
		this.tstgCount = tstgCount;
		this.tswtgCount = tswtgCount;
		this.jyblCount = jyblCount;
		this.jyhfCount = jyhfCount;
		this.jytgCount = jytgCount;
		this.jywtgCount = jywtgCount;
	}

	/**
	 * 网上咨询
	 * 
	 * @param organName
	 * @param tgCount
	 * @param wtgCount
	 */
	public InteractiveVo(String organName, Long tgCount, Long wtgCount) {
		super();
		this.organName = organName;
		this.tgCount = tgCount;
		this.wtgCount = wtgCount;
	}



	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public Long getZxblCount() {
		return zxblCount;
	}

	public void setZxblCount(Long zxblCount) {
		this.zxblCount = zxblCount;
	}

	public Long getZxhfCount() {
		return zxhfCount;
	}

	public void setZxhfCount(Long zxhfCount) {
		this.zxhfCount = zxhfCount;
	}

	public Long getZxtgCount() {
		return zxtgCount;
	}

	public void setZxtgCount(Long zxtgCount) {
		this.zxtgCount = zxtgCount;
	}

	public Long getZxwtgCount() {
		return zxwtgCount;
	}

	public void setZxwtgCount(Long zxwtgCount) {
		this.zxwtgCount = zxwtgCount;
	}

	public Long getTsblCount() {
		return tsblCount;
	}

	public void setTsblCount(Long tsblCount) {
		this.tsblCount = tsblCount;
	}

	public Long getTshfCount() {
		return tshfCount;
	}

	public void setTshfCount(Long tshfCount) {
		this.tshfCount = tshfCount;
	}

	public Long getTstgCount() {
		return tstgCount;
	}

	public void setTstgCount(Long tstgCount) {
		this.tstgCount = tstgCount;
	}

	public Long getTswtgCount() {
		return tswtgCount;
	}

	public void setTswtgCount(Long tswtgCount) {
		this.tswtgCount = tswtgCount;
	}

	public Long getJyblCount() {
		return jyblCount;
	}

	public void setJyblCount(Long jyblCount) {
		this.jyblCount = jyblCount;
	}

	public Long getJyhfCount() {
		return jyhfCount;
	}

	public void setJyhfCount(Long jyhfCount) {
		this.jyhfCount = jyhfCount;
	}

	public Long getJytgCount() {
		return jytgCount;
	}

	public void setJytgCount(Long jytgCount) {
		this.jytgCount = jytgCount;
	}

	public Long getJywtgCount() {
		return jywtgCount;
	}

	public void setJywtgCount(Long jywtgCount) {
		this.jywtgCount = jywtgCount;
	}

	public Long getTgCount() {
		return tgCount;
	}

	public void setTgCount(Long tgCount) {
		this.tgCount = tgCount;
	}

	public Long getWtgCount() {
		return wtgCount;
	}

	public void setWtgCount(Long wtgCount) {
		this.wtgCount = wtgCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((organName == null) ? 0 : organName.hashCode());
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
		InteractiveVo other = (InteractiveVo) obj;
		if (organName == null) {
			if (other.organName != null)
				return false;
		} else if (!organName.equals(other.organName))
			return false;
		return true;
	}
}
