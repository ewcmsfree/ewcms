/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */
package com.ewcms.content.particular.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 企业基本信息
 * 
 * <ul>
 * <li>id:编号</li>
 * <li>name:企业名称</li> 
 * <li>publishingSector:发布部门</li>
 * <li>published:发布时间</li>
 * <li>content:工商年检结果</li>
 * <li>yyzzzch:营业执照注册号</li> 
 * <li>yyzzdjjg:营业执照登记机关</li> 
 * <li>frdb:法人代表</li> 
 * <li>clrq:成立日期</li>
 * <li>jyfw:经营范围</li> 
 * <li>zzjgdjjg:组织机构登记机关</li> 
 * <li>zzjgdm:组织机构代码</li> 
 * <li>qyrx:企业类型</li> 
 * <li>zzzb:注册资本</li> 
 * <li>sjzzzb:实缴注册资本</li> 
 * <li>jyqx:经营期限</li> 
 * <li>zs:住所</li> 
 * <li>dense:所属密级</li>
 * <li>channelId:专栏编号</li>
 * 
 * @author wuzhijun
 */
@Entity
@Table(name = "particular_enterprise_basic")
@SequenceGenerator(name = "seq_particular_enterprise_basic", sequenceName = "seq_particular_enterprise_basic_id", allocationSize = 1)
public class EnterpriseBasic implements Serializable {

	private static final long serialVersionUID = 3884541781798152400L;

	@Id
    @GeneratedValue(generator = "seq_particular_enterprise_basic",strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	private Long id;
	@Column(name = "name", length = 200)
	private String name;
	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REFRESH}, targetEntity = PublishingSector.class)
	@JoinColumn(name="publishing_sector", referencedColumnName = "code")
	private PublishingSector publishingSector;
	private Date published;
	@OneToOne(cascade = { CascadeType.ALL }, targetEntity = ParticularContent.class)
	@JoinColumn(name = "content_id")
	private ParticularContent content;
	@Column(name = "yyzzzch", length = 200, nullable = false, unique = true)
	private String yyzzzch;
	@Column(name = "yyzzdjjg", length = 200)
	private String yyzzdjjg;
	@Column(name = "frdb", length = 200)
	private String frdb;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "clrq")
	private Date clrq;
	@Column(name = "jyfw", length = 200)
	private String jyfw;
	@Column(name = "zzjgdjjg", length = 200)
	private String zzjgdjjg;
	@Column(name = "zzjgdm", length = 200)
	private String zzjgdm;
	@Column(name = "qyrx", length = 200)
	private String qyrx;
	@Column(name = "zzzb", length = 200)
	private String zzzb;
	@Column(name = "sjzzzb", length = 200)
	private String sjzzzb;
	@Column(name = "jyqx", length = 200)
	private String jyqx;
	@Column(name = "zs", length = 200)
	private String zs;
	@Column(name = "dense")
	@Enumerated(EnumType.STRING)	
	private Dense dense;
	@Column(name = "channel_id")
	private Integer channelId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PublishingSector getPublishingSector() {
		return publishingSector;
	}

	public void setPublishingSector(PublishingSector publishingSector) {
		this.publishingSector = publishingSector;
	}

	public Date getPublished() {
		return published;
	}

	public void setPublished(Date published) {
		this.published = published;
	}

	public ParticularContent getContent() {
		return content;
	}

	public void setContent(ParticularContent content) {
		this.content = content;
	}

	public String getYyzzzch() {
		return yyzzzch;
	}

	public void setYyzzzch(String yyzzzch) {
		this.yyzzzch = yyzzzch;
	}

	public String getYyzzdjjg() {
		return yyzzdjjg;
	}

	public void setYyzzdjjg(String yyzzdjjg) {
		this.yyzzdjjg = yyzzdjjg;
	}

	public String getFrdb() {
		return frdb;
	}

	public void setFrdb(String frdb) {
		this.frdb = frdb;
	}

	public Date getClrq() {
		return clrq;
	}

	public void setClrq(Date clrq) {
		this.clrq = clrq;
	}

	public String getJyfw() {
		return jyfw;
	}

	public void setJyfw(String jyfw) {
		this.jyfw = jyfw;
	}

	public String getZzjgdjjg() {
		return zzjgdjjg;
	}

	public void setZzjgdjjg(String zzjgdjjg) {
		this.zzjgdjjg = zzjgdjjg;
	}

	public String getZzjgdm() {
		return zzjgdm;
	}

	public void setZzjgdm(String zzjgdm) {
		this.zzjgdm = zzjgdm;
	}

	public String getQyrx() {
		return qyrx;
	}

	public void setQyrx(String qyrx) {
		this.qyrx = qyrx;
	}

	public String getZzzb() {
		return zzzb;
	}

	public void setZzzb(String zzzb) {
		this.zzzb = zzzb;
	}

	public String getSjzzzb() {
		return sjzzzb;
	}

	public void setSjzzzb(String sjzzzb) {
		this.sjzzzb = sjzzzb;
	}

	public String getJyqx() {
		return jyqx;
	}

	public void setJyqx(String jyqx) {
		this.jyqx = jyqx;
	}

	public String getZs() {
		return zs;
	}

	public void setZs(String zs) {
		this.zs = zs;
	}

	public Dense getDense() {
		return dense;
	}

	public String getDenseDescription(){
		if (dense != null){
			return dense.getDescription();
		}else{
			return Dense.GENERAL.getDescription();
		}
	}
	
	public void setDense(Dense dense) {
		this.dense = dense;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
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
		EnterpriseBasic other = (EnterpriseBasic) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
