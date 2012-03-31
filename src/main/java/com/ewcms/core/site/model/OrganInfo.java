/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

/**
 * 
 */
package com.ewcms.core.site.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * <li>id:机构信息编号
 * <li>address:机构地址
 * <li>postCode:机构邮编
 * <li>tranWay: 交通方式
 * <li>tel:电话
 * <li>serviceTime:上班服务时间
 * <li>describe:机构介绍
 * </ul>
 * 
 * @author 周冬初
 */
@Entity
@Table(name = "site_organinfo")
@SequenceGenerator(name = "seq_site_organinfo", sequenceName = "seq_site_organinfo_id", allocationSize = 1)
public class OrganInfo implements Serializable {

	private static final long serialVersionUID = 7342446995346166030L;
	
	@Id
    @GeneratedValue(generator = "seq_site_organinfo", strategy = GenerationType.SEQUENCE)
    private Integer id;	
    @Column(length = 200)
    private String address;
    @Column(length = 8)
    private String postCode;
    @Column()
    private String tranWay;
    @Column(length = 15)
    private String tel;
    @Column(length = 200)
    private String serviceTime;
    @Column()
    private String describe;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getTranWay() {
		return tranWay;
	}
	public void setTranWay(String tranWay) {
		this.tranWay = tranWay;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getServiceTime() {
		return serviceTime;
	}
	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
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
		OrganInfo other = (OrganInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	
}
